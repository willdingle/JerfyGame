package com.willdingle.jerfygame.areas;

import java.io.File;

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
import com.willdingle.jerfygame.files.Save;
import com.willdingle.jerfygame.items.Bullet;
import com.willdingle.jerfygame.menus.InventoryMenu;
import com.willdingle.jerfygame.menus.PauseMenu;
import com.willdingle.jerfygame.menus.Shop;

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
	private Sprite gem;
	private boolean moveAllowed;
	private boolean showNeeded;
	private float plx, ply;
	public MovingNPC movingNPCs[];
	public StillNPC stillNPCs[];
	private Enemy[] enemies;
	private boolean[] roomUnlocked = {false,false,false,true,true,true,false,true};
	private int curRoom;
	private int dungeonNum;
	
	public Dungeon(final JerfyGame game, Player player, int dungeonNum) {
		this.game = game;
		
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		game.parameter.size = 10;
		healthFont = game.generator.generateFont(game.parameter);
		
		map = new TmxMapLoader().load("maps/dungeon" + dungeonNum + ".tmx");
		mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map);
		player.setColLayer(mapLayer);
		
		switch(dungeonNum) {
		case 1:
			player.setPosition(7*16, 20*16);
			gem = new Sprite(new Texture("gem 1.png"));
			gem.setPosition(39*16, 2*16);
			break;
		case 2:
			player.setPosition(34*16, 21*16);
			roomUnlocked = new boolean[9];
			for(int n = 0; n < roomUnlocked.length; n++) {
				roomUnlocked[n] = false;
			}
			roomUnlocked[4] = true;
			roomUnlocked[6] = true;
			roomUnlocked[7] = true;
			gem = new Sprite(new Texture("gem 2.png"));
			gem.setPosition(2*16, 2*16);
			break;
		}
		
		curRoom = 4;
		this.player = player;
		
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
		this.dungeonNum = dungeonNum;
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
		
		if(gem != null) {
			gem.draw(renderer.getBatch());
			if(HitBox.player(player, gem.getX(), gem.getY(), gem.getWidth(), gem.getHeight(), HitBox.ALL, HitBox.COLLIDE)) {
				player.addToInventory("gem " + dungeonNum, Integer.toString(dungeonNum));
				Save.write(new File(System.getenv("appdata") + "/Jerfy/" + game.fileName), "inv", player);
				gem = null;
			}
		}
		
		if (moveAllowed) player.move(Gdx.graphics.getDeltaTime(), movingNPCs, stillNPCs, enemies);
		renderer.getBatch().end();
		
		if(player.getHealth() == 0) game.setScreen(new TownTown(game, 1, 1, player.inv));
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(new PauseMenu(game, game.getScreen()));
		if(Gdx.input.isKeyJustPressed(Keys.E)) game.setScreen(new InventoryMenu(game, game.getScreen(), player));
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			switch(dungeonNum) {
			case 1:
				interact();
				break;
			case 2:
				interact2();
				break;
			}
		}
		
		//Draw text box
		if(txtBox != null) txtBox.render();
		
		//Draw HUD
		game.hud.draw(batch, player);
	}
	
	private void interact2() {
		if (txtBox != null) {
			moveAllowed = true;
			txtBox = null;
		}
		
		//Vertical-travelling doors (ordered left to right then down)
		else if(HitBox.player(player, 34*16, (55-14)*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[2]) {
			player.translateY(36);
			if(! roomUnlocked[0]) initRoom2(0);
			curRoom = 0;
		} else if(HitBox.player(player, 34*16, (55-14)*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[0]) {
			player.translateY(-36);
			if(! roomUnlocked[2]) initRoom2(2);
			curRoom = 2;
		} else if(HitBox.player(player, 20*16, (55-28)*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[3]) {
			player.translateY(36);
			if(! roomUnlocked[1]) initRoom2(1);
			curRoom = 1;
		} else if(HitBox.player(player, 20*16, (55-28)*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[1]) {
			player.translateY(-36);
			if(! roomUnlocked[3]) initRoom2(3);
			curRoom = 3;
		} else if(HitBox.player(player, 34*16, (55-28)*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[4]) {
			player.translateY(36);
			if(! roomUnlocked[2]) initRoom2(2);
			curRoom = 2;
		} else if(HitBox.player(player, 34*16, (55-28)*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[2]) {
			player.translateY(-36);
			curRoom = 4;
		} else if(HitBox.player(player, 20*16, (55-42)*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[8]) {
			player.translateY(36);
			if(! roomUnlocked[3]) initRoom2(3);
			curRoom = 3;
		} else if(HitBox.player(player, 20*16, (55-42)*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[3]) {
			player.translateY(-36);
			if(! roomUnlocked[8]) initRoom2(8);
			curRoom = 8;
		}
		
		//Horizontal-travelling doors (ordered left to right then down)
		else if(HitBox.player(player, 27*16, (55-21)*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[1]) {
			player.translateX(36);
			if(! roomUnlocked[2]) initRoom2(2);
			curRoom = 2;
		} else if(HitBox.player(player, 27*16, (55-21)*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[2]) {
			player.translateX(-36);
			if(! roomUnlocked[1]) initRoom2(1);
			curRoom = 1;
		} else if(HitBox.player(player, 28*16, (55-35)*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[3]) {
			player.translateX(36);
			curRoom = 4;
		} else if(HitBox.player(player, 28*16, (55-35)*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[4]) {
			player.translateX(-36);
			if(! roomUnlocked[3]) initRoom2(3);
			curRoom = 3;
		} else if(HitBox.player(player, 41*16, (55-35)*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[4]) {
			player.translateX(36);
			if(! roomUnlocked[5]) initRoom2(5);
			curRoom = 5;
		} else if(HitBox.player(player, 41*16, (55-35)*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[5]) {
			player.translateX(-36);
			curRoom = 4;
		} else if(HitBox.player(player, 55*16, (55-35)*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[5]) {
			player.translateX(36);
			curRoom = 6;
		} else if(HitBox.player(player, 55*16, (55-35)*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[6]) {
			player.translateX(-36);
			if(! roomUnlocked[5]) initRoom2(5);
			curRoom = 5;
		} else if(HitBox.player(player, 14*16, (55-49)*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[7]) {
			player.translateX(36);
			if(! roomUnlocked[8]) initRoom2(8);
			curRoom = 8;
		} else if(HitBox.player(player, 14*16, (55-49)*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[8]) {
			player.translateX(-36);
			curRoom = 7;
			
		//Trapdoor
		} else if(HitBox.player(player, 2*16, (55-44)*16, 16, 16, HitBox.ALL, HitBox.INTERACT) && player.inventoryContains("gem 2")) {
			game.setScreen(new TownTown(game, 22, 19-12, player.inv));
		}
		
	}
	
	private void initRoom2(int num) {
		curRoom = num;

		switch(num) {
		case 0:
			enemies = new Enemy[1];
			enemies[0] = new Enemy("johnz.png", mapLayer, 31, 55-4);
			break;
		case 1:
			enemies = new Enemy[2];
			enemies[0] = new Enemy("johnz.png", mapLayer, 16, 55-16);
			enemies[1] = new Enemy("robin.png", mapLayer, 21, 55-21);
			break;
		case 2:
			enemies = new Enemy[3];
			enemies[0] = new Enemy("johnz.png", mapLayer, 31, 55-18);
			enemies[1] = new Enemy("robin.png", mapLayer, 37, 55-21);
			enemies[2] = new Enemy("droopy.png", mapLayer, 34, 55-20);
			break;
		case 3:
			enemies = new Enemy[4];
			enemies[0] = new Enemy("johnz.png", mapLayer, 15, 55-39);
			enemies[1] = new Enemy("robin.png", mapLayer, 15, 55-34);
			enemies[2] = new Enemy("droopy.png", mapLayer, 21, 55-40);
			enemies[3] = new Enemy("droopy.png", mapLayer, 20, 55-34);
			break;
		case 5:
			enemies = new Enemy[3];
			enemies[0] = new Enemy("droopy.png", mapLayer, 47, 55-30);
			enemies[1] = new Enemy("droopy.png", mapLayer, 51, 55-37);
			enemies[2] = new Enemy("droopy.png", mapLayer, 48, 55-39);
			break;
		case 8:
			enemies = new Enemy[4];
			enemies[0] = new Enemy("robin.png", mapLayer, 15, 55-49);
			enemies[1] = new Enemy("robin.png", mapLayer, 18, 55-54);
			enemies[2] = new Enemy("robin.png", mapLayer, 22, 55-53);
			enemies[3] = new Enemy("robin.png", mapLayer, 24, 55-49);
			break;
		}
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
		}
		
		//Vertical-travelling doors (ordered left to right then down)
		else if(HitBox.player(player, 6*16, 27*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[4]) {
			player.translateY(36);
			if(! roomUnlocked[0]) initRoom(0);
			curRoom = 0;
		} else if(HitBox.player(player, 6*16, 27*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[0]) {
			player.translateY(-36);
			curRoom = 4;
		} else if(HitBox.player(player, 34*16, 27*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[6]) {
			player.translateY(36);
			if(! roomUnlocked[2]) initRoom(2);
			curRoom = 2;
		} else if(HitBox.player(player, 34*16, 27*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[2]) {
			player.translateY(-36);
			if(! roomUnlocked[6]) initRoom(6);
			curRoom = 6;
		} else if(HitBox.player(player, 34*16, 13*16, 32, 16, HitBox.UP, HitBox.INTERACT) && roomUnlocked[7]) {
			player.translateY(36);
			if(! roomUnlocked[6]) initRoom(6);
			curRoom = 6;
		} else if(HitBox.player(player, 34*16, 13*16, 32, 16, HitBox.DOWN, HitBox.INTERACT) && roomUnlocked[6]) {
			player.translateY(-36);
			curRoom = 7;
		}
		
		//Horizontal-travelling doors (ordered left to right then down)
		else if(HitBox.player(player, 13*16, 34*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[0]) {
			player.translateX(36);
			if(! roomUnlocked[1]) initRoom(1);
			curRoom = 1;
		} else if(HitBox.player(player, 13*16, 34*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[1]) {
			player.translateX(-36);
			if(! roomUnlocked[0]) initRoom(0);
			curRoom = 0;
		} else if(HitBox.player(player, 27*16, 34*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[1]) {
			player.translateX(36);
			if(! roomUnlocked[2]) initRoom(2);
			curRoom = 2;
		} else if(HitBox.player(player, 27*16, 34*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[2]) {
			player.translateX(-36);
			if(! roomUnlocked[1]) initRoom(1);
			curRoom = 1;
		} else if(HitBox.player(player, 41*16, 34*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[2]) {
			game.setScreen(new Shop(game, game.getScreen(), player));
		} else if(HitBox.player(player, 28*16, 20*16, 16, 32, HitBox.RIGHT, HitBox.INTERACT) && roomUnlocked[5]) {
			player.translateX(36);
			if(! roomUnlocked[6]) initRoom(6);
			curRoom = 6;
		} else if(HitBox.player(player, 28*16, 20*16, 16, 32, HitBox.LEFT, HitBox.INTERACT) && roomUnlocked[6]) {
			player.translateX(-36);
			curRoom = 5;
		}
		
		//Trapdoor
		else if(HitBox.player(player, 31*16, (42-31)*16, 16, 16, HitBox.ALL, HitBox.INTERACT) && player.inventoryContains("gem 1")) {
			game.setScreen(new Dungeon(game, player, 2));
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
			enemies[0] = new Enemy("johnz.png", mapLayer, 20, 41-11);
			enemies[1] = new Enemy("robin.png", mapLayer, 25, 41-4);
			break;
			
		case 2:
			enemies = new Enemy[3];
			enemies[0] = new Enemy("droopy.png", mapLayer, 40, 41-4);
			enemies[1] = new Enemy("johnz.png", mapLayer, 35, 41-6);
			enemies[2] = new Enemy("robin.png", mapLayer, 37, 41-4);
			break;
			
		case 6:
			enemies = new Enemy[4];
			enemies[0] = new Enemy("droopy.png", mapLayer, 40, 41-26);
			enemies[1] = new Enemy("johnz.png", mapLayer, 38, 41-21);
			enemies[2] = new Enemy("robin.png", mapLayer, 31, 41-25);
			enemies[3] = new Enemy("robin.png", mapLayer, 29, 41-20);
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
