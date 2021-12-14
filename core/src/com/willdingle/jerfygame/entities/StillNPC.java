package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class StillNPC extends Sprite {
	
	public StillNPC(String spriteName, TiledMapTileLayer collisionLayer, float x, float y) {
		super(new Sprite(new Texture(spriteName)));
		setX(x * collisionLayer.getTileWidth());
		setY(y * collisionLayer.getTileHeight());
		
	}
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch);
	}

}
