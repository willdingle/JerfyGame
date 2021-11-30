package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenu implements Screen {
	
	final JerfyGame game;
	private SpriteBatch batch;
	
	private BitmapFont font;
	
	public MainMenu(final JerfyGame game) {
		this.game = game;
		
		batch = new SpriteBatch();
		game.parameter.size = 150;
		font = game.generator.generateFont(game.parameter);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 1, 0);
		
		batch.begin();
		font.draw(batch, "Jerfy", 750, 1025);
		batch.end();
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			game.setScreen(new TownTown(game));
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
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();

	}

}
