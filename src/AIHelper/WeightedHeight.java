package AIHelper;

import tetris.Board;

/**
 * Created by Rflaxman on 3/22/2014.
 */

    public class WeightedHeight extends BoardRater
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

            double weightedHeightCount = 0.0;
            int[] heights = new int[board.getWidth()];

            for(int x=0; x < board.getWidth(); x++)
            {
                heights[x] = board.getColumnHeight(x);
                int y = heights[x] - 1;
                while(y>=0)
                {
                    if(board.getGrid(x, y))
                        weightedHeightCount+=(double)(maxHeight)/(double)(maxHeight-y);
                    y--;
                }
            }

            return weightedHeightCount;

        }
    }
