import java.awt.Color;

public class Paddle extends Sprite {
 public static final int PADDLE_WIDTH = 10;
 public static final int PADDLE_HEIGHT = 100;
 public static final Color PADDLE_COLOUR = Color.BLACK;
 public static final int DISTANCE_FROM_EDGE = 40;
 
//constructor
 public Paddle(Player player, int panelWidth, int panelHeight) {
	setWidth(PADDLE_WIDTH);
	setHeight(PADDLE_HEIGHT);
	setColour(PADDLE_COLOUR);
	int xPos;
	if (player == Player.ONE) {
		xPos = DISTANCE_FROM_EDGE; //sets the position of the player.one paddle to the distance from the edge
	}
	else {
		xPos = panelWidth - DISTANCE_FROM_EDGE - getWidth(); //sets the position of the player.two paddle to the width of panel - distance from edge - width
	}
	setInitialPosition(xPos, panelHeight/2 - (getHeight()/2)); //sets the initial position of the paddle to (x,y) (xPos, midpoint of height - midpoint of paddleheight)
	resetToInitialPosition();
 }
 
 
}
