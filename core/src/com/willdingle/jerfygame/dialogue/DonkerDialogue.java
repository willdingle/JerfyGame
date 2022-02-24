package com.willdingle.jerfygame.dialogue;

public class DonkerDialogue {
	
	public static String getText(int index) {
		String txt = null;
		
		switch(index) {
		case 0:
			txt = "Hello there!";
			break;
		case 1:
			txt = "You must be in need of a weapon to fight all those enemies";
			break;
		case 2:
			txt = "I happen to have one on me. I will give it to you now. You have no choice.";
			break;
		}
		
		return txt;
	}
}
