package packed;

import tetris.Board;

import java.lang.reflect.Method;

public class Heuristics {

    public static double[] coefficients = new double[] {
            8, 3, 4, -3, 3, -8, 7, 6, 0, 5, -6, 4, -1, 3, -2, 4, 0, 1
    };

    //Included in provided source
    public static double hueConsecHorzHoles(Board board) {
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
    public static double hueHeightAvg(Board board) {
        int sumHeight =0;
        //count the holes and sum up the heights
        for(int x=0; x < board.getWidth(); x++)
        {
            final int colHeight = board.getColumnHeight(x);
            sumHeight += colHeight;
        }

        return ((double)sumHeight/board.getWidth());
    }
    public static double hueHeightMax(Board board) {
        return board.getMaxHeight();
    }
    public static double hueHeightMinMax(Board board) {
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
    public static double hueHeightVar(Board board) {
        int sumHeight = 0;

        //count the holes and sum up the height
        for(int x=0; x <board.getWidth(); x++)
        {
            final int colHeight = board.getColumnHeight(x);
            sumHeight += colHeight;
        }

        double avgHeight = ((double)sumHeight)/board.getWidth();

        //first variance
        int varisum = 0;
        for(int x = 0; x < board.getWidth(); x++)
        {
            final int colHeight = board.getColumnHeight(x);
            varisum += Math.pow(colHeight - avgHeight, 2);
        }

        return varisum / board.getWidth();
    }
    public static double hueHeightStdDev(Board board) {
        return Math.sqrt(hueHeightVar(board));
    }
    public static double hueSimpleHoles(Board board) {
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
    public static double hueThreeVariance(Board board) {
        int w = board.getWidth();
        double runningVarianceSum = 0.0;
        for(int i=0; i<w-2; i++) {
            double  h0 = (double)board.getColumnHeight(i),
                    h1 = (double)board.getColumnHeight(i+1),
                    h2 = (double)board.getColumnHeight(i+2);
            double  m = (h0+h1+h2)/3.0;
            h0-=m;  h1-=m;  h2-=m;
            h0*=h0; h1*=h1; h2*=h2;
            runningVarianceSum+=(h0+h1+h2)/3.0;
        }
        return runningVarianceSum / (double)(w-3);
    }
    public static double hueTrough(Board board) {
        int[] through = new int [board.getWidth()];
        int troughCount = 0;

        for(int x = 0; x < board.getWidth(); x++)
        {
            int height = board.getColumnHeight(x);
            //store the hieght for each coloumn
            if(height > 0 && board.getGrid(x, height-1))
            {
                through[x]++;
                troughCount++;
            }
        }
        return troughCount;
    }
    public static double hueWeightedHoles(Board board) {
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
    public static double hueRowsWithHolesInMostHoledColumn(Board board) {
        //count the holes and sum up the heights
        int mostHolesInAnyColumn = 0;
        for(int x=0; x<board.getWidth(); x++)
        {
            final int colHeight = board.getColumnHeight(x);
            int y = colHeight -2;
            int holes = 0;

            while(y>=0)
            {
                if(!board.getGrid(x, y))
                {
                    holes++;
                }
                y--;
            }

            if(mostHolesInAnyColumn<holes)mostHolesInAnyColumn = holes;
        }

        return mostHolesInAnyColumn;
    }
    public static double hueAverageSquaredTroughHeight(Board board) {
        // Count the holes, and sum up the heights
        int mostHolesInAnyColumn = 0;
        for (int x=0; x<board.getWidth(); x++)
        {
            final int colHeight = board.getColumnHeight(x);

            int y = colHeight-2;
            int holes = 0;

            while (y>=0)
            {
                if  (!board.getGrid(x,y))
                {
                    holes++;
                }
                y--;
            }

            if(mostHolesInAnyColumn<holes) mostHolesInAnyColumn = holes;
        }
        return mostHolesInAnyColumn;
    }
    public static double hueBlocksAboveHoles(Board board) {
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

    //Ours
    public static double hueRowsWithOneHole(Board board) {
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
    public static double hueStairs(Board board) {
        int init = 0;
        int stairs = 0;

        for(int x = 0; x < board.getWidth() - 1; x++) {
            int dif = Math.abs(board.getColumnHeight(x) - board.getColumnHeight(x + 1));
            if(dif == 1)
                stairs++;

        }
        return stairs;
    }
    public static double hueRoughness(Board board) {
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
    public static double hueWeightedHeight(Board board) {
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
    public static double hueValleys(Board board) {
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

    public static double total(Board board) {
        double sum = 0;
        int heuIndex = 0;
        for(Method method : Heuristics.class.getMethods()) {
            //Don't want to call this method again
            if(method.getName().startsWith("hue"))
                try {
                    double val = (Double) method.invoke(null, board);
                    sum += val * coefficients[heuIndex++];
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return sum;
    }

}
