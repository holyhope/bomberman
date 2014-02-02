import game.Game;
import game.Player;
import graphics.GameFrame;

import java.awt.event.KeyEvent;

import controller.Controls;
import texturePacks.Default.TexturePack;


public class Main {
	
	/**
	 * Main method of the bomberman.
	 * 
	 * @param args arguments for the program.
	 */
	public static void main(String[] args) {
		Game game = new Game();

		Player pierre = game.addPlayer("Pierre");
		Player marwin = game.addPlayer("Marwin");

		GameFrame gameframe = TexturePack.create(game);
		if (pierre != null)
			gameframe.addPlayer(pierre, new Controls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_NUMPAD0));
		if (marwin != null)
			gameframe.addPlayer(marwin, new Controls(KeyEvent.VK_Z, KeyEvent.VK_S, KeyEvent.VK_Q, KeyEvent.VK_D, KeyEvent.VK_SPACE));
		game.startGame();
	}
}
