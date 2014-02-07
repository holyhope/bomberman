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
	private Date end;
	private Date lastUpdate = new Date(0);
	private int playerLimit = 4;

	private Board bufferBoard;
	private ArrayList<Player> bufferPlayers;

	/**
	 * Constructor for a Game
	 * Calls another constructor with default values
	 */
	public Game() {
		this(new Dimension(11, 11));
	}

	/**
	 * Constructor for a Game
	 * 
	 * @param boardDimension the dimension of the board
	 */
	public Game(Dimension boardDimension) {
		board = new Board(boardDimension, this);
		windowFavoriteSize = new Dimension(60*board.getWidth(), 60*board.getHeight());
		playerList = new ArrayList<Player>();
		bufferPlayers = new ArrayList<Player>();
		try {
			bufferBoard = board.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getter for the board
	 * 
	 * @return a clone of the board
	 */
	public Board getBoard() {
		try {
			return bufferBoard.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * getter for the playerlist
	 * 
	 * @return a new list containing clones of each player
	 */
	public Collection<Player> getPlayerList() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player player: bufferPlayers)
			players.add(player.clone());
		return (Collection<Player>) players;
	}

	/**
	 * setter for the buffers (bufferBoard and bufferPlayers)
	 */
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

	/**
	 * Updates the game by updating each player and the board
	 * Keeps the date of the last update in lastUpdate
	 */
	private synchronized void update() {
		Date now = new Date();
		for (Player player: playerList)
			player.update();
		board.update();
		lastUpdate = now;
	}

	/**
	 * Starts the game by doing every required initialization
	 * 
	 * @return true if the game has started, false otherwise
	 */
	public boolean startGame() {
		if (isStarted())
			return false;

		while (!isReady()) {
			setBuffers();
		}

		ArrayList<Point> positions = new ArrayList<Point>();
		for (Player player: playerList)
			positions.add(player.getBoardPosition());
		board.generateBoard(positions);
		start = new Date();
		do {
			setBuffers();
			update();
		} while (!isOver());
		end = new Date();

		return true;
	}

	/**
	 * Adds a player to the playerlist if there is enough space left in it
	 * 
	 * @param string the name of the player
	 * @return the new player
	 */
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

	/**
	 * Checks if the game is over by counting the number of players still alive
	 * 
	 * @return true if the number of players alive is less than 2, false otherwise
	 */
	public boolean isOver() {
		int count = 0;
		for (Player character : bufferPlayers) {
			if (!character.isDead()) {
				count++;
			}
		}
		return count <= 1;
	}

	/**
	 * Return a list containing the players still alive at the end of the game
	 * 
	 * @return a list containing the winners
	 */
	public List<Player> getWinners() {
		ArrayList<Player> winners = new ArrayList<Player>();

		for (Player player: playerList)
			if (!player.isDead())
				winners.add(player.clone());
		
		return winners;
	}

	/**
	 * Looks for the owner of the bomb
	 * 
	 * @param bomb bomb used to retrieve the owner
	 * @return the owner of the bomb or null
	 */
	private Player getOwner(Bomb bomb) {
		for (Player player: playerList)
			if (player.hasBomb(bomb))
				return player;
		return null;
	}

	/**
	 * Makes the bomb explode
	 * Destroys any destructible item and kills every player within range
	 * 
	 * @param bomb bomb to explode
	 */
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
			if (item instanceof Bomb)
				explode((Bomb)item);
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
			if (item instanceof Bomb)
				explode((Bomb)item);
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
			if (item instanceof Bomb)
				explode((Bomb)item);
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
			if (item instanceof Bomb)
				explode((Bomb)item);
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

	/**
	 * Destroys an item
	 * 
	 * @param player player who destroyed the item
	 * @param item item to be destroyed
	 */
	void destroy(Player player, Item item) {
		if (item instanceof Buff)
			((Buff)item).detroyed(player);
		if(item instanceof Bomb) {
			Date now = new Date();
			now = new Date(now.getTime());
			((Bomb)item).setExplosion(now);
		}
		destroy(item);
	}

	/**
	 * Destroys an item and replaces it by its child
	 * 
	 * @param item item to be destroyed
	 */
	void destroy(Item item) {
		board.setItem(item.child());
	}

	/**
	 * getter for windowFavoriteSize
	 * 
	 * @return a clone of windowFavoriteSize
	 */
	public Dimension getWindowFavoriteSize() {
		return (Dimension) windowFavoriteSize.clone();
	}

	/**
	 * For each player, displays its status (dead or alive)
	 */
	public void printCharacterNbLives() {
		for (Player character : playerList) {
			if (character.isDead()) {
				System.out.println("est mort");
			} else {
				System.out.println("est vivant");
			}
		}	
	}

	/**
	 * Checks if the game has started
	 * 
	 * @return true if the game has started, false otherwise
	 */
	public boolean isStarted() {
		return start != null && start.getTime() > 0;
	}

	/**
	 * getter for lastUpdate
	 * 
	 * @return a clone of lastUpdate
	 */
	public Date getLastUpdate() {
		return (Date) lastUpdate.clone();
	}

	/**
	 * Calculates the time spent during the game
	 * 
	 * @return the time spent since the beginning of the game
	 */
	public long timeSpent() {
		if (end != null)
			return end.getTime()-start.getTime();
		Date now = new Date();
		if (!isStarted())
			return 0;
		return now.getTime()-start.getTime();
	}

	/**
	 * getter for boardSize
	 * 
	 * @return the board size
	 */
	public Dimension getBoardSize() {
		return board.getSize();
	}

	/**
	 * Plants a bomb on the board
	 * 
	 * @param player player who is planting a bomb
	 * @return the new bomb
	 */
	public Bomb plantBomb(Player player) {
		return board.plantBomb(player);
	}

	/**
	 * Checks if the player can move forward in its direction
	 * 
	 * @param newPosition position where the player is heading
	 * @return true if the player can move towards newPosition, false otherwise
	 */
	protected boolean canGo(Point newPosition) {
		return board.canGo(newPosition);
	}

	/**
	 * Converts the position in the window to its equivalent in the board
	 * 
	 * @param point the position in the window
	 * @return the corresponding position in the board
	 */
	public Point window2Board(Point point) {
		Dimension dimension = getWindowFavoriteSize();
		Dimension boardSize = getBoardSize();
		Point position = new Point(
				(int)Math.floor((double)(point.x*boardSize.width)/	(double)dimension.width),
				(int)Math.floor((double)(point.y*boardSize.height)/	(double)dimension.height));
		return position;
	}

	/**
	 * Converts the position in the board to its equivalent in the window
	 * 
	 * @param point the position in the board
	 * @return the corresponding position in the window
	 */
	public Point board2Window(Point point) {
		Dimension dimension = getWindowFavoriteSize();
		Dimension boardSize = getBoardSize();
		Point position = new Point(
				(int)Math.floor((double)(point.x*dimension.width)/	(double)boardSize.width),
				(int)Math.floor((double)(point.y*dimension.height)/	(double)boardSize.height));
		return position;
	}

	/**
	 * Scales the position when the dimension changes
	 * 
	 * @param point position in the default dimension
	 * @param oldDim the old dimension
	 * @param newDim the new dimension
	 */
	public static void scalePosition(Point2D point, Dimension2D oldDim, Dimension2D newDim) {
		point.setLocation((point.getX()*newDim.getWidth())/oldDim.getWidth(), (point.getY()*newDim.getHeight())/oldDim.getHeight());
	}

	/**
	 * Calculates the left border of a square
	 * 
	 * @param x the horizontal position of the square
	 * @return an int value of the leftmost position of the square
	 */
	protected int getLeftColumn(int x) {
		Dimension boardSize = getBoardSize();
		Dimension windowSize = getWindowFavoriteSize();
		return (x*windowSize.width)/boardSize.width+1;
	}

	/**
	 * Calculates the right border of a square
	 * 
	 * @param x the horizontal position of the square
	 * @return an int value of the rightmost position of the square
	 */
	protected int getRightColumn(int x) {
		Dimension boardSize = getBoardSize();
		Dimension windowSize = getWindowFavoriteSize();
		return ((x+1)*windowSize.width)/boardSize.width-1;
	}

	/**
	 * Calculates the upper border of a square
	 * 
	 * @param y the vertical position of the square
	 * @return an int value of the upmost position of the square
	 */
	protected int getTopLine(int y) {
		Dimension boardSize = getBoardSize();
		Dimension windowSize = getWindowFavoriteSize();
		return (y*windowSize.height)/boardSize.height+1;
	}

	/**
	 * Calculates the lower border of a square
	 * 
	 * @param y the vertical position of the square
	 * @return an int value of the downmost position of the square
	 */
	protected int getBottomLine(int y) {
		Dimension boardSize = getBoardSize();
		Dimension windowSize = getWindowFavoriteSize();
		return ((y+1)*windowSize.height)/boardSize.height-1;
	}

	/**
	 * Checks if a bomb can be planted at the specified position
	 * 
	 * @param boardPosition position where the player wants to plant the bomb
	 * @return true if the bomb can be planted, false otherwise
	 */
	public boolean canPlant(Point boardPosition) {
		return bufferBoard.canPlant(boardPosition);
	}

	/**
	 * getter for an item
	 * 
	 * @param x the horizontal position of the item
	 * @param y the vertical position of the item
	 * @return the item
	 */
	public Item getItem(int x, int y) {
		return bufferBoard.getItem(x, y);
	}
	
	/**
	 * getter for an item
	 * 
	 * @param point position of the item
	 * @return the item
	 */
	public Item getItem(Point point) {
		return bufferBoard.getItem(point);
	}

	/**
	 * Let the player walk through an item
	 * 
	 * @param player player who is walking
	 * @param point position of the item the player is walking through
	 */
	protected void walk(Player player, Point point) {
		Item item = board.getItem(point);
		if (item == null)
			return;
		item.walkedTrough(player);
	}

	/**
	 * Checks if every player is ready
	 * 
	 * @return true if all the player are ready, false otherwise
	 */
	public boolean isReady() {
		for (Player player: playerList) {
			if (!player.isReady())
				return false;
		}
		return true;
	}
}
