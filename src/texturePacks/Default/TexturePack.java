package texturePacks.Default;

import game.Game;
import graphics.GameFrame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class TexturePack extends GameFrame {
	public TexturePack(Game bomberman) {
		super(bomberman);
	}

	public static TexturePack create(Game bomberman) {
		TexturePack texture = new TexturePack(bomberman);

		texture.setLocationRelativeTo(null);
		texture.setTitle("Bomberman");
		texture.setIcon();

		texture.setVisible(true);

		texture.setRootPane(Background.Create(texture));
		texture.setContentPane(Content.Create(texture));

		return texture;
	}

	private void setIcon() {
		URL url = getClass().getResource("images/icon.gif");
		if (url == null)
			throw new IllegalArgumentException("\"images/icon.gif\" doesn't exists.");

		File file = new File(url.getPath());
		try {
			Image image = ImageIO.read(file);
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateTitle() {
		StringBuilder string = new StringBuilder();

		long time = getGame().timeSpent();
		long seconds = (time/1000)%60;
		long minutes = (time/1000)/60;
		string.append("Bomberman - ").append(minutes).append(":");
		if (seconds < 10)
			string.append(0);
		string.append(seconds);
		setTitle(string.toString());
	}

	@Override
	protected void changePane(int n) {
		switch (n) {
		case 0:
			setContentPane(Content.Create(this));
			break;
		case 1:
			setContentPane(new VictoryScreen(this));
			break;
			default:
		}
	}
}
