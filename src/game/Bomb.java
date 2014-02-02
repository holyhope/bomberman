package game;

import java.awt.Point;
import java.util.Date;

@SuppressWarnings("serial")
public class Bomb extends Item {
	private final int range;
	private Date timer;
	
	/**
	 * Constructor for a Bomb
	 * 
	 * @param game game in which the bomb will be created
	 * @param position position of the bomb
	 * @param range range of the bomb
	 */
	protected Bomb(Game game, Point position, int range) {
		super(game, position);
		this.range = range;
	}

	/**
	 * Method clone for a Bomb
	 * 
	 * @return a clone of the bomb
	 */
	@Override
	public Bomb clone() {
		return Bomb.create(game, this, timer, range);
	}

	/**
	 * Update method inherited from Item
	 * Checks the current date, the bomb will explode after the deadline
	 */
	@Override
	protected synchronized void update() {
		Date now = new Date();
		if (now.after(timer)) {
			game.explode(this);
		}
	}

	/**
	 * getter for range
	 * 
	 * @return range
	 */
	public int getRange() {
		return range;
	}
	
	/**
	 * Method toString for a Bomb
	 * 
	 * @return a String representation of the bomb.
	 */
	@Override
	public String toString() {
		return "Bomb "+super.toString();
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
	 * @return true
	 */
	public boolean destructible() {
		return true;
	}

	/**
	 * Factory method to create a bomb
	 * 
	 * @param game game in which the bomb will be created
	 * @param position position of the bomb
	 * @param timer timer of the bomb
	 * @param range range of the bomb
	 * @return the new bomb
	 */
	protected static Bomb create(Game game, Point position, int timer, int range) {
		Date now = new Date();
		now = new Date(now.getTime()+timer);
		return create(game, position, new Date(now.getTime()+timer), range);
	}

	/**
	 * Factory method to create a bomb
	 * 
	 * @param game game in which the bomb will be created
	 * @param position position of the bomb
	 * @param timer timer of the bomb
	 * @param range range of the bomb
	 * @return the new bomb
	 */
	private static Bomb create(Game game, Point position, Date timer, int range) {
		Bomb bomb = new Bomb(game, position, range);
		bomb.timer = (Date) timer.clone();
		return bomb;
	}
	
	/**
	 * Method equals for a Bomb.
	 * 
	 * @param obj the object tested on the equality.
	 * @return true if obj is equal to the bomb, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Bomb))
			return false;
		return super.equals(obj);
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
	 * getter for timer
	 * 
	 * @return a clone of timer
	 */
	public Date getTimer() {
		return (Date) timer.clone();
	}
}
