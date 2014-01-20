package game;

import java.awt.Point;

@SuppressWarnings("serial")
public class LifeBuff extends Buff {
	protected LifeBuff(Game game, Point position) {
		super(game, position);
	}

	@Override
	protected void walkedTrough(Player player) {
		player.addLife(1);
		super.walkedTrough(player);
	}

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof LifeBuff))
			return false;
		return super.equals(obj);
	}
}
