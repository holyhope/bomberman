package game;

import java.awt.Point;

@SuppressWarnings("serial")

public class Wall extends Item {
	
	/**
	 * Constructor for a Wall.
	 * 
	 * @param game game in which the buff wall be created.
	 * @param position position of the wall.
	 */
	protected Wall(Game game, Point position) {
		super(game, position);
	}

	/**
	 * Method toString for a wall
	 * 
	 * @return a String representation of the wall.
	 */
	@Override
	public String toString() {
		return "Wall "+super.toString();
	}
	
	/**
	 * blockExplosion method inherited from Item
	 * 
	 * @return true
	 */
	public boolean blockExplosion() {
		return true;
	}
	
	/**
	 * destructible method inherited from Item
	 * 
	 * @return true
	 */
	public boolean destructible() {
		return true;
	}

	/**
	 * blockWalk method inherited from Item
	 * 
	 * @return true
	 */
	@Override
	public boolean blockWalk() {
		return true;
	}

	/**
	 * Method equals for a Wall.
	 * 
	 * @param obj the object tested on the equality.
	 * @return true if obj is equal to the wall, false otherwise.
	 */
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof Wall))
			return false;
		return super.equals(obj);
	}
}
