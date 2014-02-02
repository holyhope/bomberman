package texturePacks.Default;

import game.Game;
import game.Player;
import graphics.PlayerGraphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class PlayerImage extends PlayerGraphic {
	static private float reduction = 0.8f;
	private Color color;

	protected PlayerImage(TexturePack texturePack, Game game, Player player, Color color) {
		super(texturePack, game, player);
		this.color = new Color(color.getRGB());
	}

	@Override
	public TexturePack  getParent() {
		return (TexturePack) super.getParent();
	}

	@Override
	public void update(PlayerGraphic player) {
		super.update(player);
		// Ne pas mettre à jour la couleur car elle est fixé à la création de l'objet.
	}

	public Color getColor() {
		return new Color(color.getRGB());
	}
	
	public void setColor(Color color) {
		this.color = new Color(color.getRGB());
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

	private void drawName(Graphics g) {
		Point position = getPosition();
		getParent().positionGraphic(position);

		String string = getName();
		g.setColor(color);
		g.drawChars(string.toCharArray(), 0, Math.min(10, string.length()), position.x, position.y);
	}

	public Image getImage(Dimension dimension) {
		BufferedImage image = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		Dimension size = corpusSize();
		Point position = corpusPosition();
		Dimension radius;
		Point center = new Point(position.x+size.width/2, position.y+size.height/2);
		Shape bomb;

		radius = new Dimension((int)(size.width)/2, (int)(size.height)/2);
		if (radius.width <= 0 || radius.height <= 0)
			return null;
		float[] dist = {0f, 1f};
		Color[] colors = {Color.WHITE, new Color(220, 220, 220)};
		g2d.setPaint(new RadialGradientPaint(
				new Point(position.x+size.width/4, position.y+size.height/4),
				Math.min(radius.width, radius.height),
				new Point(position.x+size.width/5, position.y+size.height/5),
				dist,
				colors,
				CycleMethod.NO_CYCLE));
		bomb = new RoundRectangle2D.Double(center.x-radius.width, center.y-radius.height, radius.width*2, radius.height*2, (radius.width*3)/2, (radius.height*3)/2);
		g2d.fill(bomb);
		g2d.setColor(Color.BLACK);
		g2d.draw(bomb);

		radius = new Dimension((4*radius.width)/5, (7*radius.height)/10);
		center.y+= dimension.height/20;
		if (radius.width <= 0 || radius.height <= 0)
			return null;
		colors[0] = new Color(245, 220, 160);
		colors[1] = new Color(240, 225, 175);
		g2d.setPaint(new RadialGradientPaint(
				new Point(position.x+size.width/4, position.y+size.height/4),
				Math.min(radius.width, radius.height),
				new Point(position.x+size.width/5, position.y+size.height/5),
				dist,
				colors,
				CycleMethod.NO_CYCLE));
		bomb = new RoundRectangle2D.Double(center.x-radius.width, center.y-radius.height, radius.width*2, radius.height*2, radius.width, radius.height);
		g2d.fill(bomb);
		g2d.setColor(Color.BLACK);
		g2d.draw(bomb);

		center.y-= dimension.height/20;
		radius = new Dimension(radius.width/7, radius.height);
		if (radius.width <= 0 || radius.height <= 0)
			return null;
		center.x-= dimension.width/7;
		colors[0] = Color.BLACK;
		colors[1] = new Color(50, 50, 50);
		g2d.setPaint(new RadialGradientPaint(
				new Point(position.x+size.width/4, position.y+size.height/4),
				Math.min(radius.width, radius.height),
				new Point(position.x+size.width/5, position.y+size.height/5),
				dist,
				colors,
				CycleMethod.NO_CYCLE));
		bomb = new Ellipse2D.Double(center.x-radius.width, center.y-radius.height, radius.width*2, radius.height*2);
		g2d.fill(bomb);

		center.x+= (dimension.width/7)*2;
		g2d.setPaint(new RadialGradientPaint(
				new Point(position.x+size.width/4, position.y+size.height/4),
				Math.min(radius.width, radius.height),
				new Point(position.x+size.width/5, position.y+size.height/5),
				dist,
				colors,
				CycleMethod.NO_CYCLE));
		bomb = new Ellipse2D.Double(center.x-radius.width, center.y-radius.height, radius.width*2, radius.height*2);
		g2d.fill(bomb);

		radius = new Dimension(radius.width, radius.width);
		if (radius.width <= 0 || radius.height <= 0)
			return null;
		center.x-= dimension.width/7;
		center.y-= dimension.width/7;
		colors[0] = Color.WHITE;
		colors[1] = new Color(50, 50, 50);
		g2d.setPaint(new RadialGradientPaint(
				new Point(position.x+size.width/4, position.y+size.height/4),
				Math.min(radius.width, radius.height),
				new Point(position.x+size.width/5, position.y+size.height/5),
				dist,
				colors,
				CycleMethod.NO_CYCLE));
		bomb = new Ellipse2D.Double(center.x-radius.width, center.y-radius.height, radius.width*2, radius.height*2);
		g2d.fill(bomb);

		return image;
	}

	@Override
	public void paint(Graphics g) {
		Point position = new Point(this);
		getParent().positionGraphic(position);

		Dimension size = getParent().imageSize();
		Image player = getImage(size);
		if (player != null) {
			g.drawImage(player, position.x, position.y, size.width, size.height, getParent());
			drawName(g);
		}
	}

	@Override
	public PlayerImage clone() {
		PlayerImage player = new PlayerImage(getParent(), getParent().getGame(), this, color);
		return player;
	}
}
