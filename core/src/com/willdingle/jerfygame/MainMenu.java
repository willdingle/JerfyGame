package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenu implements Screen {
	
	final JerfyGame game;
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shRen;
	
	private int boxX;
	
	public MainMenu(final JerfyGame game) {
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 150;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		
		boxX = Gdx.graphics.getWidth() / 2 - 400 / 2;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(178/255f, 0, 1, 0);
		
		//Draw title
		batch.begin();
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, "Jerfy");
		font.draw(batch, "Jerfy", Gdx.graphics.getWidth() / 2 - layout.width / 2, 1025);
		batch.end();
		
		//Draw save boxes
		batch.begin();
		shRen.begin(ShapeType.Line);
		shRen.setColor(1,1,1,1);
		shRen.rect(boxX, 400, 400, 400);
		shRen.rect(boxX - 400 - 200, 400, 400, 400);
		shRen.rect(boxX + 400 + 200, 400, 400, 400);
		shRen.end();
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			interact();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			game.setScreen(new TownTown(game));
		}

	}
	
	public void interact() {
		if(HitBox.mouse(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), boxX, 400, 400, 400)) {
			game.setScreen(new TownTown(game));
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
