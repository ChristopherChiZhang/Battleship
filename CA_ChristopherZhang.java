import java.awt.EventQueue;

public class CA_ChristopherZhang {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game game = new Game(); // Create new Game object
					game.frame.setVisible(true); // Set frame visible
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
