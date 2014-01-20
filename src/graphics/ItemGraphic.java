package graphics;

import java.awt.Graphics;

public interface ItemGraphic {
	public ContentPane getParent();
	public void paint(Graphics graph);
}
