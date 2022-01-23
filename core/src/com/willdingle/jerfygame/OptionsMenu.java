package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

public class OptionsMenu implements Screen {
	
	final JerfyGame game;
	private Screen prevScreen;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button backBut, videoBut, controlsBut, soundBut, saveBut;
	private float titleX, titleY;

	public OptionsMenu (final JerfyGame game, Screen prevScreen) {
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 120;
		titleFont = game.generator.generateFont(game.parameter);
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		this.prevScreen = prevScreen;
		
		//Positions title text
		GlyphLayout layout = new GlyphLayout();
		layout.setText(titleFont, "OPTIONS");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;
		
		//Creates buttons
		videoBut = new Button(300, 600, 500, 300, font, "Video");
		controlsBut = new Button(1120, 600, 500, 300, font, "Controls");
		soundBut = new Button(300, 250, 500, 300, font, "Sound");
		saveBut = new Button(1120, 250, 500, 300, font, "Save Mgmt");
		backBut = new Button(710, 100, 500, 100, font, "Back");
	}
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 1, 0);
		
		//Menu boxes
		batch.begin();
		shRen.begin(ShapeType.Filled);
		shRen.setColor(Color.DARK_GRAY);
		videoBut.draw(shRen, batch);
		controlsBut.draw(shRen, batch);
		soundBut.draw(shRen, batch);
		saveBut.draw(shRen, batch);
		backBut.draw(shRen, batch);
		shRen.end();
		batch.end();
		
		//Draw text
		batch.begin();
		titleFont.draw(batch, "OPTIONS", titleX, titleY);
		videoBut.drawText(batch, font);
		controlsBut.drawText(batch, font);
		soundBut.drawText(batch, font);
		saveBut.drawText(batch, font);
		backBut.drawText(batch, font);
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
	}
	
	private void interact() {
		if(videoBut.pressed()) game.setScreen(new VideoOptions(game, game.getScreen()));
		if(backBut.pressed()) {
			dispose();
			game.setScreen(prevScreen);
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
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		shRen.dispose();
	}

}
