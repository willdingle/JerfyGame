package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenu implements Screen {
	
	final JerfyGame game;
	OrthographicCamera cam;
	
	public MainMenu(final JerfyGame game) {
		this.game = game;
		cam = new OrthographicCamera();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 255, 0);
		
		game.batch.begin();
		game.font.draw(game.batch, "Jerfy", 750, 1025);
		game.batch.end();
		
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
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
