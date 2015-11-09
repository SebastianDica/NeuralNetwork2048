package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TopPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TopPanel()
	{
		super();
		setBackground(new Color(45,45,45));
		JLabel intro = new JLabel("NeuralNet2048");
		intro.setPreferredSize(new Dimension(700,100));
		intro.setFont(new Font(Font.MONOSPACED,Font.PLAIN,18));
		intro.setForeground(Color.WHITE);
		add(intro,SwingConstants.CENTER);
	}
}
