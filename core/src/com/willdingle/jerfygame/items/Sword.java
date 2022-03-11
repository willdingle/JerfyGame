package com.willdingle.jerfygame.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Sword extends Sprite {
	private float timer;
	private char dir;
	
	public Sword(char dir) {
		super(new Sprite(new Texture("sword.png")));
		setScale(0.5f);
		
		this.dir = dir;
		switch(dir) {
		case 'u':
			break;
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
	
	public void draw(Batch batch, float x, float y, float w, float h) {
		float tempX = x + w - 5;
		float tempY = y + h/2 - 20;
		switch(dir) {
		case 'u':
			setPosition(tempX - 10, tempY + 20);
			break;
		case 'd':
			setPosition(tempX - 10, tempY - 10);
			break;
		case 'l':
			setPosition(tempX - 22, tempY);
			break;
		case 'r':
			setPosition(tempX, tempY);
			break;
		}
		super.draw(batch);
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}
}
