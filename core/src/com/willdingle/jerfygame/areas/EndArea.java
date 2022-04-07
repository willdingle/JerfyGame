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
import com.willdingle.jerfygame.HitBox;
import com.willdingle.jerfygame.JerfyGame;
import com.willdingle.jerfygame.TextBox;
import com.willdingle.jerfygame.entities.MovingNPC;
import com.willdingle.jerfygame.entities.Player;
import com.willdingle.jerfygame.entities.StillNPC;
import com.willdingle.jerfygame.menus.InventoryMenu;
import com.willdingle.jerfygame.menus.PauseMenu;

public class EndArea implements Screen {
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
	private Screen prevScreen;
	public MovingNPC movingNPCs[];
	public StillNPC stillNPCs[];


	public EndArea(final JerfyGame game, Player player, Screen prevScreen) {
		this.game = game;

		map = new TmxMapLoader().load("maps/EndArea.tmx");
		mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map);

		cam = new OrthographicCamera(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);
		cam.position.x = cam.viewportWidth / 2;
		cam.position.y = cam.viewportHeight / 2;
		cam.update();

		player.setColLayer(mapLayer);
		player.setPosition(4.5f*16, 16);
		this.player = player;

		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);

		batch = new SpriteBatch();
		shRen = new ShapeRenderer();

		moveAllowed = true;
		
		this.prevScreen = prevScreen;
		
		movingNPCs = new MovingNPC[0];
		stillNPCs = new StillNPC[0];

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
		player.draw(renderer.getBatch());
		if (moveAllowed) player.move(Gdx.graphics.getDeltaTime(), movingNPCs, stillNPCs, null);

		renderer.getBatch().end();
		
		//Draw text box
		if(txtBox != null) txtBox.render();
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) interact();
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(new PauseMenu(game, game.getScreen()));
		if(Gdx.input.isKeyJustPressed(Keys.E)) game.setScreen(new InventoryMenu(game, game.getScreen(), player));

		//Draw HUD
		game.hud.draw(batch, player);
	}

	private void interact() {
		if (txtBox != null) {
			moveAllowed = true;
			txtBox = null;
		}
		
		//Exit
		else if(HitBox.player(player, 4*16, 0, 32, 16, HitBox.DOWN, HitBox.INTERACT)) {
			game.setScreen(prevScreen);
		}
		
		//Left sign
		else if(HitBox.player(player, 2*16, 6*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			txtBox = new TextBox(batch, shRen, font, "Congratulations! You have conquered the dungeons, gathered the gems and made it to the end of the game!");
		}
		
		//Right sign
		else if(HitBox.player(player, 7*16, 6*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			txtBox = new TextBox(batch, shRen, font, "You have reached the end of the game. You are free to go through the dungeons again if you so choose or exit the game.");
		}
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
