package controller;

import game.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener,FocusListener,ActionListener {
	private final Player player;
	Controls controls;

	/**
	 * Constructor for PlayerController
	 * 
	 * @param player player whose controls are assigned
	 * @param controls controls assigned to the player
	 */
	public PlayerController(Player player, Controls controls) {
		this.player = player;
		this.controls = controls;
	}

	/**
	 * Performs an action when a key is pressed
	 * 
	 * @param e event deciding the action to do
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (controls.getEvent(e.getKeyCode())) {
		case UP:	player.up();	break;
		case DOWN:	player.down();	break;
		case LEFT:	player.left();	break;
		case RIGHT:	player.right();	break;
		case BOMB:		player.plant();		break;
		case SURREND:	player.surrend();	break;
		default:	break;
		}
	}

	/**
	 * Performs an action when a key is released
	 * 
	 * @param e event deciding the action to do
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (controls.getEvent(e.getKeyCode())) {
		case UP:	player.stopY();	break;
		case DOWN:	player.stopY();	break;
		case LEFT:	player.stopX();	break;
		case RIGHT:	player.stopX();	break;
		default:	break;
		} 
	}

	/**
	 * 
	 */
	@Override
	public void keyTyped(KeyEvent e) {}
	
	/**
	 * 
	 */
	@Override
	public void focusGained(FocusEvent e) {}

	/**
	 * 
	 */
	@Override
	public void focusLost(FocusEvent e) {
		player.stop();
	}

	/**
	 * getter for the player
	 * 
	 * @return a clone of the player
	 */
	public Player getPlayer() {
		return player.clone();
	}

	/**
	 * Waits a specific event to set the player to ready
	 * 
	 * @param event event used to set the player status to ready
	 * @return true if the player is ready, false otherwise
	 */
	public boolean perform(MyEvent event) {
		switch (event) {
		case READY:
			player.ready();
			return true;
		default:
			return false;
		}
	}

	/**
	 * Waits an event and calls perform
	 * 
	 * @param e event used to set the player status to ready
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "ready":	perform(MyEvent.READY);	break;
		default:	break;
		}
	}
}
