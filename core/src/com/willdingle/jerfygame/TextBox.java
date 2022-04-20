package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextBox {
	private SpriteBatch batch;
	private ShapeRenderer shRen;
	private BitmapFont font;
	
	private int x;
	private int y;
	private int boxWidth;
	private int boxHeight;
	private int boundWidth;
	private String txt;
	
	public TextBox(SpriteBatch batch, ShapeRenderer shRen, BitmapFont font, String txt) {
		this.batch = batch;
		this.shRen = shRen;
		this.font = font;
		x = 20;
		y = 20;
		boxWidth = Gdx.graphics.getWidth() - (20*2);
		boxHeight = 300;
		boundWidth = boxWidth - 20;
		this.txt = formatText(txt);
	}
	
	private String formatText(String txt) {
		GlyphLayout layout = new GlyphLayout();
		String newTxt = "";
		String tempTxt = "";
		boolean done = false;
		while (! done) {
			tempTxt = "";
			int wordLen = 0;
			for (int i = 0; i < txt.length(); i++) {
				tempTxt = tempTxt + txt.charAt(i);
				wordLen += 1;
				
				if(txt.charAt(i) == ' ') {
					layout.setText(font, tempTxt);
					
					if (layout.width > boundWidth) {
						tempTxt = tempTxt.substring(0, i - wordLen);
						newTxt = newTxt + tempTxt + "\n";
						txt = txt.substring(i - wordLen, txt.length());
						break;
					}
					wordLen = 0;
					
				} else if(i == txt.length() - 1) {
					done = true;
				}
			}
		}
		newTxt = newTxt + tempTxt;
		return newTxt;
	}
	
	public void render() {
		shRen.begin(ShapeType.Filled);
		shRen.setColor(0, 0, 0, 1);
		shRen.rect(x, y, boxWidth, boxHeight);
		shRen.end();
		shRen.begin(ShapeType.Line);
		shRen.setColor(1, 1, 1, 1);
		shRen.rect(x, y, boxWidth, boxHeight);
		shRen.end();
		
		batch.begin();
		font.draw(batch, txt, 40, boxHeight);
		batch.end();
	}
	
}
