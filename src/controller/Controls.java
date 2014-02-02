package controller;


public class Controls {
	private int up = -1;
	private int down = -1;
	private int left = -1;
	private int right = -1;
	private int bomb = -1;
	private int surrend = -1;

	/**
	 * Constructor for Controls
	 * 
	 * @param up the up key
	 * @param down the down key
	 * @param left the left key
	 * @param right the right key
	 * @param bomb the bomb key
	 */
	public Controls(int up, int down, int left, int right, int bomb) {
		this.up		= up;
		this.down	= down;
		this.left	= left;
		this.right	= right;
		this.bomb	= bomb;
	}

	/**
	 * setter for Controls
	 * 
	 * @param event event of which the corresponding key will be updated
	 * @param key the new key used to replace the old one
	 */
	public void setControl(MyEvent event, int key) {
		switch (event) {
		case UP:		up = key;		break;
		case DOWN:		down = key;		break;
		case LEFT:		left = key;		break;
		case RIGHT:		right = key;	break;
		case BOMB:		bomb = key;		break;
		case SURREND:	surrend = key;	break;
		default:						break;
		}
	}

	/**
	 * getter for a control
	 * 
	 * @param event event used to retrieve the corresponding key
	 * @return the key triggering the event
	 */
	public int getControl(MyEvent event) {
		switch (event) {
		case UP:		return up;
		case DOWN:		return down;
		case LEFT:		return left;
		case RIGHT:		return right;
		case BOMB:		return bomb;
		case SURREND:	return surrend;
		default:		return -1;
		}
	}

	/**
	 * getter for an event
	 * 
	 * @param key key used to retrieve the corresponding event
	 * @return the event triggered by the key
	 */
	public MyEvent getEvent(int key) {
		if (key == up)		return MyEvent.UP;
		if (key == down)	return MyEvent.DOWN;
		if (key == left)	return MyEvent.LEFT;
		if (key == right)	return MyEvent.RIGHT;
		if (key == bomb)	return MyEvent.BOMB;
		if (key == surrend)	return MyEvent.SURREND;
		return MyEvent.NONE;
	}
	
	/**
	 * Method toString for Controls
	 * 
	 * @return a String representation of the Controls
	 */
	@Override
	public String toString() {
		return "Controls:"+up+"\t"+down+"\t"+left+"\t"+right+"\t"+bomb+"\t"+surrend;
	}
}
