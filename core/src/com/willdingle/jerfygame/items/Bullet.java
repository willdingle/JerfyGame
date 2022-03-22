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
	
	private void collide(Enemy enemy) {
		boolean col = false;
		//for(Enemy enemy : enemies) {
			col = HitBox.bullet(this, enemy);
			if(col) System.out.println(col);
		//}
	}
	
	private void move(float delta, Enemy enemy) {
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
		collide(enemy);
	}
	
	public void draw(Batch batch, float delta, Enemy enemy) {
		move(delta, enemy);
		super.draw(batch);
	}
}
