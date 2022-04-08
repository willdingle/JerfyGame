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
import com.willdingle.jerfygame.files.Save;

public class SaveOptions implements Screen {
	final JerfyGame game;
	private Screen prevScreen;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button backBut, del1, del2, del3;
	private Button[] buttons;
	private float titleX, titleY;

	public SaveOptions(JerfyGame game, Screen prevScreen) {
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
		layout.setText(titleFont, "SAVE MANAGEMENT");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;

		//Creates buttons
		int butAmount = 1;
		for(int n = 0; n < 3; n++) {
			if(game.saves[n]) butAmount += 1;
		}
		buttons = new Button[butAmount];
		backBut = new Button(710, 100, 500, 100, font, "Back");
		buttons[0] = backBut;
		
		int butIndex = 1;
		if(game.saves[0]) {
			del1 = new Button(100, 425, 500, 300, font, "Delete 1");
			buttons[butIndex] = del1;
			butIndex += 1;
		}
		if(game.saves[1]) {
			del2 = new Button(700, 425, 500, 300, font, "Delete 2");
			buttons[butIndex] = del2;
			butIndex += 1;
		}
		if(game.saves[2]) {
			del3 = new Button(1300, 425, 500, 300, font, "Delete 3");
			buttons[butIndex] = del3;
		}
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 128/255f, 0, 1);

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
		titleFont.draw(batch, "SAVE MANAGEMENT", titleX, titleY);
		for(Button button : buttons) {
			button.drawText(batch, font);
		}
		batch.end();

		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
	}
	
	private void interact() {
		if(backBut.pressed()) game.setScreen(prevScreen);
		if(del1 != null) if(del1.pressed()) {
			Save.delete("save1");
			game.saves[0] = false;
			game.setScreen(new SaveOptions(game, prevScreen));
		} if(del2 != null) if(del2.pressed()) {
			Save.delete("save2");
			game.saves[1] = false;
			game.setScreen(new SaveOptions(game, prevScreen));
		} if(del3 != null) if(del3.pressed()) {
			Save.delete("save3");
			game.saves[2] = false;
			game.setScreen(new SaveOptions(game, prevScreen));
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
