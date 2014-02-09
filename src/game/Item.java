package game;

import java.awt.Point;

/**
 * @author Péronnet Pierre & Damis Marwin
 * 
 */
@SuppressWarnings("serial")
public abstract class Item extends Point implements Cloneable {
	protected final Game game;
	protected int cost = 1;

	/**
	 * Constructor for an Item.
	 * 
	 * @param game game in which the item will be created.
	 * @param position position of the item.
	 */
	protected Item(Game game, Point position) {
		this.game = game;
		x = position.x;
		y = position.y;
	}

	/**
	 * Determines if the item blocks the explosion.
	 * 
	 * @return true if it stop explosion propagation. Otherwise, return false.
	 */
	public abstract boolean blockExplosion();
	
	/**
	 * Determines if the item blocks the movement.
	 * 
	 * @return false if a player can walk through the item. Otherwise, return true. 
	 */
	public abstract boolean blockWalk();

	/**
	 * Update the item.
	 */
	protected  void update() {
		
	}

	/**
	 * Happens when the item is destroyed by a player.
	 * 
	 * @param player the player who destroyed the item.
	 */
	protected void detroyed(Player player) {
		player.addPoints(cost);
	}

	/**
	 * Happens when a player walk on the item.
	 * 
	 * @param player the player who walked through the item.
	 */
	protected void walkedTrough(Player player) {
	}

	/**
	 * Creates a new item that will replace the current item after its destruction.
	 * 
	 * @return new item.
	 */
	protected Item child() {
		return new Ground(game, new Point(x, y));
	}

	/**
	 * Method toString for an Item
	 * 
	 * @return a String representation of the item.
	 */
	@Override
	public String toString() {
		return "("+x+";"+y+")";
	}

	/**
	 * Method clone for an Item.
	 * 
	 * @return a clone of the item.
	 */
	@Override
	public Item clone() {
		return (Item) super.clone();
	}

	/**
	 * Finds the position of the item in the board.
	 * 
	 * @return the position of the item in the board.
	 */
	public Point getBoardPosition() {
		return new Point(x, y);
	}

	/**
	 * Method equals for an Item
	 * 
	 * @param obj the object tested on the equality.
	 * @return true if obj is equal to the item, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Item))
			return false;
		Item item = (Item) obj;
		return item.game.equals(game) && super.equals(obj);
	}
}
