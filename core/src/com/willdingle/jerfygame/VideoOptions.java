package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

public class VideoOptions implements Screen {
	final JerfyGame game;
	private Screen prevScreen;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button backBut, fullscreenBut, vsyncBut;
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
		
		vSync = true;
		
		//Positions title text
		GlyphLayout layout = new GlyphLayout();
		layout.setText(titleFont, "VIDEO OPTIONS");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;
		
		//Creates buttons
		backBut = new Button(710, 100, 500, 100, font, "Back");
		if(Gdx.graphics.isFullscreen()) fullscreenBut = new Button(Gdx.graphics.getWidth() - 700, 800, 100, 100, font, "X");
		else fullscreenBut = new Button(1280, 800, 100, 100, font, "");
		vsyncBut = new Button(1280, 680, 100, 100, font, "X");
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 1, 0);
		
		//Menu boxes
		batch.begin();
		shRen.begin(ShapeType.Line);
		shRen.setColor(1,1,1,1);
		fullscreenBut.draw(shRen, batch);
		vsyncBut.draw(shRen, batch);
		backBut.draw(shRen, batch);
		shRen.end();
		batch.end();
		
		//Draw text
		batch.begin();
		titleFont.draw(batch, "VIDEO OPTIONS", titleX, titleY);
		font.draw(batch, "Fullscreen:", 400, 875);
		fullscreenBut.drawText(batch, font);
		font.draw(batch, "VSync:", 400, 755);
		vsyncBut.drawText(batch, font);
		backBut.drawText(batch, font);
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(prevScreen);
		
	}
	
	private void interact() {
		if(backBut.pressed()) game.setScreen(prevScreen);
		
		else if(fullscreenBut.pressed()) {
			if(Gdx.graphics.isFullscreen()) {
				fullscreenBut = new Button(1280, 800, 100, 100, font, "");
			}
			else {
				fullscreenBut = new Button(1280, 800, 100, 100, font, "X");
			}
		}
		
		else if(vsyncBut.pressed()) {
			if(vSync) {
				Gdx.graphics.setVSync(false);
				vSync = false;
				vsyncBut = new Button(1280, 680, 100, 100, font, "");
			} else {
				Gdx.graphics.setVSync(true);
				vSync = true;
				vsyncBut = new Button(1280, 680, 100, 100, font, "X");
			}
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
