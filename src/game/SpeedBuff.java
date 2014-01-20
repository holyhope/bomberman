package game;

import java.awt.Point;

@SuppressWarnings("serial")
public class SpeedBuff extends Buff {
	protected SpeedBuff(Game game, Point position) {
		super(game, position);
	}

	@Override
	protected void walkedTrough(Player player) {
		super.walkedTrough(player);
		player.addSpeed(5);
	}

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof SpeedBuff))
			return false;
		return super.equals(obj);
	}
}
