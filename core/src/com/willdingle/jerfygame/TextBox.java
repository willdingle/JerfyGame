package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextBox extends Sprite {
	private OrthographicCamera cam;

	public TextBox(Sprite sprite, OrthographicCamera cam) {
		super(sprite);
		this.cam = cam;
		setSize(getWidth() / 5, getHeight() / 5);
		setX(cam.viewportWidth / 2 - getWidth() / 2);
		setY(cam.position.y + 50);
		
	}
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch);
	}

}
