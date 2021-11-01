package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.willdingle.jerfygame.entities.*;

public class TownTown implements Screen {
	final JerfyGame game;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera cam;
	
	private SpriteBatch batch;
	private Player player;
	private TextBox txtbox;

	public TownTown(final JerfyGame game) {
		this.game = game;
		
		map = new TmxMapLoader().load("maps/TownTown.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);
		cam.position.x = cam.viewportWidth / 2;
		cam.position.y = cam.viewportHeight / 2;
		cam.update();
		
		player = new Player(new Sprite(new Texture("jerfy/down.png")), (TiledMapTileLayer) map.getLayers().get(0));
		
		txtbox = new TextBox(new Sprite(new Texture("txtbox.png")), cam);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(104/255f, 104/255f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		cam.update();
		
		renderer.setView(cam);
		renderer.render();
		
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		txtbox.draw(renderer.getBatch());
		renderer.getBatch().end();

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
		map.dispose();
		renderer.dispose();

	}

}
