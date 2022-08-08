import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

 
public class PongPanel extends JPanel implements ActionListener, KeyListener { //the panel which the game run in (not window)
	
	private static final Color BACKGROUND_COLOUR = Color.BLACK;
	private static final int TIMER_DELAY = 5; //ms?
	
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
	}
	
	private void update() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(20, 20, 100, 100);
	}
  
 }