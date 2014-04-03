package AIHelper;

import tetris.Board;

/**
 * Created by Rflaxman on 3/22/2014.
 */
public class Valleys extends BoardRater {


double rate(Board board)
 {

      int vallyNum = 0;

      for (int x = 0; x < board.getWidth() - 1;x++)
      {
          int leftHeight;
          if(x - 1 < 0)
              leftHeight = board.getHeight();
          else
            leftHeight = board.getColumnHeight(x - 1);
          int rightHeight;
          if(x + 1 > board.getWidth())
              rightHeight = board.getHeight();
          else
              rightHeight = board.getColumnHeight(x + 1);


          if(board.getColumnHeight(x) <= (rightHeight - 3))
               if(board.getColumnHeight(x) <= (leftHeight - 3))
                    vallyNum += 1;

      }

           return vallyNum;




 }
}