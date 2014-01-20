package texturePacks.Default;

import graphics.GameFrame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JRootPane;

@SuppressWarnings("serial")
class Background extends JRootPane {
	private Image image;

	private void setImage(String path) {
		URL url = getClass().getResource(path);
		if (url == null)
			throw new IllegalArgumentException("\""+path+"\" doesn't exists.");
		File file = new File(url.getPath());
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static Background Create(GameFrame gameframe) {
		Background background = new Background();
		background.setImage("images/ground.png");
		background.setDoubleBuffered(true);
		background.setSize(gameframe.getSize());
		return background;
	}

	private void paintGrid(Graphics g, Dimension boardSize) {
/*		int i;
		Rectangle current = g.getClipBounds();
		g.setColor(new Color(0, 0, 0)); 

		for (i=1; i<boardSize.width; i++)
			g.drawLine(0, (i * current.height) / boardSize.width, current.width, (i * current.height) / boardSize.width);
		for (i=1; i<boardSize.height; i++)
			g.drawLine((i*current.width)/boardSize.height, 0, (i * current.width) / boardSize.height, current.height);
*/	}

	@Override
	public void paint(Graphics g) {
		int i, j;
		Dimension boardSize = ((GameFrame) getParent()).getGame().getBoardSize();
		Rectangle dimension = g.getClipBounds();
		Image img = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) img.getGraphics();
		g2d.setClip(0, 0, dimension.width, dimension.height);

		if (prepareImage(image, dimension.width/boardSize.width+1, dimension.height/boardSize.height+1, this)) {
			for (i=0; i<boardSize.width; i++) {
				for (j=0; j<boardSize.height; j++) {
					g2d.drawImage(image,
							(i*dimension.width)		/boardSize.width,
							(j*dimension.height)	/boardSize.height,
							dimension.width		/boardSize.width		+1,
							dimension.height	/boardSize.height	+1,
							this);
				}
			}
			paintGrid(g2d, boardSize);
			g.drawImage(img, 0, 0, dimension.width, dimension.height, this);
			g2d.dispose();
		}
	}
}
