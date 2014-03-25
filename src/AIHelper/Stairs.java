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
public class Stairs extends BoardRater {

	double rate(Board board) {
        int init = 0;
        int stairs = 0;

        for(int x = 0; x < board.getWidth() - 1; x++) {
            int dif = Math.abs(board.getColumnHeight(x) - board.getColumnHeight(x + 1));
            if(dif == 1)
                stairs++;

         }
    return stairs;
    }
}
