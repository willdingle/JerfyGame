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

public class MainMenu implements Screen {
	
	final JerfyGame game;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private File file;
	
	private boolean save1;
	private boolean save2;
	private boolean save3;
	
	private Button save1but, save2but, save3but, optionsBut, exitBut;
	private float titleX, titleY;
	
	public MainMenu(final JerfyGame game) {
		//Initial variable declarations
		this.game = game;
		batch = new SpriteBatch();
		game.parameter.size = 150;
		titleFont = game.generator.generateFont(game.parameter);
		game.parameter.size = 70;
		font = game.generator.generateFont(game.parameter);
		shRen = new ShapeRenderer();
		
		//Positions title text
		GlyphLayout layout = new GlyphLayout();
		layout.setText(titleFont, "Jerfy");
		this.titleX = Gdx.graphics.getWidth()/2 - layout.width/2;
		this.titleY = Gdx.graphics.getHeight() - layout.height;
		
		//Checks if save directory exists in appdata
		file = new File(System.getenv("appdata") + "/Jerfy");
		if(! file.exists()) {
			file.mkdir();
		}
		
		//Checks if saves exist and gets names of saves
		String box1txt, box2txt, box3txt;
		save1 = false;
		box1txt = "EMPTY SLOT";
		save2 = false;
		box2txt = "EMPTY SLOT";
		save3 = false;
		box3txt = "EMPTY SLOT";
		file = new File(System.getenv("appdata") + "/Jerfy/save1");
		if(file.isFile()) {
			save1 = true;
			String fileContents[] = new String[4];
			fileContents = Save.load(file);
			box1txt = fileContents[0];
			fileContents = null;
		}
		file = new File(System.getenv("appdata") + "/Jerfy/save2");
		if(file.isFile()) {
			save2 = true;
			String fileContents[] = new String[4];
			fileContents = Save.load(file);
			box2txt = fileContents[0];
			fileContents = null;
		}
		file = new File(System.getenv("appdata") + "/Jerfy/save3");
		if(file.isFile()) {
			save3 = true;
			String fileContents[] = new String[4];
			fileContents = Save.load(file);
			box3txt = fileContents[0];
			fileContents = null;
		}
		
		//Creates buttons
		save1but = new Button(160, 340, 400, 400, font, box1txt);
		save2but = new Button(760, 340, 400, 400, font, box2txt);
		save3but = new Button(1360, 340, 400, 400, font, box3txt);
		optionsBut = new Button(400, 100, 500, 100, font, "Options");
		exitBut = new Button(1000, 100, 500, 100, font, "Exit");
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
		shRen.begin(ShapeType.Line);
		shRen.setColor(1,1,1,1);
		save1but.draw(shRen, batch); //Save 1 button
		save2but.draw(shRen, batch); //Save 2 button
		save3but.draw(shRen, batch); //Save 3 button
		optionsBut.draw(shRen, batch); //Options button
		exitBut.draw(shRen, batch); //Exit button
		shRen.end();
		batch.end();
		
		//Draw text
		batch.begin();
		titleFont.draw(batch, "Jerfy", titleX, titleY);
		save1but.drawText(batch, font); //Save 1 box text
		save2but.drawText(batch, font); //Save 2 box text
		save3but.drawText(batch, font); //Save 3 box text
		optionsBut.drawText(batch, font); //Options box text
		exitBut.drawText(batch, font); //Exit box text
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) game.setScreen(new TownTown(game, 1, 1));
	}
	
	private void interact() {
		if(save1but.pressed()) {
			file = new File(System.getenv("appdata") + "/Jerfy/save1");
			if(! save1) {
				String name = "Save 1";
				Save.create(file, name);
				game.setScreen(new TownTown(game, 1, 1));
			} else {
				String fileContents[] = new String[4];
				fileContents = Save.load(file);
				float area = Float.parseFloat(fileContents[1]);
				float plx = Float.parseFloat(fileContents[2]);
				float ply = Float.parseFloat(fileContents[3]);
				dispose();
				if(area == 0) {
					game.setScreen(new TownTown(game, plx, ply));
				}
			}
			
		} else if (save2but.pressed()) {
			file = new File(System.getenv("appdata") + "/Jerfy/save2");
			if(! save2) {
				String name = "Save 2";
				Save.create(file, name);
				game.setScreen(new TownTown(game, 1, 1));
			} else {
				String fileContents[] = new String[4];
				fileContents = Save.load(file);
				float area = Float.parseFloat(fileContents[1]);
				float plx = Float.parseFloat(fileContents[2]);
				float ply = Float.parseFloat(fileContents[3]);
				dispose();
				if(area == 0) {
					game.setScreen(new TownTown(game, plx, ply));
				}
			}
			
		} else if (save3but.pressed()) {
			file = new File(System.getenv("appdata") + "/Jerfy/save3");
			if(! save3) {
				String name = "Save 3";
				Save.create(file, name);
				game.setScreen(new TownTown(game, 1, 1));
			} else {
				String fileContents[] = new String[4];
				fileContents = Save.load(file);
				float area = Float.parseFloat(fileContents[1]);
				float plx = Float.parseFloat(fileContents[2]);
				float ply = Float.parseFloat(fileContents[3]);
				dispose();
				if(area == 0) {
					game.setScreen(new TownTown(game, plx, ply));
				}
			}
		}
		else if (optionsBut.pressed()) game.setScreen(new OptionsMenu(game, game.getScreen()));
		else if (exitBut.pressed()) System.exit(0);
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
		titleFont.dispose();
		font.dispose();
		shRen.dispose();
		file = null;
	}

}
