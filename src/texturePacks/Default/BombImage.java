package texturePacks.Default;

import game.Game;
import graphics.BombGraphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Date;

@SuppressWarnings("serial")
public class BombImage extends BombGraphic {
	static private float reduction = 0.8f;

	protected BombImage(Content parent, Game game, Point position, int range, Date end) {
		super(parent, game, position, range, end);
	}

	@Override
	public Content getParent() {
		return (Content) super.getParent();
	}

	@Override
	public void die(Graphics2D g2d) {
		Date now = new Date();
		long progress = now.getTime()-getEnd().getTime();
		int duration = getDuration();
		Content parent = getParent();

		if (progress > getDuration()) {
			return;
		}

		int range = getRange();
		Dimension size = parent.imageSize();
		Dimension radius = new Dimension((int)(progress*size.width)/(2*duration), (int)(progress*size.height)/(2*duration));
		if (radius.width <= 0 || radius.height <= 0) {
			return;
		}

		float[] dist = {0.0f, 0.4f, 0.6f};
		Color[] colors = {Color.RED, Color.YELLOW, Color.WHITE};
		Point position = getBoardPosition();
		parent.board2Graphic(position);
		Point center = new Point(position.x+size.width/2, position.y+size.height/2);
		g2d.setPaint(new RadialGradientPaint(center, (radius.width+radius.height)/2, center, dist, colors, CycleMethod.NO_CYCLE));
		Ellipse2D shape = new Ellipse2D.Double();

		shape.setFrame(center.x-radius.width/2, center.y-radius.height/2, radius.width, radius.height);
		g2d.fill(shape);
		shape.setFrame(center.x-radius.width/2-size.width*range, center.y-radius.height/2, radius.width+size.width*range*2, radius.height);
		g2d.fill(shape);
		shape.setFrame(center.x-radius.width/2, center.y-radius.height/2-size.height*range, radius.width, radius.height+size.height*range*2);
		g2d.fill(shape);
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

		float[] dist = {0.0f, 0.3f, 0.7f, 0.9f};
		Color[] colors = {Color.WHITE, new Color(25, 25, 25), new Color(15, 15, 15), Color.BLACK};
		g2d.setPaint(new RadialGradientPaint(
				new Point(position.x+size.width/4, position.y+size.height/4),
				Math.min(radius.width, radius.height),
				new Point(position.x+size.width/5, position.y+size.height/5),
				dist,
				colors,
				CycleMethod.NO_CYCLE));
		Ellipse2D bomb = new Ellipse2D.Double();
		bomb.setFrame(center.x-radius.width, center.y-radius.height, radius.width*2, radius.height*2);
		g2d.fill(bomb);

		g2d.setColor(Color.GRAY);
		g2d.drawOval(center.x-radius.width, center.y-radius.height, radius.width*2, radius.height*2);

		g2d.setPaint(new RadialGradientPaint(
				new Point(position.x+size.width/4, position.y+size.height/4),
				Math.min(radius.width, radius.height),
				new Point(position.x+size.width/5, position.y+size.height/5),
				dist,
				colors,
				CycleMethod.NO_CYCLE));
		int x[] = {center.x+(6*radius.width)/7, center.x+(8*radius.width)/7, center.x+(6*radius.width)/7, center.x+radius.width/7};
		int y[] = {center.y-(8*radius.height)/7, center.y-(6*radius.height)/7, center.y-(4*radius.height)/7, center.y-(6*radius.height)/7};
		Shape head = new Polygon(x, y, 4);
		g2d.fill(head);

		return image;
	}

	@Override
	public void show(Graphics2D g2d) {
		Point position = getBoardPosition();
		getParent().board2Graphic(position);
		Image bomb = getImage(getParent().imageSize());
		if (bomb != null) {
			g2d.drawImage(bomb, position.x, position.y, bomb.getWidth(getParent()), bomb.getHeight(getParent()), getParent());
		}
	}
}
