package com.willdingle.jerfygame.areas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.willdingle.jerfygame.JerfyGame;
import com.willdingle.jerfygame.TextBox;
import com.willdingle.jerfygame.entities.Enemy;
import com.willdingle.jerfygame.entities.MovingNPC;
import com.willdingle.jerfygame.entities.Player;
import com.willdingle.jerfygame.entities.StillNPC;
import com.willdingle.jerfygame.items.Bullet;
import com.willdingle.jerfygame.menus.InventoryMenu;
import com.willdingle.jerfygame.menus.PauseMenu;

public class Dungeon implements Screen {
	final JerfyGame game;
	
	private TiledMap map;
	private TiledMapTileLayer mapLayer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera cam;
	private Player player;
	private TextBox txtBox;
	private ShapeRenderer shRen;
	private BitmapFont font;
	private SpriteBatch batch;
	private boolean moveAllowed;
	private boolean showNeeded;
	private float plx, ply;
	public MovingNPC movingNPCs[];
	public StillNPC stillNPCs[];
	private Enemy enemy1;
	
	public Dungeon(final JerfyGame game, Player player) {
		this.game = game;
		
		map = new TmxMapLoader().load("maps/dungeon1.tmx");
		mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map);
		player.setColLayer(mapLayer);
		
		player.setPosition(7 * 16, 20 * 16);
		this.player = player;
		
		enemy1 = new Enemy("johnz.png", mapLayer, 9, 23);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
		cam.position.x = (14 * 16)/2;
		cam.position.y = (40 * 16)/2;
		cam.update();
		
		movingNPCs = new MovingNPC[0];
		stillNPCs = new StillNPC[0];
		moveAllowed = true;
		showNeeded = false;
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Camera
		cam.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		cam.update();
		renderer.setView(cam);
		renderer.render();
		
		//Draw sprites and player control
		renderer.getBatch().begin();
		player.draw(renderer.getBatch(), enemy1);
		
		enemy1.draw(renderer.getBatch());
		if (moveAllowed) player.move(Gdx.graphics.getDeltaTime(), movingNPCs, stillNPCs);
		renderer.getBatch().end();
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(new PauseMenu(game, game.getScreen()));
		if(Gdx.input.isKeyJustPressed(Keys.E)) game.setScreen(new InventoryMenu(game, game.getScreen(), player));
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
