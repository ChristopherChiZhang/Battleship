import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GameMode extends JPanel {
	// Declaration and Initialization
	// Line border and colour for GameMode JPanel
	Color borderColor = new Color(0, 0, 0);
	LineBorder border = new LineBorder(borderColor, 3, true);
	// JLabels
	JLabel modeSelection = new JLabel(), timeSelection = new JLabel();
	JButton salvoModeButton = new JButton(), classicModeButton = new JButton();
	JButton fiveMinButton = new JButton(), tenMinButton = new JButton();
	boolean isClassic = true; // true if game mode is classic, false otherwise
	// Line borders for JButtons
	LineBorder lineBorder = new LineBorder(Color.green, 3, true);
	LineBorder lineBorder2 = new LineBorder(Color.black, 1, true);
	LineBorder lineBorder3 = new LineBorder(Color.white, 1, true);
	// Images & Icons for JButtons
	Image classicModeIcon = new ImageIcon(this.getClass().getResource("/ClassicIconButton.jpg")).getImage();
	Icon classicModeHoverIcon = new ImageIcon(this.getClass().getResource("/ClassicIconHoverButton.jpg"));
	Image salvoModeIcon = new ImageIcon(this.getClass().getResource("/SalvoIconButton.jpg")).getImage();
	Icon salvoModeHoverIcon = new ImageIcon(this.getClass().getResource("/SalvoIconHoverButton.jpg"));
	Image gameModeBackground = new ImageIcon(this.getClass().getResource("/GameModeBack.jpg")).getImage();
	Image fiveMinIcon = new ImageIcon(this.getClass().getResource("/FiveMinIcon.jpg")).getImage();
	Icon fiveMinHoverIcon = new ImageIcon(this.getClass().getResource("/FiveMinHoverIcon.jpg"));
	Image tenMinIcon = new ImageIcon(this.getClass().getResource("/TenMinIcon.jpg")).getImage();
	Icon tenMinHoverIcon = new ImageIcon(this.getClass().getResource("/TenMinHoverIcon.jpg"));
	Image selectGameMode = new ImageIcon(this.getClass().getResource("/SelectAGameMode.png")).getImage();
	Image selectTime = new ImageIcon(this.getClass().getResource("/SelectATime.png")).getImage();

	public GameMode() {
		// Set gameMode to desired properties and add it to GameMode object
		setBorder(border);
		setName("gameMode");
		setLayout(null);
		// Set modeSelection to desired properties and add it to GameMode object
		modeSelection.setBounds(125, 20, 450, 60);
		modeSelection.setIcon(new ImageIcon(selectGameMode));
		add(modeSelection);
		// Set modeSelection to desired properties and add it to GameMode object
		timeSelection.setBounds(200, 320, 300, 60);
		timeSelection.setIcon(new ImageIcon(selectTime));
		add(timeSelection);
		// Set salvoModeButton to desired properties and add it to GameMode object
		salvoModeButton.setBounds(390, 95, 250, 200);
		salvoModeButton.setIcon(new ImageIcon(salvoModeIcon));
		add(salvoModeButton);
		// Set classicModeButton to desired properties and add it to GameMode object
		classicModeButton.setBounds(60, 95, 250, 200);
		classicModeButton.setIcon(new ImageIcon(classicModeIcon));
		add(classicModeButton);
		// Set fiveMinButton to desired properties and add it to GameMode object
		fiveMinButton.setBounds(60, 400, 250, 100);
		fiveMinButton.setIcon(new ImageIcon(fiveMinIcon));
		add(fiveMinButton);
		// Set tenMinButton to desired properties and add it to GameMode object
		tenMinButton.setBounds(390, 400, 250, 100);
		tenMinButton.setIcon(new ImageIcon(tenMinIcon));
		add(tenMinButton);
	}

	/*
	 * This method paints the background of the JPanel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(gameModeBackground, 0, 0, null);
	}
}
