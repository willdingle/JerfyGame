package com.willdingle.jerfygame;

import com.willdingle.jerfygame.entities.Player;

public class HitBox {
	
	public static final int ALL = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	
	public static boolean interact(Player player, float objx, float objy, float objw, float objh, int mode) {
		boolean col = false;
		float plx = player.getX();
		float ply = player.getY();
		float plw = player.getWidth();
		float plh = player.getHeight();
		
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;
		switch (mode) {
		case 0:
			left = true;
			right = true;
			up = true;
			down = true;
			break;
		case 1:
			left = true;
			break;
		case 2:
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
			col = (plx <= (objx + objw + 2) && plx >= (objx + objw)) && (ply <= (objy + objh) && ply + plh >= objy);
		}
		
		//right
		if (!col && right) {
			col = (plx + plw <= objx && plx + plw >= (objx - 2)) && (ply <= (objy + objh) && ply + plh >= objy);
		}
		
		//up
		if (!col && up) {
			col = (plx <= objx + objw && plx + plw >= objx) && (ply + plh <= objy && ply + plh >= objy - 2);
		}
		
		//down
		if (!col && down) {
			col  = (plx <= objx + objw && plx + plw >= objx) && (ply <= objy + objh + 2 && ply >= objy + objh);
		}
		
		return col;
	}
}