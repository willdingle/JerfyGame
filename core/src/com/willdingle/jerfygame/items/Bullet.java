package com.willdingle.jerfygame.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.willdingle.jerfygame.HitBox;
import com.willdingle.jerfygame.entities.Enemy;

public class Bullet extends Sprite {
	//TiledMapTileLayer colLayer;
	private char dir;
	private int speed;
	
	public Bullet(TiledMapTileLayer colLayer, float plx, float ply, float plw, float plh, char dir) {
		super(new Sprite(new Texture("bullet.png")));
		//this.colLayer = colLayer;
		this.dir = dir;
		speed = 150;
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
	
	private boolean collide(Enemy[] enemies) {
		boolean col = false;
		for(int n = 0; n < enemies.length; n++) {
			if(enemies[n] != null) {
				col = HitBox.bullet(this, enemies[n]);
				if(col) {
					enemies[n].setHealth(enemies[n].getHealth() - 1);
					if(enemies[n].getHealth() <= 0) enemies[n] = null;
					break;
				}
				
			}
		}
		return col;
	}
	
	private boolean move(float delta, Enemy[] enemies) {
		switch(dir) {
		case 'l':
			setX(getX() - speed * delta);
			break;
		case 'r':
			setX(getX() + speed * delta);
			break;
		case 'u':
			setY(getY() + speed * delta);
			break;
		case 'd':
			setY(getY() - speed * delta);
			break;
		}
		return collide(enemies);
	}
	
	public boolean draw(Batch batch, float delta, Enemy[] enemies) {
		boolean col = move(delta, enemies);
		super.draw(batch);
		return col;
	}
}
