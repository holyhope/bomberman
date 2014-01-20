package game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public final class Game {
	private ArrayList<Player> playerList;
	private Board board;
	private final Dimension windowFavoriteSize;
	private Date start;
	private long duration = 60;
	private Date lastUpdate = new Date(0);
	private int playerLimit = 4;

	private Board bufferBoard;
	private ArrayList<Player> bufferPlayers;

	public Game() {
		this(new Dimension(13, 15));
	}

	public Game(Dimension boardDimension) {
		board = new Board(boardDimension, this);
		windowFavoriteSize = new Dimension(800, 600);
		playerList = new ArrayList<Player>();
		bufferPlayers = new ArrayList<Player>();
		try {
			bufferBoard = board.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public Board getBoard() {
		try {
			return bufferBoard.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Collection<Player> getPlayerList() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player player: bufferPlayers)
			players.add(player.clone());
		return (Collection<Player>) players;
	}

	private synchronized void setBuffers() {
		try {
			bufferBoard = board.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player player: playerList)
			players.add(player.clone());
		bufferPlayers = players;
	}

	private synchronized void update() {
		Date now = new Date();
		for (Player player: playerList)
			player.update();
		board.update();
		lastUpdate = now;
	}

	public boolean startGame() {
		if (isStarted())
			return false;

		ArrayList<Point> positions = new ArrayList<Point>();
		for (Player player: playerList)
			positions.add(player.getBoardPosition());
		board.generateBoard(positions);
		start = new Date();
		do {
			setBuffers();
			update();
			if(timeSpent()/1000 >= duration) {
				board.suddenDeathBoard(timeSpent() / 1000 - duration);
			}
		} while (!isOver());
		System.out.println("Le(s) vainqueur(s) est/sont:");
		for (Player player: playerList)
			if (!player.isDead())
				System.out.println(player);
		return true;
	}

	public Player addPlayer(String string) {
		if (isStarted())
			return null;
		int size = playerList.size();
		if (size > playerLimit)
			return null;
		Point position = new Point();
		Dimension dimension = getBoardSize();
		switch (size) {
		case 0:
			position.setLocation(getLeftColumn(0), getTopLine(0));
			break;
		case 1:
			position.setLocation(getRightColumn(dimension.width-1), getBottomLine(dimension.height-1));
			break;
		case 2:
			position.setLocation(getRightColumn(dimension.width-1), getTopLine(0));
			break;
		case 3:
			position.setLocation(getLeftColumn(0), getBottomLine(dimension.height-1));
			break;
		default:
			return null;
		}
		Player player = Player.create(this, position, string);
		playerList.add(player);
		return player;
	}

	public boolean isOver() {
		int count = 0;
		for (Player character : bufferPlayers) {
			if (!character.isDead()) {
				count++;
			}
		}
		return count <= 1;
	}

	public List<Player> getWinners() {
		ArrayList<Player> list = new ArrayList<Player>();
		if (!isOver())
			return list;
		for (Player player: bufferPlayers)
			if (!player.isDead())
				list.add(player.clone());
		return list;
	}

	private Player getOwner(Bomb bomb) {
		for (Player player: playerList)
			if (player.hasBomb(bomb))
				return player;
		return null;
	}

	protected void explode(Bomb bomb) {
		Point position = bomb.getBoardPosition();
		Point origine = (Point) position.clone();
		Player killer = getOwner(bomb);

		int i;
		int range = bomb.getRange();
		Item item;
		Point tmp;

		for (Player player: playerList) {
			tmp = player.getBoardPosition();
			if (tmp.equals(position)) {
				player.kill();
			}
		}

		destroy(bomb);
		for (i=0; i<range; i++) {
			position.x--;
			item = board.getItem(position);
			if (item == null)
				break;
			destroy(killer, item);
			for (Player player: playerList) {
				tmp = player.getBoardPosition();
				if (tmp.equals(position)) {
					player.kill();
				}
			}
			if (item.blockExplosion())
				break;
		}
		position.setLocation(origine);
		for (i=0; i<range; i++) {
			position.x++;
			item = board.getItem(position);
			if (item == null)
				break;
			destroy(killer, item);
			for (Player player: playerList) {
				tmp = player.getBoardPosition();
				if (tmp.equals(position)) {
					player.kill();
				}
			}
			if (item.blockExplosion())
				break;
		}
		position.setLocation(origine);
		for (i=0; i<range; i++) {
			position.y--;
			item = board.getItem(position);
			if (item == null)
				break;
			destroy(killer, item);
			for (Player player: playerList) {
				tmp = player.getBoardPosition();
				if (tmp.equals(position)) {
					player.kill();
				}
			}
			if (item.blockExplosion())
				break;
		}
		position.setLocation(origine);
		for (i=0; i<range; i++) {
			position.y++;
			item = board.getItem(position);
			if (item == null)
				break;
			destroy(killer, item);
			for (Player player: playerList) {
				tmp = player.getBoardPosition();
				if (tmp.equals(position)) {
					player.kill();
				}
			}
			if (item.blockExplosion())
				break;
		}

		for (Player player: playerList) {
			if (player.removeBomb(bomb)) {
				break;
			}
		}
	}

	void destroy(Player player, Item item) {
		if (item instanceof Buff)
			((Buff)item).detroyed(player);
		if(item instanceof Bomb)
			this.explode((Bomb) item);
		destroy(item);
	}

	void destroy(Item item) {
		board.setItem(item.child());
	}

	public Dimension getWindowFavoriteSize() {
		return (Dimension) windowFavoriteSize.clone();
	}

	public void printCharacterNbLives() {
		for (Player character : playerList) {
			if (character.isDead()) {
				System.out.println("est mort");
			} else {
				System.out.println("est vivant");
			}
		}	
	}

	public boolean isStarted() {
		return start != null && start.getTime() > 0;
	}

	public Date getLastUpdate() {
		return (Date) lastUpdate.clone();
	}

	public long timeSpent() {
		Date now = new Date();
		if (!isStarted())
			return 0;
		return now.getTime()-start.getTime();
	}

	public Dimension getBoardSize() {
		return board.getSize();
	}

	public Bomb plantBomb(Player player) {
		return board.plantBomb(player);
	}

	protected boolean canGo(Point newPosition) {
		return board.canGo(newPosition);
	}

	public Point window2Board(Point point) {
		Dimension dimension = getWindowFavoriteSize();
		Dimension boardSize = getBoardSize();
		Point position = new Point(
				(int)Math.floor((double)(point.x*boardSize.width)/	(double)dimension.width),
				(int)Math.floor((double)(point.y*boardSize.height)/	(double)dimension.height));
		return position;
	}

	public static void scalePosition(Point2D point, Dimension2D oldDim, Dimension2D newDim) {
		point.setLocation((point.getX()*newDim.getWidth())/oldDim.getWidth(), (point.getY()*newDim.getHeight())/oldDim.getHeight());
	}

	protected int getLeftColumn(int x) {
		Dimension boardSize = getBoardSize();
		Dimension windowSize = getWindowFavoriteSize();
		return (x*windowSize.width)/boardSize.width+1;
	}

	protected int getRightColumn(int x) {
		Dimension boardSize = getBoardSize();
		Dimension windowSize = getWindowFavoriteSize();
		return ((x+1)*windowSize.width)/boardSize.width-1;
	}

	protected int getTopLine(int y) {
		Dimension boardSize = getBoardSize();
		Dimension windowSize = getWindowFavoriteSize();
		return (y*windowSize.height)/boardSize.height+1;
	}

	protected int getBottomLine(int y) {
		Dimension boardSize = getBoardSize();
		Dimension windowSize = getWindowFavoriteSize();
		return ((y+1)*windowSize.height)/boardSize.height-1;
	}

	public boolean canPlant(Point boardPosition) {
		return bufferBoard.canPlant(boardPosition);
	}

	public Item getItem(int x, int y) {
		return bufferBoard.getItem(x, y);
	}

	public Item getItem(Point point) {
		return bufferBoard.getItem(point);
	}

	protected void walk(Player player, Point point) {
		Item item = board.getItem(point);
		if (item == null)
			return;
		item.walkedTrough(player);
	}
}
