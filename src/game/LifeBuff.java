package game;

import java.awt.Point;

@SuppressWarnings("serial")
public class LifeBuff extends Buff {
	
	/**
	 * Constructor for a LifeBuff.
	 * 
	 * @param game game in which the lifebuff will be created.
	 * @param position position of the lifebuff.
	 */
	protected LifeBuff(Game game, Point position) {
		super(game, position);
	}

	/**
	 * walkedThrough method inherited from Buff
	 * 
	 * @param player the player who walked through the lifebuff.
	 */
	@Override
	protected void walkedTrough(Player player) {
		player.addLife(1);
		super.walkedTrough(player);
	}

	/**
	 * Method equals for a LifeBuff.
	 * 
	 * @param obj the object tested on the equality.
	 * @return true if obj is equal to the lifebuff, false otherwise.
	 */
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof LifeBuff))
			return false;
		return super.equals(obj);
	}
}
