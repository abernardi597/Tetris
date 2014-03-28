package genetic;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    /**
     * The number of FitnessTests that should be run simultaneously
     */
    public static int batchSize = 5;
    /**
     * In the selection process, if the less fit of the two "winners" has a fitness less than this percentage of the more
     * fit, then both parents will become the more fit parent.
     */
    public static double parentPercentageOverride = .75;
    /**
     * The DecimalFormat for the time (output to console).
     */
    public static DecimalFormat formatter = new DecimalFormat("#.##");

    /**
     * Runs the genetic algorithm to generate a Offspring that has a fitness level higher than <tt>desiredFitness</tt>.
     * @param desiredFitness The goal of the algorithm
     * @param generationSize The size of each generation
     * @param traits1 The traits to use as a parent for the first generation
     * @param traits2 The traits to use as the other parent for the first generation
     * @param random The Random to use for random stuff
     * @return The Offspring that has surpassed the <tt>desiredFitness</tt>
     */
    public static Offspring runSimulation(int desiredFitness, int generationSize, double[] traits1, double[] traits2, Random random) {
        //Swing stuff
        JFrame frame = new JFrame("Simulation");
        frame.getContentPane().setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Offspring[] generation = new Offspring[generationSize];
        //Set our initial generation
        generation[0] = new Offspring().useTraits(traits1);
        generation[1] = new Offspring().useTraits(traits2);
        System.out.println("Beginning Genetic Algorithm");
        System.out.println("--------------------------------------------------------------");
        int gens = 0;
        long startTime = System.currentTimeMillis();
        while(generation[0].fitness < desiredFitness) {
            System.out.println("Generation " + gens++);
            System.out.println("\tParents:");
            for(int i = 0; i < 2; i++)
                System.out.println("\t\t" + generation[i]);
            populate(generation, generation[0], generation[1], random);
            System.out.println("\tGeneration Populated!");
            System.out.println("\tTesting Offspring...");
            calculateGenerationFitness(generation, frame);
            generation = sort(generation);
            naturallySelect(generation);
            System.out.println("\tDone in " + formatter.format((System.currentTimeMillis() - startTime) / 1000D) + "s");
            System.out.println("--------------------------------------------------------------");
            System.gc();
        }
        return generation[0];
    }

    /**
     * Calculates the fitness of each individual in the generation by putting it through a series of Tetris games. The
     * average number of rows cleared is the fitness.
     * @param generation The generation to compute
     * @param frame The JFrame to render the tests to
     */
    private static void calculateGenerationFitness(Offspring[] generation, JFrame frame) {
        int numDone = 0;
        int lastNumDone = -1;
        int index = 0;
        int highest = 0;
        ArrayList<Integer> running = new ArrayList<Integer>();
        while(numDone < generation.length) {

            //Check running processes
            for(int i = 0; i < running.size(); i++) {
                int subIndex = running.get(i);
                Offspring sub = generation[subIndex];
                if(sub.tester.isDone()) {
                    System.out.print("\t\t\t");
                    if(sub.fitness > highest) {
                        highest = sub.fitness;
                        System.out.print("New Best: ");
                    } else
                        System.out.print("Finished: ");
                    String time = formatter.format(sub.tester.runtime() / 1000D);
                    System.out.println(String.format("%d in %ss (Thread %d)", sub.fitness, time, subIndex));
                    frame.remove(sub.tester);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    running.remove(i);
                    numDone++;
                }
            }
            //Guarantee that at most batchSize threads are running
            while(running.size() < batchSize && index < generation.length) {
                generation[index].tester.updateThreadLabel(index);
                constructThread(generation[index]).start();
                running.add(index);
                frame.add(generation[index].tester);
                frame.pack();
                frame.setLocationRelativeTo(null);
                index++;
            }
            if(numDone >= generation.length)
                break;
            if(lastNumDone != numDone)
                System.out.println("\t\t" + (generation.length - numDone) + " threads remaining");
            lastNumDone = numDone;
            doWait(1000);
        }
    }

    /**
     * Uses the Offspring's FitnessTest to create a Thread.
     * @param offspring The Offspring to use
     * @return The Thread that was constructed
     */
    private static Thread constructThread(Offspring offspring) {
        Thread t = new Thread(offspring.tester);
        t.setDaemon(true);
        return t;
    }

    /**
     * Waits for a period of time.
     * @param time The time, in milliseconds, to wait before resuming.
     */
    private static void doWait(long time) {
        try {
            synchronized(Simulation.class) {
                Simulation.class.wait(time);
            }
        } catch (Exception ignored) {}
    }

    /**
     * Populates the empty (null or Offspring.clear)
     * @param generation The generation to populate
     * @param parent1 One parent
     * @param parent2 The other parent
     * @param random The Random to use for reproduction
     */
    private static void populate(Offspring[] generation, Offspring parent1, Offspring parent2, Random random) {
        for(int i = 0; i < generation.length; i++) {
            if(generation[i] == null)
                generation[i] = new Offspring();
            if(generation[i].clear)
                generation[i].useTraits(Offspring.mate(parent1, parent2, random));
        }
    }

    /**
     * Removes the "unfit" from a generation.
     * @param generation The generation to cleanse
     */
    private static void naturallySelect(Offspring[] generation) {
        int length = generation.length - 2;
        for(int i = 2 + length / 3; i < generation.length; i++)
            generation[i].clear();
        double fit1 = generation[0].fitness;
        double fit2 = generation[1].fitness;
        if(fit2/fit1 < parentPercentageOverride) {
            generation[1].clear().useTraits(generation[0].traits);
            generation[1].fitness = generation[0].fitness;
        }
    }

    /**
     * Sorts a generation in order of decreasing fitness
     * @param generation The generation to sort
     * @return a copy of the array that has been sorted
     */
    private static Offspring[] sort(Offspring[] generation) {
        //Find the highest fitness in generation, then add it to the results and remove it from generation
        Offspring[] result = new Offspring[generation.length];
        for(int i = 0; i < result.length; i++) {
            int highestIndex = 0;
            int highest = -1;
            for (int i1 = 0; i1 < generation.length; i1++)
                if(generation[i1] != null && generation[i1].fitness > highest) {
                    highestIndex = i1;
                    highest = generation[i1].fitness;
                }
            result[i] = generation[highestIndex];
            generation[highestIndex] = null;
        }
        return result;
    }

}
