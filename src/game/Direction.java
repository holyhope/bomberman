package game;

import java.awt.Point;

@SuppressWarnings("serial")
class Direction extends Point {
	@Override
	public Direction clone() {
		return (Direction) super.clone();
	}
}
