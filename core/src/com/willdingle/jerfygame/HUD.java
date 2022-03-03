package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.willdingle.jerfygame.entities.Player;

public class HUD {
	private static Sprite dreamCoin;
	private static Sprite heart;
	private static BitmapFont font;
	
	public HUD(BitmapFont font) {
		dreamCoin = new Sprite(new Texture("dream coin/0.png"));
		dreamCoin.setPosition(22, Gdx.graphics.getHeight() - dreamCoin.getWidth() - 30);
		dreamCoin.setScale(5);
		
		heart = new Sprite(new Texture("heart.png"));
		heart.setPosition(22, Gdx.graphics.getHeight() - dreamCoin.getWidth() - heart.getWidth() - 80);
		heart.setScale(3.5f);
		
		HUD.font = font;
	}
	
	public void draw(SpriteBatch batch, Player player) {
		batch.begin();
		dreamCoin.draw(batch);
		heart.draw(batch);
		batch.end();
		drawText(batch, player);
	}
	
	private void drawText(SpriteBatch batch, Player player) {
		batch.begin();
		font.draw(batch, Integer.toString(player.getMoney()), dreamCoin.getX() + dreamCoin.getWidth() + 30, Gdx.graphics.getHeight() - dreamCoin.getWidth() - 5);
		font.draw(batch, Integer.toString(player.getHealth()), heart.getX() + heart.getWidth() + 25, Gdx.graphics.getHeight() - dreamCoin.getWidth() - heart.getWidth() - 50);
		batch.end();
	}
}
