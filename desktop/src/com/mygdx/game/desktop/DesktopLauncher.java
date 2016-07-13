package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BulletGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.foregroundFPS = 120;
		config.backgroundFPS = 120;
		config.width = 1920;
		config.height = 1080;
		//config.fullscreen = true;
		new LwjglApplication(new BulletGame(), config);
	}
}
