package graphics;

import game.Game;
import game.Wall;

import java.awt.Point;

@SuppressWarnings("serial")
public abstract class WallGraphic extends Wall implements ItemGraphic {
	private ContentPane parent;

	protected WallGraphic(ContentPane parent, Game game, Point position) {
		super(game, position);
		this.parent = parent;
	}

	public ContentPane getParent() {
		return parent;
	}
}
