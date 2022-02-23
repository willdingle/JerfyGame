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

public class InventoryMenu implements Screen {
	final JerfyGame game;
	private Screen prevScreen;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button slot1, slot2, slot3, slot4, slot5;
	private Button[] buttons;
	private float titleX, titleY;
	
	public InventoryMenu(final JerfyGame game, Screen prevScreen) {
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
		slot1 = new Button(300, 600, 300, 300, font, "");
		buttons[0] = slot1;
		slot2 = new Button(1120, 600, 300, 300, font, "");
		buttons[1] = slot2;
		slot3 = new Button(300, 250, 300, 300, font, "");
		buttons[2] = slot3;
		slot4 = new Button(1120, 250, 300, 300, font, "");
		buttons[3] = slot4;
		slot5 = new Button(710, 100, 300, 300, font, "");
		buttons[4] = slot5;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 1, 0);

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
		titleFont.draw(batch, "OPTIONS", titleX, titleY);
		for(Button button : buttons) {
			button.drawText(batch, font);
		}
		batch.end();

		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
		
	}
	
	private void interact() {
		
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
