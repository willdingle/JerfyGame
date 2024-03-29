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

public class OptionsMenu implements Screen {
	
	final JerfyGame game;
	private Screen prevScreen;
	private boolean mainMenu;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button backBut, videoBut, saveBut;
	private Button[] buttons;
	private float titleX, titleY;

	public OptionsMenu (final JerfyGame game, Screen prevScreen, boolean mainMenu) {
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 120;
		titleFont = game.generator.generateFont(game.parameter);
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		this.prevScreen = prevScreen;
		this.mainMenu = mainMenu;
		
		//Positions title text
		GlyphLayout layout = new GlyphLayout();
		layout.setText(titleFont, "OPTIONS");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;
		
		//Creates buttons
		buttons = new Button[3];
		videoBut = new Button(300, 425, 500, 300, font, "Video");
		buttons[0] = videoBut;
		saveBut = new Button(1120, 425, 500, 300, font, "Save Mgmt");
		buttons[1] = saveBut;
		backBut = new Button(710, 100, 500, 100, font, "Back");
		buttons[2] = backBut;
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
		titleFont.draw(batch, "OPTIONS", titleX, titleY);
		for(Button button : buttons) {
			button.drawText(batch, font);
		}
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
	}
	
	private void interact() {
		if(videoBut.pressed()) game.setScreen(new VideoOptions(game, game.getScreen()));
		else if(saveBut.pressed()) game.setScreen(new SaveOptions(game, game.getScreen()));
		else if(backBut.pressed()) {
			dispose();
			if(mainMenu) game.setScreen(new MainMenu(game));
			else game.setScreen(prevScreen);
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
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		shRen.dispose();
	}

}
