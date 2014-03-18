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
public class BlocksAboveHoles extends BoardRater {

double rate(Board board) 
{
    int w=board.getWidth(), blocksAboveHoles = 0;
    for(int x=0; x<w; x++) 
    {
      int blocksAboveHoleThisColumn = 0;
      boolean hitHoleYet = false;
      for(int i=board.getColumnHeight(x)-1; i>=0; i--) 
      {
        if(!board.getGrid(x,i)) hitHoleYet = true;
        blocksAboveHoleThisColumn += hitHoleYet?0:1;
      }
      
      if(!hitHoleYet) blocksAboveHoleThisColumn = 0;
      blocksAboveHoles+=blocksAboveHoleThisColumn;
    }
    return blocksAboveHoles;
}
    
}
