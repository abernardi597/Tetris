package genetic;

import AIHelper.FinalRater;
import tetris.ITLPAI;

import javax.swing.*;

public class FitnessTest extends JPanel implements Runnable {

    /**
     * The number of trials to perform in a test. The
     */
    public static int numberTrials = 5;

    /**
     * The test subject for this FitnessTest.
     */
    private final Offspring subject;
    /**
     * The instance of MiniTetris that this will use to test the subject.
     */
    private final MiniTetris tetris;
    /**
     * Whether the testing is complete or not.
     */
    private boolean done;
    /**
     * The number of rows cleared in the test so far, or the total number if the test is complete.
     */
    private int rowsCleared;
    /**
     * The number of trials that have been completed.
     */
    private int completedTrials;

    /**
     * Makes a new FitnessTest with the subject.
     * @param sub The Offspring to test
     */
    public FitnessTest(Offspring sub) {
        subject = sub;
        tetris = new MiniTetris(new ITLPAI());
        add(tetris);
    }

    /**
     * Sets this FitnessTest to its initial state
     */
    public void clear() {
        done = false;
        rowsCleared = 0;
        completedTrials = 0;
    }

    /**
     * @return Whether or not the test has been completed.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * @return The number of rows cleared in the current trial
     */
    public int rowsCleared() {
        return rowsCleared;
    }

    /**
     * @return The number of trials completed in the test.
     */
    public int getCompletedTrials() {
        return completedTrials;
    }

    public void run() {
        clear();
        //Run the tests
        tetris.setRater(new FinalRater(subject.traits));
        float totalRows = 0;
        while(completedTrials < numberTrials) {
            tetris.startGame();
            while(tetris.running()) {
                tetris.tick();
                rowsCleared = tetris.getRowsCleared();
            }
            totalRows += rowsCleared;
            rowsCleared = 0;
            completedTrials++;
        }
        subject.fitness = rowsCleared = Math.round(totalRows / numberTrials);
        done = true;
    }

}
