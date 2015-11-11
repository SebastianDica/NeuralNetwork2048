package panels;

import game.Game2048;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
public class MainActivityPanel extends JPanel implements Observer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GamePlayPanel gamePlay;
	private InMindPanel inMind;
	private JTextArea mainArea;
	private JTextField initialValueField;
	private JTextField randomField;
	private Game2048 game;
	public MainActivityPanel()
	{
		super();
		setBackground(new Color(24,24,24));
		mainArea = new JTextArea();
		game = new Game2048(null, 0, 0);
		mainView();
	}
	public void gameView(Game2048 game)
	{
		this.game = game;
		removeAll();
		game.initialiseGame();
		game.addObserver(this);
		gamePlay = new GamePlayPanel();
		gamePlay.createView(game.getSize(), game.getMap());
		inMind = new InMindPanel();
		gamePlay.setPreferredSize(new Dimension(500,325));
		inMind.setPreferredSize(new Dimension(595,325));
		setLayout(new BorderLayout(10,10));
		add(gamePlay,BorderLayout.WEST);
		add(inMind,BorderLayout.EAST);
		setFocusable(true);
		requestFocusInWindow();
		setFocusTraversalKeysEnabled(false);
		addKeyListener(game);
		revalidate();
		repaint();
	}

	public void up() {
		try {
			Robot bot = new Robot();
			requestFocusInWindow();
			bot.keyPress(KeyEvent.VK_UP);
			requestFocusInWindow();
			bot.keyRelease(KeyEvent.VK_UP);
			requestFocusInWindow();
		} catch (Exception e) {

		}

	}
	public void mainView()
	{
		removeAll();
		setLayout(new GridBagLayout());
		mainArea.setEditable(false);
       	mainArea.setBackground(new Color(24,24,24));
       	mainArea.setForeground(Color.RED);
       	mainArea.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
		JScrollPane scrollPane = new JScrollPane(mainArea);
        GridBagConstraints constraints = new GridBagConstraints();
       	constraints.gridwidth = GridBagConstraints.REMAINDER;
       	constraints.fill = GridBagConstraints.HORIZONTAL;
      	constraints.fill = GridBagConstraints.BOTH;
       	constraints.weightx = 1.0;
       	constraints.weighty = 1.0;
       	add(scrollPane, constraints);
       	mainArea.setText("");
       	revalidate();
       	repaint();
	}
	public void gameCreationView()
	{
		removeAll();
		setLayout(new GridBagLayout());
		GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.EAST;
        GridBagConstraints center = new GridBagConstraints();
        center.weightx = 2.0;
        center.fill  = GridBagConstraints.HORIZONTAL;
        center.gridwidth = GridBagConstraints.REMAINDER;
        
        JLabel initialValue = new JLabel("What size should the matrix be? ");
        initialValue.setForeground(Color.WHITE);
        initialValueField = new JTextField();
        	initialValueField.setForeground(Color.WHITE);
        	initialValueField.setBackground(new Color(40,40,40));
        JLabel randomValue = new JLabel("How many initial random numbers should there be?");
        randomValue.setForeground(Color.WHITE);
        randomField = new JTextField();
        	randomField.setForeground(Color.WHITE);
        	randomField.setBackground(new Color(40,40,40));
        add(initialValue,left); add(initialValueField,center);
        add(randomValue,left); add(randomField,center);
        mainArea.setEditable(false);
       	mainArea.setBackground(new Color(24,24,24));
       	mainArea.setForeground(Color.WHITE);
       	mainArea.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
		JScrollPane scrollPane = new JScrollPane(mainArea);
        GridBagConstraints constraints = new GridBagConstraints();
       	constraints.gridwidth = GridBagConstraints.REMAINDER;
       	constraints.fill = GridBagConstraints.HORIZONTAL;
      	constraints.fill = GridBagConstraints.BOTH;
       	constraints.weightx = 1.0;
       	constraints.weighty = 1.0;
       	add(scrollPane, constraints);
        revalidate();
        repaint();
	}
	public String getInitialValue()
	{
		return initialValueField.getText();
	}
	public String getRandomValue()
	{
		return randomField.getText();
	}
	public void alertMessage(String message)
	{
		mainArea.setText("");
		mainArea.setText(message);
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		gamePlay.createView(game.getSize(), game.getMap());	
	}
}
