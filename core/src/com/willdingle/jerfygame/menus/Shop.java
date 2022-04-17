package com.willdingle.jerfygame.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.willdingle.jerfygame.JerfyGame;
import com.willdingle.jerfygame.entities.Player;

public class Shop implements Screen {
	final JerfyGame game;
	private Screen prevScreen;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	
	private Button buy1, buy2, backBut;
	private Button[] buttons;
	private float titleX, titleY;
	
	private Player player;
	
	public Shop(JerfyGame game, Screen prevScreen, Player player) {
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
		layout.setText(titleFont, "SHOP");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;
		
		//Creates buttons
		buttons = new Button[3];
		buy1 = new Button(160, 340, 700, 400, font, "Upgrade Gun to 1.5\n Cost: 5");
		buttons[0] = buy1;
		backBut = new Button(710, 100, 500, 100, font, "Back");
		buttons[1] = backBut;
		buy2 = new Button(880, 340, 900, 400, font, "Buy helmet (defence 0.3)\n Cost: 3");
		buttons[2] = buy2;
		
		this.player = player;
	}
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 1, 0);

		//Menu buttons
		batch.begin();
		shRen.begin(ShapeType.Filled);
		shRen.setColor(Color.DARK_GRAY);
		for(Button button : buttons) {
			if(button != null) button.draw(shRen, batch);
		}
		shRen.end();
		batch.end();

		//Draw text
		batch.begin();
		titleFont.draw(batch, "SHOP", titleX, titleY);
		font.draw(batch, "Money: " + player.getMoney(), 20, Gdx.graphics.getHeight() - 40);
		for(Button button : buttons) {
			if(button != null) button.drawText(batch, font);
		}
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
	}
	
	private void interact() {
		if(backBut.pressed()) {
			dispose();
			game.setScreen(prevScreen);
		} else if(buy1.pressed() && player.getMoney() >= 5) {
			player.inv[player.getGunIndex()][1] = "1.5";
			player.setMoney(player.getMoney() - 5);
			buy1 = null;
			buttons[0] = null;
		} else if(buy2.pressed() && player.getMoney() >= 3) {
			player.addToInventory("helmet", "0.3");
			player.setDefence(0.3f);
			player.setMoney(player.getMoney() - 3);
			buy2 = null;
			buttons[2] = null;
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
