package panels;

import game.Game2048;




import java.awt.AWTException;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;




import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;








import com.sun.glass.events.KeyEvent;




import terminal.CallHandler;

public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TerminalPanel terminal;
	private MainActivityPanel activity;
	private Robot bot;
	private Game2048 game;
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
			activity.mainView();
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
				game = new Game2048(terminal, sizeI, randomI);
				activity.gameView(game);
				inGameControls();
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
	public void inGameControls()
	{
		removeAll();
		JButton back = new JButton("Back");
		back.addActionListener(e ->{
			submitCreationGame();
			activity.gameCreationView();
		});
		JLabel disableKeyboardLabel = new JLabel("Keyboard is enabled");
		JButton disableKeyboard = new JButton("Disable/Enable keyboard");
		disableKeyboard.addActionListener(e ->{
			game.enableInput(!game.isEnabled());
			activity.requestFocusInWindow();
			if(game.isEnabled()) disableKeyboardLabel.setText("Keyboard is enabled");
			else disableKeyboardLabel.setText("Keyboard is disabled");
		});
		add(back);
		add(disableKeyboard);
		add(disableKeyboardLabel);
		revalidate();
		repaint();
	}
}
