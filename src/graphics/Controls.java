package graphics;

import java.awt.event.KeyEvent;

public class Controls {
	private final int up;
	private final int down;
	private final int left;
	private final int right;
	private final int bomb;

	public Controls(int up, int down, int left, int right, int bomb) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.bomb = bomb;
	}

	public MyEvent getEvent(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == up) {
			return MyEvent.UP;
		} else if (key == down) {
			return MyEvent.DOWN;
		} else if (key == left) {
			return MyEvent.LEFT;
		} else if (key == right) {
			return MyEvent.RIGHT;
		} else if (key == bomb) {
			return MyEvent.BOMB;
		}
		return MyEvent.NONE;
	}
}
