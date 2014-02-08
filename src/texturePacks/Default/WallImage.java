package texturePacks.Default;

import game.Game;
import graphics.ContentPane;
import graphics.WallGraphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

@SuppressWarnings("serial")
public class WallImage extends WallGraphic {
	private int reduction = 5;
	private int row = 3;
	private int column = 2;
	private int[] startX;

	protected WallImage(ContentPane parent, Game game, Point position) {
		super(parent, game, position);
		Random random = new Random();
		startX = new int[row];

		for (int i=0; i<row; i++)
			startX[i] = random.nextInt(reduction);
	}

	@Override
	public void paint(Graphics graph) {
		Dimension dimension = getParent().imageSize();
		Point boardPos = getBoardPosition();
		Point position = getBoardPosition();
		getParent().board2Graphic(position);
		Image img = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) img.getGraphics();

		if((boardPos.x%2 == 1 && boardPos.y%2 == 1)) {
			g2d.setColor(Color.DARK_GRAY);
			g2d.fill(new Rectangle2D.Double(0, 0, dimension.width, dimension.height));
		}
		else {
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fill(new Rectangle2D.Double(0, 0, dimension.width, dimension.height));
			Dimension dim = new Dimension((dimension.width-(row+1)*reduction)/column, (dimension.height-(column+1)*reduction)/row);
			Point pos = (Point) position.clone();
			pos.x = reduction/2;
			pos.y = reduction/2;
			g2d.setColor(Color.WHITE);
			for (int i=0; i<row; i++) {
				pos.x = startX[i];
				for (int j=0; j<column+1; j++) {
					g2d.fill(new Rectangle2D.Double(
							pos.x+(j*(dim.width+reduction)),
							pos.y+(i*(dim.height+reduction)),
							dim.width,
							dim.height));
				}
			}
		}

		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, dimension.width-1, dimension.height-1);
		graph.drawImage(img, position.x, position.y, dimension.width, dimension.height, getParent());
		g2d.dispose();
	}
}
