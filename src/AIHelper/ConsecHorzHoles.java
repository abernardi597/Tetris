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
public class ConsecHorzHoles extends BoardRater {

  double rate(Board board) {
		final int width = board.getWidth();
		final int maxHeight = board.getMaxHeight();
		
		int holes = 0;
		
		// Count the holes, and sum up the heights
		for (int x=0; x<width; x++) {
			final int colHeight = board.getColumnHeight(x);
			int y = colHeight - 2;	// addr of first possible hole
			
			boolean consecutiveHole = false;
			while (y>=0) {
				if  (!board.getGrid(x,y)) {
					if (!consecutiveHole) {
						holes++;
						consecutiveHole = true;
					}
				} else {
					consecutiveHole = false;
				}
				y--;
			}
		}
		
		return holes;
	}
    
}
