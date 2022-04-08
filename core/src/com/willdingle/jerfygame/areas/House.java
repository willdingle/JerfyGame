package com.willdingle.jerfygame.areas;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.willdingle.jerfygame.HitBox;
import com.willdingle.jerfygame.JerfyGame;
import com.willdingle.jerfygame.TextBox;
import com.willdingle.jerfygame.dialogue.Dialogue;
import com.willdingle.jerfygame.entities.MovingNPC;
import com.willdingle.jerfygame.entities.Player;
import com.willdingle.jerfygame.entities.StillNPC;
import com.willdingle.jerfygame.files.Save;
import com.willdingle.jerfygame.items.Bullet;
import com.willdingle.jerfygame.menus.Button;
import com.willdingle.jerfygame.menus.InventoryMenu;
import com.willdingle.jerfygame.menus.PauseMenu;

public class House implements Screen {
	final JerfyGame game;
	
	private TiledMap map;
	private TiledMapTileLayer mapLayer;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera cam;
	private Player player;
	private TextBox txtBox;
	private ShapeRenderer shRen;
	private SpriteBatch batch;
	private BitmapFont font;
	private StillNPC npc;
	private int npcNum;
	private int txtIndex;
	public MovingNPC movingNPCs[];
	public StillNPC stillNPCs[];
	private Screen prevScreen;
	private Button[] buttons;
	
	private boolean moveAllowed;
	
	public House(String mapName, final JerfyGame game, Screen prevScreen, Player player, OrthographicCamera cam, StillNPC npc, TextBox txtBox, ShapeRenderer shRen, SpriteBatch batch, BitmapFont font) {
		this.game = game;
		this.prevScreen = prevScreen;
		
		map = new TmxMapLoader().load("maps/" + mapName);
		mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map);
		
		this.player = player;
		this.player.setColLayer(mapLayer);
		this.player.setX(9 * mapLayer.getTileWidth());
		this.player.setY(0 * mapLayer.getTileHeight());
		
		this.cam = cam;
		
		this.npc = npc;
		switch(mapName) {
		case "DonkerHouse.tmx":
			npcNum = 0;
			break;
		case "PaperHouse.tmx":
			npcNum = 1;
			break;
		case "BuggoHouse.tmx":
			npcNum = 2;
			break;
		}
		this.npc.setX(3 * mapLayer.getTileWidth());
		this.npc.setY(6 * mapLayer.getTileHeight());
		stillNPCs = new StillNPC[1];
		stillNPCs[0] = this.npc;
		movingNPCs = new MovingNPC[0];
		
		moveAllowed = true;
		
		this.txtBox = txtBox;
		this.shRen = shRen;
		this.batch = batch;
		this.font = font;
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
		
		npc.draw(renderer.getBatch());
		
		if(player.bullets.length > 0) {
			for(Bullet bullet : player.bullets) {
				bullet.draw(renderer.getBatch(), Gdx.graphics.getDeltaTime());
			}
		}
		renderer.getBatch().end();
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(new PauseMenu(game, game.getScreen()));
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) interact();
		if(Gdx.input.isKeyJustPressed(Keys.E)) game.setScreen(new InventoryMenu(game, game.getScreen(), player));
		if(Gdx.input.isButtonJustPressed(Buttons.LEFT)) buttonInteract();;
		
		//Draw text box and buttons
		if(txtBox != null) txtBox.render();
		if(buttons != null) {
			batch.begin();
			shRen.begin(ShapeType.Filled);
			shRen.setColor(Color.DARK_GRAY);
			for(Button button : buttons) {
				button.draw(shRen, batch);
			}
			shRen.end();
			batch.end();
		}
		
		//Draw button text
		if(buttons != null) {
			batch.begin();
			for(Button button : buttons) {
				button.drawText(batch, font);
			}
			batch.end();
		}
		
		//Draw HUD
		game.hud.draw(batch, player);
	}
	
	private void buttonInteract() {
		if(buttons != null) {
			if(buttons[0].pressed()) {
				txtIndex += 1;
				txtBox = new TextBox(batch, shRen, font, Dialogue.buggo(txtIndex));
				buttons = null;
			} else if(buttons[1].pressed()) {
				txtIndex += 2;
				txtBox = new TextBox(batch, shRen, font, Dialogue.buggo(txtIndex));
				buttons = null;
			}
		}
	}
	
	private void interact() {
		if(txtBox != null) {
			switch(npcNum) {
			//DONKER TEXT
			case 0:
				if(! player.isRangedAllowed()) {
					switch(txtIndex) {
					case 2:
						player.addToInventory("gun", "1");
						player.setRangedAllowed(true);
						Save.write(new File(System.getenv("appdata") + "/Jerfy/" + game.fileName), "inv", player);
						txtIndex += 1;
						txtBox = new TextBox(batch, shRen, font, Dialogue.donker(txtIndex));
						break;
					case 3:
						moveAllowed = true;
						txtBox = null;
						break;
					default:
						txtIndex += 1;
						txtBox = new TextBox(batch, shRen, font, Dialogue.donker(txtIndex));
						break;
					}
				} else {
					moveAllowed = true;
					txtBox = null;
				}
				break;
			
			//PAPER TEXT
			case 1:
				if(! player.isMeleeAllowed()) {
					switch(txtIndex) {
					case 4:
						player.addToInventory("sword", "1.5");
						player.setMeleeAllowed(true);
						Save.write(new File(System.getenv("appdata") + "/Jerfy/" + game.fileName), "inv", player);
						txtIndex += 1;
						txtBox = new TextBox(batch, shRen, font, Dialogue.paper(txtIndex));
						break;
					case 5:
						moveAllowed = true;
						txtBox = null;
						break;
					default:
						txtIndex += 1;
						txtBox = new TextBox(batch, shRen, font, Dialogue.paper(txtIndex));
						break; 
					}
				} else {
					moveAllowed = true;
					txtBox = null;
				}
				break;
			
			//BUGGO TEXT
			case 2:
				if(player.isMeleeAllowed() && player.isRangedAllowed()) {
					switch(txtIndex) {
					case 0:
						buttons = new Button[2];
						buttons[0] = new Button(20, 100, 300, 100, font, "Yes");
						buttons[1] = new Button(420, 100, 300, 100, font, "No");
						break;
					case 1:
						game.setScreen(new Dungeon(game, player, 1));
						moveAllowed = true;
						txtBox = null;
						break;
					case 2:
						moveAllowed = true;
						txtBox = null;
						break;
					}
				} else {
					moveAllowed = true;
					txtBox = null;
				}
			}
			
		} else if(HitBox.player(player, 9*16, 0, 32, 0, HitBox.DOWN, HitBox.INTERACT)) {
			game.setScreen(prevScreen);
			
		} else if(HitBox.player(player, npc.getX(), npc.getY(), npc.getWidth(), npc.getHeight(), HitBox.ALL, HitBox.INTERACT)) {
			switch(npcNum) {
			case 0:
				if(! player.isRangedAllowed()) {
					txtIndex = 0;
					txtBox = new TextBox(batch, shRen, font, Dialogue.donker(txtIndex));
				} else txtBox = new TextBox(batch, shRen, font, "Use your new weapon well!");
				moveAllowed = false;
				break;
				
			case 1:
				if(! player.isMeleeAllowed()) {
					txtIndex = 0;
					txtBox = new TextBox(batch, shRen, font, Dialogue.paper(txtIndex));
				} else txtBox = new TextBox(batch, shRen, font, "Use your new weapon well!");
				moveAllowed = false;
				break;
				
			case 2:
				if(player.isMeleeAllowed() && player.isRangedAllowed()) {
					txtIndex = 0;
					txtBox = new TextBox(batch, shRen, font, Dialogue.buggo(txtIndex));
				} else txtBox = new TextBox(batch, shRen, font, "I believe Donker and Paper have a couple of things for you. They are in the other 2 houses.");
				moveAllowed = false;
				break;
			}
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
