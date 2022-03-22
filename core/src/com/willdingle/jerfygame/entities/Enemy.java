package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemy extends Sprite {
	public Enemy(String spriteName, TiledMapTileLayer collisionLayer, float x, float y) {
		super(new Sprite(new Texture(spriteName)));
		setX(x * collisionLayer.getTileWidth());
		setY(y * collisionLayer.getTileHeight());
	}
}
