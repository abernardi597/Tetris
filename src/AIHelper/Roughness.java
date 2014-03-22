package AIHelper;

import tetris.Board;

/**
 * Created by Rflaxman on 3/22/2014.
 */
public class Roughness extends BoardRater {


    double rate(Board board)
    {

        int diffHeight = 0;

        for (int x = 0; x < (board.getWidth() - 1); x++)
        {
            //Get difference between each row
            int slopeHeight = Math.abs(board.getColumnHeight(x) - board.getColumnHeight(x + 1));
            diffHeight += slopeHeight;
        }
        //Return sum of difference
        return (diffHeight);
    }

}
