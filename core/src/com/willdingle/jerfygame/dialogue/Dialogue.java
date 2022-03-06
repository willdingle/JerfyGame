package com.willdingle.jerfygame.dialogue;

public class Dialogue {
	
	public static String donker(int index) {
		String txt = null;
		
		switch(index) {
		case 0:
			txt = "Hello there!";
			break;
		case 1:
			txt = "You must be in need of a weapon to fight all those \nenemies!";
			break;
		case 2:
			txt = "I happen to have one on me. I will give it to you now. You have no choice.";
			break;
		case 3:
			txt = "*You got a gun!*";
			break;
		}
		
		return txt;
	}
	
	public static String paper(int index) {
		String txt = null;
		
		switch(index) {
		case 0:
			txt = "'elo";
			break;
		case 1:
			txt = "Welcome to my 'ouse mate";
			break;
		case 2:
			txt = "I s'ppose you'll be needin' a shank ye?";
			break;
		case 3:
			txt = "Well son, I just so 'appen to 'ave one on me";
			break;
		case 4:
			txt = "You can fank me la'er";
			break;
		case 5:
			txt = "*You got a sword!*";
			break;
		}
		
		return txt;
	}
}
