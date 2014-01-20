package game;

import java.awt.Point;

@SuppressWarnings("serial")
public abstract class Item extends Point implements Cloneable {
	protected final Game game;
	protected int cost = 1;

	protected Item(Game game, Point position) {
		this.game = game;
		x = position.x;
		y = position.y;
	}

	public abstract boolean blockExplosion();
	public abstract boolean blockWalk();

	protected synchronized void update() {
		
	}

	protected void detroyed(Player player) {
		player.addPoints(cost);
	}

	protected void walkedTrough(Player player) {
	}

	protected Item child() {
		return new Ground(game, new Point(x, y));
	}

	@Override
	public String toString() {
		return "("+x+";"+y+")";
	}

	@Override
	public Item clone() {
		return (Item) super.clone();
	}

	public Point getBoardPosition() {
		return new Point((int)getX(), (int)getY());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Item))
			return false;
		Item item = (Item) obj;
		return item.game.equals(game) && super.equals(obj);
	}
}
