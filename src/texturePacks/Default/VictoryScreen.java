package texturePacks.Default;

import graphics.GameFrame;
import graphics.Panel;
import graphics.PlayerGraphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

@SuppressWarnings("serial")
class VictoryScreen extends Panel {
	public VictoryScreen(GameFrame gameFrame) {
		super(gameFrame);
	}

	private void showWinners(Graphics2D g, int positiony, int size) {
		Point position = new Point(0, positiony);
		FontMetrics fm = g.getFontMetrics(g.getFont());
		Dimension dimension = getSize();
		Rectangle2D rect;
		String string;
		List<PlayerGraphic> winners = getFrame().getWinners();
		switch (winners.size()) {
		case 1:
			string = "Le vainqueur est :";
			break;
		case 0:
			string = "Égalité !";
			break;
		default:
			string = "Les vainqueurs sont :";
		}
		rect = fm.getStringBounds(string, g);
		position.x = (dimension.width - (int)rect.getWidth()) / 2;
		position.y+= size;
		g.drawString(string, position.x, position.y);

		for (PlayerGraphic winner: winners) {
			string = winner.getName();
			rect = fm.getStringBounds(string, g);
			position.x = (dimension.width - (int)rect.getWidth()) / 2;
			position.y+= size;
			g.setColor(((PlayerImage)winner).getColor());
			g.drawString(string, position.x, position.y);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Dimension dimension = getSize();
		int size = dimension.height/20;
		BufferedImage image = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		Point position = new Point(10, size+10);
		g2d.setFont(new Font("Arial Bold Italic", Font.BOLD, size));
		g2d.setColor(Color.black);
		String string = "Bomberman";
		Rectangle2D rect = g2d.getFontMetrics(g2d.getFont()).getStringBounds(string, g2d);
		position.x = (dimension.width - (int)rect.getWidth()) / 2;
		g2d.drawString(string, position.x, position.y);
		showWinners(g2d, position.y+20, size);
		g.drawImage(image, 0, 0, dimension.width, dimension.height, this);
	}
}
