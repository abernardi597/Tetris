# Tetris #
Repo for the 2014 GE Case Study

## Notes ##
Keep in mind these notes are a result of my analysis, so they could be wrong
### Included AI ###
- Is consulted on generation of every piece, and then the resulting Move is used to determine the movement of the pieces,
    where it distributes the moves over ticks in order of ROTATION, and then TRANSLATION
- Iterates through every rotation, then every column
- Uses a BoardRater to rate the resultant board, then chooses the Move with the lowest (best) score

#### Heuristics ####
This section will elaborate on the various heuristics the included AI uses to rate a given tetris board.

#### Average Squared Trough Height ####
Returns the largest number of holes per column.
For each column, the number of "holes" (the number of empty spaces) is counted. The largest sum is returned.

#### Blocks Above Holes ####
Returns the sum of the number of blocks (including holes) above the first "hole" in each column

#### Consec Horz Holes ####
Returns the sum of the number of consecutive horizontal holes in each column

#### Height Avg ####
Returns the average height of each column

#### Height Max ####
Returns the height of the tallest column

#### Height Min Max ####
Returns the difference between the highest and lowest columns

#### Height Standard Dev ####
Returns the standard deviation of the board. This is the square root of the variance

#### Height Var ####
Returns the variance of the heights of the columns. This is how spread out the values are

#### Rows With Holes in Most Holed Column ####
Returns the number of holes in the column with the most holes

#### Simple Holes ####
Returns the total number of holes in the board

#### Three Variance ####
Returns the variance between the variances of each column of three

#### Through ####
Returns the number of columns with at least one block in it

#### Weighted Holes ####
Returns a weighted value that treats holes near the bottom as a higher penalty than holes near the top

### Move ###
- Move.piece should be the orientation we want to rotate the piece
- Move.x and Move.y should be the position of the center of the piece, however only Move.x is used to translate the piece

### Piece ###
- The actual Piece object contains methods for retrieving various data about the piece
- A Piece with the same shape, yet different rotations are not equal as a rotation of the piece actually rearranges the
    body (or the points that makeup the piece)
- There is a method for finding how tall a given column of the piece is (getSkirt())
- Consequently, Piece.getWidth() == Piece.getSkirt().length
