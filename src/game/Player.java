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
	private static int maxSpeed = 50;

	public Player(Game game, Point position, String name) {
		super(game, position);
		this.name = name;
	}

	protected static Player create(Game game, Point position, String name) {
		Player player = new Player(game, position, name);
		player.setLocation(position);
		return player;
	}

	@Override
	public boolean blockExplosion() {
		return false;
	}

	public String getName() {
		return name;
	}

	public boolean isDead() {
		return nbLives <= 0;
	}

	protected boolean removeBomb(Bomb bomb) {
		return bombs.remove(bomb);
	}

	public void plant() {
		update();
		if (canPlant())
			add(game.plantBomb(this));
	}

	public boolean canPlant() {
		return !isDead() && (bombs.size() < nbBombMax) && game.canPlant(getBoardPosition());
	}

	protected void kill() {
		if (isDead())
			return;
		nbLives--;
	}

	protected void add(Bomb bomb) {
		if (bomb != null)
			bombs.add(bomb);
	}

	protected ArrayList<Bomb> getBombs() {
		ArrayList<Bomb> bombList = new ArrayList<Bomb>();
		for (Bomb bomb: bombs)
			bombList.add(bomb.clone());
		return bombs;
	}

	public int getNbBombMax() {
		return nbBombMax;
	}

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

	protected void setNbBomb(int nbBombMax) {
		this.nbBombMax = nbBombMax;
	}

	@Override
	public Point getBoardPosition() {
		return game.window2Board(this);
	}

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

	protected void setRange(int bombRange) {
		this.bombRange = bombRange;
	}

	protected void addRange(int bombRange) {
		this.bombRange+= bombRange;
	}

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

	public void up() {
		if (isDead())
			return;
		speed.setDirectionY(-1);
	}

	public void down() {
		if (isDead())
			return;
		speed.setDirectionY(1);
	}

	public void left() {
		if (isDead())
			return;
		speed.setDirectionX(-1);
	}

	public void right() {
		if (isDead())
			return;
		speed.setDirectionX(1);
	}

	public int getSpeedY() {
		return speed.moveY(0, 1);
	}

	public int getSpeedX() {
		return speed.moveX(0, 1);
	}

	public void stopY() {
		update();
		speed.setDirectionY(0);
	}

	public void stopX() {
		update();
		speed.setDirectionX(0);
	}

	public void stop() {
		update();
		speed.setDirectionX(0);
		speed.setDirectionY(0);
	}

	@Override
	public String toString() {
		return name+"("+getX()+","+getY()+")";
	}

	public int getTimeBomb() {
		return timeBomb;
	}

	public Point getPosition() {
		update();
		return super.getBoardPosition();
	}

	public int getBombRange() {
		return bombRange;
	}

	@Override
	public boolean blockWalk() {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Player))
			return false;
		Player player = (Player) obj;
		return name.equals(player.name);
	}

	protected void addPoints(int i) {
		points+= i;
	}

	public int getPoints() {
		return points;
	}

	protected void addSpeed(int i) {
		int value = speed.getSpeed();
		value+= i;
		if (value < maxSpeed )
			speed.setSpeed(value);
	}

	protected void addLife(int i) {
		nbLives+= i;
	}

	public int getNumberLives() {
		return nbLives;
	}

	protected void setLife(int i) {
		nbLives = -1;
	}

	public boolean hasBomb(Bomb bomb) {
		return bombs.contains(bomb);
	}

	protected void addCapacity(int i) {
		nbBombMax+= i;
	}
}
