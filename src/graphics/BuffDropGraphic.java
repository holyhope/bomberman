package graphics;

import game.DropBuff;
import game.Game;

import java.awt.Point;

@SuppressWarnings("serial")
public abstract class BuffDropGraphic extends DropBuff implements ItemGraphic {
	private ContentPane parent;

	protected BuffDropGraphic(ContentPane parent, Game game, Point position) {
		super(game, position);
		this.parent = parent;
	}

	@Override
	public ContentPane getParent() {
		return parent;
	}
}
