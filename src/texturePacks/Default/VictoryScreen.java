package texturePacks.Default;

import game.Player;
import graphics.GameFrame;
import graphics.Victory;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.List;

@SuppressWarnings("serial")
public class VictoryScreen extends Victory {
	public VictoryScreen(GameFrame gameframe) {
		super(gameframe);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Dimension dimension = getSize();
		int size = 15;
		StringBuilder sb = new StringBuilder();
		Font font = new Font("Arial Bold", Font.BOLD, size);
		FontMetrics fm = g.getFontMetrics(font);
		
		String string = "Bomberman";
		
		
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(string, g);
		int x = (dimension.width - (int) rect.getWidth()) / 2;
		g.drawString(string, x, dimension.height/4);
		
		List<Player> winners = getGame().getWinners();
		switch (winners.size()) {
		case 1:
			string = "Le vainqueur est : ";
			break;
		case 0:
			string = "Egalité !";
			break;
		default:
			string = "Les vainqueurs sont : ";
		}
		
		rect = fm.getStringBounds(string, g);
		x = (dimension.width - (int) rect.getWidth()) / 2;
		g.drawString(string, x, dimension.height*2/4);
		
		for (Player winner: winners)
			sb.append(winner.getName());

		string = sb.toString();
		
		rect = fm.getStringBounds(string, g);
		x = (dimension.width - (int) rect.getWidth()) / 2;
		g.drawString(string, x, dimension.height*3/4);
	}
}
