package graphics;

import game.Game;
import game.Player;

import java.awt.Color;

@SuppressWarnings("serial")
public class PlayerGraphic extends Player {
	public Color color;
	
	public PlayerGraphic(Game game, Player player, int color) {
		super(game, player.getPosition(), player.getName());
		this.color = new Color(color);
	}

	@Override
	public PlayerGraphic clone() {
		PlayerGraphic player = new PlayerGraphic(this.game, super.clone(), color.getRGB());
		return player;
	}

	public void setData(Player player) {
		x = player.x;
		y = player.y;
	}
}
