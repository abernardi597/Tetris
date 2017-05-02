package genetic;

import AIHelper.FinalRater;
import tetris.ITLPAI;

import javax.swing.*;
import java.awt.*;

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
     * The total number of rows cleared.
     */
    private int totalRows;
    /**
     * The number of trials that have been completed.
     */
    private int completedTrials;
    /**
     * The time that this test started.
     */
    private long startTime;

    //Swing components
    private final JLabel timeLabel;
    private final JLabel countLabel;
    private final JLabel rowLabel;
    private final JLabel averageLabel;
    private final JLabel trialLabel;
    private final JLabel threadLabel;

    /**
     * Makes a new FitnessTest with the subject.
     * @param sub The Offspring to test
     */
    public FitnessTest(Offspring sub) {
        subject = sub;
        tetris = new MiniTetris(new ITLPAI());
        //Set up the UI
        setLayout(new BorderLayout());
        timeLabel = new JLabel();
        countLabel = new JLabel();
        rowLabel = new JLabel();
        averageLabel = new JLabel();
        trialLabel = new JLabel();
        threadLabel = new JLabel();
        JPanel labelPane = new JPanel();
        labelPane.setLayout(new BoxLayout(labelPane, BoxLayout.PAGE_AXIS));
        labelPane.add(threadLabel);
        labelPane.add(timeLabel);
        labelPane.add(countLabel);
        labelPane.add(rowLabel);
        labelPane.add(averageLabel);
        labelPane.add(trialLabel);
        add(tetris, BorderLayout.WEST);
        add(labelPane, BorderLayout.NORTH);
        updateLabels();
    }

    /**
     * Sets this FitnessTest to its initial state
     */
    public void clear() {
        done = false;
        rowsCleared = 0;
        totalRows = 0;
        completedTrials = 0;
    }

    /**
     * Updates the threadLabel with the given thread number.
     * @param thread The thread number that the threadLabel should reflect
     */
    public void updateThreadLabel(int thread) {
        threadLabel.setText("Thread " + thread);
    }

    /**
     * Updates the labels.
     */
    public void updateLabels() {
        timeLabel.setText(getTimeString(runtime()));
        countLabel.setText("Pieces: " + tetris.getCount());
        rowLabel.setText("Rows:   " + rowsCleared);
        averageLabel.setText("Avg:    " + Math.round((float) totalRows / Math.min(numberTrials, completedTrials + 1)));
        trialLabel.setText("Trial:     " + (completedTrials + 1));
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
     * @return How long, in milliseconds, this particular test has been running
     */
    public long runtime() {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * @return The number of trials completed in the test.
     */
    public int getCompletedTrials() {
        return completedTrials;
    }

    public void run() {
        startTime = System.currentTimeMillis();
        clear();
        //Run the tests
        tetris.setRater(new FinalRater(subject.traits));
        while(completedTrials < numberTrials) {
            tetris.startGame();
            while(tetris.running()) {
                tetris.tick();
                int cleared = tetris.getRowsCleared();
                totalRows += cleared - rowsCleared;
                rowsCleared = cleared;
                updateLabels();
            }
            rowsCleared = 0;
            completedTrials++;
        }
        subject.fitness = rowsCleared = Math.round((float) totalRows / numberTrials);
        done = true;
    }

    /**
     * Creates a String that shows the time in the format "HH:mm:ss:MMMM". Unable to use SimpleDateFormatter because the
     * hour rolls over after 24.
     * @param time The time in milliseconds
     * @return The formatted String.
     */
    private static String getTimeString(long time) {
        int ms = (int) time % 1000;
        time /= 1000;
        int secs = (int) time % 60;
        time /= 60;
        int mins = (int) time % 60;
        time /= 60;
        int hrs = (int) time;
        return String.format("%d:%02d:%02d:%03d", hrs, mins, secs, ms);
    }
}
