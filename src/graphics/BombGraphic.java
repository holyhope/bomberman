package graphics;

import game.Bomb;
import game.Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Date;

@SuppressWarnings("serial")
public abstract class BombGraphic extends Bomb implements ItemGraphic,TmpGraphic {
	private ContentPane parent;
	private int duration = 300;
	private Date end;

	protected BombGraphic(ContentPane parent, Game game, Point position, int range, Date end) {
		super(game, position, range);
		this.parent = parent;
		this.end = (Date) end.clone();
	}

	public ContentPane getParent() {
		return parent;
	}

	@Override
	public Date getEnd() {
		return (Date) end.clone();
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public void paint(Graphics graph) {
		Date now = new Date();
		if (now.after(end)) {
			if (now.getTime() < end.getTime()+duration) {
				die((Graphics2D) graph);
			} else {
				parent.remove(this);
			}
		} else {
			show((Graphics2D) graph);
		}
	}
}
