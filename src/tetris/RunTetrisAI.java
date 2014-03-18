/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import javax.swing.Box;

/**
 *
 * @author justinbehymer
 */
public class RunTetrisAI extends RunTetris {
    
    
	private static final long serialVersionUID = 1L;

	private AI mBrain = new ITLPAI();
	private Move mMove;
	protected javax.swing.Timer timerAI;
	int current_count = -1;

	/** Creates new JBrainTetris */
	public RunTetrisAI(int width, int height) {
		super(width, height);
		
	}
	
	public void startGame() {
		super.startGame();
		// Create the Timer object and have it send
		//timerAI.start();
	}
	
	public void stopGame() {
		super.stopGame();
		//timerAI.stop();
	}
	
	public void tick(int verb) {
		if (tickAI()) {
			super.tick(verb);
		}
	}

	public boolean tickAI() {
		if (current_count != tc.count) {
			current_count = tc.count;
			mMove = mBrain.bestMove(new Board(tc.board), tc.currentMove.piece, tc.nextPiece, tc.board.getHeight()-TetrisController.TOP_SPACE);
		}
		
		if (!tc.currentMove.piece.equals(mMove.piece)) { 
			super.tick(TetrisController.ROTATE);
		} else if (tc.currentMove.x != mMove.x) {
			super.tick(((tc.currentMove.x < mMove.x) ? TetrisController.RIGHT : TetrisController.LEFT));
		} else {
			return true;
		}
		return false;
	}


	public java.awt.Container createControlPanel() {
		java.awt.Container panel2 = Box.createVerticalBox();
		panel2 = super.createControlPanel();


		return (panel2);
	}
}
    

