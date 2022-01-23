package com.willdingle.jerfygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JerfyGame extends Game {
	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;
	
	@Override
	public void create () {
		this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DeterminationMono.ttf"));
		this.parameter = new FreeTypeFontParameter();
		
		File file;
		//Checks if Jerfy directory exists in appdata
		file = new File(System.getenv("appdata") + "/Jerfy");
		if(! file.exists()) {
			file.mkdir();
		}
		
		//Checks if settings file exists
		file = new File(System.getenv("appdata") + "/Jerfy/settings.ini");
		if(! file.isFile()) {
			Settings.create(file);
		}
		
		//Loads settings
		String fileContents[] = Settings.load(file);
		
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
		Gdx.graphics.setTitle(Gdx.graphics.getFramesPerSecond() + " fps");
		
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
