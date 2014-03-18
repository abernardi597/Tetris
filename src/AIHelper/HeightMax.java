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
public class HeightMax  extends BoardRater {
    
    double rate(Board board)
    {
        return (double) board.getMaxHeight();
        
    }
    
}
