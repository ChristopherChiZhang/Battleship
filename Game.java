import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Game {
	/*
	 * This method loops through the components of the given container and returns
	 * the name of the JPanel that is visible
	 */
	public static String showTopCard(Container c) {
		String name = "";
		Component arr1[] = c.getComponents();
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i].isVisible())
				name = arr1[i].getName();
		}
		/*
		 * for (Component j : c.getComponents()) { if (j.isVisible()) name =
		 * j.getName(); }
		 */
		return name;
	}

	// Declaration
	// Timers
	Timer switchToCpuTurn, switchToUserTurn, botGuessing, botSalvoMode, hideMissIcon, hideHitIcon, switchToGameOver;
	Point cpuGuess; // The Point the CpuBot guesses
	// Timer delay values for timers
	int delayCpuTurn, delayUserTurn, delayBotGuess, delaySalvoMode, delayMissIcon, delayHitIcon, delayGameOver;
	// JPanel objects
	JFrame frame;
	Menu menu;
	Rules rules;
	UserMap userMap;
	CpuMap cpuMap;
	GameMode gameMode;
	ModeTypes modeTypes;
	GameMechanics1 gameMechanics1;
	GameMechanics2 gameMechanics2;
	GameOver gameOver;
	// CpuBot
	CpuBot cpuBot;
	// Next and Back arrow JButtons
	JButton nextArrowButton, backArrowButton;
	CardLayout layout1; // Layout
	Toolkit toolkit; // To change mouse icon
	// Images
	Image mouseIcon, nextArrowIcon, nextArrowHoverIcon, backArrowIcon, backArrowHoverIcon, battleshipLogo;
	Cursor mouseIconCursor; // Mouse cursor icon
	boolean isGameOver; // true if game is over

	public Game() {
		runGame();
	}

	public void runGame() {
		/*
		 * btnPlayAgain.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { frame.dispose(); Game window = new Game();
		 * window.frame.setVisible(true); } });
		 */
		// Initialization
		frame = new JFrame();
		menu = new Menu();
		rules = new Rules();
		userMap = new UserMap();
		cpuMap = new CpuMap();
		gameMode = new GameMode();
		modeTypes = new ModeTypes();
		gameMechanics1 = new GameMechanics1();
		gameMechanics2 = new GameMechanics2();
		cpuBot = new CpuBot();
		gameOver = new GameOver();
		nextArrowButton = new JButton();
		backArrowButton = new JButton();
		layout1 = new CardLayout(50, 50);
		cpuGuess = new Point();
		toolkit = Toolkit.getDefaultToolkit();
		mouseIcon = new ImageIcon(this.getClass().getResource("/MouseIcon.png")).getImage();
		mouseIconCursor = toolkit.createCustomCursor(mouseIcon,
				new java.awt.Point(frame.getX() + 15, frame.getY() + 15), "img");
		nextArrowIcon = new ImageIcon(this.getClass().getResource("/NextArrowIcon.png")).getImage();
		nextArrowHoverIcon = new ImageIcon(this.getClass().getResource("/nextArrowHoverIcon.png")).getImage();
		backArrowIcon = new ImageIcon(this.getClass().getResource("/BackArrowIcon.png")).getImage();
		backArrowHoverIcon = new ImageIcon(this.getClass().getResource("/backArrowHoverIcon.png")).getImage();
		battleshipLogo = new ImageIcon(this.getClass().getResource("/battleshiplogo.png")).getImage();
		delayCpuTurn = 1000;
		delayUserTurn = 2000;
		delayBotGuess = 1000;
		delaySalvoMode = 1500;
		delayMissIcon = 900;
		delayHitIcon = 900;
		delayGameOver = 1000;
		isGameOver = false;
		cpuMap.timerGameLength = new Timer(cpuMap.delayGameLength, new ActionListener() { // Game timer
			public void actionPerformed(ActionEvent e) {
				if (cpuMap.sec == 0 && cpuMap.min > 0) { // If sec reaches 0, decrease min
					cpuMap.sec = 59;
					cpuMap.min--;
				}
				if (cpuMap.sec == 0 && cpuMap.min == 0) { // If time's up
					cpuMap.timeRemaining.setText(" Time's up!");
					isGameOver = true;
					if (!switchToCpuTurn.isRunning() && !switchToUserTurn.isRunning() && !botGuessing.isRunning()) {
						switchToGameOver.start(); // Start timer
					}
					cpuMap.timerGameLength.stop(); // Stop timer

				} else
					cpuMap.timeRemaining
							.setText(" Time remaining: 0" + cpuMap.min + ":" + cpuMap.df.format(cpuMap.sec));
				cpuMap.lagReducer.setVisible(false);
				cpuMap.sec--;
			}
		});
		switchToGameOver = new Timer(delayGameOver, new ActionListener() { // This timer switches to gameOver JPanel
			public void actionPerformed(ActionEvent e) {
				// Disable all buttons on cpuMap
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						cpuMap.cpuGrid[i][j].setEnabled(false);
					}
				}
				// Obtain final scores
				gameOver.userPoints = cpuMap.userScore;
				gameOver.opponentPoints = userMap.cpuScore;
				// Set text of JLabels to display score
				gameOver.yourScoreNum.setText(Integer.toString(gameOver.userPoints));
				gameOver.opponentScoreNum.setText(Integer.toString(gameOver.opponentPoints));
				// Determine outcome: win, lose, tie
				if (gameOver.userPoints > gameOver.opponentPoints) {
					gameOver.youWinGame.setBounds(130, 300, 440, 60);
				} else if (gameOver.userPoints < gameOver.opponentPoints) {
					gameOver.opponentWinGame.setBounds(53, 300, 595, 60);
				} else {
					gameOver.youTiedGame.setBounds(128, 300, 445, 60);
				}
				layout1.show(frame.getContentPane(), "gameOver"); // show gameOver JPanel
				switchToGameOver.stop(); // Stop timer
			}
		});
		// This timer switches to userMap JPanel and starts the CPU's turn
		switchToCpuTurn = new Timer(delayCpuTurn, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Add timer
				userMap.add(cpuMap.timeRemaining);
				layout1.show(frame.getContentPane(), "userMap"); // Show userMap
				// Set amount of shots remaining
				userMap.remainingCpuShots
						.setText(" Shots Remaining: " + cpuMap.shotsPerTurn + "/" + cpuMap.shotsPerTurn);
				userMap.backToEnemyShipsButton.setVisible(false); // Hide JButton
				switchToCpuTurn.stop(); // Stop timer
				botGuessing.start(); // Start timer
				if (gameMode.isClassic && !isGameOver) // If mode is classic & game is not over
					switchToUserTurn.start(); // Start timer
				else if (gameMode.isClassic && isGameOver) { // If mode is classic & game is over
					switchToGameOver.setInitialDelay(2000); // Set initial timer delay
					switchToGameOver.start(); // Start timer
				}
				userMap.opponentTurn.setVisible(true); // Set JLabel visible
			}
		});
		// This timer hides the miss icon
		hideMissIcon = new Timer(delayMissIcon, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Hide icons
				cpuMap.missIcon.setVisible(false);
				userMap.missIcon.setVisible(false);
				// Enable all cpuGrid buttons
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (!cpuMap.cpuGrid[i][j].isContentAreaFilled())
							cpuMap.cpuGrid[i][j].setEnabled(true);
					}
				}
				hideMissIcon.stop(); // Stop timer
			}
		});
		// This timer hides the hit icon
		hideHitIcon = new Timer(delayHitIcon, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Hide icons
				cpuMap.hitIcon.setVisible(false);
				userMap.hitIcon.setVisible(false);
				// Enable all cpuGrid buttons
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (!cpuMap.cpuGrid[i][j].isContentAreaFilled())
							cpuMap.cpuGrid[i][j].setEnabled(true);
					}
				}
				hideHitIcon.stop(); // Stop timer
			}
		});
		// This timer starts the cpuBot if game mode is salvo
		botSalvoMode = new Timer(delaySalvoMode, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cpuGuess = cpuBot.guess(); // generate random Point
				cpuBot.guessed.add(cpuGuess); // Add Point to guessed list
				userMap.cpuSelection = cpuGuess;
				if (userMap.userBattlefield.field[cpuGuess.x][cpuGuess.y]) { // If hit
					// Set ship hit icon
					UserMap.userGrid[cpuGuess.x][cpuGuess.y].setDisabledIcon(new ImageIcon(userMap.shipHitIcon));
					userMap.hitIcon.setVisible(true); // Display hitIcon
					hideHitIcon.start(); // Start timer
					// Determine original and previous hit
					cpuBot.originalHit = cpuBot.previousHit;
					cpuBot.previousHit = cpuGuess;
					if (cpuBot.vicinity.isEmpty()) { // If empty, add all surrounding points
						cpuBot.addAllVicinity(cpuGuess);
						Collections.shuffle(cpuBot.vicinity);
					} else { // If not empty, add only aligned points
						cpuBot.addMinVicinity();
						cpuBot.vicinity.remove(0);
					}
					// Check if any Ships are sunk and calculate score
					for (int i = 0; i < 5; i++) {
						if (UserMap.isBetween(userMap.userShips[i].start, userMap.userShips[i].end,
								userMap.cpuSelection)) {
							userMap.userShips[i].life--;
							if (userMap.userShips[i].life == 0 && !userMap.userShips[i].isSunk) {
								userMap.userShips[i].isSunk = true;
								userMap.shotsPerTurn--; // Decrease shots per turn
								userMap.cpuScore += 500; // Update cpu's score
							} else
								userMap.cpuScore += 200; // Update cpu's score
							break;
						}
					}
				} else { // If miss
					// Set ship miss icon
					UserMap.userGrid[cpuGuess.x][cpuGuess.y].setIcon(new ImageIcon(userMap.shipMissIcon));
					UserMap.userGrid[cpuGuess.x][cpuGuess.y].setDisabledIcon(new ImageIcon(userMap.shipMissIcon));
					UserMap.userGrid[cpuGuess.x][cpuGuess.y].setContentAreaFilled(true);
					// Remove Point from list if it was from vicinity
					if (!cpuBot.vicinity.isEmpty())
						cpuBot.vicinity.remove(0);
					userMap.missIcon.setVisible(true); // Set hit icon visible
					hideMissIcon.start(); // Start timer
				}
				// If all ships are sunk, start gameOver timer
				if (userMap.userShips[0].isSunk && userMap.userShips[1].isSunk && userMap.userShips[2].isSunk
						&& userMap.userShips[3].isSunk && userMap.userShips[4].isSunk) {
					botSalvoMode.stop(); // Stop timer
					switchToGameOver.start(); // Start timer
				} else {
					if (cpuMap.shotNumber == cpuMap.shotsPerTurn) { // If all shots used up
						userMap.remainingCpuShots.setText(" Shots Remaining: "
								+ (cpuMap.shotsPerTurn - cpuMap.shotNumber) + "/" + cpuMap.shotsPerTurn);
						cpuMap.shotNumber = 1;
						if (isGameOver) {
							switchToGameOver.start(); // Start timer
						} else {
							switchToUserTurn.setInitialDelay(1200); // Set initial delay
							switchToUserTurn.start(); // Start timer
						}
						botSalvoMode.stop(); // Stop timer
					} else {
						// Update remaining shots left
						userMap.remainingCpuShots.setText(" Shots Remaining: "
								+ (cpuMap.shotsPerTurn - cpuMap.shotNumber) + "/" + cpuMap.shotsPerTurn);
						cpuMap.shotNumber++;
					}
				}
			}
		});
		// This timer starts the CpuBot
		botGuessing = new Timer(delayBotGuess, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameMode.isClassic) { // If mode is classic
					cpuGuess = cpuBot.guess(); // generate random Point
					cpuBot.guessed.add(cpuGuess); // Add Point to guessed list
					userMap.cpuSelection = cpuGuess;
					if (userMap.userBattlefield.field[cpuGuess.x][cpuGuess.y]) { // If hit
						// Set ship hit icon
						UserMap.userGrid[cpuGuess.x][cpuGuess.y].setDisabledIcon(new ImageIcon(userMap.shipHitIcon));
						UserMap.userGrid[cpuGuess.x][cpuGuess.y].setBackground(Color.red);
						userMap.hitIcon.setVisible(true); // Set hit icon visible
						hideHitIcon.start(); // Start timer
						// Check if any Ships are sunk and calculate score
						for (int i = 0; i < 5; i++) {
							if (UserMap.isBetween(userMap.userShips[i].start, userMap.userShips[i].end,
									userMap.cpuSelection)) {
								userMap.userShips[i].life--;
								if (!userMap.userShips[i].isSunk && userMap.userShips[i].life == 0) {
									userMap.userShips[i].isSunk = true;
									userMap.cpuScore += 500; // Update cpu's score
								} else
									userMap.cpuScore += 200; // Update cpu's score
								break;
							}
						}
						// Determine original and previous hit
						cpuBot.originalHit = cpuBot.previousHit;
						cpuBot.previousHit = cpuGuess;
						if (cpuBot.vicinity.isEmpty()) { // If empty, add all surrounding points
							cpuBot.addAllVicinity(cpuGuess);
							Collections.shuffle(cpuBot.vicinity);
						} else { // If not empty, all only aligned points
							cpuBot.addMinVicinity();
							cpuBot.vicinity.remove(0);
						}
					} else { // If miss
						// Set shipt miss icon
						UserMap.userGrid[cpuGuess.x][cpuGuess.y].setIcon(new ImageIcon(userMap.shipMissIcon));
						UserMap.userGrid[cpuGuess.x][cpuGuess.y].setDisabledIcon(new ImageIcon(userMap.shipMissIcon));
						UserMap.userGrid[cpuGuess.x][cpuGuess.y].setContentAreaFilled(true);
						// Remove Point from list if it was from vicinity
						if (!cpuBot.vicinity.isEmpty())
							cpuBot.vicinity.remove(0);
						userMap.missIcon.setVisible(true); // Set miss icon visible
						hideMissIcon.start(); // Start timer
					}
					// Update remaining shots
					userMap.remainingCpuShots.setText(" Shots Remaining: " + (cpuMap.shotsPerTurn - cpuMap.shotNumber)
							+ "/" + cpuMap.shotsPerTurn);
				} else { // If mode is salvo
					botSalvoMode.setInitialDelay(0); // Set initial delay
					botSalvoMode.start(); // Start timer
				}
				if ((gameMode.isClassic && isGameOver) || (userMap.userShips[0].isSunk && userMap.userShips[1].isSunk
						&& userMap.userShips[2].isSunk && userMap.userShips[3].isSunk && userMap.userShips[4].isSunk)) {
					switchToUserTurn.stop(); // Stop timer
					switchToGameOver.start(); // Start timer
				}
				botGuessing.stop(); // Stop timer
			}
		});
		// This timer switches to cpuMap JPanel
		switchToUserTurn = new Timer(delayUserTurn, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isGameOver) { // If game is over
					switchToUserTurn.stop(); // Stop timer
					switchToGameOver.start(); // Start timer
				} else {
					// Enable all JButtons
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							if (!cpuMap.cpuGrid[i][j].isContentAreaFilled())
								cpuMap.cpuGrid[i][j].setEnabled(true);
						}
					}
					// Update remaining shots
					cpuMap.remainingUserShots
							.setText(" Shots Remaining: " + userMap.shotsPerTurn + "/" + userMap.shotsPerTurn);
					userMap.remainingCpuShots
							.setText(" Shots Remaining: " + cpuMap.shotsPerTurn + "/" + cpuMap.shotsPerTurn);
					cpuMap.add(cpuMap.timeRemaining); // add game timer
					cpuMap.viewYourShipsButton.setEnabled(true); // Enable JButton
					layout1.show(frame.getContentPane(), "cpuMap"); // Show cpuMap JPanel
					userMap.backToEnemyShipsButton.setVisible(true); // Set JButton visible
					userMap.opponentTurn.setVisible(false); // Hide JLabel
					switchToUserTurn.stop(); // Stop timer
				}
			}
		});
		// Set frame to desired properties
		frame.setBounds(300, 10, 800, 750);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.orange);
		frame.setCursor(mouseIconCursor);
		frame.setIconImage(battleshipLogo);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(layout1);
		// add JPanels to ContentPane
		frame.getContentPane().add(menu, "menu");
		frame.getContentPane().add(rules, "rules");
		frame.getContentPane().add(userMap, "userMap");
		frame.getContentPane().add(cpuMap, "cpuMap");
		frame.getContentPane().add(gameMode, "gameMode");
		frame.getContentPane().add(modeTypes, "modeTypes");
		frame.getContentPane().add(gameMechanics1, "gameMechanics1");
		frame.getContentPane().add(gameMechanics2, "gameMechanics2");
		frame.getContentPane().add(gameOver, "gameOver");
		// Add ActionListener to playButton
		menu.playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextArrowButton.setVisible(false);
				gameMode.add(nextArrowButton);
				backArrowButton.setBounds(60, 540, 180, 60);
				gameMode.add(backArrowButton);
				gameMode.classicModeButton.setIcon(new ImageIcon(gameMode.classicModeIcon));
				gameMode.salvoModeButton.setIcon(new ImageIcon(gameMode.salvoModeIcon));
				gameMode.fiveMinButton.setIcon(new ImageIcon(gameMode.fiveMinIcon));
				gameMode.tenMinButton.setIcon(new ImageIcon(gameMode.tenMinIcon));
				layout1.show(frame.getContentPane(), "gameMode");
			}
		});
		// Add ActionListener to howToPlayButton
		menu.howToPlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextArrowButton.setVisible(true);
				backArrowButton.setBounds(270, 540, 180, 60);
				rules.add(nextArrowButton);
				rules.add(backArrowButton);
				layout1.show(frame.getContentPane(), "rules");
			}
		});
		// Add ActionListener to salvoModeButton
		gameMode.salvoModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameMode.isClassic = false;
				userMap.shotsPerTurn = 5;
				cpuMap.shotsPerTurn = 5;
				delayUserTurn = 0;
				gameMode.salvoModeButton.setBorder(gameMode.lineBorder2);
				gameMode.salvoModeButton.setIcon(gameMode.salvoModeHoverIcon);
				gameMode.classicModeButton.setIcon(new ImageIcon(gameMode.classicModeIcon));
				if ((gameMode.fiveMinButton.getIcon().equals(gameMode.fiveMinHoverIcon)
						|| gameMode.tenMinButton.getIcon().equals(gameMode.tenMinHoverIcon))
						&& (gameMode.classicModeButton.getIcon().equals(gameMode.classicModeHoverIcon)
								|| gameMode.salvoModeButton.getIcon().equals(gameMode.salvoModeHoverIcon))) {
					nextArrowButton.setVisible(true);
				}
			}
		});
		// Add MouseListener to salvoModeButton
		gameMode.salvoModeButton.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent arg0) {
				if (!gameMode.salvoModeButton.getIcon().equals(gameMode.salvoModeHoverIcon))
					gameMode.salvoModeButton.setBorder(gameMode.lineBorder3);
			}

			public void mouseExited(MouseEvent arg0) {
				if (!gameMode.salvoModeButton.getIcon().equals(gameMode.salvoModeHoverIcon))
					gameMode.salvoModeButton.setBorder(gameMode.lineBorder2);
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		// Add ActionListener to classicModeButton
		gameMode.classicModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameMode.isClassic = true;
				userMap.shotsPerTurn = 1;
				cpuMap.shotsPerTurn = 1;
				gameMode.classicModeButton.setBorder(gameMode.lineBorder2);
				gameMode.classicModeButton.setIcon(gameMode.classicModeHoverIcon);
				gameMode.salvoModeButton.setIcon(new ImageIcon(gameMode.salvoModeIcon));
				if ((gameMode.fiveMinButton.getIcon().equals(gameMode.fiveMinHoverIcon)
						|| gameMode.tenMinButton.getIcon().equals(gameMode.tenMinHoverIcon))
						&& (gameMode.classicModeButton.getIcon().equals(gameMode.classicModeHoverIcon)
								|| gameMode.salvoModeButton.getIcon().equals(gameMode.salvoModeHoverIcon))) {
					nextArrowButton.setVisible(true);
				}
			}
		});
		// Add MouseListener to classicModeButton
		gameMode.classicModeButton.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				if (!gameMode.classicModeButton.getIcon().equals(gameMode.classicModeHoverIcon))
					gameMode.classicModeButton.setBorder(gameMode.lineBorder3);
			}

			public void mouseExited(MouseEvent e) {
				if (!gameMode.classicModeButton.getIcon().equals(gameMode.classicModeHoverIcon))
					gameMode.classicModeButton.setBorder(gameMode.lineBorder2);
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		// Add ActionListener to fiveMinButton
		gameMode.fiveMinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpuMap.min = 4;
				cpuMap.sec = 59;
				cpuMap.lagReducer.setText("Time remaining: 04:59");
				gameMode.fiveMinButton.setBorder(gameMode.lineBorder2);
				gameMode.fiveMinButton.setIcon(gameMode.fiveMinHoverIcon);
				gameMode.tenMinButton.setIcon(new ImageIcon(gameMode.tenMinIcon));
				if ((gameMode.fiveMinButton.getIcon().equals(gameMode.fiveMinHoverIcon)
						|| gameMode.tenMinButton.getIcon().equals(gameMode.tenMinHoverIcon))
						&& (gameMode.classicModeButton.getIcon().equals(gameMode.classicModeHoverIcon)
								|| gameMode.salvoModeButton.getIcon().equals(gameMode.salvoModeHoverIcon))) {
					nextArrowButton.setVisible(true);
				}
			}
		});
		// Add MouseListener to fiveMinButton
		gameMode.fiveMinButton.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent arg0) {
				if (!gameMode.fiveMinButton.getIcon().equals(gameMode.fiveMinHoverIcon))
					gameMode.fiveMinButton.setBorder(gameMode.lineBorder3);
			}

			public void mouseExited(MouseEvent arg0) {
				if (!gameMode.fiveMinButton.getIcon().equals(gameMode.fiveMinHoverIcon))
					gameMode.fiveMinButton.setBorder(gameMode.lineBorder2);
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		// Add ActionListener to tenMinButton
		gameMode.tenMinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpuMap.min = 9;
				cpuMap.sec = 59;
				cpuMap.lagReducer.setText("Time remaining: 09:59");
				gameMode.tenMinButton.setBorder(gameMode.lineBorder2);
				gameMode.tenMinButton.setIcon(gameMode.tenMinHoverIcon);
				gameMode.fiveMinButton.setIcon(new ImageIcon(gameMode.fiveMinIcon));
				if ((gameMode.fiveMinButton.getIcon().equals(gameMode.fiveMinHoverIcon)
						|| gameMode.tenMinButton.getIcon().equals(gameMode.tenMinHoverIcon))
						&& (gameMode.classicModeButton.getIcon().equals(gameMode.classicModeHoverIcon)
								|| gameMode.salvoModeButton.getIcon().equals(gameMode.salvoModeHoverIcon))) {
					nextArrowButton.setVisible(true);
				}
			}
		});
		// Add MouseListener to tenMinButton
		gameMode.tenMinButton.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent arg0) {
				if (!gameMode.tenMinButton.getIcon().equals(gameMode.tenMinHoverIcon))
					gameMode.tenMinButton.setBorder(gameMode.lineBorder3);
			}

			public void mouseExited(MouseEvent arg0) {
				if (!gameMode.tenMinButton.getIcon().equals(gameMode.tenMinHoverIcon))
					gameMode.tenMinButton.setBorder(gameMode.lineBorder2);
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		// Add ActionListener to cpuGrid
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				cpuMap.cpuGrid[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cpuMap.srcButton = (JButton) arg0.getSource(); // Get JButton user clicked
						cpuMap.userSelection = cpuMap.cpuGridMapping.get(cpuMap.srcButton.getName()); // Obtain Point
						if (cpuMap.cpuBattlefield.field[cpuMap.userSelection.x][cpuMap.userSelection.y]) { // If hit
							// Disable JButton and set ship hit icon
							cpuMap.srcButton.setEnabled(false);
							cpuMap.srcButton.setIcon(new ImageIcon(cpuMap.shipHitIcon));
							cpuMap.srcButton.setDisabledIcon(new ImageIcon(cpuMap.shipHitIcon));
							cpuMap.srcButton.setContentAreaFilled(true);
							// Disable all JButtons
							for (int i = 0; i < 10; i++) {
								for (int j = 0; j < 10; j++) {
									cpuMap.cpuGrid[i][j].setEnabled(false);
								}
							}
							// Loop through cpuShips and check which ship was hit
							for (int i = 0; i < 5; i++) {
								if (CpuMap.isBetween(cpuMap.cpuShips[i].start, cpuMap.cpuShips[i].end,
										cpuMap.userSelection)) {
									cpuMap.cpuShips[i].life--;
									if (cpuMap.cpuShips[i].life == 0 && !cpuMap.cpuShips[i].isSunk) {
										cpuMap.cpuShips[i].isSunk = true;
										if (!gameMode.isClassic)
											cpuMap.shotsPerTurn--;
										cpuMap.userScore += 500; // Update cpu's score
										cpuMap.displayUserScore.setText(" Score: " + cpuMap.userScore);
									} else {
										cpuMap.userScore += 200; // Update cpu's score
										cpuMap.displayUserScore.setText(" Score: " + cpuMap.userScore);
									}
									break;
								}
							}
							cpuMap.hitIcon.setVisible(true); // Set hit icon visible
							hideHitIcon.start(); // Start timer
						} else { // If miss
							cpuMap.missIcon.setVisible(true); // Set miss icon visible
							// Disable all JButtons
							for (int i = 0; i < 10; i++) {
								for (int j = 0; j < 10; j++) {
									cpuMap.cpuGrid[i][j].setEnabled(false);
								}
							}
							hideMissIcon.start(); // Start timer
							// Disable JButton and set ship miss icon
							cpuMap.srcButton.setIcon(new ImageIcon(cpuMap.shipMissIcon));
							cpuMap.srcButton.setDisabledIcon(new ImageIcon(cpuMap.shipMissIcon));
							cpuMap.srcButton.setContentAreaFilled(true);
							cpuMap.srcButton.setEnabled(false);
						}
						if (gameMode.isClassic) { // If classic mode
							cpuMap.viewYourShipsButton.setEnabled(false); // Disable JButton
							cpuMap.viewYourShipsButton.setDisabledIcon(new ImageIcon(cpuMap.viewYourShipsIcon));
							// If all ships sunk, game over
							if (cpuMap.cpuShips[0].isSunk && cpuMap.cpuShips[1].isSunk && cpuMap.cpuShips[2].isSunk
									&& cpuMap.cpuShips[3].isSunk && cpuMap.cpuShips[4].isSunk)
								switchToGameOver.start(); // Start timer
							else {
								// Update remaining shots
								cpuMap.remainingUserShots.setText(" Shots Remaining: "
										+ (userMap.shotsPerTurn - userMap.shotNumber) + "/" + userMap.shotsPerTurn);
								switchToCpuTurn.start(); // Start timer
							}
						} else { // If salvo mode
							// If all ships sunk, game over
							if (cpuMap.cpuShips[0].isSunk && cpuMap.cpuShips[1].isSunk && cpuMap.cpuShips[2].isSunk
									&& cpuMap.cpuShips[3].isSunk && cpuMap.cpuShips[4].isSunk)
								switchToGameOver.start(); // Start timer
							else {
								if (userMap.shotNumber == userMap.shotsPerTurn) { // If all shots used up
									// Update remaining shots
									cpuMap.remainingUserShots.setText(" Shots Remaining: "
											+ (userMap.shotsPerTurn - userMap.shotNumber) + "/" + userMap.shotsPerTurn);
									userMap.shotNumber = 1;
									// Disable all buttons
									for (int i = 0; i < 10; i++) {
										for (int j = 0; j < 10; j++) {
											cpuMap.cpuGrid[i][j].setEnabled(false);
										}
									}
									// Disable JButton
									cpuMap.viewYourShipsButton.setEnabled(false);
									cpuMap.viewYourShipsButton.setDisabledIcon(new ImageIcon(cpuMap.viewYourShipsIcon));
									switchToCpuTurn.start(); // Start timer
								} else {
									// Update remaining shots
									cpuMap.remainingUserShots.setText(" Shots Remaining: "
											+ (userMap.shotsPerTurn - userMap.shotNumber) + "/" + userMap.shotsPerTurn);
									userMap.shotNumber++;
								}
							}
						}
					}
				});
			}
		}
		// Add ActionListener to battleButton
		userMap.battleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameMode.isClassic)
					cpuMap.remainingUserShots
							.setText(" Shots Remaining: " + userMap.shotsPerTurn + "/" + userMap.shotsPerTurn);
				else
					cpuMap.remainingUserShots
							.setText(" Shots Remaining: " + userMap.shotsPerTurn + "/" + userMap.shotsPerTurn);
				userMap.remainingCpuShots.setVisible(true);
				userMap.remove(backArrowButton);
				userMap.remove(userMap.battleButton);
				userMap.remove(userMap.positioning);
				userMap.remove(userMap.displayShipIcons);
				layout1.show(frame.getContentPane(), "cpuMap");
				cpuMap.timerGameLength.setInitialDelay(0);
				cpuMap.timerGameLength.start();
			}
		});
		// Add ActionListener to viewYourShipsButton
		cpuMap.viewYourShipsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userMap.add(cpuMap.timeRemaining);
				userMap.hitIcon.setVisible(false);
				userMap.missIcon.setVisible(false);
				userMap.backToEnemyShipsButton.setVisible(true);
				layout1.show(frame.getContentPane(), "userMap");
			}
		});
		// Add ActionListener to backToEnemyShipsButton
		userMap.backToEnemyShipsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cpuMap.add(cpuMap.timeRemaining);
				layout1.show(frame.getContentPane(), "cpuMap");
			}
		});
		// Add ActionListener to toMenuButton
		gameMechanics2.toMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout1.show(frame.getContentPane(), "menu");
			}
		});
		// Set backArrowButton to desired properties
		backArrowButton.setContentAreaFilled(false);
		backArrowButton.setBorder(null);
		backArrowButton.setIcon(new ImageIcon(backArrowIcon));
		backArrowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (showTopCard(frame.getContentPane()).equals("rules")) {
					layout1.show(frame.getContentPane(), "menu");
				} else if (showTopCard(frame.getContentPane()).equals("modeTypes")) {
					// Add JButtons
					rules.add(nextArrowButton);
					rules.add(backArrowButton);
					layout1.show(frame.getContentPane(), "rules");
				} else if (showTopCard(frame.getContentPane()).equals("gameMechanics1")) {
					// Add JButtons
					modeTypes.add(nextArrowButton);
					modeTypes.add(backArrowButton);
					layout1.show(frame.getContentPane(), "modeTypes");
				} else if (showTopCard(frame.getContentPane()).equals("gameMechanics2")) {
					// Add JButtons
					gameMechanics1.add(nextArrowButton);
					gameMechanics1.add(backArrowButton);
					layout1.show(frame.getContentPane(), "gameMechanics1");
				} else if (showTopCard(frame.getContentPane()).equals("gameMode")) {
					userMap.battleButton.setVisible(false);
					userMap.userShipIndex = 0;
					UserMap.userGridPointsTaken.clear();
					userMap.displayShipIcons.setIcon(new ImageIcon(userMap.shipIcons[userMap.userShipIndex]));
					userMap.userBattlefield.restart(); // Reset userBattlefield
					// Reset userShips
					for (int i = 0; i < 5; i++) {
						userMap.userShips[i].restart();
					}
					// Reset JButtons
					for (int o = 0; o < 10; o++) {
						for (int p = 0; p < 10; p++) {
							UserMap.userGrid[o][p].setIcon(null);
							UserMap.userGrid[o][p].setContentAreaFilled(false);
							UserMap.userGrid[o][p].setEnabled(true);
						}
					}
					layout1.show(frame.getContentPane(), "menu");
				} else if (showTopCard(frame.getContentPane()).equals("userMap")) {
					gameMode.add(backArrowButton);
					layout1.show(frame.getContentPane(), "gameMode");
				}
			}
		});
		// Add MouseListener to backArrowButton
		backArrowButton.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				backArrowButton.setIcon(new ImageIcon(backArrowHoverIcon));
			}

			public void mouseExited(MouseEvent e) {
				backArrowButton.setIcon(new ImageIcon(backArrowIcon));
			}

			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				backArrowButton.setIcon(new ImageIcon(backArrowIcon));
			}

			public void mouseReleased(MouseEvent e) {
				backArrowButton.setIcon(new ImageIcon(backArrowHoverIcon));
			}
		});
		// Set nextArrowButton to desired properties
		nextArrowButton.setBounds(460, 540, 180, 60);
		nextArrowButton.setBorder(null);
		nextArrowButton.setContentAreaFilled(false);
		nextArrowButton.setIcon(new ImageIcon(nextArrowIcon));
		nextArrowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showTopCard(frame.getContentPane()).equals("rules")) {
					modeTypes.add(nextArrowButton);
					modeTypes.add(backArrowButton);
					layout1.show(frame.getContentPane(), "modeTypes");
				} else if (showTopCard(frame.getContentPane()).equals("modeTypes")) {
					gameMechanics1.add(nextArrowButton);
					gameMechanics1.add(backArrowButton);
					layout1.show(frame.getContentPane(), "gameMechanics1");
				} else if (showTopCard(frame.getContentPane()).equals("gameMechanics1")) {
					gameMechanics2.add(backArrowButton);
					layout1.show(frame.getContentPane(), "gameMechanics2");
				} else if (showTopCard(frame.getContentPane()).equals("gameMode")) {
					layout1.show(frame.getContentPane(), "userMap");
					userMap.add(backArrowButton);
				}
			}
		});
		// Add MouseListener to nextArrowButton
		nextArrowButton.addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
				nextArrowButton.setIcon(new ImageIcon(nextArrowHoverIcon));
			}

			public void mouseExited(MouseEvent e) {
				nextArrowButton.setIcon(new ImageIcon(nextArrowIcon));
			}

			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				nextArrowButton.setIcon(new ImageIcon(nextArrowIcon));
			}

			public void mouseReleased(MouseEvent e) {
				nextArrowButton.setIcon(new ImageIcon(nextArrowHoverIcon));
			}
		});
		// Add ActionListener to playAgainButton
		gameOver.playAgainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				cpuBot.restart();
				userMap.restart();
				cpuMap.restart();
				Game window = new Game();
				window.frame.setVisible(true);
			}
		});
	}
}