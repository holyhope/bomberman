package texturePacks.Default;

import graphics.Panel;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;

@SuppressWarnings("serial")
class MainScreen extends Panel {
	private JButton quit;

	MainScreen(TexturePack texturePack) {
		super(texturePack);
		setDoubleBuffered(true);
		setCursor("Default/images/head.gif", "head");
		quit = new JButton("Quitter");
		quit.setVerticalTextPosition(AbstractButton.CENTER);
		quit.setHorizontalTextPosition(AbstractButton.LEADING);
		quit.setMnemonic(KeyEvent.VK_ESCAPE);
		quit.setActionCommand("quit");
		quit.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		add(quit);
	}

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
	}
}
