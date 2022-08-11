import java.awt.Color;
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
	//variables for PongPanel cass
	private static final Color BACKGROUND_COLOUR = Color.BLACK;
	private static final int TIMER_DELAY = 5; //ms?
	private final static int BALL_MOVEMENT_SPEED = 2;
	Ball ball; //ball variable (of type Ball (from the Ball class))
	GameState gameState = GameState.INITIALISING; //gamestate variable of type GameState //not sure if uppercase or not
	Paddle paddle1, paddle2;
	private final static int POINTS_TO_WIN = 3;
	int player1Score = 0, player2Score = 0;
	Player gameWinner;
		
	public PongPanel() { //this is the PongPanel constructor
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer(TIMER_DELAY, this);
		timer.start();
		addKeyListener(this); //enables keyPressed() and keyReleased() to work
		setFocusable(true); //jPanel must have focus to receive keyboard events
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

	@Override //
	public void actionPerformed(ActionEvent event) { //as we have TIMER, this method will get called on a loop
		update();
		repaint(); //updates the graphics
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
            break;
       }
       case GAMEOVER: {
           break;
       }
   }
	}
	
	private void moveObject(Sprite obj) {
		obj.setXPosition(obj.getXPosition() + obj.getXVelocity(),getWidth());
		obj.setYPosition(obj.getYPosition() + obj.getYVelocity(),getHeight());
	}
	
	private void checkWallBounce() { //ball has hit the LHS of the screen
		if (ball.getXPosition() <= 0) {
			ball.setXVelocity(-ball.getXVelocity()); //reverses the velocity of the ball "bounce"
			resetBall(); //if we don't reverse the velocity before this, the ball would always start moving the direction of the loser
		}
		else if (ball.getXPosition() >= getWidth() - ball.getWidth()) { //ball has hit the RHS of the screen
			ball.setXVelocity(-ball.getXVelocity());
			resetBall(); //if we don't reverse the velocity before this, the ball would always start moving the direction of the loser
		}
		if (ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() - ball.getHeight()) { //ball has hit the top or bottom of the screen
			ball.setYVelocity(-ball.getYVelocity());
		}
	}
	
	private void resetBall() {
		ball.resetToInitialPosition();
	}
	
	private void checkPaddleBounce() {
		if(ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
	          ball.setXVelocity(BALL_MOVEMENT_SPEED);
	}
		if(ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
	          ball.setXVelocity(-BALL_MOVEMENT_SPEED);
	      }
	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if(gameState != GameState.INITIALISING) {
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
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
	
	public void createObjects() {
		ball = new Ball(getWidth(), getHeight()); //this creates the ball object using the width and height properties that are part of the Ball class.
		paddle1 = new Paddle(Player.ONE, getWidth(), getHeight());  //creates paddle1 (not paint!)
		paddle2 = new Paddle(Player.TWO, getWidth(), getHeight()); //creates paddle2 (not paint!)
	}
	
	  
 }