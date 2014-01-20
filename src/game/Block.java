package game;

import java.awt.Point;

@SuppressWarnings("serial")
final class Block extends Wall {
	protected Block(Game game, Point position) {
		super(game, position);
	}

	@Override
	public String toString() {
		return "Undestructible "+super.toString();
	}

	@Override
	public boolean destructible() {
		return false;
	}

	@Override
	protected Item child() {
		return this;
	}
}
