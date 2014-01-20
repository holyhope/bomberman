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
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

@SuppressWarnings("serial")
public abstract class ContentPane extends Panel {
	private Image imagePlayer;
	private ArrayList<BombGraphic> bombList = new ArrayList<BombGraphic>();
	private ArrayList<WallGraphic> wallList = new ArrayList<WallGraphic>();
	private ArrayList<BuffSpeedGraphic> speedBuffList = new ArrayList<BuffSpeedGraphic>();
	private ArrayList<BuffRangeGraphic> rangeBuffList = new ArrayList<BuffRangeGraphic>();
	private ArrayList<BuffLifeGraphic> lifeBuffList = new ArrayList<BuffLifeGraphic>();
	private ArrayList<BuffDropGraphic> dropBuffList = new ArrayList<BuffDropGraphic>();

	public ContentPane(GameFrame gameframe) {
		super(gameframe);
	}

	protected void setCursor(Object object, String path) {
		Image cursor = GameFrame.openImage(object, path);
		Dimension size = Toolkit.getDefaultToolkit().getBestCursorSize(32, 32);
		Point position = new Point(size.width/2, size.height/2);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				cursor, position, "blank cursor"));
	}

	protected void setImagePlayer(Image openImage) {
		if (imagePlayer != null)
			imagePlayer.flush();
		imagePlayer = openImage;
	}

	protected void drawPlayer(Graphics g, PlayerGraphic player) {
		Point position = player.getPosition();
		positionGraphic(position);

		drawImage(g, imagePlayer, position);
		String string = player.getName();
		g.setColor(player.color);
		g.drawChars(string.toCharArray(), 0, Math.min(10, string.length()), position.x, position.y);
	}

	protected synchronized void drawBombs(Graphics g) {
		ArrayList<BombGraphic> list = new ArrayList<BombGraphic>(bombList);
		for (BombGraphic bomb: list)
			bomb.paint(g);
	}
	
	protected synchronized void drawWalls(Graphics g) {
		ArrayList<WallGraphic> list = new ArrayList<WallGraphic>(wallList);
		for (WallGraphic wall: list)
			wall.paint(g);
	}

	private void drawSpeedBuffs(Graphics g) {
		ArrayList<BuffSpeedGraphic> list = new ArrayList<BuffSpeedGraphic>(speedBuffList);
		for (BuffSpeedGraphic buff: list)
			buff.paint(g);
	}

	private void drawRangeBuffs(Graphics g) {
		ArrayList<BuffRangeGraphic> list = new ArrayList<BuffRangeGraphic>(rangeBuffList);
		for (BuffRangeGraphic buff: list)
			buff.paint(g);
	}

	private void drawLifeBuffs(Graphics g) {
		ArrayList<BuffLifeGraphic> list = new ArrayList<BuffLifeGraphic>(lifeBuffList);
		for (BuffLifeGraphic buff: list)
			buff.paint(g);
	}

	protected abstract BombGraphic newBomb(Bomb bomb);
	protected abstract WallGraphic newWall(Wall wall);
	protected abstract BuffSpeedGraphic newSpeedBuff(SpeedBuff buff);
	protected abstract BuffRangeGraphic newRangeBuff(RangeBuff buff);
	protected abstract BuffLifeGraphic newLifeBuff(LifeBuff buff);
	protected abstract BuffDropGraphic newDropBuff(DropBuff buff);

	private void updateBomb(Graphics g, Bomb bomb) {
		Point position = bomb.getBoardPosition();
		board2Graphic(position);

		if (!bombList.contains(bomb)) {
			BombGraphic bombGraph = newBomb(bomb);
			if (bombGraph != null) {
				bombList.add(bombGraph);
			}
		}
	}

	private void updateWall(Graphics g, Wall wall) {
		Point position = wall.getBoardPosition();
		board2Graphic(position);

		if (!wallList.contains(wall)) {
			WallGraphic wallGraph = newWall(wall);
			if (wallGraph != null) {
				wallList.add(wallGraph);
			}
		}
	}

	private void updateSpeedBuff(Graphics g, SpeedBuff buff) {
		Point position = buff.getBoardPosition();
		board2Graphic(position);

		if (!speedBuffList.contains(buff)) {
			BuffSpeedGraphic buffGraph = newSpeedBuff(buff);
			if (buffGraph != null) {
				speedBuffList.add(buffGraph);
			}
		}
		
	}

	private void updateRangeBuff(Graphics g, RangeBuff buff) {
		Point position = buff.getBoardPosition();
		board2Graphic(position);

		if (!rangeBuffList.contains(buff)) {
			BuffRangeGraphic buffGraph = newRangeBuff(buff);
			if (buffGraph != null) {
				rangeBuffList.add(buffGraph);
			}
		}
		
	}

	private void updateLifeBuff(Graphics g, LifeBuff buff) {
		Point position = buff.getBoardPosition();
		board2Graphic(position);

		if (!lifeBuffList.contains(buff)) {
			BuffLifeGraphic buffGraph = newLifeBuff(buff);
			if (buffGraph != null) {
				lifeBuffList.add(buffGraph);
			}
		}
	}

	private void updateDropBuff(Graphics g, DropBuff buff) {
		Point position = buff.getBoardPosition();
		board2Graphic(position);

		if (!dropBuffList.contains(buff)) {
			BuffDropGraphic buffGraph = newDropBuff(buff);
			if (buffGraph != null) {
				dropBuffList.add(buffGraph);
			}
		}
		
	}

	protected void updateItem(Graphics g, Item item) {
		if (item instanceof Bomb) {
			updateBomb(g, (Bomb) item);
		} else if (item instanceof Wall) {
			updateWall(g, (Wall) item);
		} else if (item instanceof SpeedBuff) {
			updateSpeedBuff(g, (SpeedBuff) item);
		} else if (item instanceof RangeBuff) {
			updateRangeBuff(g, (RangeBuff) item);
		} else if (item instanceof LifeBuff) {
			updateLifeBuff(g, (LifeBuff) item);
		} else if (item instanceof DropBuff) {
			updateDropBuff(g, (DropBuff) item);
		} else if (!(item instanceof Ground)) {
			System.out.println("Objet inconnu. Impossible de l'afficher:\n"+item);
		}
	}

	protected void drawImage(Graphics g, Image image, Point position) {
		Dimension size = imageSize();
		g.drawImage(image, position.x, position.y, size.width, size.height, this);
	}

	public void board2Graphic(Point position) {
		scalePosition(position, getParent().getGame().getBoardSize());
	}

	public synchronized void remove(BombGraphic bombGraphic) {
		bombList.remove(bombGraphic);
	}

	public synchronized void remove(WallGraphic wallGraphic) {
		wallList.remove(wallGraphic);
	}

	public synchronized void remove(ItemGraphic itemGraphic) {
		bombList.remove(itemGraphic);
		wallList.remove(itemGraphic);
	}

	protected void positionGraphic(Point position) {
		scalePosition(position, getParent().getGame().getWindowFavoriteSize());
		Dimension imageSize = imageSize();
		position.setLocation(position.x-imageSize.width/2, position.y-imageSize.height/2);
	}

	protected void scalePosition(Point position, Dimension dimension) {
		Dimension actualSize = getSize();
		position.setLocation(
				(position.x*actualSize.width)	/dimension.width,
				(position.y*actualSize.height)	/dimension.height);
	}

	public Dimension imageSize() {return getParent().imageSize();}

	private void clearWalls(Game game) {
		ArrayList<WallGraphic> list = new ArrayList<WallGraphic>(wallList);
		for (WallGraphic wallGraph: list) {
			if (!wallGraph.equals(game.getItem(wallGraph.getBoardPosition()))) {
				wallList.remove(wallGraph);
			}
		}
	}

	private void clearBuffsSpeed(Game game) {
		ArrayList<BuffSpeedGraphic> list = new ArrayList<BuffSpeedGraphic>(speedBuffList);
		for (BuffSpeedGraphic buffGraph: list) {
			if (!buffGraph.equals(game.getItem(buffGraph.getBoardPosition()))) {
				speedBuffList.remove(buffGraph);
			}
		}
	}

	private void clearBuffsRange(Game game) {
		ArrayList<BuffRangeGraphic> list = new ArrayList<BuffRangeGraphic>(rangeBuffList);
		for (BuffRangeGraphic buffGraph: list) {
			if (!buffGraph.equals(game.getItem(buffGraph.getBoardPosition()))) {
				rangeBuffList.remove(buffGraph);
			}
		}
	}

	private void clearBuffsLife(Game game) {
		ArrayList<BuffLifeGraphic> list = new ArrayList<BuffLifeGraphic>(lifeBuffList);
		for (BuffLifeGraphic buffGraph: list) {
			if (!buffGraph.equals(game.getItem(buffGraph.getBoardPosition()))) {
				lifeBuffList.remove(buffGraph);
			}
		}
	}

	private void clearBuffsDrop(Game game) {
		ArrayList<BuffDropGraphic> list = new ArrayList<BuffDropGraphic>(dropBuffList);
		for (BuffDropGraphic buffGraph: list) {
			if (!buffGraph.equals(game.getItem(buffGraph.getBoardPosition()))) {
				dropBuffList.remove(buffGraph);
			}
		}
	}

	private void clearLists(Game game) {
		clearWalls(game);
		clearBuffsSpeed(game);
		clearBuffsRange(game);
		clearBuffsLife(game);
		clearBuffsDrop(game);
	}

	private void drawLists(Graphics g) {
		drawBombs(g);
		drawWalls(g);
		drawSpeedBuffs(g);
		drawRangeBuffs(g);
		drawLifeBuffs(g);
	}

	@Override
	public void paint(Graphics g) {
		GameFrame gameFrame = getParent();
		Game game = gameFrame.getGame();
		Dimension boardSize = game.getBoardSize();

		clearLists(game);
		for (int i=0; i<boardSize.width; i++) {
			for (int j=0; j<boardSize.height; j++) {
				updateItem(g, game.getItem(i, j));
			}
		}
		drawLists(g);
		for (PlayerGraphic player: gameFrame.getPlayerList())
			drawPlayer(g, player);
	}
}
