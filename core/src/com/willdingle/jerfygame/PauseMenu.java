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

public class PauseMenu implements Screen {
	
	final JerfyGame game;
	private Screen prevScreen;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button resBut, exitBut, optionsBut;
	private float titleX, titleY;
	
	public PauseMenu(JerfyGame game, Screen prevScreen) {
		//Initial variable declarations
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 150;
		titleFont = game.generator.generateFont(game.parameter);
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		this.prevScreen = prevScreen;
		
		//Positions title text
		GlyphLayout layout = new GlyphLayout();
		layout.setText(titleFont, "Jerfy");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;
		
		//Creates buttons
		resBut = new Button(Gdx.graphics.getWidth()/2 - 250, 800, 500, 100, font, "Resume");
		optionsBut = new Button(Gdx.graphics.getWidth()/2 - 250, 680, 500, 100, font, "Options");
		exitBut = new Button(Gdx.graphics.getWidth()/2 - 250, 560, 500, 100, font, "Exit");
		
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 1, 0);
		
		//Buttons
		batch.begin();
		shRen.begin(ShapeType.Filled);
		shRen.setColor(Color.DARK_GRAY);
		resBut.draw(shRen, batch);
		optionsBut.draw(shRen, batch);
		exitBut.draw(shRen, batch);
		shRen.end();
		batch.end();
		
		//Draw text
		batch.begin();
		titleFont.draw(batch, "PAUSE", titleX, titleY);
		resBut.drawText(batch, font);
		optionsBut.drawText(batch, font);
		exitBut.drawText(batch, font);
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
		
	}

	private void interact() {
		if(resBut.pressed()) {
			dispose();
			game.setScreen(prevScreen);
		}
		else if(optionsBut.pressed()) game.setScreen(new OptionsMenu(game, game.getScreen()));
		else if(exitBut.pressed()) System.exit(0);
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
		batch.dispose();
		titleFont.dispose();
		font.dispose();
		shRen.dispose();
	}
}
