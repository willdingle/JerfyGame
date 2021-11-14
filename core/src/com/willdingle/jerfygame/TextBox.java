package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class TextBox {
	
	private int x = 20;
	private int y = 20;
	private int boxWidth = Gdx.graphics.getWidth() - (20*2);
	private int boxHeight = 300;
	private int boundWidth = boxWidth;
	private int boundHeight = boxHeight;
	
	public String formatText(String txt, FreeTypeFontGenerator generator, FreeTypeFontParameter parameter, BitmapFont font) {
		GlyphLayout layout = new GlyphLayout();
		String newTxt = "";
		String tempTxt = "";
		boolean done = false;
		while (! done) {
			tempTxt = "";
			for (int i = 0; i < txt.length(); i++) {
				tempTxt = tempTxt + txt.charAt(i);
				layout.setText(font, tempTxt);
				if (layout.width > boundWidth) {
					tempTxt = tempTxt.substring(0, i - 1);
					newTxt = newTxt + tempTxt + "\n";
					txt = txt.substring(i - 1, txt.length());
					System.out.println(txt);
					break;
				} else if (i == txt.length() - 1) {
					done = true;
				}
			}
			
		}
		newTxt = newTxt + tempTxt;
		System.out.println(newTxt);
		return newTxt;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return boxWidth;
	}
	public int getHeight() {
		return boxHeight;
	}
	
}
