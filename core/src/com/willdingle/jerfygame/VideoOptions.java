package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
	private Button backBut;
	private float titleX, titleY;
	
	public VideoOptions(JerfyGame game, Screen prevScreen) {
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
		layout.setText(titleFont, "VIDEO OPTIONS");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;
		
		//Creates buttons
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
		shRen.begin(ShapeType.Line);
		shRen.setColor(1,1,1,1);
		backBut.draw(shRen, batch);
		shRen.end();
		batch.end();
		
		//Draw text
		batch.begin();
		titleFont.draw(batch, "VIDEO OPTIONS", titleX, titleY);
		backBut.drawText(batch, font);
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
		
	}
	
	private void interact() {
		if(backBut.pressed()) game.setScreen(prevScreen);
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
