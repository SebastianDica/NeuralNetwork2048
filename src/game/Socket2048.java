package game;

import neuralnet.Socket2048NeuralNetwork;

public class Socket2048 {
	
	private Socket2048NeuralNetwork socket;
	private Game2048 game;
	public Socket2048(Game2048 game)
	{
		this.game = game;
		this.socket = new Socket2048NeuralNetwork(this);
	}
}
