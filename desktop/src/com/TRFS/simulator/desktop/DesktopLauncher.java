package com.TRFS.simulator.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.TRFS.simulator.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Main.TITLE + " " + Main.VERSION;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		//config.useGL30 = true;
		config.width = 1280;
		config.height = 720;
		config.addIcon("img/logos/TRFSLogo128.png", Files.FileType.Internal);
		config.addIcon("img/logos/TRFSLogo32.png", Files.FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}