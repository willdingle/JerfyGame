package com.willdingle.jerfygame.areas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.willdingle.jerfygame.HitBox;
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
	private BitmapFont font, healthFont;
	private SpriteBatch batch;
	private boolean moveAllowed;
	private boolean showNeeded;
	private float plx, ply;
	public MovingNPC movingNPCs[];
	public StillNPC stillNPCs[];
	private Enemy[] enemies;
	private boolean[] roomUnlocked = {false,false,false,false,true,false,false,false};
	private int curRoom;
	
	public Dungeon(final JerfyGame game, Player player) {
		this.game = game;
		
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		game.parameter.size = 10;
		healthFont = game.generator.generateFont(game.parameter);
		
		map = new TmxMapLoader().load("maps/dungeon1.tmx");
		mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map);
		player.setColLayer(mapLayer);
		
		player.setPosition(7 * 16, 20 * 16);
		this.player = player;
		curRoom = 4;
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
		cam.position.x = (14 * 16)/2;
		cam.position.y = (40 * 16)/2;
		cam.update();
		
		enemies = new Enemy[0];
		movingNPCs = new MovingNPC[0];
		stillNPCs = new StillNPC[0];
		moveAllowed = true;
		showNeeded = false;
		
		batch = new SpriteBatch();
		shRen = new ShapeRenderer();
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
		
		//Draw sprites, check door and player control
		renderer.getBatch().begin();
		player.draw(renderer.getBatch(), enemies);
		
		boolean enemiesNull = true;
		for(Enemy enemy : enemies) {
			if(enemy != null) {
				enemy.draw(renderer.getBatch(), healthFont, player);
				enemiesNull = false;
			}
		}
		if(enemiesNull) {
			roomUnlocked[curRoom] = true;
			enemies = new Enemy[0];
		}
		
		if (moveAllowed) player.move(Gdx.graphics.getDeltaTime(), movingNPCs, stillNPCs, enemies);
		renderer.getBatch().end();
		
		if(player.getHealth() == 0) game.setScreen(new TownTown(game, 1, 1, player.inv));
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(new PauseMenu(game, game.getScreen()));
		if(Gdx.input.isKeyJustPressed(Keys.E)) game.setScreen(new InventoryMenu(game, game.getScreen(), player));
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) interact();
		
		//Draw text box
		if(txtBox != null) txtBox.render();
		
		//Draw HUD
		game.hud.draw(batch, player);
	}
	
	private void interact() {
		if (txtBox != null) {
			moveAllowed = true;
			txtBox = null;
		
		//Left sign
		} else if(HitBox.player(player, 5*16, 21*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			txtBox = new TextBox(batch, shRen, font, "To equip your weapon:\n"
					+ "Go into your inventory by pressing E, then equip your weapon by selecting it");
			moveAllowed = false;
			
		//Right sign
		} else if(HitBox.player(player, 8*16, 21*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			txtBox = new TextBox(batch, shRen, font, "To go through doors:\n"
					+ "Go up to a door and press ENTER\n"
					+ "This can only be done once you have defeated all enemies in the room");
			moveAllowed = false;
			
		//Vertical-travelling doors (ordered left to right then down)
		} else if(HitBox.player(player, 6*16, 27*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[4]) {
			player.translateY(36);
			initRoom(0);
			curRoom = 0;
		} else if(HitBox.player(player, 6*16, 27*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[0]) {
			player.translateY(-36);
			curRoom = 4;
		} else if(HitBox.player(player, 34*16, 27*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[6]) {
			player.translateY(36);
			curRoom = 2;
		} else if(HitBox.player(player, 34*16, 27*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[2]) {
			player.translateY(-36);
			curRoom = 6;
		} else if(HitBox.player(player, 34*16, 13*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[7]) {
			player.translateY(36);
			curRoom = 6;
		} else if(HitBox.player(player, 34*16, 13*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[6]) {
			player.translateY(-36);
			curRoom = 7;
		}
		
		//Horizontal-travelling doors (ordered left to right then down)
		else if(HitBox.player(player, 13*16, 34*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[0]) {
			player.translateX(36);
			initRoom(1);
			curRoom = 1;
		} else if(HitBox.player(player, 13*16, 34*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[1]) {
			player.translateX(-36);
			curRoom = 0;
		} else if(HitBox.player(player, 27*16, 34*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[1]) {
			player.translateX(36);
			initRoom(2);
			curRoom = 2;
		} else if(HitBox.player(player, 27*16, 34*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[2]) {
			player.translateX(-36);
			curRoom = 1;
		} else if(HitBox.player(player, 41*16, 34*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[2]) {
			player.translateX(36);
			curRoom = 3;
		} else if(HitBox.player(player, 41*16, 34*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[3]) {
			player.translateX(-36);
			curRoom = 2;
		} else if(HitBox.player(player, 28*16, 20*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[5]) {
			player.translateX(36);
			curRoom = 6;
		} else if(HitBox.player(player, 28*16, 20*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[6]) {
			player.translateX(-36);
			curRoom = 5;
		}
		
	}
	
	private void initRoom(int num) {
		curRoom = num;
		
		switch(num) {
		case 0:
			enemies = new Enemy[1];
			enemies[0] = new Enemy("johnz.png", mapLayer, 3, 35);
			break;
			
		case 1:
			enemies = new Enemy[2];
			enemies[0] = new Enemy("johnz.png", mapLayer, 17, 35);
			enemies[1] = new Enemy("robin.png", mapLayer, 23, 38);
			break;
			
		case 2:
			enemies = new Enemy[3];
			enemies[0] = new Enemy("droopy.png", mapLayer, 40, 38);
			enemies[1] = new Enemy("johnz.png", mapLayer, 31, 35);
			enemies[2] = new Enemy("robin.png", mapLayer, 37, 38);
			break;
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
