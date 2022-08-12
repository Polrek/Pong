import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BasicStroke;
import java.awt.Stroke;
import javax.swing.Timer;
import javax.swing.JPanel;
 
public class PongPanel extends JPanel implements ActionListener, KeyListener { //the panel which the game runs in (different from the window)
	//variables for PongPanel class
	private static final Color BACKGROUND_COLOUR = Color.WHITE;
	private static final int TIMER_DELAY = 5; //ms?
	private final static int BALL_MOVEMENT_SPEED = 2;
	private final static int POINTS_TO_WIN = 11;
	private final static int SCORE_TEXT_X = 100;
	private final static int SCORE_TEXT_Y = 100;
	private final static int SCORE_FONT_SIZE = 50;
	private final static String SCORE_FONT_FAMILY = "Serif";
	private final static int WINNER_TEXT_X = 200;
	private final static int WINNER_TEXT_Y = 200;
	private final static int WINNER_FONT_SIZE = 40;
	private final static String WINNER_FONT_FAMILY = "Serif";
	private final static String WINNER_TEXT = "WIN!";
	int player1Score = 0;
	int player2Score = 0;
	Player gameWinner;
	Ball ball; //ball variable (of type Ball (from the Ball class))
	GameState gameState = GameState.INITIALISING; //gamestate variable of type GameState //not sure if uppercase or not
	Paddle paddle1, paddle2;
	public PongPanel() { //this is the PongPanel constructor
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer(TIMER_DELAY, this);
		timer.start();
		addKeyListener(this); //enables keyPressed() and keyReleased() to work
		setFocusable(true); //jPanel must have focus to receive keyboard events
	}

	public void createObjects() {
		ball = new Ball(getWidth(), getHeight()); //this creates the ball object using the width and height properties that are part of the Ball class.
		paddle1 = new Paddle(Player.ONE, getWidth(), getHeight());  //creates paddle1 (not paint!)
		paddle2 = new Paddle(Player.TWO, getWidth(), getHeight()); //creates paddle2 (not paint!)
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if(gameState != GameState.INITIALISING) {
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
			paintWinner(g);
		}
		
	}

	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
	    g2d.setStroke(dashed);
	    g2d.setPaint(Color.WHITE);
	    g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
	    g2d.dispose();
		
	}

	private void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}

	private void paintScores(Graphics g) {
	     Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
	     String leftScore = Integer.toString(player1Score); //converts the player 1 score to a string and assigns to variable
	     String rightScore = Integer.toString(player2Score); //converts the player 2 score to a string and assigns to variable
	     g.setFont(scoreFont);
	     g.drawString(leftScore, SCORE_TEXT_X, SCORE_TEXT_Y); //draws the leftScore in location (x,y)
	     g.drawString(rightScore, getWidth()-SCORE_TEXT_X, SCORE_TEXT_Y); //draws the rightScore in location (pongPanelWidth-x,y) [think mirror image about the dotted center line]
	}

	private void paintWinner(Graphics g) {
		 if (gameWinner != null) {
			 Font winnerFont = new Font(WINNER_FONT_FAMILY, Font.BOLD, WINNER_FONT_SIZE);
			 g.setFont(winnerFont);
			 int xPositionWin = getWidth() / 2; //mid point of pong panel
			 if (gameWinner == Player.ONE) {
				 xPositionWin -= WINNER_TEXT_X; //moves the x coord to the left of centre by winner_text_x px
			 }
			 else if (gameWinner == Player.TWO) {
				 xPositionWin += WINNER_TEXT_X; //moves the x coord to the right of centre by winner_text_x px
			 }
			g.drawString(WINNER_TEXT, xPositionWin, WINNER_TEXT_Y);	
		 }
		 
	 }

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_UP) { //detects the keyboard up arrow being pressed
			paddle2.setYVelocity(-1);
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) { //detects the keyboard down arrow being pressed
			paddle2.setYVelocity(1);
		}
		if (event.getKeyCode() == KeyEvent.VK_W) { //detects the W key being pressed
			paddle1.setYVelocity(-1);
		}
		else if (event.getKeyCode() == KeyEvent.VK_S) { //detects the S key being pressed
			paddle1.setYVelocity(1);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		 if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.setYVelocity(0);
		 }
		 if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
	        paddle1.setYVelocity(0);
		 }
	}

	private void update() {
			switch(gameState) {
	        case INITIALISING: {
	           createObjects();
	           gameState = GameState.PLAYING;
	           ball.setXVelocity(BALL_MOVEMENT_SPEED);
	           ball.setYVelocity(BALL_MOVEMENT_SPEED);
	           break;
	        }
	        case PLAYING: {
	        	moveObject(paddle1); 
	        	moveObject(paddle2);
	        	moveObject(ball); //moves the ball
	        	checkWallBounce(); //checks of the ball hit the wall
	        	checkPaddleBounce(); // check if the ball intersects with the paddle/hits it
	        	checkWin(); //checks if any player score equals the points to win value (has won the game)
	            break;
	       }
	       case GAMEOVER: {
	           break;
	       }
	   }
		}

	@Override //
	public void actionPerformed(ActionEvent event) { //as we have TIMER, this method will get called on a loop
		update();
		repaint(); //updates the graphics
	}
	
	private void checkPaddleBounce() {
		if(ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
	          ball.setXVelocity(BALL_MOVEMENT_SPEED);
	}
		if(ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
	          ball.setXVelocity(-BALL_MOVEMENT_SPEED);
	      }
	
	}

	private void resetBall() {
		ball.resetToInitialPosition();
	}

	private void checkWallBounce() { //ball has hit the LHS of the screen
		if (ball.getXPosition() <= 0) {
			ball.setXVelocity(-ball.getXVelocity()); //reverses the velocity of the ball "bounce"
			addScore(Player.TWO);
			resetBall(); //if we don't reverse the velocity before this, the ball would always start moving the direction of the loser
		}
		else if (ball.getXPosition() >= getWidth() - ball.getWidth()) { //ball has hit the RHS of the screen
			ball.setXVelocity(-ball.getXVelocity());
			addScore(Player.ONE);
			resetBall(); //if we don't reverse the velocity before this, the ball would always start moving the direction of the loser
		}
		if (ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() - ball.getHeight()) { //ball has hit the top or bottom of the screen
			ball.setYVelocity(-ball.getYVelocity());
		}
		
	}

	private void moveObject(Sprite obj) {
		obj.setXPosition(obj.getXPosition() + obj.getXVelocity(),getWidth());
		obj.setYPosition(obj.getYPosition() + obj.getYVelocity(),getHeight());
	}
	
	private void addScore(Player player) {
        if (player == Player.ONE) {
            player1Score++;
        } 
        else if (player == Player.TWO) {
            player2Score++;
        }
    }
	
	 private void checkWin() {
         if (player1Score >= POINTS_TO_WIN) {
             gameWinner = Player.ONE;
             gameState = GameState.GAMEOVER;
         } 
         else if (player2Score >= POINTS_TO_WIN) {
             gameWinner = Player.TWO;
             gameState = GameState.GAMEOVER;
         }
     }
	  
 }