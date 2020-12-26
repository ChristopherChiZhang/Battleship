import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class ModeTypes extends JPanel {
	// Declaration and Initialization
	Color borderColor = new Color(0, 0, 0);
	LineBorder border = new LineBorder(borderColor, 3, true);
	// JLabel
	JLabel selectingModes = new JLabel();
	// Image background
	Image modeTypesBackground = new ImageIcon(this.getClass().getResource("/ModeTypes.jpg")).getImage();
	Icon selectingModesIcon = new ImageIcon(this.getClass().getResource("/SelectingMode.gif"));

	public ModeTypes() { // ModeTypes constructor
		// Set modeTypes to desired properties
		setBorder(border);
		setName("modeTypes");
		setLayout(null);
		// Set selectingModes to desired properties
		selectingModes.setBounds(370, 70, 260, 240);
		selectingModes.setIcon(selectingModesIcon);
		add(selectingModes);
	}

	/*
	 * This method paints the background of the JPanel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(modeTypesBackground, 0, 0, null);
	}
}
