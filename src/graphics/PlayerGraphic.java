package graphics;

import game.Game;
import game.Player;

@SuppressWarnings("serial")
public abstract class PlayerGraphic extends Player implements ItemGraphic {
	private GameFrame parent;
	
	protected PlayerGraphic(GameFrame gameFrame, Game game, Player player) {
		super(game, player.getPosition(), player.getName());
		parent = gameFrame;
	}

	public void setData(Player player) {
		x = player.x;
		y = player.y;
	}

	@Override
	public GameFrame getParent() {
		return parent;
	}

	@Override
	public abstract PlayerGraphic clone();

	public void update(PlayerGraphic player) {
		x = player.x;
		y = player.y;
	}
}
