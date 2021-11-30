package com.willdingle.jerfygame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseMenu {
	
	private SpriteBatch batch;
	private BitmapFont font;
	
	public PauseMenu(SpriteBatch batch, BitmapFont font) {
		this.batch = batch;
		this.font = font;
	}
	
	public void render() {
		ScreenUtils.clear(178/255f, 0, 1, 0);
		
		batch.begin();
		font.draw(batch, "Jerfy", 750, 1025);
		batch.end();
	}
}
