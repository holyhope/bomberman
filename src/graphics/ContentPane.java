package graphics;

import game.Bomb;
import game.DropBuff;
import game.Game;
import game.Ground;
import game.Item;
import game.LifeBuff;
import game.RangeBuff;
import game.SpeedBuff;
import game.Wall;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@SuppressWarnings("serial")
public abstract class ContentPane extends Panel {
	private ItemGraphic board[][];
	private ArrayList<TmpGraphic> dead = new ArrayList<TmpGraphic>();

	public ContentPane(GameFrame gameFrame) {
		super(gameFrame);
		Dimension dimension = gameFrame.getGame().getBoardSize();
		board = new ItemGraphic[dimension.width][dimension.height];
	}

	protected  void drawPlayers(Graphics g) {
		for (PlayerGraphic player: getFrame().getPlayerList())
			player.paint(g);
	}

	protected abstract BombGraphic newBomb(Bomb bomb);
	protected abstract WallGraphic newWall(Wall wall);
	protected abstract BuffSpeedGraphic newSpeedBuff(SpeedBuff buff);
	protected abstract BuffRangeGraphic newRangeBuff(RangeBuff buff);
	protected abstract BuffLifeGraphic newLifeBuff(LifeBuff buff);
	protected abstract BuffDropGraphic newDropBuff(DropBuff buff);

	protected void drawImage(Graphics g, Image image, Point position) {
		Dimension size = imageSize();
		g.drawImage(image, position.x, position.y, size.width, size.height, this);
	}

	public void board2Graphic(Point position) {
		getFrame().board2Graphic(position);
	}

	protected void positionGraphic(Point position) {
		getFrame().positionGraphic(position);
	}

	public Dimension imageSize() {return getFrame().imageSize();}

	private void drawAll(Graphics2D g2d) {
		drawDeads(g2d);
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board[i].length; j++) {
				getItem(i, j).paint(g2d);
			}
		}
		drawPlayers(g2d);
	}
	
	private void drawDeads(Graphics2D g2d) {
		for (TmpGraphic item: dead) {
			item.paint(g2d);
		}
	}

	private ItemGraphic getItem(int x, int y) {
		return board[x][y];
	}
	
	private ItemGraphic newItem(Item item) {
		ItemGraphic itemG = new ItemGraphic() {
			@Override
			public void paint(Graphics graph) {}
			@Override
			public Object getParent() {return null;}
			@Override
			public Point getBoardPosition() {return null;}
		};

		if (item instanceof Bomb) {
			itemG = newBomb((Bomb) item);
		} else if (item instanceof Wall) {
			itemG = newWall((Wall) item);
		} else if (item instanceof SpeedBuff) {
			itemG = newSpeedBuff((SpeedBuff) item);
		} else if (item instanceof RangeBuff) {
			itemG = newRangeBuff((RangeBuff) item);
		} else if (item instanceof LifeBuff) {
			itemG = newLifeBuff((LifeBuff) item);
		} else if (item instanceof DropBuff) {
			itemG = newDropBuff((DropBuff) item);
		} else if (!(item instanceof Ground)) {
			System.out.println("Objet inconnu.");
		}

		return itemG;
	}
	
	private void setItem(Item item) {
		board[item.x][item.y] = newItem(item);
	}

	private void updateDead(Date now) {
		Iterator<TmpGraphic> it = dead.iterator();
		TmpGraphic item;
		long t = now.getTime();

		while (it.hasNext()) {
			item = it.next();
			if (item.getEnd().getTime()+item.getDuration() < t) {
				it.remove();
			}
		}
	}

	private void updateBoard(Date now, Game game) {
		Item item;
		ItemGraphic current;
		Dimension dimension = game.getBoardSize();

		for (int i=0; i<dimension.width; i++) {
			for (int j=0; j<dimension.height; j++) {
				item = game.getItem(i, j);
				current = getItem(i, j);
				if (!item.equals(current)) {
					if (current instanceof TmpGraphic) {
						dead.add((TmpGraphic)current);
						((TmpGraphic)current).die(now);
					}
					setItem( item );
				}
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		GameFrame gameFrame = getFrame();
		Game game = gameFrame.getGame();
		Dimension size = getSize();
		Image img = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) img.getGraphics();
		Date now = new Date();

		updateDead(now);
		updateBoard(now, game);
		drawAll(g2d);
		g.drawImage(img, 0, 0, size.width, size.height, this);
	}
}
