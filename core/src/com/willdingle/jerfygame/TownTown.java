package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	private boolean disTxt;
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
		chocm = new StillNPC("doggo/chocm.png", mapLayer, 4, 3);
		
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		
		batch = new SpriteBatch();
		shRen = new ShapeRenderer();
		
		disTxt = false;
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
		if(disTxt) {
			txtBox.render();
		}
	}
	
	public void interact() {
		if (disTxt) {
			disTxt = false;
			moveAllowed = true;
			txtBox = null;
		} else if (hitBox(player.getX(), player.getY(), player.getWidth(), player.getHeight(), 48, 48, 16, 16)) {
			txtBox = new TextBox(batch, shRen, font, "Welcome to Town Town!");
			disTxt = true;
			moveAllowed = false;
		}
	}
	
	public boolean hitBox(float plx, float ply, float plw, float plh, float objx, float objy, float objw, float objh) {
		boolean col = false;
		
		//left
		col = (plx <= (objx + objw + 8) && plx >= (objx + objw)) && (ply <= (objy + objh + 8) && ply + plh >= objy - 8);
		
		//right
		if (!col) {
			col = (plx + plw <= objx && plx + plw >= (objx - 8)) && (ply <= (objy + objh + 8) && ply + plh >= objy - 8);
		}
		
		//up
		if (!col) {
			col = (plx <= objx + objw + 8 && plx + plw >= objx - 8) && (ply + plh <= objy && ply + plh >= objy - 8);
		}
		
		//down
		if (!col) {
			col  = (plx <= objx + objw + 8 && plx + plw >= objx - 8) && (ply <= objy + objh + 8 && ply >= objy + objh);
		}
		
		return col;
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
		batch.dispose();

	}

}
