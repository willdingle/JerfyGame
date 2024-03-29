package com.willdingle.jerfygame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.willdingle.jerfygame.JerfyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		cfg.width = 1920;
		cfg.height = 1080;
		cfg.fullscreen = true;
		cfg.vSyncEnabled = true;
		cfg.foregroundFPS = 120;
		cfg.addIcon("icon.png", Files.FileType.Internal);
		
		new LwjglApplication(new JerfyGame(), cfg);
	}
}
