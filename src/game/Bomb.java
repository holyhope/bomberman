package game;

import java.awt.Point;
import java.util.Date;

@SuppressWarnings("serial")
public class Bomb extends Item {
	private final int range;
	private Date timer;
	
	protected Bomb(Game game, Point position, int range) {
		super(game, position);
		this.range = range;
	}

	@Override
	public Bomb clone() {
		return Bomb.create(game, this, timer, range);
	}

	@Override
	protected synchronized void update() {
		Date now = new Date();
		if (now.after(timer)) {
			game.explode(this);
		}
	}

	public int getRange() {
		return range;
	}
	
	@Override
	public String toString() {
		return "Bomb "+super.toString();
	}
	
	
	public boolean blockExplosion() {
		return true;
	}
	
	public boolean destructible() {
		return true;
	}

	protected static Bomb create(Game game, Point position, int timer, int range) {
		Date now = new Date();
		now = new Date(now.getTime()+timer);
		return create(game, position, new Date(now.getTime()+timer), range);
	}

	private static Bomb create(Game game, Point position, Date timer, int range) {
		Bomb bomb = new Bomb(game, position, range);
		bomb.timer = (Date) timer.clone();
		return bomb;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Bomb))
			return false;
		return super.equals(obj);
	}
	
	@Override
	public boolean blockWalk() {
		return true;
	}
	
	public Date getTimer() {
		return (Date) timer.clone();
	}
}
