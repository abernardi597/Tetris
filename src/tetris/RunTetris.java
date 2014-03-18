/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author justinbehymer
 */

public class RunTetris extends JComponent {

	
	protected PiecePanel nextPiecePanel; // Displays the nextPiece for the player to see
	
	// Controls
	protected JLabel countLabel;
	protected JLabel timeLabel;
	protected JButton startButton;
	protected JButton stopButton;
	protected javax.swing.Timer timer;
	protected JSlider speed;
        protected JLabel rowsClearedLabel;
        protected JSlider Diffcult;
        protected JLabel difficulty;  
	
        // milliseconds per tick
	public final int DELAY = 400;	
	
        // used to measure elapsed time
	protected long startTime;	
	
	TetrisController tc;
        

	RunTetris(int width, int height) {
		super();

		setPreferredSize(new Dimension(width, height));
		
		tc = new TetrisController();
		
		// Create the Timer object and have it send
		timer = new javax.swing.Timer(DELAY, new ActionListener() 
                {
			public void actionPerformed(ActionEvent e) 
                        {
				tick(TetrisController.DOWN);
			}
		});
	}
        
       
	void tick(int verb) {
		tc.tick(verb);

		
		if (!tc.gameOn) {
			stopGame();
		}
		
		countLabel.setText(Integer.toString(tc.count) + " Moves");
                rowsClearedLabel.setText(tc.rowsCleared + " Rows Cleared");
                //difficulty.setText(Integer.toString(tc.difficulty));
		nextPiecePanel.setPiece(tc.nextPiece);    
		
		repaint();
	}

	/**
	 Sets the internal state and starts the timer
	 so the game is happening.
	*/
	public void startGame() {
		tc.startGame();
			
		// draw the new board state once
		repaint();
		
		enableButtons();
		timeLabel.setText(" ");
		
		timer.start();
		startTime = System.currentTimeMillis();
                               
                // LEFT
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
					tick(TetrisController.LEFT);
				}
			}, "left", KeyStroke.getKeyStroke('4'), WHEN_IN_FOCUSED_WINDOW
		);
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
					tick(TetrisController.LEFT);
				}
			}, "left", KeyStroke.getKeyStroke('a'), WHEN_IN_FOCUSED_WINDOW
		);		
		// RIGHT
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
					tick(TetrisController.RIGHT);
				}
			}, "right", KeyStroke.getKeyStroke('6'), WHEN_IN_FOCUSED_WINDOW
		);
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
					tick(TetrisController.RIGHT);
				}
			}, "right", KeyStroke.getKeyStroke('d'), WHEN_IN_FOCUSED_WINDOW
		);		
		// ROTATE	
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
					tick(TetrisController.ROTATE);
				}
			}, "rotate", KeyStroke.getKeyStroke('5'), WHEN_IN_FOCUSED_WINDOW
		);
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
					tick(TetrisController.ROTATE);
				}
			}, "rotate", KeyStroke.getKeyStroke('w'), WHEN_IN_FOCUSED_WINDOW
		);		
		// DROP
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
					tick(TetrisController.DROP);
				}
			}, "drop", KeyStroke.getKeyStroke('0'), WHEN_IN_FOCUSED_WINDOW
		);
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
					tick(TetrisController.DROP);
				}
			}, "drop", KeyStroke.getKeyStroke('s'), WHEN_IN_FOCUSED_WINDOW
		);		
		
	}
	

	
	
	
	/**
	 Sets the enabling of the start/stop buttons
	 based on the gameOn state.
	*/
	private void enableButtons() {
	    startButton.setEnabled(!tc.gameOn);
	    stopButton.setEnabled(tc.gameOn);
	}
	
	/**
	 Stops the game.
	*/
	public void stopGame() {
		tc.gameOn = false;
		enableButtons();
		timer.stop();
		
		long delta = (System.currentTimeMillis() - startTime)/10;
		timeLabel.setText(Double.toString(delta/100.0) + " seconds");                
                // LEFT
		registerKeyboardAction(new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) {
}
			}, "left", KeyStroke.getKeyStroke('4'), WHEN_IN_FOCUSED_WINDOW
		);
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
				}
			}, "left", KeyStroke.getKeyStroke('a'), WHEN_IN_FOCUSED_WINDOW
		);				
		// RIGHT
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
				}
			}, "right", KeyStroke.getKeyStroke('6'), WHEN_IN_FOCUSED_WINDOW
		);
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
				}
			}, "right", KeyStroke.getKeyStroke('d'), WHEN_IN_FOCUSED_WINDOW
		);				
		// ROTATE	
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
				}
			}, "rotate", KeyStroke.getKeyStroke('5'), WHEN_IN_FOCUSED_WINDOW
		);
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
				}
			}, "rotate", KeyStroke.getKeyStroke('w'), WHEN_IN_FOCUSED_WINDOW
		);
		// DROP
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
				}
			}, "drop", KeyStroke.getKeyStroke('0'), WHEN_IN_FOCUSED_WINDOW
		);
		registerKeyboardAction(
			new ActionListener() 
                        {
				public void actionPerformed(ActionEvent e) 
                                {
				}
			}, "drop", KeyStroke.getKeyStroke('s'), WHEN_IN_FOCUSED_WINDOW
		);	
	}
	
	
	
	
	// width in pixels of a block
	private final float dX() {
		return( ((float)(getWidth()-2)) / tc.board.getWidth() );
	}

	// height in pixels of a block
	private final float dY() {
		return( ((float)(getHeight()-2)) / tc.board.getHeight() );
	}
	
	// the x pixel coord of the left side of a block
	private final int xPixel(int x) {
		return(Math.round(1 + (x * dX())));
	}
	
	// the y pixel coord of the top of a block
	private final int yPixel(int y) {
		return(Math.round(getHeight() -1 - (y+1)*dY()));
	}


	/**
	 Draws the current board with a 1 pixel border
	 around the whole thing. 
	*/
	public void paintComponent(Graphics g) {
		
		// Draw a rect around the whole thing
		g.fillRect(0, 0, getWidth()-1, getHeight()-1);
		
		
		// Draw the line separating the top
		int spacerY = yPixel(tc.displayBoard.getHeight() - TetrisController.TOP_SPACE - 1);
		g.setColor(Color.WHITE);
		g.drawLine(0, spacerY, getWidth()-1, spacerY);		
		
		// Factor a few things out to help the optimizer
		final int dx = Math.round(dX()-2);
		final int dy = Math.round(dY()-2);
		final int bWidth = tc.displayBoard.getWidth();

		int x, y;
		// Loop through and draw all the blocks
		// left-right, bottom-top
		for (x=0; x<bWidth; x++) {
			int left = xPixel(x);	// the left pixel
			
			// draw from 0 up to the col height
			final int yHeight = tc.displayBoard.getColumnHeight(x);
			for (y=0; y<yHeight; y++) 
                        {
				if (tc.displayBoard.getGrid(x, y)) 
                                {
					g.setColor(tc.displayBoard.colorGrid[x][y]);
					g.fillRect(left+1, yPixel(y)+1, dx, dy);
					
				}
			}
		}
	}
	
	
	/**
	 Updates the timer to reflect the current setting of the 
	 speed slider.
	*/
	public void updateTimer() 
        {
		double value = ((double)speed.getValue())/speed.getMaximum();
		timer.setDelay((int)(DELAY - value*DELAY));
	}
	
	
	/**
	 *Creates the panel of UI controls.
	*/
	public java.awt.Container createControlPanel() 
        {
		java.awt.Container panel = Box.createVerticalBox();
		
		nextPiecePanel = new PiecePanel();
		panel.add(nextPiecePanel);
		
		// COUNT
		countLabel = new JLabel("0" + " Moves");
		panel.add(countLabel);
                
                
                //ROWS Cleared
                rowsClearedLabel = new JLabel("0" + " Rows CLeared");
                panel.add(rowsClearedLabel);
                
                difficulty = new JLabel();
                panel.add(difficulty);
		
		// TIME 
		timeLabel = new JLabel(" ");
		panel.add(timeLabel);

		panel.add(Box.createVerticalStrut(12));
		
		// START button
		startButton = new JButton("Start");
		panel.add(startButton);
		startButton.addActionListener( new ActionListener() 
                {
			public void actionPerformed(ActionEvent e) 
                        {
				startGame();
			}
		});
                
		
		// STOP button
		stopButton = new JButton("Stop");
		panel.add(stopButton);
		stopButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopGame();
			}
		});
                	
		enableButtons();
		   
		JPanel row = new JPanel();
		
		// SPEED slider
		panel.add(Box.createVerticalStrut(12));
		row.add(new JLabel("Speed:"));
		speed = new JSlider(0, DELAY, 75);	// min, max, current
		speed.setPreferredSize(new Dimension(100,15));
               		
		updateTimer();
		row.add(speed);
		
		panel.add(row);
		speed.addChangeListener( new ChangeListener()
                {
			// when the slider changes, sync the timer to its value
			public void stateChanged(ChangeEvent e) 
                        {
				updateTimer();
			}
           
		});
	
		return(panel);
	}
	

	
	/**
	 Creates a Window
	*/
	public static void main(String[] args)
	
	{
		final JFrame frame = new JFrame("TETRIS CSC");
		JComponent container = (JComponent)frame.getContentPane();
		container.setLayout(new BorderLayout());
                
        // Set the metal look and feel
        try 
        {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName() );
        }
        catch (Exception ignored) {}
		
		final int pixels = 20;
		RunTetris tetris = new RunTetrisAI(TetrisController.WIDTH*pixels+2, (TetrisController.HEIGHT+TetrisController.TOP_SPACE)*pixels+2);	
		
		container.add(tetris, BorderLayout.CENTER);
		
		Container panel = tetris.createControlPanel();
                
               
		
		// Add the quit button last so it's at the bottom
		panel.add(Box.createVerticalStrut(12));
		JButton quit = new JButton("Quit");
		panel.add(quit);
		quit.addActionListener( new ActionListener() 
                {
			public void actionPerformed(ActionEvent e) 
                        {
				System.exit(0);
			}
		});
		
		
		container.add(panel, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);

		// Quit on window close
		frame.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			}
		);
                        
	}
        
}

	