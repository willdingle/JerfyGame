package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextBox {
	
	private int x = 20;
	private int y = 20;
	private int width = Gdx.graphics.getWidth() - (20*2);
	private int height = 300;
	private int widthBound = width;
	private int heightBound = height;
	
	public void formatText(String txt, FreeTypeFontGenerator generator, FreeTypeFontParameter parameter, BitmapFont font) {
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, txt);
		float txtWidth = layout.width;
		float txtHeight = layout.height;
		String newTxt = "";
		
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
}
