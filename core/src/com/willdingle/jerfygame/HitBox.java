package com.willdingle.jerfygame;

import com.badlogic.gdx.Gdx;
import com.willdingle.jerfygame.entities.Player;

public class HitBox {
	
	public static final int ALL = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	
	public static final int INTERACT = 4;
	public static final int COLLIDE = 2;
	
	public static boolean player(Player player, float objx, float objy, float objw, float objh, int dirs, float mode) {
		boolean col = false;
		float plx = player.getX();
		float ply = player.getY();
		float plw = player.getWidth();
		float plh = player.getHeight();
		
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;
		switch (dirs) {
		case 0:
			left = true;
			right = true;
			up = true;
			down = true;
			break;
		case 1:
			plx += 3;
			left = true;
			break;
		case 2:
			plx -= 3;
			right = true;
			break;
		case 3:
			up = true;
			break;
		case 4:
			down = true;
			break;
		}
		
		//left
		if (left) {
			col = (plx <= (objx + objw + mode) && plx >= (objx + objw)) && (ply <= (objy + objh) && ply + plh >= objy);
		}
		
		//right
		if (!col && right) {
			col = (plx + plw <= objx && plx + plw >= (objx - mode)) && (ply <= (objy + objh) && ply + plh >= objy);
		}
		
		//up
		if (!col && up) {
			col = (plx <= objx + objw && plx + plw >= objx) && (ply + plh <= objy && ply + plh >= objy - mode);
		}
		
		//down
		if (!col && down) {
			col  = (plx <= objx + objw && plx + plw >= objx) && (ply <= objy + objh + mode && ply >= objy + objh);
		}
		
		return col;
	}
	
	public static boolean mouse(float objx, float objy, float objw, float objh) {
		boolean click = false;
		float plx = Gdx.input.getX();
		float ply = Gdx.graphics.getHeight() - Gdx.input.getY();
		if((plx >= objx && plx <= objx + objw) && (ply >= objy && ply <= objy + objh)) {
			click = true;
		}
		return click;
	}
}
