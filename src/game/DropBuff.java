package game;

import java.awt.Point;

@SuppressWarnings("serial")
public class DropBuff extends Buff {
	protected DropBuff(Game game, Point position) {
		super(game, position);
	}

	@Override
	protected void walkedTrough(Player player) {
		player.addCapacity(1);
		super.walkedTrough(player);
	}

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof DropBuff))
			return false;
		return super.equals(obj);
	}
}
