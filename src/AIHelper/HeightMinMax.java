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
public class HeightMinMax extends BoardRater 
{
    double rate(Board board)
    {
        int maxHeight = 0;
        int minHeight = board.getHeight();
        
        for(int x = 0; x < board.getWidth(); x++)
        {
            int height = board.getColumnHeight(x);
            
            if(height > maxHeight)
                //record the height of highest coloumn
                maxHeight = height;
            if(height < minHeight)
                //record height of lowest coloumn
                minHeight = height;
            
        }
        
        return maxHeight - minHeight;
    }
    
}
