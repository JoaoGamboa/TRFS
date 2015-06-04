package com.TRFS.simulator;

import com.TRFS.screens.Settings;
import com.TRFS.screens.SplashScreen;
import com.badlogic.gdx.Game;

/**
 * @author jgamboa
 *
 */
public class Main extends Game {
	
	//Name & Version
	public static final String TITLE = "TRFS", VERSION = "0.1.3";
		
	@Override
	public void create () {
		
		//Apply user settings to Desktop App
		PreferencesManager.checkUserPrefs();
		Settings.setVSync();
		Settings.setScreenSize(); //If necessary

		//Initiate Splash Screen
		AssetsMan.loadSplashQueue();
		AssetsMan.manager.finishLoading();
		setScreen(new SplashScreen());
								
	}
	
	@Override
	
	public void render () {
		super.render();
		
	}
		
	public void dispose() {
		super.dispose();
		AssetsMan.dispose();
	}	
	public void resize(int width, int height) {
		super.resize(width, height);
	}	
	@Override
	public void pause() {
		super.pause();
	}
	@Override
	public void resume() {
		super.resume();
	}

}
