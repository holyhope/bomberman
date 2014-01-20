package game;

import java.awt.Point;

class Speed {
	private long time = 100;
	private long range = 30;
	private Direction direction = new Direction();

	Point move(Point position, long time) {
		return new Point(moveX(position.x, time), moveY(position.y, time));
	}

	int moveX(int x, long time) {
		return (int) (x+(time*range*direction.x)/this.time);
	}

	int moveY(int y, long time) {
		return (int) (y+(time*range*direction.y)/this.time);
	}
	
	public int getDirectionX() {
		return direction.x;
	}
	
	public int getDirectionY() {
		return direction.y;
	}
	
	public int setDirectionX(int x) {
		return direction.x = x>0?1:x==0?0:-1;
	}
	
	public int setDirectionY(int y) {
		return direction.y = y>0?1:y==0?0:-1;
	}
	
	public void setSpeed(int x) {
		range = x;
	}
	
	public int getSpeed() {
		return (int) range;
	}
	
	@Override
	protected Speed clone() {
		Speed speed = new Speed();
		speed.direction = direction.clone();
		speed.time = time;
		speed.range = range;
		return speed;
	}
}
