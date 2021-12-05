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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainMenu implements Screen {
	
	final JerfyGame game;
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shRen;
	private File file;
	
	private int boxX;
	
	private boolean save1;
	private boolean save2;
	private boolean save3;
	
	public MainMenu(final JerfyGame game) {
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 150;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		
		boxX = Gdx.graphics.getWidth() / 2 - 400 / 2;
		
		file = new File(System.getenv("appdata") + "/Jerfy");
		if(! file.exists()) {
			file.mkdir();
		}
		
		save1 = false;
		save2 = false;
		save3 = false;
		file = new File(System.getenv("appdata") + "/Jerfy/save1");
		if(file.isFile()) {
			save1 = true;
		}
		file = new File(System.getenv("appdata") + "/Jerfy/save2");
		if(file.isFile()) {
			save2 = true;
		}
		file = new File(System.getenv("appdata") + "/Jerfy/save3");
		if(file.isFile()) {
			save3 = true;
		}
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
	}
	
	public void interact() {
		if(HitBox.mouse(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), boxX - 400 - 200, 400, 400, 400)) {
			file = new File(System.getenv("appdata") + "/Jerfy/save1");
			if(! save1) {
				String name = "Test";
				Save.create(file, name);
				game.setScreen(new TownTown(game, 1, 1));
			} else {
				String fileContents[] = new String[4];
				fileContents = Save.load(file);
				float plx = Float.parseFloat(fileContents[2]);
				float ply = Float.parseFloat(fileContents[3]);
				System.out.println(fileContents[1]);
				if(fileContents[1].contains("0")) {
					System.out.println("yes");
					game.setScreen(new TownTown(game, plx, ply));
				}
			}
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
		file = null;
	}

}
