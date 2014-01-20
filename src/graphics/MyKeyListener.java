package graphics;

import game.Player;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MyKeyListener implements KeyListener, FocusListener {
	final Player player;
	Controls controls;

	MyKeyListener(Player player, Controls controls) {
		this.player = player;
		this.controls = controls;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (controls.getEvent(e)) {
			case UP:	player.up();		break;
			case DOWN:	player.down();	break;
			case LEFT:	player.left();	break;
			case RIGHT:	player.right();	break;
			case BOMB:	player.plant();	break;
			default:	break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (controls.getEvent(e)) {
			case UP:
				player.stopY();
			break;
			case DOWN:
				player.stopY();
			break;
			case LEFT:
				player.stopX();
			break;
			case RIGHT:
				player.stopX();
			break;
			default:	break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void focusGained(FocusEvent arg0) {}

	@Override
	public void focusLost(FocusEvent arg0) {
		player.stop();
	}
}
