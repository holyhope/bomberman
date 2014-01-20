package texturePacks.Default;

import game.Game;
import graphics.BuffSpeedGraphic;
import graphics.ContentPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class BuffSpeedImage extends BuffSpeedGraphic {
	static private float reduction = 0.5f;
	static private float size = 5;

	BuffSpeedImage(ContentPane parent, Game game, Point position) {
		super(parent, game, position);
	}

	@Override
	public void paint(Graphics g2d) {
		Point position = getBoardPosition();
		getParent().board2Graphic(position);
		Image buff = getImage(getParent().imageSize());
		if (buff != null) {
			g2d.drawImage(buff, position.x, position.y, buff.getWidth(getParent()), buff.getHeight(getParent()), getParent());
		}
	}

	private Dimension corpusSize() {
		Dimension size = getParent().imageSize();
		size.width*= reduction;
		size.height*= reduction;
		return size;
	}

	private Point corpusPosition() {
		Dimension corpusSize = corpusSize();
		Dimension imageSize = getParent().imageSize();
		return new Point((imageSize.width-corpusSize.width)/2, (imageSize.height-corpusSize.height)/2);
	}

	public Image getImage(Dimension dimension) {
		BufferedImage image = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		Dimension size = corpusSize();
		Point position = corpusPosition();
		Dimension radius = new Dimension((int)(size.width)/2, (int)(size.height)/2);
		Point center = new Point(position.x+size.width/2, position.y+size.height/2);
		if (radius.width <= 0 || radius.height <= 0)
			return null;

		float[] dist = {0.0f, 0.8f};
		Color[] colors = {new Color(0, 200, 100, 0), Color.YELLOW};
		g2d.setPaint(new RadialGradientPaint(
				new Point(position.x+size.width/2, position.y+size.height/2),
				BuffSpeedImage.size,
				new Point(position.x+size.width/2, position.y+size.height/2),
				dist,
				colors,
				CycleMethod.REFLECT));
		Ellipse2D buff = new Ellipse2D.Double();
		buff.setFrame(center.x-radius.width, center.y-radius.height, radius.width*2, radius.height*2);
		g2d.fill(buff);

		return image;
	}
}
