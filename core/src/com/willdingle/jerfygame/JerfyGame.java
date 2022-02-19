package com.willdingle.jerfygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.willdingle.jerfygame.files.Settings;
import com.willdingle.jerfygame.menus.MainMenu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JerfyGame extends Game {
	public FreeTypeFontGenerator generator;
	public FreeTypeFontParameter parameter;
	public HUD hud;
	
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
		if(Boolean.parseBoolean(fileContents[0])) Gdx.graphics.setVSync(true);
		else Gdx.graphics.setVSync(false);
		
		//Creates HUD
		parameter.size = 70;
		BitmapFont font = generator.generateFont(parameter);
		hud = new HUD(font);
		
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
		Gdx.graphics.setTitle("Jerfy - " + Gdx.graphics.getFramesPerSecond() + "fps");
		
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
