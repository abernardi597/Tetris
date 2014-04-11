package packed;

import tetris.Board;

import java.lang.reflect.Method;

public class Heuristics {

    public final static double[] coefficients = new double[] {
            8, 6, 4, -3, 3, -8, 7, 6, 0, 5, -6, 2, -1, 0, -2, 4, 0, 1
};

    //Included in provided source
    /**
     * @return The sum of the number of consecutive horizontal holes in each column
     */
    public static double hue00ConsecHorzHoles(Board board) {
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
    /**
     * @return The average height of each column
     */
    public static double hue01HeightAvg(Board board) {
        int sumHeight =0;
        //count the holes and sum up the heights
        for(int x=0; x < board.getWidth(); x++)
        {
            final int colHeight = board.getColumnHeight(x);
            sumHeight += colHeight;
        }

        return ((double)sumHeight/board.getWidth());
    }
    /**
     * @return The height of the tallest column
     */
    public static double hue02HeightMax(Board board) {
        return board.getMaxHeight();
    }
    /**
     * @return The difference between the highest and lowest columns
     */
    public static double hue03HeightMinMax(Board board) {
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
    /**
     * @return The variance of the heights of the columns. This is how spread out the values are
     */
    public static double hue04HeightVar(Board board) {
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
    /**
     * @return The standard deviation of the board. This is the square root of the variance
     */
    public static double hue05HeightStdDev(Board board) {
        return Math.sqrt(hue04HeightVar(board));
    }
    /**
     * @return The total number of holes in the board
     */
    public static double hue06SimpleHoles(Board board) {
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
    /**
     * @return The variance between the variances of each column of three
     */
    public static double hue07ThreeVariance(Board board) {
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
    /**
     * @return The number of columns with at leas one block in it
     */
    public static double hue08Trough(Board board) {
        int troughCount = 0;

        for(int x = 0; x < board.getWidth(); x++)
        {
            int height = board.getColumnHeight(x);
            //store the hieght for each coloumn
            if(height > 0 && board.getGrid(x, height-1))
                troughCount++;
        }
        return troughCount;
    }
    /**
     * @return A weighted value that treats holes near the bottom as a higher penalty than holes near the top
     */
    public static double hue09WeightedHoles(Board board) {
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
    /**
     * @return The number of holes in the column with the most holes
     */
    public static double hue10RowsWithHolesInMostHoledColumn(Board board) {
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
    /**
     * @return The Returns the largest number of holes per column. For each column, the number of "holes" (the number of
     * empty spaces) is counted. The largest sum is returned.
     */
    public static double hue11AverageSquaredTroughHeight(Board board) {
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
    /**
     * @return The sum of the number of blocks (including holes) above the first "hole" in each column
     */
    public static double hue12BlocksAboveHoles(Board board) {
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

    /**
     * @return The number of rows with only one hole
     */
    public static double hue13RowsWithOneHole(Board board) {
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

    /**
     * @return The count of the places that form "stairs," as in having a slope of one
     */
    public static double hue14Stairs(Board board) {
        int init = 0;
        int stairs = 0;

        for(int x = 0; x < board.getWidth() - 1; x++) {
            int dif = Math.abs(board.getColumnHeight(x) - board.getColumnHeight(x + 1));
            if(dif == 1)
                stairs++;

        }
        return stairs;
    }

    /**
     * @return How "rough" the board is, or how uneven it looks
     */
    public static double hue15Roughness(Board board) {
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

    /**
     * @return A weighted value based on how the column compares to the shortest and tallest columns
     */
    public static double hue16WeightedHeight(Board board) {
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

    /**
     * @return The number of spots where only a vertical "I" piece will fit
     */
    public static double hue17Valleys(Board board) {
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

    /**
     * Uses reflection to call each heuristic in this class, then multiply them by their coefficient.
     * Because it's more fun than just calling them all.
     * @return The final rating of the Board
     */
    public static double total(Board board) {
        double sum = 0;
        int heuIndex = 0;
        for(Method method : Heuristics.class.getMethods()) {
            //Don't want to call this method again
            if(method.getName().startsWith("hue"))
                try {
                    //Call the evaluation method
                    double val = (Double) method.invoke(null, board);
                    //Coefficient index is in the name.
                    String co = method.getName().substring(3, 5);
                    //Multiply by the coefficient, then add it to the running total
                    sum += val * coefficients[Integer.valueOf(co)];
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return sum;
    }

}
