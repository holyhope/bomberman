package game;

import java.awt.Point;

@SuppressWarnings("serial")
public class RangeBuff extends Buff {
	protected RangeBuff(Game game, Point position) {
		super(game, position);
	}

	@Override
	protected void walkedTrough(Player player) {
		player.addRange(1);
		super.walkedTrough(player);
	}

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof RangeBuff))
			return false;
		return super.equals(obj);
	}
}
