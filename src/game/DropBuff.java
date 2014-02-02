package game;

import java.awt.Point;

@SuppressWarnings("serial")
public class DropBuff extends Buff {
	
	/**
	 * Constructor for a DropBuff.
	 * 
	 * @param game game in which the dropbuff will be created.
	 * @param position position of the dropbuff.
	 */
	protected DropBuff(Game game, Point position) {
		super(game, position);
	}

	/**
	 * walkedThrough method inherited from Buff
	 * 
	 * @param player the player who walked through the dropbuff.
	 */
	@Override
	protected void walkedTrough(Player player) {
		player.addCapacity(1);
		super.walkedTrough(player);
	}

	/**
	 * Method equals for a DropBuff.
	 * 
	 * @param obj the object tested on the equality.
	 * @return true if obj is equal to the dropbuff, false otherwise.
	 */
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof DropBuff))
			return false;
		return super.equals(obj);
	}
}
