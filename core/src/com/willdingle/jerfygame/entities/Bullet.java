package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Bullet extends Sprite {
	TiledMapTileLayer colLayer;
	char dir;
	
	public Bullet(TiledMapTileLayer colLayer, float x, float y, char dir) {
		super(new Sprite(new Texture("bullet.png")));
		this.colLayer = colLayer;
		setX(x);
		setY(y);
		this.dir = dir;
	}
	
	private void move(float delta) {
		switch(dir) {
		case 'l':
			setX(getX() - 50 * delta);
		}
	}
	
	@Override
	public void draw(Batch batch, float delta) {
		move(delta);
		super.draw(batch);
	}
}
