package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemy extends Sprite {
	private float health;
	
	public Enemy(String spriteName, TiledMapTileLayer collisionLayer, float x, float y) {
		super(new Sprite(new Texture(spriteName)));
		setX(x * collisionLayer.getTileWidth());
		setY(y * collisionLayer.getTileHeight());
		setHealth(3);
	}
	
	public void draw(Batch batch, BitmapFont font) {
		super.draw(batch);
		font.draw(batch, Float.toString(health), getX(), getY() + getWidth() * 2 + 10);
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
}
