package game;

import java.awt.Point;
import java.util.Random;

@SuppressWarnings("serial")
final class BuffedWall extends Wall {
	protected BuffedWall(Game game, Point position) {
		super(game, position);
	}

	@Override
	protected Item child() {
		Random random = new Random();
		switch (random.nextInt(6)) {
		case 0:case 1:
			return new RangeBuff(game, this);
		case 2:case 3:
			return new SpeedBuff(game, this);
		case 4:case 5:
			return new DropBuff(game, this);
		case 6:
			return new LifeBuff(game, this);
		default:
			return super.child();
		}
	}
}
