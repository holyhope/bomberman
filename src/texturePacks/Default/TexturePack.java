package texturePacks.Default;

import game.Game;
import game.Player;
import graphics.GameFrame;
import graphics.PlayerGraphic;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Random;

@SuppressWarnings("serial")
public class TexturePack extends GameFrame {
	private Screen screen = Screen.Other;

	public TexturePack(Game bomberman) {
		super(bomberman);
	}

	public static TexturePack create(Game bomberman) {
		TexturePack texture = new TexturePack(bomberman);

		texture.setLocationRelativeTo(null);
		texture.setTitle("Bomberman");
		texture.setIconImage(openImage("icon.gif"));

		texture.setVisible(true);

		texture.setRootPane(Background.Create(texture));

		return texture;
	}
	
	public static Image openImage(String path) {
		if (path.startsWith("/"))
			return GameFrame.openImage(path);
		return GameFrame.openImage("Default/images/"+path);
	}

	@Override
	public void updateTitle() {
		StringBuilder string = new StringBuilder();
		long time = getGame().timeSpent();
		long seconds = (time/1000)%60;
		long minutes = (time/1000)/60;

		string.append("Bomberman");
		if (getGame().isStarted()) {
			string.append(" - ").append(minutes).append(":");
			if (seconds < 10)
				string.append(0);
			string.append(seconds);
		}
		setTitle(string.toString());
	}
	
	@Override
	public void update(ActionEvent e) {
		switch (screen) {
		case Main:
			if (getGame().isStarted()) {
				screen = Screen.Game;
				updatePlayerList();
				setContentPane(new Content(this));
				System.out.println("let's play.");
				return;
			}
			repaint();
			break;
		case Game:
			if (getGame().isOver()) {
				screen = Screen.Victory;
				setContentPane(new VictoryScreen(this));
				return;
			}
			super.update(e);
			break;
		case Victory:
			repaint();
			break;
		default:
			screen = Screen.Main;
			setContentPane(new MainScreen(this));
		}
	}
	
	@Override
	protected PlayerGraphic newPlayer(Player player) {
		Random random = new Random();
		return new PlayerImage(this, getGame(), player, new Color(random.nextInt()));
	}
}
