package com.willdingle.jerfygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;

public class JerfyGame extends Game {
	SpriteBatch batch;
	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DeterminationMono.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 150;
		font = generator.generateFont(parameter);
		
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
		Gdx.graphics.setTitle(Gdx.graphics.getFramesPerSecond() + " fps");
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		generator.dispose();
		font.dispose();
	}
}
