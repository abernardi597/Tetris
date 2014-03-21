package genetic;

import AIHelper.FinalRater;
import tetris.AI;
import tetris.RunTetris;
import tetris.RunTetrisAI;
import tetris.TetrisController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;

public class Run {

    public static void main(String[] args) {

        double[] dna1 = new double[Offspring.numTraits];
        double[] dna2 = new double[Offspring.numTraits];
        for(int i = 0; i < Offspring.numTraits; i++)
            dna1[i] = dna2[i] = 0;
        //System.out.println(Simulation.runSimulation(5000, 10, dna1, dna2, new Random()));
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
