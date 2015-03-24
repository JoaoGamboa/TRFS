package com.TRFS.simulator.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ScreensManager {
	
	private static boolean worldRunning = false;
	
	private static Screen splashScreen;
	private static Screen mainMenu;
	private static Screen settings;
	private static Screen startMenu;
	private static Screen aboutScreen;
	private static Screen worldScreen;
	
	public static void swapScreen(String string, Stage stage, boolean newScreen) {
		
		final Screen screen = screenBuilder(string, newScreen);
		
		stage.addAction(Actions.sequence(Actions.fadeOut(0.2f), Actions.run(new Runnable() {
			@Override	
			public void run() {
				((Game) Gdx.app.getApplicationListener()).setScreen(screen);	} })));	
	}
	
	//UPDATE WITH NEW SCREENS
	private static Screen screenBuilder(String string, boolean newScreen) {
		
		if (string.equals("SplashScreen"))	return setSplashScreen(new SplashScreen());
		if (string.equals("MainMenu")) 		return setMainMenu( new MainMenu());
		if (string.equals("Settings")) 		return setSettings( new Settings());
		if (string.equals("StartMenu")) 	return setStartMenu( new StartMenu());
		if (string.equals("AboutScreen")) 	return setAboutScreen( new AboutScreen());
		if (string.equals("WorldScreen")) {
			if (newScreen) { 
				worldRunning = true;
				return setWorldScreen( new WorldScreen());
			} else if (!newScreen) {
				//Keep simulation running
				worldRunning = true;
				return setWorldScreen(new WorldScreen());
			}
		}
		
		return null;
	}
	
	
	public static boolean isWorldRunning() {
		return worldRunning;
	}

	public static void setWorldRunning(boolean worldRunning) {
		ScreensManager.worldRunning = worldRunning;
	}

	public static Screen getWorldScreen() {
		return worldScreen;
	}

	public static Screen setWorldScreen(Screen worldScreen) {
		ScreensManager.worldScreen = worldScreen;
		return worldScreen;
	}

	public static Screen getSplashScreen() {
		return splashScreen;
	}

	public static Screen setSplashScreen(Screen splashScreen) {
		return ScreensManager.splashScreen = splashScreen;
	}

	public static Screen getMainMenu() {
		return mainMenu;
	}

	public static Screen setMainMenu(Screen mainMenu) {
		return ScreensManager.mainMenu = mainMenu;
	}

	public static Screen getSettings() {
		return settings;
	}

	public static Screen setSettings(Screen settings) {
		return ScreensManager.settings = settings;
	}

	public static Screen getStartMenu() {
		return startMenu;
	}

	public static Screen setStartMenu(Screen startMenu) {
		return ScreensManager.startMenu = startMenu;
	}

	public static Screen getAboutScreen() {
		return aboutScreen;
	}

	public static Screen setAboutScreen(Screen aboutScreen) {
		return ScreensManager.aboutScreen = aboutScreen;
	}
}
