package com.willdingle.jerfygame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.willdingle.jerfygame.JerfyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		cfg.width = 1920;
		cfg.height = 1080;
		cfg.fullscreen = false;
		cfg.vSyncEnabled = true;
		cfg.foregroundFPS = 120;
		
		new LwjglApplication(new JerfyGame(), cfg);
	}
}
