import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GameOver extends JPanel {
	// Declaration and Initialization
	// Line border and colour for GameOver JPanel
	Color borderColor = new Color(0, 0, 0);
	LineBorder border = new LineBorder(borderColor, 3, true);
	int userPoints = 0, opponentPoints = 0; // User's and opponents final score
	// JLabels
	JLabel yourScore = new JLabel(), opponentScore = new JLabel(), yourScoreNum = new JLabel(),
			opponentScoreNum = new JLabel();
	JLabel youWinGame = new JLabel(), youTiedGame = new JLabel(), opponentWinGame = new JLabel();
	JButton playAgainButton = new JButton(); // Play again JButton
	Font battleshipFont = new Font("Dead Kansas", Font.PLAIN, 30); // Font
	// Images & Icons for JButtons and JLabels
	Image playAgainButtonIcon = new ImageIcon(this.getClass().getResource("/PlayAgainButtonIcon.png")).getImage();
	Image playAgainButtonHoverIcon = new ImageIcon(this.getClass().getResource("/PlayAgainButtonHoverIcon.png"))
			.getImage();
	Image gameOverBackground = new ImageIcon(this.getClass().getResource("/GameOverBack.jpg")).getImage();
	Icon youWinGameIcon = new ImageIcon(this.getClass().getResource("/YouWinGameIcon.png"));
	Icon opponentWinGameIcon = new ImageIcon(this.getClass().getResource("/OpponentWinGameIcon.png"));
	Icon youTiedGameIcon = new ImageIcon(this.getClass().getResource("/YouTiedGameIcon.png"));
	Icon yourScoreIcon = new ImageIcon(this.getClass().getResource("/YourScoreIcon.png"));
	Icon opponentScoreIcon = new ImageIcon(this.getClass().getResource("/OpponentScoreIcon.png"));

	public GameOver() {
		// Set properties of GameOver object and add it to GameOver object
		setLayout(null);
		setName("gameOver");
		setBackground(new Color(191, 191, 191));
		setBorder(border);
		// Set desired properties of playButton and add it to GameOver object
		playAgainButton.setBounds(140, 475, 420, 60);
		playAgainButton.setContentAreaFilled(false);
		playAgainButton.setBorder(null);
		playAgainButton.setIcon(new ImageIcon(playAgainButtonIcon));
		playAgainButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
				playAgainButton.setIcon(new ImageIcon(playAgainButtonHoverIcon));
			}

			public void mouseExited(MouseEvent arg0) {
				playAgainButton.setIcon(new ImageIcon(playAgainButtonIcon));
			}

			public void mousePressed(MouseEvent arg0) {
				playAgainButton.setIcon(new ImageIcon(playAgainButtonIcon));
			}

			public void mouseReleased(MouseEvent arg0) {
				playAgainButton.setIcon(new ImageIcon(playAgainButtonHoverIcon));
			}
		});
		add(playAgainButton);
		// Set desired properties of yourScore and add it to GameOver object
		yourScore.setBounds(30, 130, 185, 45);
		yourScore.setIcon(yourScoreIcon);
		add(yourScore);
		// Set desired properties of opponentScore and add it to GameOver object
		opponentScore.setBounds(310, 130, 285, 45);
		opponentScore.setIcon(opponentScoreIcon);
		add(opponentScore);
		// Set desired properties of yourScoreNum and add it to GameOver object
		yourScoreNum.setBounds(220, 138, 120, 35);
		yourScoreNum.setFont(battleshipFont);
		yourScoreNum.setForeground(new Color(187, 25, 9));
		yourScoreNum.setText("");
		add(yourScoreNum);
		// Set desired properties of opponentScoreNum and add it to GameOver object
		opponentScoreNum.setBounds(600, 138, 120, 35);
		opponentScoreNum.setFont(battleshipFont);
		opponentScoreNum.setForeground(new Color(187, 25, 9));
		opponentScoreNum.setText("");
		add(opponentScoreNum);
		// Set desired properties of youWinGame and add it to GameOver object
		youWinGame.setIcon(youWinGameIcon);
		add(youWinGame);
		// Set desired properties of opponentWinGame and add it to GameOver object
		opponentWinGame.setIcon(opponentWinGameIcon);
		add(opponentWinGame);
		// Set desired properties of youTiedGame and add it to GameOver object
		youTiedGame.setIcon(youTiedGameIcon);
		add(youTiedGame);
	}

	/*
	 * This method paints the background of the JPanel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(gameOverBackground, 0, 0, null);
	}

}
