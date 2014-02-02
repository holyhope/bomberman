package game;

import java.awt.Point;

@SuppressWarnings("serial")
final class NotAWall extends Wall {
	
	/**
	 * Constructor for a NotAWall.
	 * 
	 * @param game game in which the notawall will be created.
	 * @param position position of the notawall.
	 */
	protected NotAWall(Game game, Point position) {
		super(game, position);
	}

	/**
	 * Method toString for a NotAWall
	 * 
	 * @return a String representation of the notawall.
	 */
	@Override
	public String toString() {
		return "Fake-Wall "+super.toString();
	}

	/**
	 * blockExplosion method inherited from Wall
	 * 
	 * @return false
	 */
	@Override
	public boolean blockExplosion() {
		return false;
	}

	/**
	 * blockWalk method inherited from Wall
	 * 
	 * @return false
	 */
	@Override
	public boolean blockWalk() {
		return false;
	}
}
