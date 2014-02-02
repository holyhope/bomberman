package game;

import java.awt.Point;
import java.util.Random;

@SuppressWarnings("serial")
final class BuffedWall extends Wall {
	
	/**
	 * Constructor for a BuffedWall.
	 * 
	 * @param game game in which the buffedwall will be created.
	 * @param position position of the buffedwall.
	 */
	protected BuffedWall(Game game, Point position) {
		super(game, position);
	}

	/**
	 * child method inherited from Item
	 * This method is called when the buffedwall is destructed and thus creates a new buff
	 * 
	 * @return a new buff
	 */
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
