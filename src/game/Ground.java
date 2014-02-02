package game;

import java.awt.Point;

public class Ground extends Item {
	
	/**
	 * Constructor for a Ground.
	 * 
	 * @param game game in which the ground will be created.
	 * @param position position of the ground.
	 */
	Ground(Game game, Point position) {
		super(game, position);
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Method toString for a Ground
	 * 
	 * @return a String representation of the ground.
	 */
	@Override
	public String toString() {
		return "Ground "+super.toString();
	}

	/**
	 * blockExplosion method inherited from Item
	 * 
	 * @return false
	 */
	public boolean blockExplosion() {
		return false;
	}
	
	/**
	 * destructible method inherited from Item
	 * 
	 * @return false
	 */
	public boolean destructible() {
		return false;
	}

	/**
	 * blockWalk method inherited from Item
	 * 
	 * @return false
	 */
	@Override
	public boolean blockWalk() {
		return false;
	}

	/**
	 * Method equals for a Ground.
	 * 
	 * @param obj the object tested on the equality.
	 * @return true if obj is equal to the ground, false otherwise.
	 */
	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof Ground))
			return false;
		return super.equals(obj);
	}
}
