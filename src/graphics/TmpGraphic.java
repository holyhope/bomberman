package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;

interface TmpGraphic {
	Date getEnd();
	int getDuration();
	public void paint(Graphics graph);
	void show(Graphics2D g2d);
	void die(Graphics2D g2d);
	void die(Date now);
}
