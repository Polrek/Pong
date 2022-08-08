import javax.swing.JFrame;
public class Pong extends JFrame { //the window which the game runs in
	
	private final static String WINDOW_TITLE = "Pong";
	private final static int WINDOW_WIDTH = 800;
	private final static int WINDOW_HEIGHT = 600;

	//this is the constructor
	public Pong() {
    	setTitle(WINDOW_TITLE);
    	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    	setResizable(false);
    	add(new PongPanel());
    	setVisible(true);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	
	public static void main(String[] args) {
		//this is to make sure the GUI gets created within the Event Dispatch thread because the swing GUI components are not threadsafe
		javax.swing.SwingUtilities.invokeLater(new Runnable() { //delays the GUI creation task until the initial thread's task have been completed.
			public void run() {
				new Pong();
			}
		});
		}

}
