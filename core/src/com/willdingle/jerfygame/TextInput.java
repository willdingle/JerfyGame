package com.willdingle.jerfygame;

import com.badlogic.gdx.Input.TextInputListener;

public class TextInput implements TextInputListener {
	String text;

	@Override
	public void input(String text) {
		this.text = text;
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}
	
	public String getText() {
		return this.text;
	}

}
