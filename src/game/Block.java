package game;

import java.awt.Point;

@SuppressWarnings("serial")
final class Block extends Wall {
	
	/**
	 * Constructor for a Block.
	 * 
	 * @param game game in which the block will be created.
	 * @param position position of the block.
	 */
	protected Block(Game game, Point position) {
		super(game, position);
	}

	/**
	 * Method toString for a block
	 * 
	 * @return a String representation of the block.
	 */
	@Override
	public String toString() {
		return "Undestructible "+super.toString();
	}

	/**
	 * destructible method inherited from Item
	 * 
	 * @return false
	 */
	@Override
	public boolean destructible() {
		return false;
	}

	/**
	 * child method inherited from Item
	 * 
	 * @return the same block
	 */
	@Override
	protected Item child() {
		return this;
	}
}
