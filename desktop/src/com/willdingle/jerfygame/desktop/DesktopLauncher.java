package com.willdingle.jerfygame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.willdingle.jerfygame.JerfyGame;
import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		//cfg.width = (int) size.getWidth();
		//cfg.height = (int) size.getHeight();
		cfg.width = 1920;
		cfg.height = 1080;
		cfg.fullscreen = true;
		cfg.vSyncEnabled = true;
		cfg.foregroundFPS = 75;
		
		new LwjglApplication(new JerfyGame(), cfg);
	}
}
