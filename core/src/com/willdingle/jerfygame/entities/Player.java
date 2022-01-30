package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.willdingle.jerfygame.HitBox;

public class Player extends Sprite {
	
	private TiledMapTileLayer colLayer;
	private Texture[] upWalk = new Texture[2];
	private Texture[] downWalk = new Texture[2];
	private Texture[] leftWalk = new Texture[2];
	private Texture[] rightWalk = new Texture[2];
	private float elTimeF;
	private int elTime;
	
	private int meleeRange;
	private int[] inv;
	
	public Bullet[] bullets;

	public Player(TiledMapTileLayer colLayer, float x, float y) {
		super(new Sprite(new Texture("jerfy/down.png")));
		this.colLayer = colLayer;
		setX(x * colLayer.getTileWidth());
		setY(y * colLayer.getTileHeight());
		
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
		inv = new int[5];
		
		bullets = new Bullet[0];
	}
	
	public void attack() {
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			Bullet bullet = new Bullet(colLayer, getX(), getY() + 8, 'l');
			
			if(bullets.length == 0) {
				bullets = new Bullet[1];
				bullets[0] = bullet;
			}
			else {
				Bullet[] tmpBullets = bullets;
				bullets = new Bullet[bullets.length + 1];
				for(int n = 0; n < tmpBullets.length; n++) {
					bullets[n] = tmpBullets[n];
				}
				bullets[bullets.length - 1] = bullet;
			}
		}
	}
	
	public void move(float delta, MovingNPC movingNPCs[], StillNPC stillNPCs[]) {
		attack();
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			float oldY = getY();
			setY(getY() + 100 * delta);
			
			boolean spriteCol = false;
			for (MovingNPC n : movingNPCs) {
				if(HitBox.interact(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.UP)) {
					spriteCol = true;
					break;
				}
			}
			if(! spriteCol) {
				for (StillNPC n : stillNPCs) {
					if(HitBox.interact(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.UP)) {
						spriteCol = true;
						break;
					}
				}
			}
			
			if (tileCollide(0, 1) || spriteCol) {
				setY(oldY);
			}
			animate("w");
			
		}
		if (Gdx.input.isKeyPressed(Keys.S) && getY() > 0) {
			float oldY = getY();
			setY(getY() - 100 * delta);
			
			boolean spriteCol = false;
			for (MovingNPC n : movingNPCs) {
				if(HitBox.interact(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.DOWN)) {
					spriteCol = true;
					break;
				}
			}
			if(! spriteCol) {
				for (StillNPC n : stillNPCs) {
					if(HitBox.interact(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.DOWN)) {
						spriteCol = true;
						break;
					}
				}
			}
			
			if (tileCollide(0, -1) || spriteCol) {
				setY(oldY);
			}
			animate("s");
			
		}
		if (Gdx.input.isKeyPressed(Keys.A) && getX() > 0) {
			float oldX = getX();
			setX(getX() - 100 * delta);
			
			boolean spriteCol = false;
			for (MovingNPC n : movingNPCs) {
				if(HitBox.interact(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.LEFT)) {
					spriteCol = true;
					break;
				}
			}
			if(! spriteCol) {
				for (StillNPC n : stillNPCs) {
					if(HitBox.interact(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.LEFT)) {
						spriteCol = true;
						break;
					}
				}
			}
			
			if (tileCollide(-1, 0) || spriteCol) {
				setX(oldX);
			}
			animate("a");
			
		}
		if (Gdx.input.isKeyPressed(Keys.D) && (getX() + getWidth()) / colLayer.getTileWidth() < colLayer.getWidth()) {
			float oldX = getX();
			setX(getX() + 100 * delta);
			
			boolean spriteCol = false;
			for (MovingNPC n : movingNPCs) {
				if(HitBox.interact(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.RIGHT)) {
					spriteCol = true;
					break;
				}
			}
			if(! spriteCol) {
				for (StillNPC n : stillNPCs) {
					if(HitBox.interact(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.RIGHT)) {
						spriteCol = true;
						break;
					}
				}
			}
			
			if (tileCollide(1, 0) || spriteCol) {
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
	
	public boolean tileCollide(int xdir, int ydir) {
		float tileW = colLayer.getTileWidth(), tileH = colLayer.getTileHeight();
		boolean colX = false, colY = false;
		
		//collide left
		if (xdir < 0) {
			//top left
			colX = isTileBlocked((getX() / tileW), ((getY() + getHeight()) / tileH)); 
			//middle left
			if (! colX) colX = isTileBlocked((getX() / tileW), ((getY() + getHeight() / 2) / tileH));
			//bottom left
			if (! colX) colX = isTileBlocked((getX() / tileW), (getY() / tileH));
		
		//collide right
		} else if (xdir > 0) {
			//top right
			colX = isTileBlocked(((getX() + getWidth()) / tileW), ((getY() + getHeight()) / tileH));
			//middle right
			if (! colX) colX = isTileBlocked(((getX() + getWidth()) / tileW), ((getY() + getHeight() / 2) / tileH));
			//bottom right
			if (! colX) colX = isTileBlocked(((getX() + getWidth()) / tileW), (getY()  / tileH));
		}
		
		//collide down
		if (ydir < 0) {
			//bottom left
			colY = isTileBlocked((getX() / tileW), (getY()  / tileH));
			//bottom middle
			if (! colY) colY = isTileBlocked(((getX() + getWidth() / 2) / tileW), (getY()  / tileH));
			//bottom right
			if (! colY) colY = isTileBlocked(((getX() + getWidth()) / tileW), (getY()  / tileH));
		
		//collide top
		} else if (ydir > 0) {
			//top left
			colY = isTileBlocked((getX() / tileW), ((getY() + getHeight())  / tileH));
			//top middle
			if (! colY) colY = isTileBlocked(((getX() + getWidth() / 2) / tileW), ((getY() + getHeight())  / tileH));
			//top right
			if (! colY) colY = isTileBlocked(((getX() + getWidth()) / tileW), ((getY() + getHeight())  / tileH));
		}
		
		return colX || colY;
	}
	
	public boolean isTileBlocked(float x, float y) {
		boolean col = colLayer.getCell((int) x, (int) y).getTile().getProperties().containsKey("blocked");
		return col;
	}
	
	

}
