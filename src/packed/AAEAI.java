package packed;

import AIHelper.BoardRater;
import tetris.AI;
import tetris.Board;
import tetris.Move;
import tetris.Piece;

public class AAEAI implements AI {

    public Move bestMove(Board board, Piece piece, Piece nextPiece, int limitHeight) {
        double bestScore = Double.MAX_VALUE;
        //Set default x and y to -1 to check when
        int bestX = -1;
        int bestY = -1;
        //Store the best and current orientations of the piece
        Piece bestPiece = piece;
        Piece current = piece;

        //Loop through all the rotations
        do {
            final int yBound = limitHeight - current.getHeight() + 1;
            final int xBound = board.getWidth() - current.getWidth() + 1;

            //For current rotation, try all the possible columns
            for (int x = 0; x < xBound; x++) {
                int y = board.dropHeight(current, x);
                //Height check. If no piece placement is possible, then the x and y returned will be -1
                if ((y < yBound) && board.canPlace(current, x, y)) {
                    Board testBoard = new Board(board);
                    //Place the current piece on the board
                    testBoard.place(current, x, y);
                    int rowsCleared = testBoard.clearRows();
                    //If the next piece is known, check the best move with the next piece on top of this piece
                    //for combo moves
                    if(nextPiece != null) {
                        Move m = bestMove(testBoard, nextPiece, null, limitHeight);
                        testBoard.place(m);
                        rowsCleared += testBoard.clearRows();
                    }
                    //Use our heuristics to rate the board
                    double score = Heuristics.total(testBoard) - rowsCleared * 16;
                    //Check to see if we have a new best score
                    if (score < bestScore) {
                        bestScore = score;
                        bestX = x;
                        bestY = y;
                        bestPiece = current;
                    }
                }
            }
            //Set the next rotation for checking
            current = current.nextRotation();
        } while (current != piece); //Terminate when we hit the original orientation: we've checked them all
        //Construct the move
        Move move = new Move();
        move.x = bestX;
        move.y = bestY;
        move.piece = bestPiece;
        return (move);
    }

    public void setRater(BoardRater rater) {
        //Unused
        throw new RuntimeException("We don't do that here at AAE/GHAMAS");
    }
}
