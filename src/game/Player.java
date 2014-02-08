package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class Player extends Item {
	private final String name;
	private Speed speed = new Speed();
	private int nbBombMax = 1;
	private int timeBomb = 3000;
	private int nbLives = 1;
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private Date lastUpdate = new Date(0);
	private int bombRange = 1;
	private int points = 0;
	private boolean ready = true; //todo: default to false and make a controller to set it true.
	private static int maxSpeed = 50;

	/**
	 * Constructor for a Player
	 * 
	 * @param game game in which the player will be created
	 * @param position position of the player
	 * @param name name of the player
	 */
	public Player(Game game, Point position, String name) {
		super(game, position);
		this.name = name;
	}

	/**
	 * Factory method to create a player
	 * 
	 * @param game game in which the player will be created
	 * @param position position of the player
	 * @param name name of the player
	 * @return the new player
	 */
	protected static Player create(Game game, Point position, String name) {
		Player player = new Player(game, position, name);
		player.setLocation(position);
		return player;
	}

	/**
	 * blockExplosion method inherited from Item
	 * 
	 * @return false
	 */
	@Override
	public boolean blockExplosion() {
		return false;
	}

	/**
	 * getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Checks if the player is dead or alive
	 * 
	 * @return true if the player is dead, false otherwise
	 */
	public boolean isDead() {
		return nbLives <= 0;
	}

	/**
	 * Remove a bomb from the player's bombs list
	 * 
	 * @param bomb bomb to remove from the list
	 * @return the bombs list
	 */
	protected boolean removeBomb(Bomb bomb) {
		return bombs.remove(bomb);
	}

	/**
	 * Plants a bomb
	 */
	public void plant() {
		update();
		if (canPlant())
			add(game.plantBomb(this));
	}

	/**
	 * Checks if the player can plant
	 * 
	 * @return true if the player can plant a bomb, false otherwise
	 */
	public boolean canPlant() {
		return !isDead() && (bombs.size() < nbBombMax) && game.canPlant(getBoardPosition());
	}

	/**
	 * Decrease the player's nbLives
	 */
	protected void kill() {
		if (isDead())
			return;
		nbLives--;
	}

	/**
	 * Adds a bomb to the bombs list
	 * 
	 * @param bomb bomb to add to the list of bombs
	 */
	protected void add(Bomb bomb) {
		if (bomb != null)
			bombs.add(bomb);
	}

	/**
	 * getter for the list of bombs
	 * 
	 * @return a list containing clones of each bomb
	 */
	protected ArrayList<Bomb> getBombs() {
		ArrayList<Bomb> bombList = new ArrayList<Bomb>();
		for (Bomb bomb: bombs)
			bombList.add(bomb.clone());
		return bombs;
	}

	/**
	 * getter for nbBombMax
	 * 
	 * @return nbBombMax
	 */
	public int getNbBombMax() {
		return nbBombMax;
	}

	/**
	 * Method clone for a Player
	 * 
	 * @return a clone of the Player
	 */
	@Override
	public synchronized Player clone() {
		Player player = Player.create(game, new Point(x, y), name);

		player.speed = speed.clone();
		player.nbBombMax = nbBombMax;
		player.nbLives = nbLives;
		player.bombs = new ArrayList<Bomb>();
		for (Bomb bomb: bombs)
			player.bombs.add(bomb.clone());
		player.lastUpdate = new Date(lastUpdate.getTime());

		return player;
	}

	/**
	 * setter for nbBombMax
	 * 
	 * @param nbBombMax number of bombs the player can plant at the same time
	 */
	protected void setNbBomb(int nbBombMax) {
		this.nbBombMax = nbBombMax;
	}

	/**
	 * getter for the player's board position
	 * 
	 * @return a conversion from its position in the window to the position in the board
	 */
	@Override
	public Point getBoardPosition() {
		return game.window2Board(this);
	}

	/**
	 * Move the player to its new horizontal position
	 * 
	 * @param delta time since the last update
	 * @return true if the player has not been blocked, false otherwise 
	 */
	private synchronized boolean updatePosX(long delta) {
		int i;
		Point oldPosBoard = getBoardPosition();
		Point newPosition = speed.move(this, delta);
		Point newPosBoard = game.window2Board(newPosition);

		if (oldPosBoard.x == newPosBoard.x) {
			x = newPosition.x;
			return true;
		}

		Point point = new Point(oldPosBoard.x, oldPosBoard.y);
		if (newPosBoard.x > oldPosBoard.x) {
			for (i=oldPosBoard.x+1; i<newPosBoard.x; i++) {
				point.x = i;
				if (!game.canGo(point)) {
					x = game.getRightColumn(i-1);
					return true;
				}
				game.walk(this, point);
			}
		} else {
			for (i=oldPosBoard.x-1; i>newPosBoard.x; i--) {
				point.x = i;
				if (!game.canGo(point)) {
					x = game.getLeftColumn(i+1);
					return true;
				}
				game.walk(this, point);
			}
		}
		point.x = newPosBoard.x;
		if (game.canGo(point)) {
			x = newPosition.x;
			game.walk(this, point);
			return true;
		}
		return false;
	}

	/**
	 * Moves the player to its new vertical position
	 * 
	 * @param delta time since the last update
	 * @return true if the player has not been blocked, false otherwise
	 */
	private synchronized boolean updatePosY(long delta) {
		int i;
		Point oldPosBoard = getBoardPosition();
		Point newPosition = speed.move(this, delta);
		Point newPosBoard = game.window2Board(newPosition);

		if (oldPosBoard.y == newPosBoard.y) {
			y = newPosition.y;
			return true;
		}

		Point point = new Point(oldPosBoard.x, oldPosBoard.y);
		if (newPosBoard.y > oldPosBoard.y) {
			for (i=oldPosBoard.y+1; i<newPosBoard.y; i++) {
				point.y = i;
				if (!game.canGo(point)) {
					y = game.getBottomLine(i-1);
					return true;
				}
				game.walk(this, point);
			}
		} else {
			for (i=oldPosBoard.y-1; i>newPosBoard.y; i--) {
				point.y = i;
				if (!game.canGo(point)) {
					y = game.getTopLine(i+1);
					return true;
				}
				game.walk(this, point);
			}
		}
		point.y = newPosBoard.y;
		if (game.canGo(point)) {
			y = newPosition.y;
			game.walk(this, point);
			return true;
		}
		return false;
	}

	/**
	 * setter for the bomb range
	 * 
	 * @param bombRange range of the bomb
	 */
	protected void setRange(int bombRange) {
		this.bombRange = bombRange;
	}

	/**
	 * Increase the bombs' range
	 * 
	 * @param bombRange range to add to the current bombRange
	 */
	protected void addRange(int bombRange) {
		this.bombRange+= bombRange;
	}

	/**
	 * Updates the player's status
	 * Moves the player to its new position
	 * Keeps the date of the last update in lastUpdate
	 */
	protected synchronized void update() {
		Date now = new Date();
		long delta = now.getTime()-lastUpdate.getTime();

		if (isDead())
			return;

		Point oldPos = new Point(x, y);
		Point dir = new Point(speed.getDirectionX(), speed.getDirectionY());
		boolean blocked = false;

		if (dir.x != 0)
			blocked|= !updatePosX(delta);
		if (dir.y != 0)
			blocked|= !updatePosY(delta);

		if ((dir.x == 0 && dir.y == 0) || blocked || !oldPos.equals(this))
			lastUpdate = now;
	}

	/**
	 * Set the player's direction to up
	 */
	public void up() {
		speed.setDirectionY(-1);
	}

	/**
	 * Set the player's direction to down
	 */
	public void down() {
		speed.setDirectionY(1);
	}

	/**
	 * Set the player's direction to left
	 */
	public void left() {
		speed.setDirectionX(-1);
	}

	/**
	 * Set the player's direction to right
	 */
	public void right() {
		speed.setDirectionX(1);
	}

	/**
	 * getter for the vertical speed
	 * 
	 * @return the player's vertical speed
	 */
	public int getSpeedY() {
		return speed.moveY(0, 1);
	}

	/**
	 * getter for the horizontal speed
	 * 
	 * @return the player's horizontal speed
	 */
	public int getSpeedX() {
		return speed.moveX(0, 1);
	}

	/**
	 * Stops the player's vertical movement
	 */
	public void stopY() {
		update();
		speed.setDirectionY(0);
	}

	/**
	 * Stops the player's horizontal movement
	 */
	public void stopX() {
		update();
		speed.setDirectionX(0);
	}

	/**
	 * Stops all the player's movement
	 */
	public void stop() {
		update();
		speed.setDirectionX(0);
		speed.setDirectionY(0);
	}

	/**
	 * Method toString for a Player
	 * 
	 * @return a String representation of the player
	 */
	@Override
	public String toString() {
		return name+"("+getX()+","+getY()+")";
	}

	/**
	 * getter for timeBomb
	 * 
	 * @return timeBomb
	 */
	public int getTimeBomb() {
		return timeBomb;
	}

	/**
	 * getter for the board position
	 * @return board position
	 */
	public Point getPosition() {
		update();
		return super.getBoardPosition();
	}

	/**
	 * getter for bombRange
	 * 
	 * @return bombRange
	 */
	public int getBombRange() {
		return bombRange;
	}

	/**
	 * blockWalk method inherited from Item
	 * 
	 * @return false
	 */
	@Override
	public boolean blockWalk() {
		return false;
	}

	/**
	 * Method equals for a Player.
	 * 
	 * @param obj the object tested on the equality.
	 * @return true if obj is equal to the player, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Player))
			return false;
		Player player = (Player) obj;
		return name.equals(player.name);
	}

	/**
	 * Increase the points of the player
	 * 
	 * @param i number of points to add to the player
	 */
	protected void addPoints(int i) {
		points+= i;
	}

	/**
	 * getter for the points
	 * 
	 * @return points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Increase the speed of the player
	 * 
	 * @param i speed to add to the player
	 */
	protected void addSpeed(int i) {
		int value = speed.getSpeed();
		value+= i;
		if (value < maxSpeed )
			speed.setSpeed(value);
	}

	/**
	 * Increase the number of lives of the player
	 * 
	 * @param i number of lives to add to the player
	 */
	protected void addLife(int i) {
		nbLives+= i;
	}

	/**
	 * getter for the number of lives
	 * 
	 * @return nbLives
	 */
	public int getNumberLives() {
		return nbLives;
	}

	/**
	 * 
	 * @param i
	 */
	protected void setLife(int i) {
		nbLives = -1;
	}

	/**
	 * Checks if the bomb is the player's
	 * 
	 * @param bomb bomb used to check if the player is the owner
	 * @return true if the player is the owner of the bomb, false otherwise
	 */
	public boolean hasBomb(Bomb bomb) {
		return bombs.contains(bomb);
	}

	/**
	 * Increase the number of bombs the player can plant
	 * 
	 * @param i number of bombs to add to the total capacity
	 */
	protected void addCapacity(int i) {
		nbBombMax+= i;
	}

	/**
	 * Kills the player who wants to surrender
	 */
	public void surrend() {
		nbLives = 0;
	}

	/**
	 * Set the player to ready
	 */
	public void ready() {
		ready = true;
	}

	/**
	 * Checks if the player is ready
	 * 
	 * @return true if the player is ready, false otherwise
	 */
	public boolean isReady() {
		return ready ;
	}
	
	/**
	 * getter for speed
	 * 
	 * @return speed
	 */
	public Speed getSpeed() {
		return speed;
	}
}
