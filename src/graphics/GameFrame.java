package graphics;

import game.Game;
import game.Player;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

import controller.Controls;
import controller.PlayerController;

@SuppressWarnings("serial")
public abstract class GameFrame extends JFrame implements ActionListener {
	private Game game = null;
	private Timer timer;
	private Date lastUpdate = new Date(0);
	private ArrayList<PlayerGraphic> playerList = new ArrayList<PlayerGraphic>();

	public abstract void updateTitle();

	private GameFrame(Game game, int delay) {
		this.game = game;
		Dimension dimension = game.getWindowFavoriteSize();
		setSize(dimension);
		setPreferredSize(dimension);
		dimension = game.getBoardSize();
		Rectangle border = getBorder();
		dimension.width = dimension.width*5+border.width;
		dimension.height = dimension.height*5+border.height;
		setMinimumSize(dimension);
		timer = new Timer(delay, this);
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				timer.start();
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				timer.stop();
				for (PlayerGraphic player: getPlayerList())
					player.surrend();
			}

			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowClosing(WindowEvent arg0) {}
			@Override
			public void windowActivated(WindowEvent arg0) {}
		});
	}

	static protected Rectangle getBorder() {
		return new Rectangle(8, 30, 16, 38);
	}

	public static Image openImage(String path) {
		Image image = null;
		URL url = GameFrame.class.getResource("../texturePacks/"+path);
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

	protected abstract PlayerGraphic newPlayer(Player player);

	private Dimension getContentSize() {
		Dimension dimension = getSize();
		Rectangle border = getBorder();
		dimension.width-= border.width;
		dimension.height-= border.height;
		return dimension;
	}

	public void addPlayer(Player character, Controls controls) {
		addPlayer(character);
		PlayerController mkl = new PlayerController(character, controls);
		
		addKeyListener(mkl);
		addFocusListener(mkl);
	}

	public void board2Graphic(Point position) {
		scalePosition(position, getGame().getBoardSize());
	}

	public void positionGraphic(Point position) {
		scalePosition(position, getGame().getWindowFavoriteSize());
		Dimension imageSize = imageSize();
		position.setLocation(position.x-imageSize.width/2, position.y-imageSize.height/2);
	}

	public void scalePosition(Point position, Dimension dimension) {
		Dimension actualSize = getContentSize();
		position.setLocation(
				(position.x*actualSize.width)	/dimension.width,
				(position.y*actualSize.height)	/dimension.height);
	}

	protected void addPlayer(PlayerGraphic player) {
		int index = playerList.indexOf(player);
		if (index >= 0) {
			playerList.get(index).update(player);
		} else {
			playerList.add(player);
		}
	}

	protected void addPlayer(Player player) {
		addPlayer(newPlayer(player));
	}

	public Date getLastUpdate() {
		return (Date) lastUpdate.clone();
	}

	public Date getUpdate() {
		return game.getLastUpdate();
	}

	@Override
	public void paint(Graphics g) {
		Rectangle border = getBorder();
		Dimension size = getSize();
		Image img = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		Graphics g2d = img.getGraphics();

		super.paint(g2d);
		getContentPane().paint(g2d.create(border.x, border.y, size.width-border.width, size.height-border.height));

		g.drawImage(img, 0, 0, size.width, size.height, this);
	}

	public void update(ActionEvent e) {
		updateTitle();
		updatePlayerList();
		if (updated())
			repaint();
	}

	protected void updatePlayerList() {
		for (Player player: getGame().getPlayerList())
			addPlayer(player);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update(e);
		if (updated()) {
			lastUpdate = new Date();
		}
	}

	public List<PlayerGraphic> getPlayerList() {
		List<PlayerGraphic> players = new ArrayList<PlayerGraphic>();
		for (PlayerGraphic player: playerList)
			players.add(player.clone());
		return players;
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

	public List<Player> getWinners() {
		return game.getWinners();
	}
}
