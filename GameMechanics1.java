import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GameMechanics1 extends JPanel {
	// Declaration and Initialization
	Color borderColor = new Color(0, 0, 0);
	LineBorder border = new LineBorder(borderColor, 3, true);
	// JLabel
	JLabel placingShips = new JLabel();
	// Image background
	Image gameMechanics1Background = new ImageIcon(this.getClass().getResource("/GameMechanics1.jpg")).getImage();
	Icon placingShipsIcon = new ImageIcon(this.getClass().getResource("/PlacingShips.gif"));

	public GameMechanics1() {
		// Set gameMechanics1 to desired properties
		setBorder(border);
		setName("gameMechanics1");
		setLayout(null);
		// Set selectingModes to desired properties
		placingShips.setBounds(390, 110, 260, 240);
		placingShips.setIcon(placingShipsIcon);
		add(placingShips);
	}

	/*
	 * This method paints the background of the JPanel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(gameMechanics1Background, 0, 0, null);
	}
}
