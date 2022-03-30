package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Enemy extends Sprite {
	private float health;
	private TiledMapTileLayer colLayer;
	
	public Enemy(String spriteName, TiledMapTileLayer colLayer, float x, float y) {
		super(new Sprite(new Texture(spriteName)));
		this.colLayer = colLayer;
		setX(x * colLayer.getTileWidth());
		setY(y * colLayer.getTileHeight());
		setHealth(3);
	}
	
	private void move(Player player) {
		float plx = player.getX();
		float ply = player.getY();
		float enx = getX();
		float eny = getY();
		
		//move left or right
		if(enx > plx + 1) {
			float oldX = getX();
			translateX(-30 * Gdx.graphics.getDeltaTime());
			if(tileCollide(-1, 0)) setX(oldX);
		} else if(enx < plx - 1) {
			float oldX = getX();
			translateX(30 * Gdx.graphics.getDeltaTime());
			if(tileCollide(1, 0)) setX(oldX);
		}
		
		//move up or down
		if(eny < ply - 1) {
			float oldY = getY();
			translateY(30 * Gdx.graphics.getDeltaTime());
			if(tileCollide(-1, 0)) setY(oldY);
		} else if(eny > ply + 1) {
			float oldY = getY();
			translateY(-30 * Gdx.graphics.getDeltaTime());
			if(tileCollide(-1, 0)) setY(oldY);
		}
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
	
	public void draw(Batch batch, BitmapFont font, Player player) {
		move(player);
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
