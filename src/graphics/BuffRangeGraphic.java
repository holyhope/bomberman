package graphics;

import game.Game;
import game.RangeBuff;

import java.awt.Point;

@SuppressWarnings("serial")
public abstract class BuffRangeGraphic extends RangeBuff implements ItemGraphic {
	private ContentPane parent;

	protected BuffRangeGraphic(ContentPane parent, Game game, Point position) {
		super(game, position);
		this.parent = parent;
	}

	@Override
	public ContentPane getParent() {
		return parent;
	}
}
