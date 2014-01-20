package graphics;

import game.Game;
import game.LifeBuff;

import java.awt.Point;

@SuppressWarnings("serial")
public abstract class BuffLifeGraphic extends LifeBuff implements ItemGraphic {
	private ContentPane parent;

	protected BuffLifeGraphic(ContentPane parent, Game game, Point position) {
		super(game, position);
		this.parent = parent;
	}

	@Override
	public ContentPane getParent() {
		return parent;
	}
}
