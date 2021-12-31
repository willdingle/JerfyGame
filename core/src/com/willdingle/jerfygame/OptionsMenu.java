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

	public OptionsMenu (final JerfyGame game) {
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 120;
		titleFont = game.generator.generateFont(game.parameter);
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 1, 0);
		
		//Draw text
		batch.begin();
		GlyphLayout layout = new GlyphLayout();
				
		layout.setText(titleFont, "OPTIONS");
		titleFont.draw(batch, "OPTIONS", Gdx.graphics.getWidth()/2 - layout.width/2, Gdx.graphics.getHeight() - layout.height);
		
		layout.setText(font, "Back");
		font.draw(batch, "Back", Gdx.graphics.getWidth()/2 - layout.width/2, (100+200)/2 + 20);
		
		batch.end();
		
		//Menu boxes
		batch.begin();
		shRen.begin(ShapeType.Line);
		shRen.setColor(1,1,1,1);
		shRen.rect(Gdx.graphics.getWidth() / 2 - 500/2, 100, 500, 100);
		shRen.end();
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			interact();
		}
	}
	
	public void interact() {
		if(HitBox.mouse(Gdx.graphics.getWidth()/2 - 500/2, 100, 500, 100)) {
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
