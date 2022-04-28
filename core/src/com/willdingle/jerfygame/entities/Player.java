package com.willdingle.jerfygame.entities;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.willdingle.jerfygame.HitBox;
import com.willdingle.jerfygame.JerfyGame;
import com.willdingle.jerfygame.files.Save;
import com.willdingle.jerfygame.items.Bullet;

public class Player extends Sprite {
	
	private TiledMapTileLayer colLayer;
	private Texture[] upWalk = new Texture[2];
	private Texture[] downWalk = new Texture[2];
	private Texture[] leftWalk = new Texture[2];
	private Texture[] rightWalk = new Texture[2];
	private float elTimeF;
	private int elTime;
	
	private boolean rangedAllowed;
	
	private int speed;
	private float health;
	private float invinc;
	private float defence;
	
	//Inventory format: [item name, stat (defence or attack number)]
	public String[][] inv;
	private int equippedWeapon;
	private int money;
	public Bullet[] bullets;

	public Player(TiledMapTileLayer colLayer, float x, float y, String[][] inv) {
		super(new Sprite(new Texture("jerfy/down.png")));
		this.colLayer = colLayer;
		setX(x * 16);
		setY(y * 16);
		
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
				
		if(inv != null) this.inv = inv;
		else this.inv = new String[0][0];
		
		setSpeed(100);
		setMoney(0);
		setHealth(3);
		setDefence(0);
		
		bullets = new Bullet[0];
		
		setRangedAllowed(false);
		for(String[] n : this.inv) {
			if(n[0].equals("gun")) setRangedAllowed(true);
			else if(n[0].equals("helmet")) setDefence(Float.valueOf(n[1]));
		}
		setEquippedWeapon(-1);
	}
	
	public void draw(Batch batch, Enemy[] enemies) {
		if(bullets.length > 0) {
			for(int n = 0; n < bullets.length; n++) {
				if(bullets[n] != null) {
					if(bullets[n].draw(batch, Gdx.graphics.getDeltaTime(), enemies)) bullets[n] = null;
				}
			}
		}
		
		if(getInvinc() > 0) setInvinc(getInvinc() - Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}
	
	public void hit(float damage) {
		setHealth(getHealth() - damage + getDefence());
		setInvinc(1);
	}
	
	public void addToInventory(String item, String stat, JerfyGame game) {
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
		Save.write(new File(System.getenv("appdata") + "/Jerfy/" + game.fileName), "inv", this);
	}
	
	public boolean inventoryContains(String item) {
		boolean found = false;
		for(String[] n : inv) {
			if(n[0].equals(item)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	public void rangedAttack(char dir) {
		Bullet bullet = new Bullet(colLayer, getX(), getY(), getWidth(), getHeight(), dir, inv[getGunIndex()][1]);

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
	
	public void move(float delta, MovingNPC movingNPCs[], StillNPC stillNPCs[], Enemy[] enemies) {
		if(equippedWeapon == getGunIndex()) {
			if (Gdx.input.isKeyJustPressed(Keys.LEFT)) rangedAttack('l');
			else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) rangedAttack('r');
			else if (Gdx.input.isKeyJustPressed(Keys.UP)) rangedAttack('u');
			else if (Gdx.input.isKeyJustPressed(Keys.DOWN)) rangedAttack('d');
		}
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			float oldY = getY();
			setY(getY() + speed * delta);
			
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
			if(! spriteCol && enemies != null) {
				if(enemies.length > 0) {
					for(int n = 0; n < enemies.length; n++) {
						if(enemies[n] != null) {
							if(HitBox.player(this, enemies[n].getX(), enemies[n].getY(), enemies[n].getWidth(), enemies[n].getHeight(), HitBox.UP, HitBox.COLLIDE)) {
								spriteCol = true;
								break;
							}
						}
					}
				}
			}
			if (tileCollide('w') || spriteCol || getY() + getHeight() >= colLayer.getHeight() * colLayer.getTileHeight() - 1) {
				setY(oldY);
			}
			animate("w");
			
		}
		if (Gdx.input.isKeyPressed(Keys.S) && getY() >= 2) {
			float oldY = getY();
			setY(getY() - speed * delta);
			
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
			if(! spriteCol && enemies != null) {
				if(enemies.length > 0) {
					for(int n = 0; n < enemies.length; n++) {
						if(enemies[n] != null) {
							if(HitBox.player(this, enemies[n].getX(), enemies[n].getY(), enemies[n].getWidth(), enemies[n].getHeight(), HitBox.DOWN, HitBox.COLLIDE)) {
								spriteCol = true;
								break;
							}
						}
					}
				}
			}
			
			if (tileCollide('a') || spriteCol) {
				setY(oldY);
			}
			animate("s");
			
		}
		if (Gdx.input.isKeyPressed(Keys.A) && getX() >= 2) {
			float oldX = getX();
			setX(getX() - speed * delta);
			
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
			if(! spriteCol && enemies != null) {
				if(enemies.length > 0) {
					for(int n = 0; n < enemies.length; n++) {
						if(enemies[n] != null) {
							if(HitBox.player(this, enemies[n].getX(), enemies[n].getY(), enemies[n].getWidth(), enemies[n].getHeight(), HitBox.LEFT, HitBox.COLLIDE)) {
								spriteCol = true;
								break;
							}
						}
					}
				}
			}
			
			if (tileCollide('a') || spriteCol) {
				setX(oldX);
			}
			animate("a");
			
		}
		if (Gdx.input.isKeyPressed(Keys.D) && getX() + getWidth() <= (colLayer.getWidth() * colLayer.getTileWidth()) - 2) {
			float oldX = getX();
			setX(getX() + speed * delta);
			
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
			if(! spriteCol && enemies != null) {
				if(enemies.length > 0) {
					for(int n = 0; n < enemies.length; n++) {
						if(enemies[n] != null) {
							if(HitBox.player(this, enemies[n].getX(), enemies[n].getY(), enemies[n].getWidth(), enemies[n].getHeight(), HitBox.RIGHT, HitBox.COLLIDE)) {
								spriteCol = true;
								break;
							}
						}
					}
				}
			}
			
			if (tileCollide('d') || spriteCol || getX() + getWidth() >= (colLayer.getWidth() * colLayer.getTileWidth()) - 1) {
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
	
	private boolean tileCollide(char dir) {
		float tileW = colLayer.getTileWidth(), tileH = colLayer.getTileHeight();
		boolean col = false;
		
		//collide left
		switch(dir) {
		case 'a':
			//top left
			col = isTileBlocked((getX()) / tileW, (getY() + getHeight()) / tileH); 
			//middle left
			if (! col) col = isTileBlocked((getX()) / tileW, (getY() + getHeight() / 2) / tileH);
			//bottom left
			if (! col) col = isTileBlocked((getX()) / tileW, getY() / tileH);
			break;
			
		//collide right
		case 'd':
			//top right
			col = isTileBlocked((getX() + getWidth()) / tileW, (getY() + getHeight()) / tileH);
			//middle right
			if (! col) col = isTileBlocked((getX() + getWidth()) / tileW, (getY() + getHeight() / 2) / tileH);
			//bottom right
			if (! col) col = isTileBlocked((getX() + getWidth()) / tileW, getY() / tileH);
			break;
		
		//collide down
		case 's':
			//bottom left
			col = isTileBlocked(getX() / tileW, getY() / tileH);
			//bottom middle
			if (! col) col = isTileBlocked((getX() + getWidth() / 2) / tileW, getY() / tileH);
			//bottom right
			if (! col) col = isTileBlocked((getX() + getWidth()) / tileW, getY() / tileH);
			break;
			
		//collide up
		case 'w':
			//top left
			col = isTileBlocked(getX() / tileW, (getY() + getHeight()) / tileH);
			//top middle
			if (! col) col = isTileBlocked((getX() + getWidth() / 2) / tileW, (getY() + getHeight()) / tileH);
			//top right
			if (! col) col = isTileBlocked((getX() + getWidth()) / tileW, (getY() + getHeight()) / tileH);
			break;
		}
		
		return col;
	}
	
	public boolean isTileBlocked(float x, float y) {
		boolean col = colLayer.getCell((int) x, (int) y).getTile().getProperties().containsKey("blocked");
		return col;
	}
	
	public int getGunIndex() {
		int index = -1;
		for(int n = 0; n < inv.length; n++) {
			if(inv[n][0].equals("gun")) {
				index = n;
				break;
			}
		}
		return index;
	}
	
	public int getHelmetIndex() {
		int index = -1;
		for(int n = 0; n < inv.length; n++) {
			if(inv[n][0].equals("helmet")) {
				index = n;
				break;
			}
		}
		return index;
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

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public int getEquippedWeapon() {
		return equippedWeapon;
	}

	public void setEquippedWeapon(int equippedWeapon) {
		this.equippedWeapon = equippedWeapon;
	}

	public float getInvinc() {
		return invinc;
	}

	public void setInvinc(float invinc) {
		this.invinc = invinc;
	}

	public float getDefence() {
		return defence;
	}

	public void setDefence(float defence) {
		this.defence = defence;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
