package com.TRFS.simulator;

import com.TRFS.scenarios.ScenariosManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetsMan {
	
	public static final AssetManager manager = new AssetManager();
	
	//Logos
	public static final String TRFSLogo = "img/logos/TRFSlogo.png";
	public static final String TRFSAbout = "img/logos/TRFSAbout.png";
	public static final String TRFSLogoTopBar = "img/logos/TRFSLogo54x58.png";
	public static final String LibGDXLogo = "img/logos/LibGDXLogo.png";
	public static final String ISELLogo = "img/logos/LogoISEL.png";
	public static final String ISELAbout = "img/logos/ISELAbout.png";
	//Font
	public static final String smallFont = "font/segoewhiteSmall.fnt", 
							   mediumFont = "font/segoewhiteMedium.fnt", 
							   largeFont = "font/segoewhiteLarge.fnt",
							   mainButtonFont = "font/segoewhiteMedium.fnt";
	//Vehicles
	public static final String carTexturePath = "img/vehicles/car/Car.png",
							   carLeftBlinkerPath = "img/vehicles/car/LeftBlinkers.png",
							   carRightBlinkerPath = "img/vehicles/car/RightBlinkers.png",
							   carRedGlowPath = "img/vehicles/car/RedGlow.png",
							   carWhiteGlowPath = "img/vehicles/car/WhiteGlow.png",
							   truckTexturePath = "img/vehicles/truck/Truck.png",
							   truckLeftBlinkerPath = "img/vehicles/truck/LeftBlinkers.png",
							   truckRightBlinkerPath = "img/vehicles/truck/RightBlinkers.png",
							   truckRedGlowPath = "img/vehicles/truck/RedGlow.png",
							   truckWhiteGlowPath = "img/vehicles/truck/WhiteGlow.png";
	
	public static TextureRegion carTexture, carLeftBlinker, carRightBlinker, carRedGlow, carWhiteGlow, truckTexture, 
								truckLeftBlinker, truckRightBlinker, truckRedGlow, truckWhiteGlow;

	public static final String atlas = "ui/ui-gray.atlas";
		
	public static Skin uiSkin;
	
	public static void loadSplashQueue() {
		manager.load(TRFSLogo, Texture.class);
	}
	
	public static void loadQueue() {	
		//UI
		manager.load(atlas, TextureAtlas.class);
		manager.load(TRFSLogoTopBar, Texture.class);
		manager.load(ISELLogo, Texture.class);
		manager.load(LibGDXLogo, Texture.class);
		manager.load(ISELAbout, Texture.class);
		manager.load(TRFSAbout, Texture.class);

		//Fonts
		manager.load(smallFont, BitmapFont.class);
		manager.load(mediumFont, BitmapFont.class);
		manager.load(largeFont, BitmapFont.class);
		manager.load(mainButtonFont, BitmapFont.class);
		//Vehicles
		manager.load(carTexturePath, Texture.class);
		manager.load(carLeftBlinkerPath, Texture.class);
		manager.load(carRightBlinkerPath, Texture.class);
		manager.load(carRedGlowPath, Texture.class);
		manager.load(carWhiteGlowPath, Texture.class);
		manager.load(truckTexturePath, Texture.class);
		manager.load(truckLeftBlinkerPath, Texture.class);
		manager.load(truckRightBlinkerPath, Texture.class);
		manager.load(truckRedGlowPath, Texture.class);
		manager.load(truckWhiteGlowPath, Texture.class);
		
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
		
	public static void finalizeLoad() {
		setMenuSkin();
		ScenariosManager.listScenarios();
		
		carTexture = new TextureRegion(manager.get(carTexturePath, Texture.class));
		carLeftBlinker = new TextureRegion(manager.get(carLeftBlinkerPath, Texture.class));
		carRightBlinker = new TextureRegion(manager.get(carRightBlinkerPath, Texture.class));
		carRedGlow = new TextureRegion(manager.get(carRedGlowPath, Texture.class));
		carWhiteGlow = new TextureRegion(manager.get(carWhiteGlowPath, Texture.class));
		truckTexture = new TextureRegion(manager.get(truckTexturePath, Texture.class));
		truckLeftBlinker = new TextureRegion(manager.get(truckLeftBlinkerPath, Texture.class));
		truckRightBlinker = new TextureRegion(manager.get(truckRightBlinkerPath, Texture.class));
		truckRedGlow = new TextureRegion(manager.get(truckRedGlowPath, Texture.class));
		truckWhiteGlow = new TextureRegion(manager.get(truckWhiteGlowPath, Texture.class));
	}
}
