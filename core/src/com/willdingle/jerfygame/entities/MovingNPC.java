package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MovingNPC extends Sprite {
	
	private float elTimeF;
	private int elTime;
	Texture[] ani;
	int lBound;
	int rBound;
	int moveSpeed;
	
	public MovingNPC(String spriteName, TiledMapTileLayer collisionLayer, int x, int y, int lBound, int rBound, int frames) {
		super(new Sprite(new Texture(spriteName)));
		setX(x * collisionLayer.getTileWidth());
		setY(y * collisionLayer.getTileHeight());
		
		ani = new Texture[frames];
		for (int n = 0; n < frames; n++) {
			ani[n] = new Texture("buggo/" + n + ".png");
		}
		
		this.lBound = lBound * collisionLayer.getTileWidth();
		this.rBound = rBound * collisionLayer.getTileWidth();
		moveSpeed = 10;
	}
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch);
		animate();
		move();
	}
	
	public void animate() {
		if (elTimeF > 0.4) {
			elTime = 0;
			elTimeF = 0;
		} else if (elTimeF > 0.3) {
			elTime = 3;
		} else if (elTimeF > 0.2) {
			elTime = 2;
		} else if (elTimeF > 0.1) {
			elTime = 1;
		}
		elTimeF += Gdx.graphics.getDeltaTime();
		setTexture(ani[elTime]);
	}
	
	public void move() {
		if ((getX() >= rBound && moveSpeed > 0) || (getX() <= lBound && moveSpeed < 0)) {
			moveSpeed *= -1;
		}
		setX(getX() + moveSpeed * Gdx.graphics.getDeltaTime());
	}

}
