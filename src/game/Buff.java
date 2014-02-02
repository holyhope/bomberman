package game;

import java.awt.Point;

@SuppressWarnings("serial")
public abstract class Buff extends Item {
	
	/**
	 * Constructor for a Buff.
	 * 
	 * @param game game in which the buff will be created.
	 * @param position position of the buff.
	 */
	protected Buff(Game game, Point position) {
		super(game, position);
	}

	/**
	 * blockExplosion method inherited from Item
	 * 
	 * @return false
	 */
	@Override
	public final boolean blockExplosion() {
		return false;
	}

	/**
	 * destroyed method inherited from Item
	 * 
	 * @param player the player who destroyed the buff.
	 */
	@Override
	public final void detroyed(Player player) {
		player.addPoints(-cost);
	}
	
	/**
	 * walkedThrough method inherited from Item
	 * 
	 * @param player the player who walked through the buff.
	 */
	@Override
	protected void walkedTrough(Player player) {
		player.addPoints(cost);
		game.destroy(this);
	}

	/**
	 * Method toString for a buff
	 * 
	 * @return a String representation of the buff.
	 */
	@Override
	public String toString() {
		return "Buff "+super.toString();
	}

	/**
	 * blockWalk method inherited from Item
	 * 
	 * @return false
	 */
	@Override
	public final boolean blockWalk() {
		return false;
	}
}
