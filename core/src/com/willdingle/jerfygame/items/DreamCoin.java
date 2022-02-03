package com.willdingle.jerfygame.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.willdingle.jerfygame.HitBox;
import com.willdingle.jerfygame.entities.Player;

public class DreamCoin extends Sprite {
	private float elTimeF;
	private int elTime;
	private Texture[] ani;
	
	public DreamCoin(TiledMapTileLayer colLayer, int x, int y) {
		super(new Sprite(new Texture("dream coin/0.png")));
		setX(5 * colLayer.getTileWidth());
		setY(5 * colLayer.getTileHeight());
		
		ani = new Texture[15];
		for (int n = 0; n < 15; n++) {
			ani[n] = new Texture("dream coin/" + n + ".png");
		}
	}
	
	public boolean touched(Player player) {
		boolean touched = false;
		if(HitBox.interact(player, getX(), getY(), getWidth(), getHeight(), HitBox.ALL)) {
			touched = true;
		}
		return touched;
	}
	
	public void animate() {
		if (elTimeF > (1/10f) * 15) {
			elTime = 0;
			elTimeF = 0;
		} else if (elTimeF > (1/10f) * 14) {
			elTime = 14;
		} else if (elTimeF > (1/10f) * 13) {
			elTime = 13;
		} else if (elTimeF > (1/10f) * 12) {
			elTime = 12;
		} else if (elTimeF > (1/10f) * 11) {
			elTime = 11;
		} else if (elTimeF > (1/10f) * 10) {
			elTime = 10;
		} else if (elTimeF > (1/10f) * 9) {
			elTime = 9;
		} else if (elTimeF > (1/10f) * 8) {
			elTime = 8;
		} else if (elTimeF > (1/10f) * 7) {
			elTime = 7;
		} else if (elTimeF > (1/10f) * 6) {
			elTime = 6;
		} else if (elTimeF > (1/10f) * 5) {
			elTime = 5;
		} else if (elTimeF > (1/10f) * 4) {
			elTime = 4;
		} else if (elTimeF > (1/10f) * 3) {
			elTime = 3;
		} else if (elTimeF > (1/10f) * 2) {
			elTime = 2;
		} else if (elTimeF > (1/10f) * 1) {
			elTime = 1;
		}
		elTimeF += Gdx.graphics.getDeltaTime();
		setTexture(ani[elTime]);
	}
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch);
		animate();
	}

}
