package com.willdingle.jerfygame.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.willdingle.jerfygame.HitBox;
import com.willdingle.jerfygame.entities.Enemy;

public class Bullet extends Sprite {
	TiledMapTileLayer colLayer;
	private char dir;
	private int speed;
	
	public Bullet(TiledMapTileLayer colLayer, float plx, float ply, float plw, float plh, char dir) {
		super(new Sprite(new Texture("bullet.png")));
		this.colLayer = colLayer;
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
		boolean col = false;
		switch(dir) {
		case 'l':
			setX(getX() - speed * delta);
			if(tileCollide(-1, 0)) col = true;
			break;
		case 'r':
			setX(getX() + speed * delta);
			if(tileCollide(1, 0)) col = true;
			break;
		case 'u':
			setY(getY() + speed * delta);
			if(tileCollide(0, 1)) col = true;
			break;
		case 'd':
			setY(getY() - speed * delta);
			if(tileCollide(0, -1)) col = true;
			break;
		}
		return collide(enemies) || col;
	}
	
	private boolean tileCollide(int xdir, int ydir) {
		float tileW = colLayer.getTileWidth(), tileH = colLayer.getTileHeight();
		boolean colX = false, colY = false;
		
		//collide left
		if (xdir < 0) {
			//top left
			colX = isTileBlocked((getX()) / tileW, (getY() + getHeight()) / tileH); 
			//middle left
			if (! colX) colX = isTileBlocked((getX()) / tileW, (getY() + getHeight() / 2) / tileH);
			//bottom left
			if (! colX) colX = isTileBlocked((getX()) / tileW, getY() / tileH);
		
		//collide right
		} else if (xdir > 0) {
			//top right
			colX = isTileBlocked((getX() + getWidth()) / tileW, (getY() + getHeight()) / tileH);
			//middle right
			if (! colX) colX = isTileBlocked((getX() + getWidth()) / tileW, (getY() + getHeight() / 2) / tileH);
			//bottom right
			if (! colX) colX = isTileBlocked((getX() + getWidth()) / tileW, getY() / tileH);
		}
		
		//collide down
		if (ydir < 0) {
			//bottom left
			colY = isTileBlocked(getX() / tileW, getY() / tileH);
			//bottom middle
			if (! colY) colY = isTileBlocked((getX() + getWidth() / 2) / tileW, getY() / tileH);
			//bottom right
			if (! colY) colY = isTileBlocked((getX() + getWidth()) / tileW, getY() / tileH);
		
		//collide top
		} else if (ydir > 0) {
			//top left
			colY = isTileBlocked(getX() / tileW, (getY() + getHeight()) / tileH);
			//top middle
			if (! colY) colY = isTileBlocked((getX() + getWidth() / 2) / tileW, (getY() + getHeight()) / tileH);
			//top right
			if (! colY) colY = isTileBlocked((getX() + getWidth()) / tileW, (getY() + getHeight()) / tileH);
		}
		
		return colX || colY;
	}
	
	public boolean isTileBlocked(float x, float y) {
		boolean col = colLayer.getCell((int) x, (int) y).getTile().getProperties().containsKey("blocked");
		return col;
	}
	
	public boolean draw(Batch batch, float delta, Enemy[] enemies) {
		boolean col = move(delta, enemies);
		super.draw(batch);
		return col;
	}
}
