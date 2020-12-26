import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Menu extends JPanel {
	// Declaration and Initialization
	// Images for JButtons
	Image menuBackground = new ImageIcon(this.getClass().getResource("/BattleshipTitle.jpg")).getImage();
	Image playButtonIcon = new ImageIcon(this.getClass().getResource("/PlayButtonIcon.png")).getImage();
	Image playButtonHoverIcon = new ImageIcon(this.getClass().getResource("/PlayButtonHoverIcon.png")).getImage();
	Image howToPlayButtonIcon = new ImageIcon(this.getClass().getResource("/HowToPlayButtonIcon.png")).getImage();
	Image howToPlayButtonHoverIcon = new ImageIcon(this.getClass().getResource("/HowToPlayButtonHoverIcon.png"))
			.getImage();
	Image quitButtonIcon = new ImageIcon(this.getClass().getResource("/QuitButtonIcon.png")).getImage();
	Image quitButtonHoverIcon = new ImageIcon(this.getClass().getResource("/QuitButtonHoverIcon.png")).getImage();
	// Line border and colour for Menu JPanel
	Color borderColor = new Color(0, 0, 0);
	LineBorder border = new LineBorder(borderColor, 3, true);
	// JButtons
	JButton playButton = new JButton(), howToPlayButton = new JButton(), quitButton = new JButton();

	public Menu() {
		// Set properties of Menu object
		setLayout(null);
		setName("menu");
		setBackground(new Color(191, 191, 191));
		setBorder(border);
		// Set desired properties of playButton
		playButton.setBounds(440, 475, 180, 60);
		playButton.setContentAreaFilled(false);
		playButton.setBorder(null);
		playButton.setIcon(new ImageIcon(playButtonIcon));
		// Add MouseListener to playButton
		playButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
				playButton.setIcon(new ImageIcon(playButtonHoverIcon));
			}

			public void mouseExited(MouseEvent arg0) {
				playButton.setIcon(new ImageIcon(playButtonIcon));
			}

			public void mousePressed(MouseEvent arg0) {
				playButton.setIcon(new ImageIcon(playButtonIcon));
			}

			public void mouseReleased(MouseEvent arg0) {
				playButton.setIcon(new ImageIcon(playButtonHoverIcon));
			}
		});
		// Set desired properties of howToPlayButton
		howToPlayButton.setBounds(310, 545, 300, 60);
		howToPlayButton.setContentAreaFilled(false);
		howToPlayButton.setBorder(null);
		howToPlayButton.setIcon(new ImageIcon(howToPlayButtonIcon));
		// Add MouseListener to howToPlayButton
		howToPlayButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {

			}

			public void mouseEntered(MouseEvent arg0) {
				howToPlayButton.setIcon(new ImageIcon(howToPlayButtonHoverIcon));
			}

			public void mouseExited(MouseEvent arg0) {
				howToPlayButton.setIcon(new ImageIcon(howToPlayButtonIcon));
			}

			public void mousePressed(MouseEvent arg0) {
				howToPlayButton.setIcon(new ImageIcon(howToPlayButtonIcon));
			}

			public void mouseReleased(MouseEvent arg0) {
				howToPlayButton.setIcon(new ImageIcon(howToPlayButtonHoverIcon));
			}
		});
		// Set desired properties of quitButton
		quitButton.setBounds(30, 545, 150, 60);
		quitButton.setContentAreaFilled(false);
		quitButton.setBorder(null);
		quitButton.setIcon(new ImageIcon(quitButtonIcon));
		// Add ActionListener to quitButton
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// Add MouseListener to quitButton
		quitButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
				quitButton.setIcon(new ImageIcon(quitButtonHoverIcon));
			}

			public void mouseExited(MouseEvent arg0) {
				quitButton.setIcon(new ImageIcon(quitButtonIcon));
			}

			public void mousePressed(MouseEvent arg0) {
				quitButton.setIcon(new ImageIcon(quitButtonIcon));
			}

			public void mouseReleased(MouseEvent arg0) {
				quitButton.setIcon(new ImageIcon(quitButtonHoverIcon));
			}
		});
		// Add JButtons to Menu object
		add(playButton);
		add(howToPlayButton);
		add(quitButton);
	}

	/*
	 * This method paints the background of the JPanel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(menuBackground, 0, 0, null);
	}
}
