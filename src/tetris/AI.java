/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import AIHelper.BoardRater;

/**
 *
 * @author justinbehymer
 */
public interface AI {
    
        
    public Move bestMove(Board board, Piece piece, Piece nextPiece, int limitHeight);
    
    public void setRater(BoardRater r);

    
}
