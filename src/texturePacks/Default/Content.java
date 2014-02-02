package texturePacks.Default;

import game.Bomb;
import game.DropBuff;
import game.LifeBuff;
import game.RangeBuff;
import game.SpeedBuff;
import game.Wall;
import graphics.BombGraphic;
import graphics.BuffDropGraphic;
import graphics.BuffLifeGraphic;
import graphics.BuffRangeGraphic;
import graphics.BuffSpeedGraphic;
import graphics.ContentPane;
import graphics.WallGraphic;

@SuppressWarnings("serial")
class Content extends ContentPane {
	public Content(TexturePack texturePack) {
		super(texturePack);
		setOpaque(false);
		setDoubleBuffered(true);
		setCursor("Default/images/head.gif", "head");
	}

	@Override
	protected BombGraphic newBomb(Bomb bomb) {
		return new BombImage(this, getGame(), bomb.getBoardPosition(), bomb.getRange(), bomb.getTimer());
	}

	@Override
	protected WallGraphic newWall(Wall wall) {
		return new WallImage(this, getGame(), wall.getBoardPosition());
	}

	@Override
	protected BuffSpeedGraphic newSpeedBuff(SpeedBuff buff) {
		return new BuffSpeedImage(this, getGame(), buff.getBoardPosition());
	}

	@Override
	protected BuffRangeGraphic newRangeBuff(RangeBuff buff) {
		return new BuffRangeImage(this, getGame(), buff.getBoardPosition());
	}

	@Override
	protected BuffLifeGraphic newLifeBuff(LifeBuff buff) {
		return new BuffLifeImage(this, getGame(), buff.getBoardPosition());
	}

	@Override
	protected BuffDropGraphic newDropBuff(DropBuff buff) {
		return new BuffDropImage(this, getGame(), buff.getBoardPosition());
	}
}
