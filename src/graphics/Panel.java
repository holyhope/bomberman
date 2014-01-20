package graphics;

import game.Game;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class Panel extends JPanel {
	private GameFrame gameFrame;
	
	public Panel(GameFrame gameframe) {
		this.gameFrame = gameframe;
		setSize(gameframe.getSize());
		Dimension dimension = getParent().getMinimumSize();
		Rectangle border = getParent().getBorder();
		dimension.width-= border.width;
		dimension.height-= border.height;
		setMinimumSize(dimension);
	}

	@Override
	public GameFrame getParent() {
		return gameFrame;
	}

	protected Game getGame() {return getParent().getGame();}
}
