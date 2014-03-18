/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AIHelper;

import tetris.Board;

/**
 *
 * @author justinbehymer
 */
public class HeightAvg extends BoardRater {
    
    double rate(Board board)
    {
        int sumHeight =0;
        //count the holes and sum up the heights
        for(int x=0; x < board.getWidth(); x++)
        {
            final int colHeight = board.getColumnHeight(x);
            sumHeight += colHeight;
        }
        
        return ((double)sumHeight/board.getWidth());
    }
    
}
