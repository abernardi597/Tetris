package genetic;

import AIHelper.FinalRater;
import tetris.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Scanner;

public class Run {

    public static void main(String[] args) {
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

    public static void simulateThenGui() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Desired Fitness: ");
        int fitness = scan.nextInt();
        System.out.print("Generation Size: ");
        int genSize = scan.nextInt();
        System.out.print("Number of Trials: ");
        FitnessTest.numberTrials = scan.nextInt();
        double[] dna1 = new double[Offspring.numTraits];
        double[] dna2 = new double[Offspring.numTraits];
        for(int i = 0; i < dna1.length; i++)
            dna1[i] = scan.nextDouble();
        for(int i = 0; i < dna2.length; i++)
            dna2[i] = scan.nextDouble();
        dna1 = Simulation.runSimulation(fitness, genSize, dna1, dna2, new Random()).traits;
        runGui(dna1);
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
