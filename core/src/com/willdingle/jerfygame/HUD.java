package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.willdingle.jerfygame.entities.Player;

public class HUD {
	private static Sprite dreamCoin;
	private static BitmapFont font;
	
	public HUD(BitmapFont font) {
		dreamCoin = new Sprite(new Texture("dream coin/0.png"));
		dreamCoin.setPosition(20, Gdx.graphics.getHeight() - dreamCoin.getWidth() - 30);
		dreamCoin.setScale(5);
		
		HUD.font = font;
	}
	
	public void draw(SpriteBatch batch, Player player) {
		batch.begin();
		dreamCoin.draw(batch);
		batch.end();
		drawText(batch, player);
	}
	
	private void drawText(SpriteBatch batch, Player player) {
		batch.begin();
		font.draw(batch, Integer.toString(player.getMoney()), dreamCoin.getX() + dreamCoin.getWidth() + 30, Gdx.graphics.getHeight() - dreamCoin.getWidth() + 25);
		batch.end();
	}
}
