package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import panels.TerminalPanel;

public class Game2048 extends Observable implements KeyListener
{
	private Integer[][] table;
	private boolean[][] entries;
	private TerminalPanel terminal;
	private int initialSize;
	private int randomValues;
	private boolean checkTyped = true; private boolean enabledInput = true;
	private int score;
	public Game2048(TerminalPanel terminal,int initialSize, int randomValues)
	{
		
		this.terminal = terminal;
		this.initialSize = initialSize;
		this.randomValues = randomValues;
		score = 0;
	}
	public boolean isEnabled()
	{
		return enabledInput;
	}
	public void enableInput(boolean value)
	{
		enabledInput = value;
	}
	public int getSize()
	{
		return initialSize;
	}
	public boolean initialiseGame()
	{
		table = new Integer[initialSize][initialSize];
		entries = new boolean[initialSize][initialSize];
		terminal.appendMessage("Rendering map... ");
		for(int i = 0; i < initialSize ; i++)
		{
			for(int j = 0 ; j < initialSize ; j++)
			{
				table[i][j] = 0;
				entries[i][j] = false;
			}
		}
		terminal.appendMessage("Randomising initial variables... ");
		for(int i = 0 ; i < randomValues ;i++)
		{
			randomizeEntrance();
		}
		return true;
	}
	public void currentState()
	{
		for(int i = 0 ; i < initialSize ; i++)
		{
			String result = "";
			for(int j = 0 ; j < initialSize; j++)
			{
				result += table[i][j] + " ";
			}
			terminal.appendMessage(result);
		}
		terminal.appendMessage("\n");
	}
	public void moveRight()
	{
		for(int j = initialSize - 2 ; j >= 0 ; j--)
		{
			for(int i = 0 ; i < initialSize ; i++)
			{
				int currentColumn = j;
				for(int u = j + 1; u < initialSize; u++)
				{
					if(table[i][currentColumn].equals(table[i][u]))
					{
						table[i][currentColumn] = 0;
						table[i][u] *= 2;
						score += table[i][u].intValue();
						entries[i][u] = true;
						entries[i][currentColumn] = false;
					}
					else
					if(table[i][u].equals(new Integer(0)))
					{
						table[i][u] = table[i][currentColumn];
						table[i][currentColumn] = 0;
						entries[i][u] = true;
						entries[i][currentColumn] = false;
						currentColumn++;
					}
					else
					{
						u = initialSize;
					}
				}
		}
		}
		randomizeEntrance();
		setChanged();
		notifyObservers();
	}
	public void moveLeft()
	{
		for(int j = 1; j < initialSize; j++)
		{
			for(int i = 0 ; i < initialSize ; i++)
			{
				int currentColumn = j;
				for(int u = j - 1; u >= 0; u--)
				{
					if(table[i][currentColumn].equals(table[i][u]))
					{
						table[i][currentColumn] = 0;
						table[i][u] *= 2;
						score += table[i][u].intValue();
						entries[i][u] = true;
						entries[i][currentColumn] = false;
					}
					else
					if(table[i][u].equals(new Integer(0)))
					{
						table[i][u] = table[i][currentColumn];
						table[i][currentColumn] = 0;
						entries[i][u] = true;
						entries[i][currentColumn] = false;
						currentColumn--;
					}
					else
					{
						u = -1;
					}
				}
			}
		}
		randomizeEntrance();
		setChanged();
		notifyObservers();
	}
	public void moveDown()
	{
		for(int i = initialSize - 2 ; i >= 0 ; i--)
		{
			for(int j = 0 ; j < initialSize ; j++)
			{
				int currentRow = i;
				for(int u = i + 1 ; u < initialSize ; u++)
				{
					if(table[currentRow][j].equals(table[u][j]))
					{
						table[currentRow][j] = 0;
						table[u][j] *= 2;
						score += table[i][u].intValue();
						entries[u][j] = true;
						entries[currentRow][j] = false;
					}
					else
					if(table[u][j].equals(new Integer(0)))
					{
						table[u][j] = table[currentRow][j];
						table[currentRow][j] = 0;
						entries[u][j] = true;
						entries[currentRow][j] = false;
						currentRow++;
					}
					else
					{
						u = initialSize;
					}
				}
			}
		}
		randomizeEntrance();
		setChanged();
		notifyObservers();
	}
	public void moveUp()
	{
		for(int i = 1 ; i < initialSize ; i++)
		{
			for(int j = 0 ; j < initialSize ; j++)
			{
				int currentRow = i;
				for(int u = i - 1 ; u >= 0 ; u--)
				{
					if(table[currentRow][j].equals(table[u][j]))
					{
						table[currentRow][j] = 0;
						table[u][j] *= 2;
						score += table[i][u].intValue();
						entries[u][j] = true;
						entries[currentRow][j] = false;
					}
					else
					if(table[u][j].equals(new Integer(0)))
					{
						table[u][j] = table[currentRow][j];
						table[currentRow][j] = 0;
						entries[u][j] = true;
						entries[currentRow][j] = false;
						currentRow--;
					}
					else
					{
						u = -1;
					}
				}
			}
		}
		randomizeEntrance();
		setChanged();
		notifyObservers();
	}
	public void randomizeEntrance()
	{
		Random generator = new Random();
		int freeEntries = getNumberOfFreeEntries();
		if(freeEntries == 0)
		{
			terminal.appendMessage("Game over.");
		}
		else
		{
			int randomEntry = generator.nextInt(freeEntries);
			int entryValue = generator.nextInt(5);
			if(entryValue < 4) entryValue = 2;
			else entryValue = 4; int count = 0;
			for(int i = 0 ; i < initialSize ; i++)
			{
				for(int j = 0 ; j < initialSize ; j++)
				{
					if(entries[i][j] == false && count == randomEntry)
					{
						entries[i][j] = true;
						table[i][j] = entryValue;
						i = initialSize; j = initialSize;
					}
					else
					{
						if(entries[i][j] == false) count++;
					}
				}
			}
		}
	}
	public Integer[][] getMap()
	{
		return table;
	}
	public int getNumberOfFreeEntries()
	{
		int count = 0;
		for(int i = 0; i < initialSize ; i++)
		{
			for(int j = 0 ; j < initialSize ; j++)
			{
				if(entries[i][j] == false)
				{
					count++;
				}
			}
		}
		return count;
	}
	public boolean moveUpPossible()
	{
		boolean[] flags = new boolean[initialSize];int counter = 0;
		for(int j = 0 ; j < initialSize ; j++)
		{
			ArrayList<Integer> sequence = new ArrayList<Integer>();
			for(int i = 0 ; i < initialSize ;i++) sequence.add(table[i][j]);
			if(consecutiveNumbers(sequence) || zeroBetween(sequence)) flags[counter] = true;
			else flags[counter] = false; counter++;
		}
		for(int count = 0 ; count < flags.length ; count++) if(flags[count] == true) return true;
		return false;
	}
	public boolean moveDownPossible()
	{
		boolean[] flags = new boolean[initialSize];int counter = 0;
		for(int j = 0 ; j < initialSize ; j++)
		{
			ArrayList<Integer> sequence = new ArrayList<Integer>();
			for(int i = initialSize - 1 ; i >= 0 ; i--) sequence.add(table[i][j]);
			if(consecutiveNumbers(sequence) || zeroBetween(sequence)) flags[counter] = true;
			else flags[counter] = false; counter++;
		}
		for(int count = 0 ; count < flags.length ; count++) if(flags[count] == true) return true;
		return false;
	}
	public boolean moveRightPossible()
	{
		boolean[] flags = new boolean[initialSize];int counter = 0;
		for(int i = 0 ; i < initialSize ; i++)
		{
			ArrayList<Integer> sequence = new ArrayList<Integer>();
			for(int j = initialSize - 1 ; j >= 0 ; j--) sequence.add(table[i][j]);
			if(consecutiveNumbers(sequence) || zeroBetween(sequence)) flags[counter] = true;
			else flags[counter] = false; counter++;
		}
		for(int count = 0 ; count < flags.length ; count++) if(flags[count] == true) return true;
		return false;
	}
	public boolean moveLeftPossible()
	{
		boolean[] flags = new boolean[initialSize];int counter = 0;
		for(int i = 0 ; i < initialSize ; i++)
		{
			ArrayList<Integer> sequence = new ArrayList<Integer>();
			for(int j = 0 ; j < initialSize ; j++) sequence.add(table[i][j]);
			if(consecutiveNumbers(sequence) || zeroBetween(sequence)) flags[counter] = true;
			else flags[counter] = false; counter++;
		}
		for(int count = 0 ; count < flags.length ; count++) if(flags[count] == true) return true;
		return false;
	}
	public boolean consecutiveNumbers(ArrayList<Integer> sequence)
	{
		for(int i = 1 ; i < sequence.size(); i++)
		{
			if(sequence.get(i-1).equals(sequence.get(i)) && 
					!sequence.get(i).equals(new Integer(0))) return true;
		}
		return false;
	}
	public boolean zeroBetween(ArrayList<Integer> sequence)
	{
		boolean check = false;
		for(int i = 0 ; i < sequence.size() ; i++)
		{
			if(!sequence.get(i).equals(new Integer(0))) check = true;
		}
		if(check==false) return false;
		check = false;
		if(sequence.get(0).equals(new Integer(0))) return true;
		else
		{
			for(int i = 1 ; i < sequence.size(); i++)
			{
				if(sequence.get(i) .equals(new Integer(0))) check = true;
				if(!sequence.get(i).equals(new Integer(0)) && check == true) return true;
			}
		}
		return false;
	}
	public boolean checkEndGame()
	{
		if(!moveUpPossible() && !moveDownPossible() && !moveLeftPossible() && !moveRightPossible())
			return true;
		return false;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(checkTyped && enabledInput)
		{
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			if(moveUpPossible())
			moveUp();
			else
			terminal.appendMessage("Move up is not possible");
			terminal.appendMessage("Score is: " + score);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			if(moveDownPossible())
			moveDown();
			else
			terminal.appendMessage("Move down is not possible");
			terminal.appendMessage("Score is: " + score);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(moveRightPossible())
			moveRight();
			else
			terminal.appendMessage("Move right is not possible");
			terminal.appendMessage("Score is: " + score);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(moveLeftPossible())
			moveLeft();
			else
			terminal.appendMessage("Move left is not possible");
			terminal.appendMessage("Score is: " + score);
		}
		}
		checkTyped = false;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		checkTyped = true;	
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
