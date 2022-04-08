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

public class PauseMenu implements Screen {
	
	final JerfyGame game;
	private Screen prevScreen;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button resBut, exitBut, optionsBut, helpBut, mainMenuBut;
	private Button[] buttons;
	private float titleX, titleY;
	
	public PauseMenu(JerfyGame game, Screen prevScreen) {
		//Initial variable declarations
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 150;
		titleFont = game.generator.generateFont(game.parameter);
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		this.prevScreen = prevScreen;
		
		//Positions title text
		GlyphLayout layout = new GlyphLayout();
		layout.setText(titleFont, "Jerfy");
		titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		titleY = Gdx.graphics.getHeight() - layout.height + 40;
		
		//Creates buttons
		buttons = new Button[5];
		resBut = new Button(Gdx.graphics.getWidth()/2 - 250, 800, 500, 100, font, "Resume");
		buttons[0] = resBut;
		optionsBut = new Button(Gdx.graphics.getWidth()/2 - 250, 680, 500, 100, font, "Options");
		buttons[1] = optionsBut;
		exitBut = new Button(Gdx.graphics.getWidth()/2 - 250, 320, 500, 100, font, "Exit");
		buttons[2] = exitBut;
		helpBut = new Button(Gdx.graphics.getWidth()/2 - 250, 560, 500, 100, font, "Help");
		buttons[3] = helpBut;
		mainMenuBut = new Button(Gdx.graphics.getWidth()/2 - 250, 440, 500, 100, font, "Main Menu");
		buttons[4] = mainMenuBut;
		
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 139/255f, 139/255f, 1);
		
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
		titleFont.draw(batch, "PAUSE", titleX, titleY);
		for(Button button : buttons) {
			button.drawText(batch, font);
		}
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
		
	}

	private void interact() {
		if(resBut.pressed()) {
			dispose();
			game.setScreen(prevScreen);
		}
		else if(optionsBut.pressed()) game.setScreen(new OptionsMenu(game, game.getScreen(), false));
		else if(exitBut.pressed()) System.exit(0);
		else if(helpBut.pressed()) game.setScreen(new HelpMenu(game, game.getScreen()));
		else if(mainMenuBut.pressed()) game.setScreen(new MainMenu(game));
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
		batch.dispose();
		titleFont.dispose();
		font.dispose();
		shRen.dispose();
	}
}
