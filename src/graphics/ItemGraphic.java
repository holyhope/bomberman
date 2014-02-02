package graphics;

import java.awt.Graphics;
import java.awt.Point;

public interface ItemGraphic {
	public Object getParent();
	public void paint(Graphics graph);
	public Point getBoardPosition();
}
