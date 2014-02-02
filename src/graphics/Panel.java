package graphics;

import game.Game;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class Panel extends JPanel {
	private final GameFrame gameFrame;

	public Panel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		setSize(gameFrame.getSize());
		setVisible(true);
		setOpaque(false);
	}

	protected void setCursor(String path, String name) {
		Image cursor = GameFrame.openImage(path);
		Dimension size = Toolkit.getDefaultToolkit().getBestCursorSize(32, 32);
		Point position = new Point(size.width/2, size.height/2);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				cursor, position,name));
		this.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent arg0) {
				Dimension dimension = getParent().getSize();
				Rectangle border = GameFrame.getBorder();
				dimension.width-= border.width;
				dimension.height-= border.height;
				setSize(dimension);
			}
			
			@Override
			public void componentResized(ComponentEvent arg0) {}
			@Override
			public void componentMoved(ComponentEvent arg0) {}
			@Override
			public void componentHidden(ComponentEvent arg0) {}
		});
	}

	public GameFrame getFrame() {return gameFrame;}
	protected Game getGame() {return gameFrame.getGame();}
}
