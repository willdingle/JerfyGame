package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player extends Sprite {
	
	private TiledMapTileLayer collisionLayer;
	Texture[] upWalk = new Texture[2];
	Texture[] downWalk = new Texture[2];
	Texture[] leftWalk = new Texture[2];
	Texture[] rightWalk = new Texture[2];
	private float elTimeF;
	private int elTime;
	
	private int meleeRange;

	public Player(TiledMapTileLayer collisionLayer) {
		super(new Sprite(new Texture("jerfy/down.png")));
		this.collisionLayer = collisionLayer;
		setX(1 * collisionLayer.getTileWidth());
		setY(1 * collisionLayer.getTileHeight());
		
		elTimeF = 0;
		elTime = 0;
		
		upWalk[0] = new Texture("jerfy/upwalk/0.png");
		upWalk[1] = new Texture("jerfy/upwalk/1.png");
		downWalk[0] = new Texture("jerfy/downwalk/0.png");
		downWalk[1] = new Texture("jerfy/downwalk/1.png");
		leftWalk[0] = new Texture("jerfy/leftwalk/0.png");
		leftWalk[1] = new Texture("jerfy/leftwalk/1.png");
		rightWalk[0] = new Texture("jerfy/rightwalk/0.png");
		rightWalk[1] = new Texture("jerfy/rightwalk/1.png");
		
		meleeRange = 16;
	}
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch);
	}
	
	public void meleeAttack() {
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		
		if (x <= getX()) {
			
		}
	}
	
	public void move(float delta) {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			float oldY = getY();
			setY(getY() + 100 * delta);
			if (collide(0, 1)) {
				setY(oldY);
			}
			animate("w");
			
		}
		if (Gdx.input.isKeyPressed(Keys.S) && getY() > 0) {
			float oldY = getY();
			setY(getY() - 100 * delta);
			if (collide(0, -1)) {
				setY(oldY);
			}
			animate("s");
			
		}
		if (Gdx.input.isKeyPressed(Keys.A) && getX() > 0) {
			float oldX = getX();
			setX(getX() - 100 * delta);
			if (collide(-1, 0)) {
				setX(oldX);
			}
			animate("a");
			
		}
		if (Gdx.input.isKeyPressed(Keys.D) && (getX() + getWidth()) / collisionLayer.getTileWidth() < collisionLayer.getWidth()) {
			float oldX = getX();
			setX(getX() + 100 * delta);
			if (collide(1, 0)) {
				setX(oldX);
			}
			animate("d");
		
		}
	}
	
	public void animate(String dir) {
			
			if (elTimeF > 0.25) {
				elTime = 0;
				elTimeF = 0;
			} else if (elTimeF > 0.125) {
				elTime = 1;
			} else {
				elTime = 0;
			}
			elTimeF += Gdx.graphics.getDeltaTime();
			
			switch (dir) {
			case "w":
				setTexture(upWalk[elTime]);
				break;
			case "s":
				setTexture(downWalk[elTime]);
				break;
			case "a":
				setTexture(leftWalk[elTime]);
				break;
			case "d":
				setTexture(rightWalk[elTime]);
				break;
			}
		}
	
	public boolean collide(int xdir, int ydir) {
		float tileW = collisionLayer.getTileWidth(), tileH = collisionLayer.getTileHeight();
		boolean colX = false, colY = false;
		
		//collide x
		if (xdir < 0) {
			//top left
			colX = isTileBlocked((getX() / tileW), ((getY() + getHeight()) / tileH)); 
			
			//middle left
			if (! colX) {
				colX = isTileBlocked((getX() / tileW), ((getY() + getHeight() / 2) / tileH));
			}
			
			//bottom left
			if (! colX) {
				colX = isTileBlocked((getX() / tileW), (getY() / tileH));
			}
		
		} else if (xdir > 0) {
			//top right
			colX = isTileBlocked(((getX() + getWidth()) / tileW), ((getY() + getHeight()) / tileH));
			
			//middle right
			if (! colX) {
				colX = isTileBlocked(((getX() + getWidth()) / tileW), ((getY() + getHeight() / 2) / tileH));
			}
			
			//bottom right
			if (! colX) {
				colX = isTileBlocked(((getX() + getWidth()) / tileW), (getY()  / tileH));
			}
			
		}
		
		//collide y
		if (ydir < 0) {
			//bottom left
			colY = isTileBlocked((getX() / tileW), (getY()  / tileH));
			
			//bottom middle
			if (! colY) {
				colY = isTileBlocked(((getX() + getWidth() / 2) / tileW), (getY()  / tileH));
			}
			
			//bottom right
			if (! colY) {
				colY = isTileBlocked(((getX() + getWidth()) / tileW), (getY()  / tileH));
			}
			
		} else if (ydir > 0) {
			//top left
			colY = isTileBlocked((getX() / tileW), ((getY() + getHeight())  / tileH));
			
			//top middle
			if (! colY) {
				colY = isTileBlocked(((getX() + getWidth() / 2) / tileW), ((getY() + getHeight())  / tileH));
			}
			
			//top right
			if (! colY) {
				colY = isTileBlocked(((getX() + getWidth()) / tileW), ((getY() + getHeight())  / tileH));
			}
		}
		
		return colX || colY;
	}
	
	public boolean isTileBlocked(float x, float y) {
		boolean col = collisionLayer.getCell((int) x, (int) y).getTile().getProperties().containsKey("blocked");
		return col;
	}
	
	

}
