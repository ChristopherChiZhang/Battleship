import java.awt.Color;
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
public class GameMechanics2 extends JPanel {
	// Declaration and Initialization
	Color borderColor = new Color(0, 0, 0);
	LineBorder border = new LineBorder(borderColor, 3, true);
	JButton toMenuButton = new JButton();
	// JLabel
	JLabel firingShips = new JLabel();
	// Image background
	Image gameMechanics2Background = new ImageIcon(this.getClass().getResource("/GameMechanics2.jpg")).getImage();
	Icon firingShipsIcon = new ImageIcon(this.getClass().getResource("/FiringShips.gif"));
	Icon toMenuButtonIcon = new ImageIcon(this.getClass().getResource("/ToMenuIcon.png"));
	Icon toMenuButtonHoverIcon = new ImageIcon(this.getClass().getResource("/ToMenuHoverIcon.png"));

	public GameMechanics2() {
		// Set gameMechanics2 to desired properties
		setBorder(border);
		setName("gameMechanics2");
		setLayout(null);
		// Set toMenuButton to desired properties and add it to gameMechanics2 object
		toMenuButton.setBounds(460, 540, 180, 60);
		toMenuButton.setContentAreaFilled(false);
		toMenuButton.setBorder(null);
		toMenuButton.setIcon(toMenuButtonIcon);
		toMenuButton.addMouseListener(new MouseListener() { // Add Mouse Listener to toMenuButton
			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
				toMenuButton.setIcon(toMenuButtonHoverIcon);
			}

			public void mouseExited(MouseEvent arg0) {
				toMenuButton.setIcon(toMenuButtonIcon);
			}

			public void mousePressed(MouseEvent arg0) {
				toMenuButton.setIcon(toMenuButtonIcon);
			}

			public void mouseReleased(MouseEvent arg0) {
				toMenuButton.setIcon(toMenuButtonHoverIcon);
			}
		});
		add(toMenuButton);
		// Set selectingModes to desired properties
		firingShips.setBounds(390, 110, 260, 240);
		firingShips.setIcon(firingShipsIcon);
		add(firingShips);
	}

	/*
	 * This method paints the background of the JPanel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(gameMechanics2Background, 0, 0, null);
	}
}
