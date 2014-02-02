package game;

import java.awt.Point;

@SuppressWarnings("serial")
class Direction extends Point {
	
	/**
	 * Method creating and returning a clone of a Direction
	 * 
	 * @return a clone of the direction
	 */
	@Override
	public Direction clone() {
		return (Direction) super.clone();
	}
}
