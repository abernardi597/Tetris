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

### Move ###
- Move.piece should be the orientation we want to rotate the piece
- Move.x and Move.y should be the position of the center of the piece, however only Move.x is used to translate the piece

### Piece ###
- The actual Piece object contains methods for retrieving various data about the piece
- A Piece with the same shape, yet different rotations are not equal as a rotation of the piece actually rearranges the
    body (or the points that makeup the piece)
- There is a method for finding how tall a given column of the piece is (getSkirt())
- Consequently, Piece.getWidth() == Piece.getSkirt().length
