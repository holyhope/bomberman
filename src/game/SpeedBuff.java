package game;

import java.awt.Point;

@SuppressWarnings("serial")
public class SpeedBuff extends Buff {
	
	/**
	 * Constructor for a SpeedBuff.
	 * 
	 * @param game game in which the speedbuff will be created.
	 * @param position position of the speedbuff.
	 */
	protected SpeedBuff(Game game, Point position) {
		super(game, position);
	}

	/**
	 * walkedThrough method inherited from Buff
	 * 
	 * @param player the player who walked through the speedbuff.
	 */
	@Override
	protected void walkedTrough(Player player) {
		super.walkedTrough(player);
		player.addSpeed(5);
	}

	/**
	 * Method equals for a SpeedBuff.
	 * 
	 * @param obj the object tested on the equality.
	 * @return true if obj is equal to the speedbuff, false otherwise.
	 */
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof SpeedBuff))
			return false;
		return super.equals(obj);
	}
}
