package genetic;

import AIHelper.BoardRater;
import AIHelper.FinalRater;
import tetris.Board;
import tetris.ITLPAI;
import tetris.Move;
import tetris.TetrisController;

import java.util.Arrays;
import java.util.Random;

public class Offspring {

    public static final int numTraits = 13;
    public static double mutationFrequency = 5D / numTraits;
    public static int mutationSpan = 5;
    public static int numTests = 5;

    public final double[] traits;

    public Offspring() {
        traits = new double[numTraits];
    }

    public Offspring copyInto(double[] newTraits) {
        for(int i = 0; i < numTraits; i++)
            traits[i] = newTraits[i];
        return this;
    }

    public Offspring reproduce(Offspring mate, Random random) {
        Offspring child = new Offspring();
        for(int i = 0; i < numTraits; i++) {
            double trait;
            if (random.nextBoolean())
                trait = traits[i];
            else
                trait = mate.traits[i];
            child.traits[i] = mutate(trait, random);
        }
        return child;
    }

    public static double mutate(double n, Random random) {
        if(random.nextDouble() < mutationFrequency)
            n += random.nextInt(2 * mutationSpan) - mutationSpan;
        return n;
    }

    public FitnessTest testThread(Random random) {
        return new FitnessTest(this, random);
    }

    public String toString() {
        return Arrays.toString(traits);
    }

    public static class FitnessTest implements Runnable {

        private boolean done;
        private final Offspring subject;
        private final Random random;
        private int result;

        public boolean checked;
        public boolean kill;

        public FitnessTest(Offspring sub, Random rng) {
            done = false;
            subject = sub;
            random = rng;
        }

        public boolean isDone() {
            return done;
        }

        public int getResult() {
            return result;
        }

        public void run() {
            FinalRater rater = new FinalRater(subject.traits);
            for(int i = 0; i < numTests; i++)
                result += playGame(rater);
            result /= numTests;
            done = true;
        }

        private int playGame(BoardRater rater) {
            ITLPAI mBrain = new ITLPAI();
            mBrain.setRater(rater);
            int current_count = -1;
            Move mMove = null;
            TetrisController tc = new TetrisController();
            tc.startGame(random);
            while(tc.gameOn) {
                if(kill) {
                    tc.gameOn = false;
                    break;
                }
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
            }
            return tc.rowsCleared;
        }

    }

}
