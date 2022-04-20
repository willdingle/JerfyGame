package com.willdingle.jerfygame.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.willdingle.jerfygame.JerfyGame;
import com.willdingle.jerfygame.areas.Dungeon;
import com.willdingle.jerfygame.areas.TownTown;
import com.willdingle.jerfygame.entities.Player;
import com.willdingle.jerfygame.files.Save;

import java.io.File;

public class MainMenu implements Screen {
	
	final JerfyGame game;
	private SpriteBatch batch;
	private BitmapFont titleFont, font;
	private ShapeRenderer shRen;
	private File file;
	
	private Button save1but, save2but, save3but, optionsBut, exitBut, helpBut;
	private Button[] buttons;
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
		
		//Checks if saves exist and gets names of saves
		String box1txt, box2txt, box3txt;
		box1txt = "EMPTY SLOT";
		box2txt = "EMPTY SLOT";
		box3txt = "EMPTY SLOT";
		file = new File(System.getenv("appdata") + "/Jerfy/save1");
		if(file.isFile()) {
			game.saves[0] = true;
			String fileContents[] = Save.load(file);
			box1txt = fileContents[0];
			fileContents = null;
		}
		file = new File(System.getenv("appdata") + "/Jerfy/save2");
		if(file.isFile()) {
			game.saves[1] = true;
			String fileContents[] = Save.load(file);
			box2txt = fileContents[0];
			fileContents = null;
		}
		file = new File(System.getenv("appdata") + "/Jerfy/save3");
		if(file.isFile()) {
			game.saves[2] = true;
			String fileContents[] = Save.load(file);
			box3txt = fileContents[0];
			fileContents = null;
		}
		
		//Creates buttons
		buttons = new Button[6];
		save1but = new Button(160, 340, 400, 400, font, box1txt);
		buttons[0] = save1but;
		save2but = new Button(760, 340, 400, 400, font, box2txt);
		buttons[1] = save2but;
		save3but = new Button(1360, 340, 400, 400, font, box3txt);
		buttons[2] = save3but;
		optionsBut = new Button(400, 100, 500, 100, font, "Options");
		buttons[3] = optionsBut;
		exitBut = new Button(1000, 100, 500, 100, font, "Exit");
		buttons[4] = exitBut;
		helpBut = new Button(700, 220, 500, 100, font, "Help");
		buttons[5] = helpBut;
		
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
		titleFont.draw(batch, "Jerfy", titleX, titleY);
		for(Button button : buttons) {
			button.drawText(batch, font);
		}
		batch.end();
		
		//Input
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) interact();
	}
	
	private void interact() {
		if(save1but.pressed()) {
			file = new File(System.getenv("appdata") + "/Jerfy/save1");
			if(! game.saves[0]) {
				String name = "Save 1";
				Save.create(file, name);
				game.fileName = "save1";
				dispose();
				game.setScreen(new TownTown(game, 1, 1, null));
			} else {
				String fileContents[] = Save.load(file);
				int area = Integer.parseInt(fileContents[1]);
				float plx = Float.parseFloat(fileContents[2]);
				float ply = Float.parseFloat(fileContents[3]);
				
				
				String[][] inv = null;
				if(fileContents[4] != null) {
					String[] tempInv = Save.loadInv(fileContents[4]);
					
					inv = new String[tempInv.length / 2][2];
					int itemStatIndex = 0;
					for(int n = 0; n < inv.length; n++) {
						inv[n][0] = tempInv[itemStatIndex];
						inv[n][1] = tempInv[itemStatIndex + 1];
						itemStatIndex += 2;
					}
				}
				
				dispose();
				game.fileName = "save1";
				switch(area) {
				case 0:
					game.setScreen(new TownTown(game, plx, ply, inv));
					break;
					
				case 1:
				case 2:
					Player player = new Player(null, plx, ply, inv);
					game.setScreen(new Dungeon(game, player, area));
					break;
				}
			}
			
		} else if (save2but.pressed()) {
			file = new File(System.getenv("appdata") + "/Jerfy/save2");
			if(! game.saves[1]) {
				String name = "Save 2";
				Save.create(file, name);
				game.fileName = "save2";
				dispose();
				game.setScreen(new TownTown(game, 1, 1, null));
			} else {
				String fileContents[] = new String[4];
				fileContents = Save.load(file);
				int area = Integer.parseInt(fileContents[1]);
				float plx = Float.parseFloat(fileContents[2]);
				float ply = Float.parseFloat(fileContents[3]);
				
				String[][] inv = null;
				if(fileContents[4] != null) {
					String[] tempInv = Save.loadInv(fileContents[4]);
					
					inv = new String[tempInv.length / 2][2];
					int itemStatIndex = 0;
					for(int n = 0; n < inv.length; n++) {
						inv[n][0] = tempInv[itemStatIndex];
						inv[n][1] = tempInv[itemStatIndex + 1];
						itemStatIndex += 2;
					}
				}
				
				dispose();
				game.fileName = "save2";
				if(area == 0) {
					game.setScreen(new TownTown(game, plx, ply, inv));
				}
			}
			
		} else if (save3but.pressed()) {
			file = new File(System.getenv("appdata") + "/Jerfy/save3");
			if(! game.saves[2]) {
				String name = "Save 3";
				Save.create(file, name);
				game.fileName = "save3";
				dispose();
				game.setScreen(new TownTown(game, 1, 1, null));
			} else {
				String fileContents[] = new String[4];
				fileContents = Save.load(file);
				int area = Integer.parseInt(fileContents[1]);
				float plx = Float.parseFloat(fileContents[2]);
				float ply = Float.parseFloat(fileContents[3]);

				String[][] inv = null;
				if(fileContents[4] != null) {
					String[] tempInv = Save.loadInv(fileContents[4]);
					
					inv = new String[tempInv.length / 2][2];
					int itemStatIndex = 0;
					for(int n = 0; n < inv.length; n++) {
						inv[n][0] = tempInv[itemStatIndex];
						inv[n][1] = tempInv[itemStatIndex + 1];
						itemStatIndex += 2;
					}
				}
				
				dispose();
				game.fileName = "save3";
				if(area == 0) {
					game.setScreen(new TownTown(game, plx, ply, inv));
				}
			}
		}
		else if (optionsBut.pressed()) game.setScreen(new OptionsMenu(game, game.getScreen(), true));
		else if (exitBut.pressed()) System.exit(0);
		else if (helpBut.pressed()) game.setScreen(new HelpMenu(game, game.getScreen()));
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
