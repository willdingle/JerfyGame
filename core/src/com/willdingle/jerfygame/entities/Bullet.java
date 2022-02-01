package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Bullet extends Sprite {
	TiledMapTileLayer colLayer;
	char dir;
	
	public Bullet(TiledMapTileLayer colLayer, float plx, float ply, float plw, float plh, char dir) {
		super(new Sprite(new Texture("bullet.png")));
		this.colLayer = colLayer;
		this.dir = dir;
		switch(dir) {
		case 'l':
			setX(plx - getWidth() + 2);
			setY(ply + plh/2);
			break;
		case 'r':
			setX(plx + plw - 2);
			setY(ply + plh/2);
			break;
		case 'u':
			setX(plx + plw/2 - (getX() + getWidth()/2));
			setY(ply + plh);
			break;
		case 'd':
			setX(plx + plw/2 - (getX() + getWidth()/2));
			setY(ply - getHeight());
			break;
		}
	}
	
	private void move(float delta) {
		switch(dir) {
		case 'l':
			setX(getX() - 50 * delta);
			break;
		case 'r':
			setX(getX() + 50 * delta);
			break;
		case 'u':
			setY(getY() + 50 * delta);
			break;
		case 'd':
			setY(getY() - 50 * delta);
			break;
		}
	}
	
	@Override
	public void draw(Batch batch, float delta) {
		move(delta);
		super.draw(batch);
	}
}
