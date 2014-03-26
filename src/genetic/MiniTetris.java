package genetic;

import AIHelper.BoardRater;
import tetris.AI;
import tetris.Board;
import tetris.Move;
import tetris.TetrisController;

import javax.swing.*;
import java.awt.*;

public class MiniTetris extends JComponent {

    public static final Color backgroundColor = new Color(0x2B2B2B);
    public static int cellSize = 7;
    public static int cellPadding = 1;
    public static int combinedCell = cellSize + cellPadding;

    private final TetrisController tetris;
    private final AI ai;

    public MiniTetris(AI ai) {
        tetris = new TetrisController();
        this.ai = ai;
        int prefWidth = tetris.board.getWidth() * combinedCell + cellPadding;
        int prefHeight = tetris.board.getHeight() * combinedCell + cellPadding;
        setPreferredSize(new Dimension(prefWidth, prefHeight));
        //play();
    }

    public void paintComponent(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        int spaceY = TetrisController.TOP_SPACE + 1;
        spaceY *= combinedCell;
        g.setColor(Color.white);
        g.drawLine(0, spaceY, getWidth(), spaceY);
        //Draw blocks
        for(int x = 0; x < tetris.board.getWidth(); x++)
            for(int y = 0; y < tetris.board.getColumnHeight(x); y++)
                if(tetris.board.getGrid(x, y)) {
                    g.setColor(tetris.board.colorGrid[x][y]);
                    g.fillRect(cellPadding + x * combinedCell, cellPadding + (tetris.board.getHeight() - y) * combinedCell, cellSize, cellSize);
                }
    }

    public void startGame() {
        tetris.startGame();
    }

    public void tick() {
        Move bestMove = ai.bestMove(new Board(tetris.board), tetris.currentMove.piece, tetris.nextPiece, tetris.board.getHeight() - TetrisController.TOP_SPACE);
        if(bestMove.x >= 0 && bestMove.y >= 0)
            tetris.currentMove = bestMove;
        tetris.tick(TetrisController.DOWN);
        paint(getGraphics());
    }

    public boolean running() {
        return tetris.gameOn;
    }

    public void setRater(BoardRater rater) {
        ai.setRater(rater);
    }

    public int getRowsCleared() {
        return tetris.rowsCleared;
    }

    public int getCount() {
        return tetris.count;
    }
}
