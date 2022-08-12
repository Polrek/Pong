import java.awt.Color;

public class Ball extends Sprite { //Ball class inherits from the sprite class
	
	private final static Color BALL_COLOUR = Color.BLACK;
	private final static int BALL_WIDTH = 25;
	private final static int BALL_HEIGHT = 25;
	
//constructor	
	public Ball(int panelWidth, int panelHeight) {
		setWidth(BALL_WIDTH); //setWidth is a method in the Sprite class (inheritance)
		setHeight(BALL_HEIGHT);
		setColour(BALL_COLOUR);
		setInitialPosition(panelWidth/2 - (getWidth()/2), panelHeight/2 - (getHeight() / 2)); //sets the ball to be in the middle of the screen (half of the panel and half of the ball diameter
		resetToInitialPosition(); //method from Sprite class which sets the ball to the specified initial position
	}
}
