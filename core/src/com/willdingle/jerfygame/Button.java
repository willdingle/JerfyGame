package com.willdingle.jerfygame;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Button {
	
	int x, y, w, h;
	float txtX, txtY;
	String txt;
	
	public Button(int x, int y, int w, int h, BitmapFont font, String txt) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.txt = txt;
		formatText(font);
	}
	
	private void formatText(BitmapFont font) {
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, txt);
		txtX = (x + x + w)/2 - layout.width/2;
		txtY = (y + y + h)/2 - layout.height/2 + 40;
	}
	
	public void draw(ShapeRenderer shRen, SpriteBatch batch) {
		shRen.rect(x, y, w, h);
	}
	
	public void drawText(SpriteBatch batch, BitmapFont font) {
		font.draw(batch, txt, txtX, txtY);
	}
	
	public boolean pressed() {
		boolean pressed = false;
		if(HitBox.mouse(x, y, w, h)) {
			pressed = true;
		}
		return pressed;
	}
}
