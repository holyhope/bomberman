package game;

import java.awt.Point;

@SuppressWarnings("serial")
public class Wall extends Item {
	protected Wall(Game game, Point position) {
		super(game, position);
	}

	@Override
	public String toString() {
		return "Wall "+super.toString();
	}
	
	public boolean blockExplosion() {
		return true;
	}
	
	public boolean destructible() {
		return true;
	}

	@Override
	public boolean blockWalk() {
		return true;
	}

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof Wall))
			return false;
		return super.equals(obj);
	}
}
