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
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera cam;
	private Player player;
	private TextBox txtBox;
	private ShapeRenderer shRen;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private BitmapFont font;
	private SpriteBatch batch;
	private boolean format;
	private boolean disTxtBox;
	private String txt;

	public TownTown(final JerfyGame game) {
		this.game = game;
		
		map = new TmxMapLoader().load("maps/TownTown.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);
		cam.position.x = cam.viewportWidth / 2;
		cam.position.y = cam.viewportHeight / 2;
		cam.update();
		
		player = new Player(new Sprite(new Texture("jerfy/down.png")), (TiledMapTileLayer) map.getLayers().get(0));
		
		shRen = new ShapeRenderer();
		txtBox = new TextBox();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DeterminationMono.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 70;
		font = generator.generateFont(parameter);
		batch = new SpriteBatch();
		format = false;
		txt = "";
		
		disTxtBox = false;
		
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
		renderer.getBatch().end();
		
		if(Gdx.input.isKeyJustPressed(Keys.E)) {
			interact();
		}
		
		//Text box
		if(disTxtBox) {
			shRen.begin(ShapeType.Filled);
			shRen.setColor(0, 0, 0, 1);
			shRen.rect(txtBox.getX(), txtBox.getY(), txtBox.getWidth(), txtBox.getHeight());
			shRen.end();
			shRen.begin(ShapeType.Line);
			shRen.setColor(1, 1, 1, 1);
			shRen.rect(txtBox.getX(), txtBox.getY(), txtBox.getWidth(), txtBox.getHeight());
			shRen.end();
			
			while (! format) {
				txt = txtBox.formatText("Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text Text", generator, parameter, font);
				format = true;
			}
			
			batch.begin();
			font.draw(batch, txt, 40, txtBox.getHeight());
			batch.end();
		}
		
	}
	
	public void interact() {
		if(hitBox(player.getX(), player.getY(), player.getWidth(), player.getHeight(), 64, 64, 16, 16)) {
			disTxtBox = true;
		}
	}
	
	public boolean hitBox(float plx, float ply, float plw, float plh, int objx, int objy, int objw, int objh) {
		boolean colx = false;
		boolean coly = false;
		
		//x left
		if(plx <= (objx + objw + 8) && plx >= (objx + objw)) {
			colx = true;
		} else if(plx + plw <= objx && plx + plw >= (objx - 8)) {
			colx = true;
		}
		
		//y
		if(ply <= (objy + objh + 8) && ply >= (objy + objh)) {
			coly = true;
		} else if (ply + plh <= objy && ply + plh >= (objy - 8)) {
			coly = true;
		}
		
		return colx || coly;
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
