package game;

import java.awt.Point;

@SuppressWarnings("serial")
public class RangeBuff extends Buff {
	
	/**
	 * Constructor for a RangeBuff.
	 * 
	 * @param game game in which the rangebuff will be created.
	 * @param position position of the rangebuff.
	 */
	protected RangeBuff(Game game, Point position) {
		super(game, position);
	}

	/**
	 * walkedThrough method inherited from Buff
	 * 
	 * @param player the player who walked through the rangebuff.
	 */
	@Override
	protected void walkedTrough(Player player) {
		player.addRange(1);
		super.walkedTrough(player);
	}

	/**
	 * Method equals for a RangeBuff.
	 * 
	 * @param obj the object tested on the equality.
	 * @return true if obj is equal to the rangebuff, false otherwise.
	 */
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof RangeBuff))
			return false;
		return super.equals(obj);
	}
}
