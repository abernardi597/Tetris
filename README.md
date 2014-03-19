Tetris
======

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
Returns the sum of the number of blocks above the first "hole" in each column
#### Consec Horz Holes ####

#### Height Avg ####

#### Height Max ####

#### Height Min Max ####

#### Height Standard Dev ####

#### Height Var ####

#### Rows With Holes in Most Holed Column ####

#### Simple Holes ####

#### Three Variance ####

#### Through ####

#### Weighted Holes ####

### Move ###
- Move.piece should be the orientation we want to rotate the piece
- Move.x and Move.y should be the position of the center of the piece, however only Move.x is used to translate the piece

### Piece ###
- The actual Piece object contains methods for retrieving various data about the piece
- A Piece with the same shape, yet different rotations are not equal as a rotation of the piece actually rearranges the
    body (or the points that makeup the piece)
- There is a method for finding how tall a given column of the piece is (getSkirt())
- Consequently, Piece.getWidth() == Piece.getSkirt().length
