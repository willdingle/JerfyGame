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
	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;
	
	@Override
	public void create () {
		this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DeterminationMono.ttf"));
		this.parameter = new FreeTypeFontParameter();
		
		this.setScreen(new MainMenu(this));
		
		
	}

	@Override
	public void render () {
		super.render();
		Gdx.graphics.setTitle(Gdx.graphics.getFramesPerSecond() + " fps");
	}
	
	@Override
	public void dispose () {
	}
}
