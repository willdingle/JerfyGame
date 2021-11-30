package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.willdingle.jerfygame.entities.*;

public class TownTown implements Screen {
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
	private MovingNPC buggo;
	private StillNPC chocm, paper, snugm;

	public TownTown(final JerfyGame game) {
		this.game = game;
		
		map = new TmxMapLoader().load("maps/TownTown.tmx");
		mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);
		cam.position.x = cam.viewportWidth / 2;
		cam.position.y = cam.viewportHeight / 2;
		cam.update();
		
		player = new Player(mapLayer);
		buggo = new MovingNPC("buggo/0.png", mapLayer, 6, 6, 4, 6, 10);
		chocm = new StillNPC("doggo/chocm.png", mapLayer, 7, 3);
		
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		
		batch = new SpriteBatch();
		shRen = new ShapeRenderer();
		
		moveAllowed = true;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(104/255f, 104/255f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Camera
		cam.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		cam.update();
		
		renderer.setView(cam);
		renderer.render();
		
		//Draw sprites and player control
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		if (moveAllowed) {
			player.move(Gdx.graphics.getDeltaTime());
		}
		buggo.draw(renderer.getBatch());
		chocm.draw(renderer.getBatch());
		renderer.getBatch().end();
		
		if(Gdx.input.isKeyJustPressed(Keys.J)) {
			interact();
		}
		
		//Text box
		if(txtBox != null) {
			txtBox.render();
		}
	}
	
	public void interact() {
		if (txtBox != null) {
			moveAllowed = true;
			txtBox = null;
		} else if (HitBox.interact(player, 48, 48, 16, 16, HitBox.UP)) {
			txtBox = new TextBox(batch, shRen, font, "Welcome to Town Town!");
			moveAllowed = false;
		} else if (HitBox.interact(player, chocm.getX(), chocm.getY(), chocm.getWidth(), chocm.getHeight(), HitBox.ALL)) {
			txtBox = new TextBox(batch, shRen, font, "Is doggo");
			moveAllowed = false;
		}
	}
	
	@Override
	public void pause() {
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		//dispose();
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		batch.dispose();

	}

}
