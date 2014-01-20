package game;

import java.awt.Point;

@SuppressWarnings("serial")
public abstract class Buff extends Item {
	protected Buff(Game game, Point position) {
		super(game, position);
	}

	@Override
	public final boolean blockExplosion() {
		return false;
	}

	@Override
	public final void detroyed(Player player) {
		player.addPoints(-cost);
	}
	
	@Override
	protected void walkedTrough(Player player) {
		player.addPoints(cost);
		game.destroy(this);
	}

	@Override
	public String toString() {
		return "Buff "+super.toString();
	}

	@Override
	public final boolean blockWalk() {
		return false;
	}
}
