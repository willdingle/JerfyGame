package com.willdingle.jerfygame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.willdingle.jerfygame.HitBox;
import com.willdingle.jerfygame.items.Bullet;
import com.willdingle.jerfygame.items.Sword;

public class Player extends Sprite {
	
	private TiledMapTileLayer colLayer;
	private Texture[] upWalk = new Texture[2];
	private Texture[] downWalk = new Texture[2];
	private Texture[] leftWalk = new Texture[2];
	private Texture[] rightWalk = new Texture[2];
	private float elTimeF;
	private int elTime;
	
	private char dir;
	
	private boolean rangedAllowed;
	private boolean meleeAllowed;
	
	private int meleeRange;
	
	private int health;
	
	//Inventory format: [item name, stat (defence or attack number)]
	public String[][] inv;
	private int equippedWeapon;
	private int money;
	public Bullet[] bullets;
	public Sword sword;

	public Player(TiledMapTileLayer colLayer, float x, float y, String[] inv) {
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
				
		if(inv != null) {
			this.inv = new String[inv.length / 2][2];
			int itemStatIndex = 0;
			for(int n = 0; n < (inv.length / 2); n++) {
				this.inv[n][0] = inv[itemStatIndex];
				this.inv[n][1] = inv[itemStatIndex + 1];
				itemStatIndex += 2;
			}
			
		} else {
			this.inv = new String[0][0];
		}
		
		setMoney(0);
		setHealth(3);
		
		bullets = new Bullet[0];
		
		setRangedAllowed(false);
		setMeleeAllowed(false);
		for(String[] n : this.inv) {
			if(n[0].equals("gun")) setRangedAllowed(true);
			else if(n[0].equals("sword")) setMeleeAllowed(true);
		}
		setEquippedWeapon(-1);
	}
	
	public void draw(Batch batch) {
		if(bullets.length > 0) {
			for(Bullet bullet : bullets) {
				bullet.draw(batch, Gdx.graphics.getDeltaTime());
			}
		}
		if(sword != null) {
			sword.draw(batch, getX(), getY(), getWidth(), getHeight());
			sword.setTimer(sword.getTimer() - Gdx.graphics.getDeltaTime());
			if(sword.getTimer() < 0) sword = null;
		}
		super.draw(batch);
	}
	
	public void addToInventory(String item, String stat) {
		if(inv.length == 0) inv = new String[1][2];
		else {
			String[][] tmpInv = inv;
			inv = new String[inv.length + 1][2];
			for(int n = 0; n < tmpInv.length; n++) {
				inv[n][0] = tmpInv[n][0];
				inv[n][1] = tmpInv[n][1];
			}
		}
		
		inv[inv.length - 1][0] = item;
		inv[inv.length - 1][1] = stat;
	}
	
	public void rangedAttack(char dir) {
		Bullet bullet = new Bullet(colLayer, getX(), getY(), getWidth(), getHeight(), dir);

		if (bullets.length == 0) {
			bullets = new Bullet[1];
			bullets[0] = bullet;
		} else {
			Bullet[] tmpBullets = bullets;
			bullets = new Bullet[bullets.length + 1];
			for (int n = 0; n < tmpBullets.length; n++) {
				bullets[n] = tmpBullets[n];
			}
			bullets[bullets.length - 1] = bullet;
		}
	}
	
	public void meleeAttack(char dir) {
		sword = new Sword(dir);
	}
	
	public void move(float delta, MovingNPC movingNPCs[], StillNPC stillNPCs[]) {
		if(equippedWeapon != -1) {
			String equippedWeaponStr = inv[equippedWeapon][0];
			switch(equippedWeaponStr) {
			case "gun":
				if(rangedAllowed) {
					if (Gdx.input.isKeyJustPressed(Keys.LEFT)) rangedAttack('l');
					else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) rangedAttack('r');
					else if (Gdx.input.isKeyJustPressed(Keys.UP)) rangedAttack('u');
					else if (Gdx.input.isKeyJustPressed(Keys.DOWN)) rangedAttack('d');
				}
				break;
				
			case "sword":
				if(meleeAllowed) {
					if (Gdx.input.isKeyJustPressed(Keys.LEFT)) meleeAttack('l');
					else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) meleeAttack('r');
					else if (Gdx.input.isKeyJustPressed(Keys.UP)) meleeAttack('u');
					else if (Gdx.input.isKeyJustPressed(Keys.DOWN)) meleeAttack('d');
				}
				break;
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			setDir('w');
			float oldY = getY();
			setY(getY() + 100 * delta);
			
			boolean spriteCol = false;
			if(movingNPCs.length > 0) {
				for (MovingNPC n : movingNPCs) {
					if(HitBox.player(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.UP, HitBox.COLLIDE)) {
						spriteCol = true;
						break;
					}
				}
			}
			if(! spriteCol && stillNPCs.length > 0) {
				for (StillNPC n : stillNPCs) {
					if(HitBox.player(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.UP, HitBox.COLLIDE)) {
						spriteCol = true;
						break;
					}
				}
			}
			
			if (tileCollide(0, 1) || spriteCol || getY() + getHeight() >= colLayer.getHeight() * colLayer.getTileHeight() - 1) {
				setY(oldY);
			}
			animate("w");
			
		}
		if (Gdx.input.isKeyPressed(Keys.S) && getY() >= 2) {
			setDir('s');
			float oldY = getY();
			setY(getY() - 100 * delta);
			
			boolean spriteCol = false;
			if(movingNPCs.length > 0) {
				for (MovingNPC n : movingNPCs) {
					if(HitBox.player(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.DOWN, HitBox.COLLIDE)) {
						spriteCol = true;
						break;
					}
				}
			}
			if(! spriteCol && stillNPCs.length > 0) {
				for (StillNPC n : stillNPCs) {
					if(HitBox.player(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.DOWN, HitBox.COLLIDE)) {
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
		if (Gdx.input.isKeyPressed(Keys.A) && getX() >= 2) {
			setDir('a');
			float oldX = getX();
			setX(getX() - 100 * delta);
			
			boolean spriteCol = false;
			if(movingNPCs.length > 0) {
				for (MovingNPC n : movingNPCs) {
					if(HitBox.player(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.LEFT, HitBox.COLLIDE)) {
						spriteCol = true;
						break;
					}
				}
			}
			if(! spriteCol && stillNPCs.length > 0) {
				for (StillNPC n : stillNPCs) {
					if(HitBox.player(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.LEFT, HitBox.COLLIDE)) {
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
		if (Gdx.input.isKeyPressed(Keys.D) && getX() + getWidth() <= (colLayer.getWidth() * colLayer.getTileWidth()) - 2) {
			setDir('d');
			float oldX = getX();
			setX(getX() + 100 * delta);
			
			boolean spriteCol = false;
			if(movingNPCs.length > 0) {
				for (MovingNPC n : movingNPCs) {
					if(HitBox.player(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.RIGHT, HitBox.COLLIDE)) {
						spriteCol = true;
						break;
					}
				}
			}
			if(! spriteCol && stillNPCs.length > 0) {
				for (StillNPC n : stillNPCs) {
					if(HitBox.player(this, n.getX(), n.getY(), n.getWidth(), n.getHeight(), HitBox.RIGHT, HitBox.COLLIDE)) {
						spriteCol = true;
						break;
					}
				}
			}
			
			if (tileCollide(1, 0) || spriteCol || getX() + getWidth() >= (colLayer.getWidth() * colLayer.getTileWidth()) - 1) {
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
			colX = isTileBlocked((getX()) / tileW, (getY() + getHeight()) / tileH); 
			//middle left
			if (! colX) colX = isTileBlocked((getX()) / tileW, (getY() + getHeight() / 2) / tileH);
			//bottom left
			if (! colX) colX = isTileBlocked((getX()) / tileW, getY() / tileH);
		
		//collide right
		} else if (xdir > 0) {
			//top right
			colX = isTileBlocked((getX() + getWidth()) / tileW, (getY() + getHeight()) / tileH);
			//middle right
			if (! colX) colX = isTileBlocked((getX() + getWidth()) / tileW, (getY() + getHeight() / 2) / tileH);
			//bottom right
			if (! colX) colX = isTileBlocked((getX() + getWidth()) / tileW, getY() / tileH);
		}
		
		//collide down
		if (ydir < 0) {
			//bottom left
			colY = isTileBlocked(getX() / tileW, getY() / tileH);
			//bottom middle
			if (! colY) colY = isTileBlocked((getX() + getWidth() / 2) / tileW, getY() / tileH);
			//bottom right
			if (! colY) colY = isTileBlocked((getX() + getWidth()) / tileW, getY() / tileH);
		
		//collide top
		} else if (ydir > 0) {
			//top left
			colY = isTileBlocked(getX() / tileW, (getY() + getHeight()) / tileH);
			//top middle
			if (! colY) colY = isTileBlocked((getX() + getWidth() / 2) / tileW, (getY() + getHeight()) / tileH);
			//top right
			if (! colY) colY = isTileBlocked((getX() + getWidth()) / tileW, (getY() + getHeight()) / tileH);
		}
		
		return colX || colY;
	}
	
	public boolean isTileBlocked(float x, float y) {
		boolean col = colLayer.getCell((int) x, (int) y).getTile().getProperties().containsKey("blocked");
		return col;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public void setColLayer(TiledMapTileLayer colLayer) {
		this.colLayer = colLayer;
	}

	public boolean isRangedAllowed() {
		return rangedAllowed;
	}

	public void setRangedAllowed(boolean attackAllowed) {
		this.rangedAllowed = attackAllowed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isMeleeAllowed() {
		return meleeAllowed;
	}

	public void setMeleeAllowed(boolean meleeAllowed) {
		this.meleeAllowed = meleeAllowed;
	}

	public int getEquippedWeapon() {
		return equippedWeapon;
	}

	public void setEquippedWeapon(int equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}

	public char getDir() {
		return dir;
	}

	public void setDir(char dir) {
		this.dir = dir;
	}
}
