package game;

import java.awt.Point;

@SuppressWarnings("serial")
final class NotAWall extends Wall {
	protected NotAWall(Game game, Point position) {
		super(game, position);
	}

	@Override
	public String toString() {
		return "Fake-Wall "+super.toString();
	}

	@Override
	public boolean blockExplosion() {
		return false;
	}

	@Override
	public boolean blockWalk() {
		return false;
	}
}
