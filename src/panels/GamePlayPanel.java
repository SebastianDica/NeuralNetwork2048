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
				if(table[i][j] != 0)
				{
					x.setBackground(new Color(20 + 
							(int)(Math.log(table[i][j].doubleValue())/Math.log(2.0d))*21,20 + 
							(int)(Math.log(table[i][j].doubleValue())/Math.log(2.0d))*21,20 + 
							(int)(Math.log(table[i][j].doubleValue())/Math.log(2.0d))*21));
					if((Math.log(table[i][j].doubleValue())/Math.log(2.0d))<5)
						x.setForeground(Color.WHITE);
					else x.setForeground(Color.BLACK);
				}
				else
				{
					x.setBackground(new Color(20 ,20 ,20));
				}
				x.setFont(new Font(Font.MONOSPACED,Font.PLAIN,30));
				add(x);
			}
		}
		revalidate();
		repaint();
		
	}
}
