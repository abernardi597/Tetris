/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import AIHelper.BoardRater;
import AIHelper.FinalRater;

/**
 *
 * @author justinbehymer
 */
public class ITLPAI implements AI 
{
    
BoardRater boardRater = new FinalRater();
    
public Move bestMove(Board board, Piece piece, Piece nextPiece, int limitHeight) 
{
		double bestScore = 1e20;
		int bestX = 0;
		int bestY = 0;
		Piece bestPiece = piece;
		Piece current = piece;

		// loop through all the rotations
		do {
			final int yBound = limitHeight - current.getHeight() + 1;
			final int xBound = board.getWidth() - current.getWidth() + 1;

			// For current rotation, try all the possible columns
			for (int x = 0; x < xBound; x++) {
				int y = board.dropHeight(current, x);
				// piece does not stick up too far
				if ((y < yBound) && board.canPlace(current, x, y)) {
					Board testBoard = new Board(board);
					testBoard.place(current, x, y);
					testBoard.clearRows();

					double score = boardRater.rateBoard(testBoard);

					if (score < bestScore) {
						bestScore = score;
						bestX = x;
						bestY = y;
						bestPiece = current;
					}
				}
			}

			current = current.nextRotation();
		} while (current != piece);

		Move move = new Move();
		move.x = bestX;
		move.y = bestY;
		move.piece = bestPiece;
		return (move);
}

    @Override
    public void setRater(BoardRater r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
