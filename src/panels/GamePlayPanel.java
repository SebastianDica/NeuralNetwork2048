package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePlayPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GamePlayPanel()
	{
		setBackground(new Color(24,24,24));
	}
	public void createView(int rows,Integer[][] table)
	{
		removeAll();
		setLayout(new GridLayout(rows, rows));
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0 ; j < rows; j++)
			{
				JButton x;
				if(table[i][j] != 0)
				x = new JButton(table[i][j].toString());
				else
				x = new JButton();
				x.setEnabled(false);
				x.setBackground(new Color(20 + table[i][j]/8,20 + table[i][j]/8,20 + table[i][j]/8));
				x.setForeground(new Color(10,10,10));
				x.setFont(new Font(Font.MONOSPACED,Font.PLAIN,30));
				add(x);
			}
		}
		revalidate();
		repaint();
		
	}
}
