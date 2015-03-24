package com.TRFS.simulator;

import com.TRFS.scenarios.ScenariosManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetsMan {
	
	public static final AssetManager manager = new AssetManager();
	
	public static final String TRFSLogo = "img/TRFSlogo.png";
	
	public static final String smallFont = "font/segoewhiteSmall.fnt", 
							   mediumFont = "font/segoewhiteMedium.fnt", 
							   largeFont = "font/segoewhiteLarge.fnt",
							   mainButtonFont = "font/segoewhiteMedium.fnt";

	public static final String atlas = "ui/ui-gray.atlas";
		
	public static Skin uiSkin;
	
	public static void loadSplashQueue() {
		manager.load(TRFSLogo, Texture.class);
	}
	
	public static void loadQueue() {	
		//UI
		manager.load(atlas, TextureAtlas.class);
		//Fonts
		manager.load(smallFont, BitmapFont.class);
		manager.load(mediumFont, BitmapFont.class);
		manager.load(largeFont, BitmapFont.class);
		manager.load(mainButtonFont, BitmapFont.class);
	}

	public static void setMenuSkin() {
        if (uiSkin == null)
            uiSkin = new Skin(Gdx.files.internal("ui/uiSkin.json"), 
            		manager.get(atlas, TextureAtlas.class));
	}
	public static boolean update(){
		return manager.update();
	}
	
	public static void dispose() {
		manager.dispose();
		uiSkin.dispose();
	}
	
	public static void setup() {
		setMenuSkin();
		ScenariosManager.listScenarios();
	}
}
