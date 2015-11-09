package panels;

import game.Game2048;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import terminal.CallHandler;

public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TerminalPanel terminal;
	private MainActivityPanel activity;
	public ControlPanel(MainActivityPanel activity, TerminalPanel terminal)
	{
		super();
		this.terminal = terminal;
		this.activity = activity;
		setBackground(Color.GRAY);
		createMainControlPanel();
	}
	public void createMainControlPanel()
	{
		terminal.appendMessage("Entering main view.");
		removeAll();
		JButton exit = new JButton("Exit System");
		exit.addActionListener(e ->
		{
			int exitOptionPane = JOptionPane.YES_NO_OPTION;
    		int answer = JOptionPane.showConfirmDialog (null, 
    				"Would you like to close this program?",
    				"Please don't close me :'(",exitOptionPane);
    		if(answer == JOptionPane.YES_OPTION)
    			{
    				System.exit(CallHandler.EC_SUCCESS);
    			}
		});
		JButton createGame = new JButton("Create game");
		createGame.addActionListener(e ->
		{
			activity.gameCreationView();
			submitCreationGame();
		});
		setLayout(new GridLayout(10,1,10,10));
		add(exit);
		add(createGame);
		revalidate();
		repaint();
	}
	public void submitCreationGame()
	{
		removeAll();
		JButton back = new JButton("Back");
		back.addActionListener(e ->
		{
			createMainControlPanel();	
		});
		JButton submit = new JButton("Submit");
		submit.addActionListener(e ->
		{
			String size = activity.getInitialValue();
			String random = activity.getRandomValue();
			try
			{
				int sizeI = Integer.parseInt(size);
				int randomI = Integer.parseInt(random);
				activity.gameView(new Game2048(terminal, sizeI, randomI));
			}
			catch(NumberFormatException nfe)
			{
				activity.alertMessage("Please insert numbers/digits and not "
						+ "letters or other characters.");
			}
		});
		add(back);
		add(submit);
		revalidate();
		repaint();
	}
}
