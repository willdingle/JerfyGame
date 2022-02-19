package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.willdingle.jerfygame.entities.MovingNPC;
import com.willdingle.jerfygame.entities.Player;
import com.willdingle.jerfygame.entities.StillNPC;
import com.willdingle.jerfygame.items.Bullet;
import com.willdingle.jerfygame.menus.PauseMenu;

public class DonkerHouse implements Screen {
	final JerfyGame game;
	
	private TiledMap map;
	private TiledMapTileLayer mapLayer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera cam;
	private Player player;
	private TextBox txtBox;
	private ShapeRenderer shRen;
	private SpriteBatch batch;
	private StillNPC donker;
	public MovingNPC movingNPCs[];
	public StillNPC stillNPCs[];
	private Screen prevScreen;
	
	private boolean moveAllowed;
	
	public DonkerHouse(final JerfyGame game, Screen prevScreen, Player player, OrthographicCamera cam, StillNPC donker, TextBox txtBox, ShapeRenderer shRen, SpriteBatch batch) {
		this.game = game;
		this.prevScreen = prevScreen;
		
		map = new TmxMapLoader().load("maps/DonkerHouse.tmx");
		mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map);
		
		this.player = player;
		this.player.setColLayer(mapLayer);
		this.player.setX(9 * mapLayer.getTileWidth());
		this.player.setY(0 * mapLayer.getTileHeight());
		
		this.cam = cam;
		
		this.donker = donker;
		this.donker.setX(3 * mapLayer.getTileWidth());
		this.donker.setY(6 * mapLayer.getTileHeight());
		stillNPCs = new StillNPC[1];
		stillNPCs[0] = this.donker;
		movingNPCs = new MovingNPC[0];
		
		moveAllowed = true;
		
		this.txtBox = txtBox;
		this.shRen = shRen;
		this.batch = batch;
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
		player.draw(renderer.getBatch());
		if (moveAllowed) player.move(Gdx.graphics.getDeltaTime(), movingNPCs, stillNPCs);
		
		donker.draw(renderer.getBatch());
		
		if(player.bullets.length > 0) {
			for(Bullet bullet : player.bullets) {
				bullet.draw(renderer.getBatch(), Gdx.graphics.getDeltaTime());
			}
		}
		renderer.getBatch().end();
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(new PauseMenu(game, game.getScreen()));
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) interact();
		
		//Draw text box
		if(txtBox != null) txtBox.render();
		
		//Draw HUD
		game.hud.draw(batch, player);
	}
	
	private void interact() {
		if(txtBox != null) {
			
		} else if(HitBox.player(player, 9*16, 0, 32, 0, HitBox.DOWN, HitBox.INTERACT)) {
			game.setScreen(prevScreen);
		} else if(HitBox.player(player, donker.getX(), donker.getY(), donker.getWidth(), donker.getHeight(), HitBox.ALL, HitBox.INTERACT)) {
			
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
