package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemy extends Sprite {
	private float health;
	
	public Enemy(String spriteName, TiledMapTileLayer collisionLayer, float x, float y) {
		super(new Sprite(new Texture(spriteName)));
		setX(x * collisionLayer.getTileWidth());
		setY(y * collisionLayer.getTileHeight());
		setHealth(3);
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
}
