import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {
	
	private int xPosition, yPosition;
	private int xVelocity, yVelocity;
	private int width, height;
	private Color colour;
	private int initialXPosition, initialYPosition;

//getters
	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public int getXVelocity() {
		return xVelocity;
	}

	public int getYVelocity() {
		return yVelocity;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColour() {
		return colour;
	}

//setters
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	//we can use duplicate method names here only if the variables are different. This is called Method Overloading
	
	public void setXPosition(int newX, int panelWidth) {
	     xPosition = newX;
	     if (xPosition < 0) { //collision with LHS
	    	 xPosition = 0;
	     }
	     else if (xPosition + width > panelWidth) { //collision with RHS
	    	 xPosition = panelWidth - width;
	     }
	}
	
	public void setYPosition(int newY, int panelHeight) {
	     yPosition = newY;
	     if (yPosition < 0) { //collision with bottom
	    	 yPosition = 0;
	     }
	     else if (yPosition + height > panelHeight) {
	    	 yPosition = panelHeight - height; //collision with top
	     }
	}
	
	public void setxVelocity(int xVelocity) { //sets the L-R velocity of the sprite
		this.xVelocity = xVelocity; 
	}

	public void setyVelocity(int yVelocity) { //sets the up-down velocity of the sprite
		this.yVelocity = yVelocity;
	}

	public void setWidth(int width) {
		this.width = width; //sets the width of the sprite
	}

	public void setHeight(int height) {
		this.height = height; //sets the height of the sprite
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	public void setInitialPosition(int initialX, int initialY ) {
		initialXPosition = initialX;
		initialYPosition = initialY;
	}
	
	public void resetToInitialPosition() {
		setXPosition(initialXPosition); //calls the method to set the x position with the initialXPosition value
		setYPosition(initialYPosition); //calls the method to set the y position with the initialYPosition value
	}

//other methods
	public Rectangle getRectangle() { //draws a rectangle using initial coordinates and defining the width and height
		return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());
	}
}
