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

	private static final Color BACKGROUND_COLOUR = Color.BLACK;
	private static final int TIMER_DELAY = 5; //ms?
	
	Ball ball; //ball variable (of type Ball (from the Ball class))
	GameState gameState = GameState.INITIALISING; //gamestate variable of type GameState //not sure if uppercase or not
	Paddle paddle1, paddle2;
		
	public PongPanel() { //this is the PongPanel constructor
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer(TIMER_DELAY, this);
		timer.start();
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		
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
            break;
        }
        case PLAYING: {
            break;
       }
       case GAMEOVER: {
           break;
       }
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
		paddle1 = new Paddle(Player.ONE, getWidth(), getHeight());
		paddle2 = new Paddle(Player.TWO, getWidth(), getHeight());
	}
	
	  
 }