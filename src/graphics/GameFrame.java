package graphics;

import game.Game;
import game.Player;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public abstract class GameFrame extends JFrame implements ActionListener {
	private Game game = null;
	private Timer timer;
	private Date lastUpdate = new Date(0);
	private ArrayList<PlayerGraphic> playerList = new ArrayList<PlayerGraphic>();
	private int screen = 0;

	public abstract void updateTitle();

	private GameFrame(Game game, int delay) {
		Dimension dimension = game.getWindowFavoriteSize();
		setSize(dimension);
		setPreferredSize(dimension);
		dimension = game.getBoardSize();
		dimension.width = dimension.width*5+38;
		dimension.height = dimension.height*5+16;
		setMinimumSize(dimension);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	protected Rectangle getBorder() {
		return new Rectangle(30, 8, 16, 16);
	}

	public static Image openImage(Object obj, String path) {
		Image image = null;
		URL url = obj.getClass().getResource(path);
		if (url == null)
			throw new IllegalArgumentException("\""+path+"\" doesn't exists.");
		File file = new File(url.getPath());
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public GameFrame(Game game) {
		this(game, 10);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game = game;
	}

	public void setDelay(int speed) {
		timer.setDelay(speed);
	}

	public boolean updated() {
		return !lastUpdate.equals(game.getLastUpdate());
	}

	public Game getGame() {
		return game;
	}

	public boolean hasGameStarted() {
		return game != null && game.isStarted();
	}

	@Override
	public Dimension getPreferredSize() {
		return game.getWindowFavoriteSize();
	}

	public void addPlayer(Player character, Controls controls) {
		MyKeyListener mkl = new MyKeyListener(character, controls);
		addKeyListener(mkl);
		addFocusListener(mkl);
	}

	public Date getLastUpdate() {
		return (Date) lastUpdate.clone();
	}

	public Date getUpdate() {
		return game.getLastUpdate();
	}

	@Override
	public void paint(Graphics g) {
		Dimension dimension = getSize();
		Image img = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
		Graphics g2d = img.getGraphics();

		super.paint(g2d);
		Container container = getContentPane();
		container.paint(g2d.create(8, 30, container.getWidth()+1, container.getHeight()+1));

		g.drawImage(img, 0, 0, dimension.width, dimension.height, this);
	}

	public void update(ActionEvent arg0) {
		updateTitle();
		if (updated()) {
			repaint();
			if (game.isStarted() && game.isOver()) {
				if (screen  == 0) {
					screen = 1;
					changePane(1);
				}
			}
		}
	}

	protected abstract void changePane(int n);

	@Override
	public final void actionPerformed(ActionEvent arg0) {
		update(arg0);
		if (updated()) {
			lastUpdate = new Date();
		}
	}

	public Iterable<PlayerGraphic> getPlayerList() {
		Random random = new Random();
		int index;

		for (Player player: game.getPlayerList()) {
			index = playerList.indexOf(player);
			if (index == -1) {
				playerList.add(new PlayerGraphic(getGame(), player, random.nextInt()));
			} else {
				playerList.get(index).setData(player);
			}
		}
		return playerList;
	}

	public int getDelay() {
		return timer.getDelay();
	}

	public Dimension imageSize() {
		Dimension dimension = getSize();
		Dimension boardSIze = getGame().getBoardSize();
		dimension = new Dimension(
				dimension.width/boardSIze.width,
				dimension.height/boardSIze.height);
		return dimension;
	}
}
