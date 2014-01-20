package graphics;

import game.Game;
import game.SpeedBuff;

import java.awt.Point;

@SuppressWarnings("serial")
public abstract class BuffSpeedGraphic extends SpeedBuff implements ItemGraphic {
	private ContentPane parent;

	protected BuffSpeedGraphic(ContentPane parent, Game game, Point position) {
		super(game, position);
		this.parent = parent;
	}

	@Override
	public ContentPane getParent() {
		return parent;
	}
}
