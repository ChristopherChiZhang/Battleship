import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class UserMap extends JPanel {
	// Declaration and Initialization
	// Line border and colour for GameMode JPanel
	Color borderColor = new Color(0, 0, 0);
	LineBorder border = new LineBorder(borderColor, 3, true);
	// JLabels
	JLabel userField = new JLabel(), positioning = new JLabel(), opponentTurn = new JLabel(),
			remainingCpuShots = new JLabel();
	// Array of user's Ships
	Ship userShips[] = { new Carrier(), new Battleship(), new Cruiser(), new Submarine(), new Destroyer() };
	// Index of userShip, shots per turn, shot #, CPU's score
	int userShipIndex = 0, shotsPerTurn = 0, shotNumber = 1, cpuScore = 0;
	// JButtons
	JButton battleButton = new JButton(), backToEnemyShipsButton = new JButton();
	JButton srcButton;
	// 2D array of JButtons for user's grid
	static JButton userGrid[][] = new JButton[10][10];
	String userGridName = "userGrid"; // Start of name of JButtons
	// Store mapping of name of JButton to Point
	static Map<String, Point> userGridMapping = new HashMap<>();
	// Stores end points of Ship placement
	static List<String> userGridEndPoints = new ArrayList<>();
	// Stores Points between the start and end of a Ship
	static List<String> userGridPointsBetween = new ArrayList<>();
	// Stores the Points already taken by other Ships
	static List<String> userGridPointsTaken = new ArrayList<>();
	Point start, end;
	Battlefield userBattlefield = new Battlefield();
	// Images & Icons for JButtons and JLabels
	Image water = new ImageIcon(this.getClass().getResource("/BattleshipField.jpg")).getImage();
	Image userMapBackground = new ImageIcon(this.getClass().getResource("/UserMapBack.jpg")).getImage();
	Image missAnimation = new ImageIcon(this.getClass().getResource("/MissAnimation.gif")).getImage();
	Image hitAnimation = new ImageIcon(this.getClass().getResource("/HitAnimation.gif")).getImage();
	Image positioningIcon = new ImageIcon(this.getClass().getResource("/PositioningIcon.png")).getImage();
	Image carrierIcon = new ImageIcon(this.getClass().getResource("/CarrierIcon.png")).getImage();
	Image battleshipIcon = new ImageIcon(this.getClass().getResource("/BattleshipIcon.png")).getImage();
	Image cruiserIcon = new ImageIcon(this.getClass().getResource("/CruiserIcon.png")).getImage();
	Image submarineIcon = new ImageIcon(this.getClass().getResource("/SubmarineIcon.png")).getImage();
	Image destroyerIcon = new ImageIcon(this.getClass().getResource("/DestroyerIcon.png")).getImage();
	Image[] shipIcons = { carrierIcon, battleshipIcon, cruiserIcon, submarineIcon, destroyerIcon };
	JLabel missIcon = new JLabel(), hitIcon = new JLabel(), displayShipIcons = new JLabel();
	Point cpuSelection;
	Image backToEnemyShipsIcon = new ImageIcon(this.getClass().getResource("/BackToEnemyShipsIcon.png")).getImage();
	Image backToEnemyShipsHoverIcon = new ImageIcon(this.getClass().getResource("/BackToEnemyShipsHoverIcon.png"))
			.getImage();
	Image battleButtonIcon = new ImageIcon(this.getClass().getResource("/BattleButtonIcon.png")).getImage();
	Image battleButtonHoverIcon = new ImageIcon(this.getClass().getResource("/BattleButtonHoverIcon.png")).getImage();
	Image shipPlacement = new ImageIcon(this.getClass().getResource("/ShipPlacement.jpg")).getImage();
	Image shipHitIcon = new ImageIcon(this.getClass().getResource("/ShipHitIcon.jpg")).getImage();
	Image shipMissIcon = new ImageIcon(this.getClass().getResource("/ShipMissIcon.png")).getImage();
	Icon shipStartPlaceIcon = new ImageIcon(this.getClass().getResource("/ShipStartPlace.gif"));
	Icon shipEndPlaceIcon = new ImageIcon(this.getClass().getResource("/ShipEndPlace.gif"));
	Font battleshipFont = new Font("Dead Kansas", Font.PLAIN, 20);

	public UserMap() {
		// Set properties of UserMap object
		setBorder(border);
		setName("userMap");
		setLayout(null);
		// Set properties of positioning and add it to UserMap object
		positioning.setBounds(100, 40, 350, 60);
		positioning.setIcon(new ImageIcon(positioningIcon));
		add(positioning);
		// Set properties of displayShipIcons and add it to UserMap object
		displayShipIcons.setBounds(450, 40, 200, 60);
		displayShipIcons.setIcon(new ImageIcon(shipIcons[userShipIndex]));
		add(displayShipIcons);
		// Set properties of opponentTurn and add it to UserMap object
		opponentTurn.setBounds(450, 10, 300, 30);
		opponentTurn.setText("Opponent's turn");
		opponentTurn.setFont(battleshipFont);
		opponentTurn.setForeground(new Color(187, 25, 9));
		opponentTurn.setVisible(false);
		add(opponentTurn);
		// Set remainingCpuShots to desired properties and add it to CpuMap object
		remainingCpuShots.setBounds(450, 40, 240, 30);
		remainingCpuShots.setOpaque(true);
		remainingCpuShots.setBackground(Color.gray);
		remainingCpuShots.setBorder(new LineBorder(Color.black, 1, true));
		remainingCpuShots.setFont(battleshipFont);
		remainingCpuShots.setForeground(new Color(187, 25, 9));
		remainingCpuShots.setText("");
		remainingCpuShots.setVisible(false);
		add(remainingCpuShots);
		// Set missIcon to desired properties and add it to UserMap object
		missIcon.setBounds(225, 200, 250, 120);
		missIcon.setIcon(new ImageIcon(missAnimation));
		missIcon.setVisible(false);
		add(missIcon);
		// Set hitIcon to desired properties and add it to UserMap object
		hitIcon.setBounds(225, 200, 250, 120);
		hitIcon.setIcon(new ImageIcon(hitAnimation));
		hitIcon.setVisible(false);
		add(hitIcon);
		// Set properties of backToEnemyShipsButton and add it to UserMap object
		backToEnemyShipsButton.setBounds(0, 540, 180, 80);
		backToEnemyShipsButton.setIcon(new ImageIcon(backToEnemyShipsIcon));
		backToEnemyShipsButton.setVisible(false);
		backToEnemyShipsButton.setContentAreaFilled(false);
		backToEnemyShipsButton.setBorder(null);
		backToEnemyShipsButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
				backToEnemyShipsButton.setIcon(new ImageIcon(backToEnemyShipsHoverIcon));
			}

			public void mouseExited(MouseEvent arg0) {
				backToEnemyShipsButton.setIcon(new ImageIcon(backToEnemyShipsIcon));
			}

			public void mousePressed(MouseEvent arg0) {
				backToEnemyShipsButton.setIcon(new ImageIcon(backToEnemyShipsIcon));
			}

			public void mouseReleased(MouseEvent arg0) {
				backToEnemyShipsButton.setIcon(new ImageIcon(backToEnemyShipsHoverIcon));
			}
		});
		add(backToEnemyShipsButton);
		// Set properties of JButtons and add it to UserMap object
		battleButton.setBounds(370, 540, 270, 60);
		battleButton.setVisible(false);
		battleButton.setContentAreaFilled(false);
		battleButton.setBorder(null);
		battleButton.setIcon(new ImageIcon(battleButtonIcon));
		battleButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
				battleButton.setIcon(new ImageIcon(battleButtonHoverIcon));
			}

			public void mouseExited(MouseEvent arg0) {
				battleButton.setIcon(new ImageIcon(battleButtonIcon));
			}

			public void mousePressed(MouseEvent arg0) {
				battleButton.setIcon(new ImageIcon(battleButtonIcon));
			}

			public void mouseReleased(MouseEvent arg0) {
				battleButton.setIcon(new ImageIcon(battleButtonHoverIcon));
			}
		});
		add(battleButton);
		// Initialize userGrid buttons
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				userGrid[i][j] = new JButton();
				userGrid[i][j].setName(userGridName + i + j);
				userGridMapping.put(userGridName + i + j, new Point(i, j)); // Add to userGridMapping
			}
		}
		// Set location of userGrid JButtons and add it to UserMap object
		for (int i = 0, y = 0; i < 10; i++, y += 40) {
			for (int j = 0, x = 0; j < 10; j++, x += 40) {
				// Set JButton to desired properties
				userGrid[i][j].setContentAreaFilled(false);
				userGrid[i][j].setBounds(150 + x, 125 + y, 40, 40);
				userGrid[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						srcButton = (JButton) arg0.getSource();
						if (srcButton.getIcon() == null) {
							// The nested for loop clears the user grid of any icons
							// other than the ships the user has already placed
							for (int o = 0; o < 10; o++) {
								for (int p = 0; p < 10; p++) {
									if (!userGridPointsTaken.contains(userGrid[o][p].getName())) { // If grid is not a
																									// ship
										userGrid[o][p].setIcon(null); // Set button's icon null
										userGrid[o][p].setContentAreaFilled(false); // Set button to transparent
									}
								}
							}
							srcButton.setIcon(shipStartPlaceIcon);
							start = userGridMapping.get(srcButton.getName()); // Obtain start position of ship
							userShips[userShipIndex].start = start; // Set start point of ship
							possibleEndPoints(start, userShips[userShipIndex].length);
							// This nested for loop sets the icons of the possible end points
							for (int o = 0; o < 10; o++) {
								for (int p = 0; p < 10; p++) {
									if (userGridEndPoints.contains(userGrid[o][p].getName())) {
										userGrid[o][p].setIcon(shipEndPlaceIcon);
									}
								}
							}
							userGridEndPoints.clear(); // Clear userGridEndPoints
						} else {
							// if user clicks on JButton with starting icon
							if (srcButton.getIcon() == shipStartPlaceIcon) {
								// Reset all JButtons to no icon except already placed Ships
								for (int o = 0; o < 10; o++) {
									for (int p = 0; p < 10; p++) {
										if (!userGridPointsTaken.contains(userGrid[o][p].getName())) {
											userGrid[o][p].setIcon(null); // Set button's icon null
											userGrid[o][p].setContentAreaFilled(false); // Set button to transparent
											userGrid[o][p].setEnabled(true); // Enable Button
										}
									}
								}
							} else if (srcButton.getIcon() == shipEndPlaceIcon) { // If user selects JButton with ending
																					// icon
								end = userGridMapping.get(srcButton.getName()); // Obtain ending Point
								userShips[userShipIndex].end = end; // Set end point of ship
								userShips[userShipIndex].placed = true; // Set current ship status to placed
								pointsBetween(start, end); // Determine Points between start and end
								// The following nested for loop changes Icon of Points between start and end
								// inclusive
								// and resets all other JButtons that have no Ship placed on them
								for (int o = 0; o < 10; o++) {
									for (int p = 0; p < 10; p++) {
										if (!userGridPointsBetween.contains(userGrid[o][p].getName())) {
											if (!userGridPointsTaken.contains(userGrid[o][p].getName())) {
												userGrid[o][p].setIcon(null); // Set button's icon null
												userGrid[o][p].setContentAreaFilled(false); // Set button to transparent
												userGrid[o][p].setEnabled(true); // Enable Button
											}
										} else {
											userGrid[o][p].setEnabled(false); // Disable Button
											userGrid[o][p].setIcon(new ImageIcon(shipPlacement));
											userGrid[o][p].setDisabledIcon(new ImageIcon(shipPlacement));
											// userGrid[o][p].setBackground(Color.gray);
											userGridPointsTaken.add(userGrid[o][p].getName());
										}
									}
								}
								userGridPointsBetween.clear(); // Clear userGridPointsBetween
							}
						}
						if (userShips.length - 1 == userShipIndex && userShips[userShipIndex].isPlaced()) {
							// Update values in userBattlefield to true
							userBattlefield.fillField(userShips[userShipIndex]);
							// Disable JButtons taken by placed Ships
							for (int o = 0; o < 10; o++) {
								for (int p = 0; p < 10; p++) {
									if (!userGridPointsTaken.contains(userGrid[o][p].getName())) {
										userGrid[o][p].setEnabled(false);
									}
								}
							}
							battleButton.setVisible(true); // Set battleButton to true
						} else if (userShips[userShipIndex].isPlaced()) {
							// Update values in userBattlefield to true
							userBattlefield.fillField(userShips[userShipIndex]);
							userShipIndex++; // Increase index by one
							// Update Icon to next Ship
							displayShipIcons.setIcon(new ImageIcon(shipIcons[userShipIndex]));
						}
					}
				});
				add(userGrid[i][j]); // Add to UserMap object
			}
		}
		// Add JLabel to UserMap object
		userField.setIcon(new ImageIcon(water));
		userField.setBounds(150, 125, 400, 400);
		add(userField);
	}

	/*
	 * This method calculates the possible end Points to place a Ship given it's
	 * starting Point and Ship length
	 */
	public static void possibleEndPoints(Point start, int shipLength) {
		boolean betweenDoesContain = false;
		if (start.x - (shipLength - 1) >= 0) {// Up
			pointsBetween(start, new Point(start.x - (shipLength - 1), start.y));
			for (String taken : userGridPointsTaken) {
				if (userGridPointsBetween.contains(taken))
					betweenDoesContain = true;
			}
			if (!betweenDoesContain) {
				// Add Point to list
				userGridEndPoints.add(userGrid[start.x - (shipLength - 1)][start.y].getName());
			}
			// Clear userGridPointsBetween
			userGridPointsBetween.clear();
			// Reset to false
			betweenDoesContain = false;
		}
		if (start.y + (shipLength - 1) <= 9) {// Right
			pointsBetween(start, new Point(start.x, start.y + (shipLength - 1)));
			for (String taken : userGridPointsTaken) {
				if (userGridPointsBetween.contains(taken))
					betweenDoesContain = true;
			}
			if (!betweenDoesContain) {
				// Add Point to list
				userGridEndPoints.add(userGrid[start.x][start.y + (shipLength - 1)].getName());
			}
			// Clear userGridPointsBetween
			userGridPointsBetween.clear();
			// Reset to false
			betweenDoesContain = false;
		}
		if (start.x + (shipLength - 1) <= 9) {// Down
			pointsBetween(start, new Point(start.x + (shipLength - 1), start.y));
			for (String taken : userGridPointsTaken) {
				if (userGridPointsBetween.contains(taken))
					betweenDoesContain = true;
			}
			if (!betweenDoesContain) {
				// Add Point to list
				userGridEndPoints.add(userGrid[start.x + (shipLength - 1)][start.y].getName());
			}
			// Clear userGridPointsBetween
			userGridPointsBetween.clear();
			// Reset to false
			betweenDoesContain = false;
		}
		if (start.y - (shipLength - 1) >= 0) {// Left
			pointsBetween(start, new Point(start.x, start.y - (shipLength - 1)));
			for (String taken : userGridPointsTaken) {
				if (userGridPointsBetween.contains(taken))
					betweenDoesContain = true;
			}
			if (!betweenDoesContain) {
				// Add Point to list
				userGridEndPoints.add(userGrid[start.x][start.y - (shipLength - 1)].getName());
			}
			// Clear userGridPointsBetween
			userGridPointsBetween.clear();
			// Reset to false
			betweenDoesContain = false;
		}
	}

	/*
	 * This method calculates the Points between the given start and end Points
	 */
	public static void pointsBetween(Point start, Point end) {
		if (start.x == end.x) {// If points horizontal
			for (int i = Math.min(start.y, end.y); i <= Math.max(start.y, end.y); i++) {
				userGridPointsBetween.add(userGrid[start.x][i].getName()); // Add Point to list
			}
		} else {// If points vertical
			for (int i = Math.min(start.x, end.x); i <= Math.max(start.x, end.x); i++) {
				userGridPointsBetween.add(userGrid[i][start.y].getName()); // Add Point to list
			}
		}
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
	 * This method resets the necessary values of the UserMap object
	 */
	public void restart() {
		userGridMapping.clear();
		userGridEndPoints.clear();
		userGridPointsBetween.clear();
		userGridPointsTaken.clear();
	}

	/*
	 * This method paints the background of the JPanel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(userMapBackground, 0, 0, null);
	}
}