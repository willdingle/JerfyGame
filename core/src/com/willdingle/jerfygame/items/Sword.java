package com.willdingle.jerfygame.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Sword extends Sprite {
	private float timer;
	
	public Sword(float x, float y, char dir) {
		super(new Sprite(new Texture("sword.png")));
		setScale(0.5f);
		setX(x);
		setY(y);
		switch(dir) {
		case 'd':
			setRotation(180);
			break;
		case 'l':
			setRotation(90);
			break;
		case 'r':
			setRotation(270);
			break;
		}
		setTimer(0.1f);
	}
	
	public void draw(Batch batch, float x, float y, float w, float h, char plDir) {
		switch(plDir) {
		case 'w':
			setPosition(x + w, y + h/2 - 20);
			break;
		case 's':
			
		}
		setPosition(x + w - 5, y + h/2 - 20);
		super.draw(batch);
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}
}
