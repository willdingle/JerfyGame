package com.willdingle.jerfygame.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.willdingle.jerfygame.JerfyGame;
import com.willdingle.jerfygame.entities.Player;

public class InventoryMenu implements Screen {
	final JerfyGame game;
	private Screen prevScreen;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button slot1, slot2, slot3, slot4, slot5;
	private Button[] buttons;
	private Sprite[] items;
	private Player player;
	private float titleX, titleY;
	
	public InventoryMenu(final JerfyGame game, Screen prevScreen, Player player) {
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 120;
		titleFont = game.generator.generateFont(game.parameter);
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		this.prevScreen = prevScreen;
		
		//Positions title text
		GlyphLayout layout = new GlyphLayout();
		layout.setText(titleFont, "INVENTORY");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;
		
		//Creates buttons
		buttons = new Button[5];
		slot1 = new Button(100, 400, 300, 300, font, "");
		buttons[0] = slot1;
		slot2 = new Button(450, 400, 300, 300, font, "");
		buttons[1] = slot2;
		slot3 = new Button(800, 400, 300, 300, font, "");
		buttons[2] = slot3;
		slot4 = new Button(1150, 400, 300, 300, font, "");
		buttons[3] = slot4;
		slot5 = new Button(1500, 400, 300, 300, font, "");
		buttons[4] = slot5;
		
		//Creates inventory item textures
		items = new Sprite[player.inv.length];
		for(int n = 0; n < player.inv.length; n++) {
			items[n] = new Sprite(new Texture(player.inv[n][0] + ".png"));
			if(player.inv[n][0].equals("key")) items[n].setScale(9);
			else items[n].setScale(10);
			items[n].setPosition(250 + n*350, 535);
		}
		
		this.player = player;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(5/255f, 117/255f, 245/255f, 0);

		//Menu buttons
		batch.begin();
		shRen.begin(ShapeType.Filled);
		shRen.setColor(Color.DARK_GRAY);
		for(Button button : buttons) {
			button.draw(shRen, batch);
		}
		shRen.end();
		batch.end();

		//Draw text
		batch.begin();
		titleFont.draw(batch, "INVENTORY", titleX, titleY);
		for(Button button : buttons) {
			button.drawText(batch, font);
		}
		for(int n = 0; n < player.inv.length; n++) {
			if(buttons[n].pressed()) {
				font.draw(batch, player.inv[n][0], 100, 390);
				if(player.inv[n][0].equals("gun") || player.inv[n][0].equals("sword")) {
					font.draw(batch, player.inv[n][1] + " attack", 100, 315);
				}
				break;
			}
		}
		if(player.getEquippedWeapon() != -1) {
			font.draw(batch, "Equipped: " + player.inv[player.getEquippedWeapon()][0], 100, 800);
		}
		batch.end();
		
		//Draw item sprites
		batch.begin();
		for(Sprite sprite : items) {
			sprite.draw(batch);
		}
		batch.end();

		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
		if(Gdx.input.isKeyJustPressed(Keys.E)) game.setScreen(prevScreen);
	}
	
	private void interact() {
		for(int n = 0; n < player.inv.length; n++) {
			if(buttons[n].pressed() && player.inv[n] != null) player.setEquippedWeapon(n);
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
