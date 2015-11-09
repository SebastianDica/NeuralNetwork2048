package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	public Game2048(TerminalPanel terminal,int initialSize, int randomValues)
	{
		this.terminal = terminal;
		this.initialSize = initialSize;
		this.randomValues = randomValues;
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
					if(table[i][currentColumn] == table[i][u])
					{
						table[i][currentColumn] = 0;
						table[i][u] *= 2;
						entries[i][u] = true;
						entries[i][currentColumn] = false;
					}
					else
					if(table[i][u] == 0)
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
					if(table[i][currentColumn] == table[i][u])
					{
						table[i][currentColumn] = 0;
						table[i][u] *= 2;
						entries[i][u] = true;
						entries[i][currentColumn] = false;
					}
					else
					if(table[i][u] == 0)
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
					if(table[currentRow][j] == table[u][j])
					{
						table[currentRow][j] = 0;
						table[u][j] *= 2;
						entries[u][j] = true;
						entries[currentRow][j] = false;
					}
					else
					if(table[u][j] == 0)
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
					if(table[currentRow][j] == table[u][j])
					{
						table[currentRow][j] = 0;
						table[u][j] *= 2;
						entries[u][j] = true;
						entries[currentRow][j] = false;
					}
					else
					if(table[u][j] == 0)
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
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			moveUp();
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			moveDown();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			moveRight();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			moveLeft();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
