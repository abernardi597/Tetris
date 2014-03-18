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
public class WeightedHoles extends BoardRater 
{
    double rate(Board board)
    {
        int maxHeight =0;
        int minHeight = board.getHeight();
        
        for(int x = 0; x < board.getWidth(); x++)
        {
            int height = board.getColumnHeight(x);
            if(height > maxHeight)
                maxHeight = height;
            if(height < minHeight)
                minHeight = height;
        }
        
        double weightedHoleCount = 0.0;
        int[] heights = new int[board.getWidth()];
        
        for(int x=0; x<board.getWidth(); x++)
        {
            heights[x] = board.getColumnHeight(x);
            int y = heights[x] - 2;
            while(y>=0)
            {
                if(!board.getGrid(x, y))
                    weightedHoleCount+=(double)(maxHeight-y)/(double)maxHeight;
                y--;
            }
        }
        
        return weightedHoleCount;
        
    }
}
