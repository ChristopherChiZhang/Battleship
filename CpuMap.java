import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class CpuMap extends JPanel {
	// Declaration and Initialization
	DecimalFormat df = new DecimalFormat("#00");
	Color borderColor = new Color(0, 0, 0); // Border color
	LineBorder border = new LineBorder(borderColor, 3, true); // Line Border for JPanel
	// Ship array of CPU's Ships
	Ship cpuShips[] = { new Carrier(), new Battleship(), new Cruiser(), new Submarine(), new Destroyer() };
	// JLabels
	JLabel timeRemaining = new JLabel(), cpuField = new JLabel(), missIcon = new JLabel(), hitIcon = new JLabel();
	JLabel displayUserScore = new JLabel(), remainingUserShots = new JLabel(), lagReducer;
	// Time, game timer delay, shots per turn, shot #, user score
	int min = 0, sec = 0, delayGameLength = 1000, shotsPerTurn = 0, shotNumber = 1, userScore = 0;
	JButton cpuGrid[][] = new JButton[10][10]; // Array of JButton's for 10 by 10 grid
	// JButtons
	JButton srcButton, viewYourShipsButton = new JButton();
	String cpuGridName = "cpuGrid"; // Starting name for JButtons
	// Store mapping of JButton name to coordinate on userGrid
	Map<String, Point> cpuGridMapping = new HashMap<>();
	Battlefield cpuBattlefield = new Battlefield(); // CPU's Battlefield
	Point userSelection; // The Point the user has selected
	// Images
	Image water = new ImageIcon(this.getClass().getResource("/BattleshipField.jpg")).getImage();
	Image missAnimation = new ImageIcon(this.getClass().getResource("/MissAnimation.gif")).getImage();
	Image hitAnimation = new ImageIcon(this.getClass().getResource("/HitAnimation.gif")).getImage();
	Image viewYourShipsIcon = new ImageIcon(this.getClass().getResource("/ViewYourShipsIcon.png")).getImage();
	Image viewYourShipsHoverIcon = new ImageIcon(this.getClass().getResource("/ViewYourShipsHoverIcon.png")).getImage();
	Image shipHitIcon = new ImageIcon(this.getClass().getResource("/ShipHitIcon.jpg")).getImage();
	Image shipMissIcon = new ImageIcon(this.getClass().getResource("/ShipMissIcon.png")).getImage();
	Image userMapBackground = new ImageIcon(this.getClass().getResource("/UserMapBack.jpg")).getImage();
	Timer timerGameLength; // Game timer
	Font battleshipFont = new Font("Dead Kansas", Font.PLAIN, 20); // Font

	public CpuMap() { // CpuMap constructor
		// Set CpuMap object to desired properties
		setBackground(Color.GREEN);
		setBorder(border);
		setName("cpuMap");
		setLayout(null);
		// Set viewYourShips to desired properties and add it to CpuMap object
		viewYourShipsButton.setBounds(0, 540, 180, 80);
		viewYourShipsButton.setIcon(new ImageIcon(viewYourShipsIcon));
		viewYourShipsButton.setContentAreaFilled(false);
		viewYourShipsButton.setBorder(null);
		viewYourShipsButton.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
				viewYourShipsButton.setIcon(new ImageIcon(viewYourShipsHoverIcon));
			}

			public void mouseExited(MouseEvent arg0) {
				viewYourShipsButton.setIcon(new ImageIcon(viewYourShipsIcon));
			}

			public void mousePressed(MouseEvent arg0) {
				viewYourShipsButton.setIcon(new ImageIcon(viewYourShipsIcon));
			}

			public void mouseReleased(MouseEvent arg0) {
				viewYourShipsButton.setIcon(new ImageIcon(viewYourShipsHoverIcon));
			}
		});
		add(viewYourShipsButton);
		// Set missIcon to desired properties and add it to CpuMap object
		missIcon.setBounds(225, 200, 250, 120);
		missIcon.setIcon(new ImageIcon(missAnimation));
		missIcon.setVisible(false);
		add(missIcon);
		// Set hitIcon to desired properties and add it to CpuMap object
		hitIcon.setBounds(225, 200, 250, 120);
		hitIcon.setIcon(new ImageIcon(hitAnimation));
		hitIcon.setVisible(false);
		add(hitIcon);
		// Set lagReducer to desired properties and add it to CpuMap object
		lagReducer = new JLabel(" Time remaining: 04:59");
		lagReducer.setFont(battleshipFont);
		lagReducer.setBounds(10, 10, 300, 30);
		add(lagReducer);
		// Set timeRemaining to desired properties and add it to CpuMap object
		timeRemaining.setBounds(10, 10, 250, 30);
		timeRemaining.setFont(battleshipFont);
		timeRemaining.setForeground(new Color(187, 25, 9));
		timeRemaining.setOpaque(true);
		timeRemaining.setBackground(Color.gray);
		timeRemaining.setBorder(new LineBorder(Color.black, 1, true));
		add(timeRemaining);
		// Set displayUserScore to desired properties and add it to CpuMap object
		displayUserScore.setBounds(450, 10, 140, 30);
		displayUserScore.setOpaque(true);
		displayUserScore.setBackground(Color.gray);
		displayUserScore.setBorder(new LineBorder(Color.black, 1, true));
		displayUserScore.setFont(battleshipFont);
		displayUserScore.setForeground(new Color(187, 25, 9));
		displayUserScore.setText(" Score: " + userScore);
		add(displayUserScore);
		// Set remainingUserShots to desired properties and add it to CpuMap object
		remainingUserShots.setBounds(450, 40, 240, 30);
		remainingUserShots.setOpaque(true);
		remainingUserShots.setBackground(Color.gray);
		remainingUserShots.setBorder(new LineBorder(Color.black, 1, true));
		remainingUserShots.setFont(battleshipFont);
		remainingUserShots.setForeground(new Color(187, 25, 9));
		remainingUserShots.setText("");
		add(remainingUserShots);
		// Initialize cpuGrid buttons
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				cpuGrid[i][j] = new JButton();
				cpuGrid[i][j].setName(cpuGridName + i + j);
				cpuGridMapping.put(cpuGridName + i + j, new Point(i, j)); // Add to cpuGridMapping
			}
		}
		// Place cpu's ships onto field
		for (int i = 0; i < 5; i++) {
			cpuBattlefield.setToField(cpuShips[i]);
		}
		// Set location of cpuGrid JButtons and add it to CpuMap object
		for (int i = 0, y = 0; i < 10; i++, y += 40) {
			for (int j = 0, x = 0; j < 10; j++, x += 40) {
				// Set JButton to desired properties
				cpuGrid[i][j].setContentAreaFilled(false);
				cpuGrid[i][j].setBounds(150 + x, 125 + y, 40, 40);
				add(cpuGrid[i][j]); // Add to CpuMap object
			}
		}
		// Set cpuField to desired properties and add it to CpuMap object
		cpuField.setIcon(new ImageIcon(water));
		cpuField.setBounds(150, 125, 400, 400);
		add(cpuField);
	}

	/*
	 * This method checks if the selected Point is between the start and end Points
	 * of a Ship and returns true if it is
	 */
	public static boolean isBetween(Point start, Point end, Point selected) {
		if (start.x == end.x) {// If points horizontal
			if (selected.x == start.x) {
				if (selected.y <= Math.max(start.y, end.y) && selected.y >= Math.min(start.y, end.y))
					return true;
			}
		} else {// If points vertical
			if (selected.y == start.y) {
				if (selected.x <= Math.max(start.x, end.x) && selected.x >= Math.min(start.x, end.x))
					return true;
			}
		}
		return false;
	}

	/*
	 * This method resets the necessary fields of CpuMap
	 */
	public void restart() {
		cpuGridMapping.clear();
	}

	/*
	 * This method paints the background of the JPanel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(userMapBackground, 0, 0, null);
	}
}
