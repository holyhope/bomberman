package game;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import java.util.Random;

class Board implements Cloneable {
	private Item[][] grid;
	private final Game game;

	Board(Dimension size, Game game) {
		this.game = game;
		grid = new Item[size.height][size.width];
		int i, j;
		for (i=0; i<size.height; i++) {
			for (j=0; j<size.width; j++) {
				grid[i][j] = new Ground(game, new Point(i, j));
			}
		}
	}

	@Override
	protected synchronized Board clone() throws CloneNotSupportedException {
		int i, j;
		Board board = (Board) super.clone();
		board.grid = this.grid.clone();
		for (i=0; i<board.grid.length; i++)
			for (j=0; j<board.grid[i].length; j++)
				board.grid[i][j] = board.grid[i][j].clone();
		return board;
	}

	int getHeight() {
		return grid.length;
	}

	int getWidth() {
		if (grid.length == 0)
			return 0;
		return grid[0].length;
	}

	Dimension getSize() {
		return new Dimension(getWidth(), getHeight());
	}

	boolean inGrid(Point position) {
		return inGrid(position.x, position.y);
	}

	boolean inGrid(int x, int y) {
		return (x >= 0 && x < getWidth() &&
				y >= 0 && y < getHeight());
	}

	void generateBoard(List<Point> positions) {
		int i, j;
		Random random = new Random();
		boolean playerArea;
		System.out.println(positions);

		for (i=0; i<getWidth(); i++) {
			for (j=0; j<getHeight(); j++) {
				playerArea = false;
				for (Point position: positions) {
					if ((position.x >= i-1 && position.x <= i+1 && position.y == j) || (position.y >= j-1 && position.x <= j+1 && position.x == i)) {
						setItem(new Ground(game, new Point(i, j)));
						playerArea = true;
						break;
					}
				}
				if (!playerArea) {
					if (i%2 == 1 && j%2 == 1) {
						setItem(new Block(game, new Point(i, j)));
					} else {
						switch (random.nextInt(10)) {
						case 0: case 1: case 2:
							setItem(new BuffedWall(game, new Point(i, j)));
							break;
						case 3: case 4: case 5:
							setItem(new Wall(game, new Point(i, j)));
							break;
						case 6:
							setItem(new NotAWall(game, new Point(i, j)));
							break;
						default:
							setItem(new Ground(game, new Point(i, j)));
							break;
						}
					}
				}
			}
		}
	}
	
	void suddenDeathBoard(long seconds) {
		if(seconds >= getWidth() * getHeight()) {
			return;
		}
		
		if(seconds%2 == 0) {
			seconds /= 2;
			int x = (int) (seconds%getWidth());
			int y = (int) (seconds/getWidth());
			setItem(new Block(game, new Point(x, y)));
			
			for (Player player: game.getPlayerList()) {
				Point tmp = player.getBoardPosition();
				if (tmp.x == x && tmp.y == y) {
					System.out.println("kaboom" + player.getName() + "EST MORT");
					player.kill();
				}
			}
		}
		else {
			seconds = (seconds - 1) / 2;
			int x = (int) (seconds%getWidth());
			int y = (int) (seconds/getWidth());
			setItem(new Block(game, new Point(getWidth()-(1+x), getHeight()-(1+y))));
			
			for (Player player: game.getPlayerList()) {
				Point tmp = player.getBoardPosition();
				if (tmp.x == getWidth()-(1+x) && tmp.y == getHeight()-(1+y)) {
					System.out.println("kaboom" + player.getName() + "EST MORT");
					player.kill();
				}
			}
		}
	}

	Bomb plantBomb(Player player) {
		Point position = player.getBoardPosition();
		if (!inGrid(position))
			return null;

		return (Bomb) setItem(Bomb.create(game, position, player.getTimeBomb(), player.getBombRange()));
	}

	void printBoard() {
		for(int i=0; i<getHeight(); i++) {
			for(int j=0; j<getWidth(); j++) {
				System.out.print(" "+getItem(i, j)+" ");
			}
			System.out.println();
		}
	}

	Item getItem(Point position) {
		return getItem(position.x, position.y);
	}

	synchronized void update() {
		int i, j;

		for (i=0; i<getWidth(); i++)
			for (j=0; j<getHeight(); j++)
				getItem(i, j).update();
	}

	boolean canGo(Point point) {
		if (!inGrid(point))
			return false;
		return !getItem(point).blockWalk();
	}

	boolean canPlant(Point point) {
		if (!inGrid(point))
			return false;
		return getItem(point) instanceof Ground;
	}

	Item setItem(Item item) {
		if (!inGrid(item.x, item.y))
			return null;
		grid[item.y][item.x] = item;
		return item;
	}

	Item getItem(int x, int y) {
		if (!inGrid(x, y))
			return null;
		return grid[y][x];
	}
}
