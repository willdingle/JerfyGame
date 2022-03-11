package com.willdingle.jerfygame.areas;

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
import com.willdingle.jerfygame.HitBox;
import com.willdingle.jerfygame.JerfyGame;
import com.willdingle.jerfygame.TextBox;
import com.willdingle.jerfygame.entities.*;
import com.willdingle.jerfygame.items.Bullet;
import com.willdingle.jerfygame.items.DreamCoin;
import com.willdingle.jerfygame.menus.InventoryMenu;
import com.willdingle.jerfygame.menus.PauseMenu;

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
	private boolean showNeeded;
	private MovingNPC buggo;
	private StillNPC chocm, paper, snugm, donker;
	public MovingNPC movingNPCs[];
	public StillNPC stillNPCs[];
	private DreamCoin[] coins;
	private float plx, ply;

	public TownTown(final JerfyGame game, float plx, float ply, String[] inv) {
		this.game = game;
		
		map = new TmxMapLoader().load("maps/TownTown.tmx");
		mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map);
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 5);
		cam.position.x = cam.viewportWidth / 2;
		cam.position.y = cam.viewportHeight / 2;
		cam.update();
		
		player = new Player(mapLayer, plx, ply, inv);
		
		movingNPCs = new MovingNPC[1];
		stillNPCs = new StillNPC[2];
		buggo = new MovingNPC("buggo/0.png", mapLayer, 1, 6, 1, 5, 4);
		movingNPCs[0] = buggo;
		chocm = new StillNPC("doggo/chocm.png", mapLayer, 2, 10);
		stillNPCs[0] = chocm;
		donker = new StillNPC("donker.png", mapLayer, 16, 10);
		stillNPCs[1] = donker;
		
		coins = new DreamCoin[5];
		coins[0] = new DreamCoin(mapLayer, 5, 5);
		coins[1] = new DreamCoin(mapLayer, 15, 15);
		coins[2] = new DreamCoin(mapLayer, 5, 7);
		coins[3] = new DreamCoin(mapLayer, 23, 10);
		coins[4] = new DreamCoin(mapLayer, 22, 9);
		
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		
		batch = new SpriteBatch();
		shRen = new ShapeRenderer();
		
		moveAllowed = true;
		showNeeded = false;
	}

	@Override
	public void show() {
		if(showNeeded) {
			this.player.setX(plx);
			this.player.setY(ply);
			this.player.setColLayer(mapLayer);
			showNeeded = false;
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(44/255f, 120/255f, 213/255f, 1);
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
		
		buggo.draw(renderer.getBatch());
		chocm.draw(renderer.getBatch());
		donker.draw(renderer.getBatch());
		if(coins.length > 0) {
			for(int n = 0; n < coins.length; n++) {
				if(coins[n] != null) {
					coins[n].draw(renderer.getBatch());
				}
			}
		}
		
		if(player.bullets.length > 0) {
			for(Bullet bullet : player.bullets) {
				bullet.draw(renderer.getBatch(), Gdx.graphics.getDeltaTime());
			}
		}
		
		if(player.sword != null) {
			player.sword.draw(renderer.getBatch(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
			player.sword.setTimer(player.sword.getTimer() - Gdx.graphics.getDeltaTime());
			if(player.sword.getTimer() < 0) player.sword = null;
		}
		renderer.getBatch().end();
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) interact();
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(new PauseMenu(game, game.getScreen()));
		if(Gdx.input.isKeyJustPressed(Keys.E)) game.setScreen(new InventoryMenu(game, game.getScreen(), player));
		if(Gdx.input.isKeyJustPressed(Keys.H)) game.setScreen(new Dungeon(game, player));
		
		//Pick up items
		if(coins.length > 0) {
			for(int n = 0; n < coins.length; n++) {
				if(coins[n] != null) {
					if(coins[n].touched(player)) {
						player.setMoney(player.getMoney() + 1);
						coins[n] = null;
						break;
					}
				}
			}
		}
		
		//Draw text box
		if(txtBox != null) txtBox.render();
		
		//Draw HUD
		game.hud.draw(batch, player);
	}
	
	private void interact() {
		if (txtBox != null) {
			moveAllowed = true;
			txtBox = null;
			
		//Welcome sign
		} else if (HitBox.player(player, 48, 48, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			txtBox = new TextBox(batch, shRen, font, "Welcome to Town Town!");
			moveAllowed = false;
			
		//Speak to chocm	
		} else if (HitBox.player(player, chocm.getX(), chocm.getY(), chocm.getWidth(), chocm.getHeight(), HitBox.ALL, HitBox.INTERACT)) {
			txtBox = new TextBox(batch, shRen, font, "Is doggo");
			moveAllowed = false;
			
		//Donker's house sign	
		} else if(HitBox.player(player, 11*16, 4*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			txtBox = new TextBox(batch, shRen, font, "Donker's House");
			moveAllowed = false;
			
		//Donker's door	
		} else if(HitBox.player(player, 9*16, 4*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			//set screen to donker's house
			plx = player.getX();
			ply = player.getY();
			showNeeded = true;
			StillNPC donkerClone = new StillNPC("donker.png", mapLayer, 10, 10);
			game.setScreen(new House("DonkerHouse.tmx", game, this, player, cam, donkerClone, txtBox, shRen, batch, font));
			
		//Paper's house sign	
		} else if(HitBox.player(player, 17*16, 4*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			txtBox = new TextBox(batch, shRen, font, "Paper's House");
			moveAllowed = false;
			
		//Paper's door	
		} else if(HitBox.player(player, 15*16, 4*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			//set screen to paper's house
			plx = player.getX();
			ply = player.getY();
			showNeeded = true;
			paper = new StillNPC("doggo/paper.png", mapLayer, 10, 10);
			game.setScreen(new House("PaperHouse.tmx", game, this, player, cam, paper, txtBox, shRen, batch, font));
			
		//Buggo's house sign	
		} else if(HitBox.player(player, 23*16, 4*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			txtBox = new TextBox(batch, shRen, font, "Buggo's House");
			moveAllowed = false;
			
		//Buggo's door
		} else if(HitBox.player(player, 21*16, 4*16, 16, 16, HitBox.UP, HitBox.INTERACT)) {
			//set screen to buggo's house
			plx = player.getX();
			ply = player.getY();
			showNeeded = true;
			StillNPC buggoStill = new StillNPC("buggo/0.png", mapLayer, 10, 10);
			game.setScreen(new House("BuggoHouse.tmx", game, this, player, cam, buggoStill, txtBox, shRen, batch, font));
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
