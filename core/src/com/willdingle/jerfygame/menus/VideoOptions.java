package com.willdingle.jerfygame.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.willdingle.jerfygame.JerfyGame;
import com.willdingle.jerfygame.files.Settings;

public class VideoOptions implements Screen {
	final JerfyGame game;
	private Screen prevScreen;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button backBut, vsyncBut;
	private Button[] buttons;
	private float titleX, titleY;
	private boolean vSync;
	
	public VideoOptions(JerfyGame game, Screen prevScreen) {
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 120;
		titleFont = game.generator.generateFont(game.parameter);
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		this.prevScreen = prevScreen;
		
		vSync = Boolean.parseBoolean(Settings.getOption("vsync"));
		
		//Positions title text
		GlyphLayout layout = new GlyphLayout();
		layout.setText(titleFont, "VIDEO OPTIONS");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;
		
		//Creates buttons
		buttons = new Button[2];
		backBut = new Button(710, 100, 500, 100, font, "Back");
		buttons[0] = backBut;
		if(vSync) vsyncBut = new Button(1280, 680, 100, 100, font, "X");
		else vsyncBut = new Button(1280, 680, 100, 100, font, "");
		buttons[1] = vsyncBut;
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 128/255f, 0, 1);
		
		//Menu buttons
		batch.begin();
		shRen.begin(ShapeType.Filled);
		shRen.setColor(Color.DARK_GRAY);
		for(Button button : buttons) {
			button.draw(shRen, batch);
		}
		shRen.end();
		batch.end();
		
		//Draw text
		batch.begin();
		titleFont.draw(batch, "VIDEO OPTIONS", titleX, titleY);
		font.draw(batch, "VSync:", 400, 755);
		for(Button button : buttons) {
			button.drawText(batch, font);
		}
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(prevScreen);
		
	}
	
	private void interact() {
		if(backBut.pressed()) game.setScreen(prevScreen);
		
		else if(vsyncBut.pressed()) {
			if(vSync) {
				Gdx.graphics.setVSync(false);
				vSync = false;
				Settings.setVsync(false);
				vsyncBut = new Button(1280, 680, 100, 100, font, "");
			} else {
				Gdx.graphics.setVSync(true);
				vSync = true;
				Settings.setVsync(true);
				vsyncBut = new Button(1280, 680, 100, 100, font, "X");
			}
			buttons[1] = vsyncBut;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
