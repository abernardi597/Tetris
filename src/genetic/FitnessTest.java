package genetic;

import AIHelper.BoardRater;
import AIHelper.FinalRater;
import tetris.*;

public class FitnessTest implements Runnable {

    /**
     * The number of trials to perform in a test. The
     */
    public static int numberTrials = 5;

    /**
     * The test subject for this FitnessTest.
     */
    private final Offspring subject;
    /**
     * Whether the testing is complete.
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
        clear();
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
        BoardRater rater = new FinalRater(subject.traits);
        double totalRows = 0;
        while(completedTrials < numberTrials) {
            runSingleTest(rater);
            totalRows += rowsCleared;
            rowsCleared = 0;
            completedTrials++;
        }
        subject.fitness = rowsCleared = (int) (totalRows / numberTrials);
        done = true;
    }

    private void runSingleTest(BoardRater rater) {
        AI mBrain = new ITLPAI();
        mBrain.setRater(rater);
        int current_count = -1;
        Move mMove = null;
        TetrisController tc = new TetrisController();
        tc.startGame();
        while(tc.gameOn) {
            if (current_count != tc.count) {
                current_count = tc.count;
                mMove = mBrain.bestMove(new Board(tc.board), tc.currentMove.piece, tc.nextPiece, tc.board.getHeight()-TetrisController.TOP_SPACE);
            }

            if (!tc.currentMove.piece.equals(mMove.piece)) {
                tc.tick(TetrisController.ROTATE);
            } else if (tc.currentMove.x != mMove.x) {
                tc.tick(((tc.currentMove.x < mMove.x) ? TetrisController.RIGHT : TetrisController.LEFT));
            } else {
                tc.tick(TetrisController.DOWN);
            }
            rowsCleared = tc.rowsCleared;
        }
    }

}
