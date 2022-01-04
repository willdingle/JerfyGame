package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

public class OptionsMenu implements Screen {
	
	final JerfyGame game;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private Button backBut;

	public OptionsMenu (final JerfyGame game) {
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 120;
		titleFont = game.generator.generateFont(game.parameter);
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		backBut = new Button(710, 100, 500, 100, font, "Back");
	}
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 1, 0);
		
		//Menu boxes
		batch.begin();
		shRen.begin(ShapeType.Line);
		shRen.setColor(1,1,1,1);
		backBut.draw(shRen, batch);
		shRen.end();
		batch.end();
		
		//Draw text
		batch.begin();
		GlyphLayout layout = new GlyphLayout();
		layout.setText(titleFont, "OPTIONS");
		titleFont.draw(batch, "OPTIONS", Gdx.graphics.getWidth()/2 - layout.width/2, Gdx.graphics.getHeight() - layout.height);
		
		backBut.drawText(batch, font);
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			interact();
		}
	}
	
	public void interact() {
		if(backBut.pressed()) {
			game.setScreen(new MainMenu(game));
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
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		shRen.dispose();
	}

}
