package game;

import java.awt.Point;

class Speed {
	private long time = 100;
	private long range = 30;
	private Direction direction = new Direction();

	/**
	 * Calls moveX and moveY to update the current position
	 * 
	 * @param position the position before update
	 * @param time the duration of the movement
	 * @return a new Point representing the updated position
	 */
	Point move(Point position, long time) {
		return new Point(moveX(position.x, time), moveY(position.y, time));
	}

	/**
	 * Updates the horizontal position x using the horizontal direction and the time
	 * 
	 * @param x the horizontal position to update
	 * @param time the duration of the movement
	 * @return the updated position
	 */
	int moveX(int x, long time) {
		return (int) (x+(time*range*direction.x)/this.time);
	}

	/**
	 * Updates the vertical position y using the vertical direction and the time
	 * 
	 * @param y the vertical position to update
	 * @param time the duration of the movement
	 * @return the updated position
	 */
	int moveY(int y, long time) {
		return (int) (y+(time*range*direction.y)/this.time);
	}
	
	/**
	 * getter for the x value of direction
	 * 
	 * @return direction.x
	 */
	public int getDirectionX() {
		return direction.x;
	}
	
	/**
	 * getter for the y value of direction
	 * 
	 * @return direction.y
	 */
	public int getDirectionY() {
		return direction.y;
	}
	
	/**
	 * setter for the horizontal direction
	 * 
	 * @param x the direction (left / right) to update
	 * @return the horizontal direction x updated
	 */
	public int setDirectionX(int x) {
		return direction.x = x>0?1:x==0?0:-1;
	}
	
	/**
	 * setter for the vertical direction
	 * 
	 * @param y the direction (up / down) to update
	 * @return the vertical direction y updated
	 */
	public int setDirectionY(int y) {
		return direction.y = y>0?1:y==0?0:-1;
	}
	
	/**
	 * setter for the speed
	 * 
	 * @param x the new speed given to range
	 */
	public void setSpeed(int x) {
		range = x;
	}
	
	/**
	 * getter for range
	 * 
	 * @return range
	 */
	public int getSpeed() {
		return (int) range;
	}
	
	/**
	 * Method clone for a Speed
	 * 
	 * @return a clone of the speed
	 */
	@Override
	protected Speed clone() {
		Speed speed = new Speed();
		speed.direction = direction.clone();
		speed.time = time;
		speed.range = range;
		return speed;
	}
}
