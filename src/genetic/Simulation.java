package genetic;

import java.util.Random;

public class Simulation {

    private final long cutoffTime;
    private final int desiredFitness;
    private final int generationSize;

    private final Offspring[] generation;
    private long generationCount;

    public Simulation(int desired, long cutoff, int genSize, double[] first, double[] second) {
        //Initialize simulation parameters
        desiredFitness = desired;
        cutoffTime = cutoff;
        generationSize = genSize;
        //Initialize arrays
        generation = new Offspring[generationSize];
        //Initialize first parents
        generation[0] = new Offspring().copyInto(first);
        generation[1] = new Offspring().copyInto(second);
    }

    public Offspring startSimulation(Random random) {
        long startTime = System.currentTimeMillis();
        int best = 0;
        while(best < desiredFitness && System.currentTimeMillis() - startTime < cutoffTime) {
            System.out.println("Generation: " + generationCount++);
            best = runGen(generation[0], generation[1], startTime, random);
            System.out.println("Best score: " + best + " @ " + generation[0]);
            System.out.println("Collecting garbage");
            System.gc();
            System.out.println("Done");
            System.out.println("------------------------------------");
        }
        return generation[0];
    }

    private int runGen(Offspring p1, Offspring p2, long startTime, Random random) {

        //Make sure parents are included in our next generation
        generation[0] = p1;
        generation[1] = p2;
        //Make a series of Runnables that will serve as simulations for each subject
        Offspring.FitnessTest[] sims = new Offspring.FitnessTest[generationSize];
        //Make an array to track our different levels of fitness
        int[] fitnesses = new int[generationSize];
        //Create a uniform seed for the random number generator in each simulation
        long seed = random.nextLong();
        //Let's have kids, honey!
        for(int i = 0; i < generationSize; i++) {
            //Skip if either 0 or 1 because we already set the parents
            if(i > 1)
                // Create a new offspring from the recombination of the parent traits
                generation[i] = p1.reproduce(p2, random);
            // Make a simulation for the new offspring
            sims[i] = generation[i].testThread(new Random(seed));
        }
        //Start our simulations
        for(int i = 0; i < generationSize; i++)
            startThread(sims[i], i);
        System.out.println("Threads started");
        //Keep track of whether any threads are running and the best fitness levels yet
        boolean stillRunning = true;
        int best = 0, best2 = 1;
        //Keep track of starting time
        while(stillRunning) {
            stillRunning = false;
            //Check if we surpassed our time limit
            if(System.currentTimeMillis() - startTime > cutoffTime) {
                for(Offspring.FitnessTest sim : sims)
                    sim.kill = true;
                return -1;
            }
            int waitingFor = 0;
            //Check our processes to see if they are done
            for(int i = 0; i < generationSize; i++) {
                if(sims[i].isDone()) {
                    if(sims[i].checked)
                        continue;
                    //If we're done, store the result
                    fitnesses[i] = sims[i].getResult();
                    //We could have a new high score!
                    if(fitnesses[i] >= fitnesses[best]) {
                        best2 = best;
                        best = i;
                    }
                    //Don't want to check this again
                    sims[i].checked = true;
                } else {
                    stillRunning = true;
                    waitingFor++;
                }
            }
            //Sleep a little to give the simulations time to work
            synchronized(this) {
                try {
                    wait(1000);
                    System.out.println("Waiting for " + waitingFor + " games...");
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }
        //Put the best in the first two spots in our generation array to use them as parents next generation
        Offspring newP1 = generation[best];
        Offspring newP2 = generation[best2];
        generation[0] = newP1;
        generation[1] = newP2;
        return fitnesses[best];
    }

    /**
     * Creates a new Thread in which to run the FitnessTest, then runs it as a daemon
     */
    private void startThread(Offspring.FitnessTest fitnessTest, int number) {
        Thread t = new Thread(fitnessTest);
        t.setDaemon(true);
        t.setName(String.format("F%d: Subject %d", generationCount, number));
        t.start();
    }

}
