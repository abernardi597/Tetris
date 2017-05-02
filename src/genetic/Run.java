package genetic;

import AIHelper.FinalRater;
import tetris.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.*;

public class Run {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Desired fitness: ");
        int fitness = scan.nextInt();
        System.out.print("Generation size: ");
        int gensize = scan.nextInt();
        System.out.print("Batch size: ");
        int batchsize = scan.nextInt();
        System.out.print("Trials: ");
        int trials = scan.nextInt();
        System.out.println("Enter dna for parent 1 (" + Offspring.numTraits + " numbers):");
        double[] parent1 = new double[Offspring.numTraits];
        for (int i = 0; i < parent1.length; ++i)
            parent1[i] = scan.nextDouble();
        System.out.println("Enter dna for parent 2 (" + Offspring.numTraits + " numbers):");
        double[] parent2 = new double[Offspring.numTraits];
        for (int i = 0; i < parent2.length; ++i)
            parent2[i] = scan.nextDouble();
        double[] dna = simulate(fitness, gensize, batchsize, trials, parent1, parent2);
        System.out.println(Arrays.toString(dna));
        String resp = "";
        while (!(resp.equals("y") || resp.equals("n"))) {
            System.out.println("Would you like to run the resultant AI? (y/n)");
            resp = scan.next();
        }
        if (resp.equals("y"))
            runGui(dna);
    }

    public static void runMiniTetris(double... d) {
        JFrame frame = new JFrame("MiniTetris");
        frame.getContentPane().setLayout(new BorderLayout());
        FitnessTest test = new Offspring().useTraits(d).tester;
        frame.add(test);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        Thread run = new Thread(test);
        run.start();
    }

    public static double[] simulate(int fitness, int genSize, int batchsize, int trials, double[] dna1, double[] dna2) {
        FitnessTest.numberTrials = trials;
        Simulation.batchSize = batchsize;
        return Simulation.runSimulation(fitness, genSize, dna1, dna2, new Random()).traits;
        // runGui(dna1);
    }

    public static void runNoGui(double... vals) {
        Offspring subject = new Offspring();
        //KNOW THIS IS HERE
        FitnessTest.numberTrials = 1;
        subject.useTraits(vals);
        new Thread(subject.tester).start();
        while(!subject.tester.isDone()) {
            waitFor(1000);
            System.out.println(subject.tester.rowsCleared());
        }
        System.out.println("LOSE");
    }

    public static void runGui(double... vals) {
        //Literally a copy-pasta from RunTetris
        final JFrame frame = new JFrame("TETRIS CSC");
        JComponent container = (JComponent)frame.getContentPane();
        container.setLayout(new BorderLayout());

        // Set the metal look and feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName() );
        }
        catch (Exception ignored) {}

        final int pixels = 20;
        //We only really care about this.
        RunTetris tetris = new RunTetrisAI(TetrisController.WIDTH*pixels+2, (TetrisController.HEIGHT+TetrisController.TOP_SPACE)*pixels+2);
        try {
            Field f = RunTetrisAI.class.getDeclaredField("mBrain");
            f.setAccessible(true);
            AI ai = (AI) f.get(tetris);
            ai.setRater(new FinalRater(vals));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        container.add(tetris, BorderLayout.CENTER);

        Container panel = tetris.createControlPanel();



        // Add the quit button last so it's at the bottom
        panel.add(Box.createVerticalStrut(12));
        JButton quit = new JButton("Quit");
        panel.add(quit);
        quit.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });


        container.add(panel, BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);

        // Quit on window close
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
    }

    /**
     * Waits for a period of time.
     * @param time The time, in milliseconds, to wait before resuming.
     */
    private static void waitFor(long time) {
        try {
            synchronized(Run.class) {
                Run.class.wait(time);
            }
        } catch (Exception ignored) {}
    }

}
