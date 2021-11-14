package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
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
		
		//Draw sprites
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		renderer.getBatch().end();
		
		//Text box
		shRen.begin(ShapeType.Filled);
		shRen.setColor(0, 0, 0, 1);
		shRen.rect(txtBox.getX(), txtBox.getY(), txtBox.getWidth(), txtBox.getHeight());
		shRen.end();
		
		
		while (! format) {
			txt = txtBox.formatText("Buggo moment Buggo moment Buggo moment Buggo moment Buggo moment Buggo moment Buggo moment Buggo moment", generator, parameter, font);
			format = true;
		}
		
		batch.begin();
		font.draw(batch, txt, 20, txtBox.getHeight());
		batch.end();
		
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
