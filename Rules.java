import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Rules extends JPanel {
	// Declaration and Initialization
	// Line border and colour for Rules JPanel
	Color borderColor = new Color(0, 0, 0);
	LineBorder border = new LineBorder(borderColor, 3, true);
	// Image background
	Image rulesBackground = new ImageIcon(this.getClass().getResource("/Rules.jpg")).getImage();

	public Rules() { // Rules constructor
		// Set Rules to desired properties
		setBorder(border);
		setName("rules");
		setLayout(null);
	}

	/*
	 * This method paints the background of the JPanel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(rulesBackground, 0, 0, null);
	}
}
