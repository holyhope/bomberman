package game;

import java.awt.Point;

public class Ground extends Item {
	Ground(Game game, Point position) {
		super(game, position);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Ground "+super.toString();
	}

	public boolean blockExplosion() {
		return false;
	}
	
	public boolean destructible() {
		return false;
	}

	@Override
	public boolean blockWalk() {
		return false;
	}

	

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof Ground))
			return false;
		return super.equals(obj);
	}
}
