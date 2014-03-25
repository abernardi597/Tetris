/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package AIHelper;

import tetris.Board;

/**
 * Mason is cool
 */
public class RowsWithOneHole extends BoardRater {

	double rate(Board board) {
        int rowswithone=0;
		for (int y=0; y<board.getHeight(); y++) {
            int holes=0;
            for (int x=0; x<board.getWidth(); x++) {
                if(!board.getGrid(x,y)){
                    holes++;
                }
            }
            if(holes==1){
                rowswithone++;
            }
		}
		return rowswithone;
	}
    
}
