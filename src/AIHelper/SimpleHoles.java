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
public class SimpleHoles extends BoardRater {

	double rate(Board board) {	
		int holes = 0;
		// Count the holes, and sum up the heights
		for (int x=0; x<board.getWidth(); x++) {
			final int colHeight = board.getColumnHeight(x);
			
			int y = colHeight - 2;	// addr of first possible hole
			
			while (y>=0) {
				if  (!board.getGrid(x,y)) {
					holes++;
				}
				y--;
			}
		}
		return holes;
	}
    
}
