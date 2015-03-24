package com.TRFS.simulator;

import com.TRFS.models.carFollowing.W74CarFollowing;
import com.badlogic.gdx.Gdx;

/**
 * @author jgamboa
 *
 */

public class PreferencesManager {
	
	private static float currentFileVersion = 7;
	private static boolean firstRun;
	
	// USER PREFERENCES --------------------
	//Populates the preferences file with the default values if it's the first run
	public static void checkUserPrefs () {
																			
		if (Gdx.app.getPreferences(Main.TITLE).contains("currentFileVersion")) {
																						
			if (getPreferenceFloat("currentFileVersion") != currentFileVersion) {																
				Gdx.app.getPreferences(Main.TITLE).clear();
				firstRun = true;
				
			} else {
				firstRun = false;
			}
			
		} else {
			Gdx.app.getPreferences(Main.TITLE).clear();
			firstRun = true;
		}
		
		if (firstRun) {
		//Use defaults
			setPreference("currentFileVersion", currentFileVersion);
			//Default Preferences
			//Settings
			setPreference("vSync", true);
			setPreference("fullScreen", false);
			setPreference("FPS", false);
			
			//Start Menu
			setPreference("desFlow", 600);
			setPreference("desVel", 50);
			setPreference("maxVel", 90);
			setPreference("trucks", true);
			setPreference("map", 1);
			setPreference("carfol", "Wiedemann '74");
			setPreference("lanechange", "Wiedemann '74");
			setPreference("originLatitude", 39.694502f);
			setPreference("originLongitude", -8.130573f);
			setPreference("smoothGeometry", true);
			setPreference("simplifyGeometry", true);
			setPreference("drawDebug", true);
			
			//World
			
			//W74
			setPreferencesDefaultCarFollowingParams();
		}
		
		presetUserPrefs();
		
		Gdx.app.getPreferences(Main.TITLE).flush();
	}
	
	public static void presetUserPrefs () {
		SimulationParameters.drawDebug = getDrawDebug();
	}
	
	//Getters for user preferences
	public static boolean getVSyncOn() {
		return getPreferenceBoolean("vSync");
	}
	public static boolean getFullScreenOn() {
		return getPreferenceBoolean("fullScreen");
	}
	public static boolean getFPSOn() {
		return getPreferenceBoolean("FPS");
	}
	
	public static float getFlow() {
		return getPreferenceFloat("desFlow");
	}
	
	public static float getDesVel() {
		return getPreferenceFloat("desVel");
	}
	
	public static float getMaxVel() {
		return getPreferenceFloat("maxVel");
	}
	public static boolean getTruckPref() {
		return getPreferenceBoolean("trucks");
	}
	public static float getMapIndex() {
		return getPreferenceFloat("map");
	}
	public static String getCarFolModel() {
		return getPreferenceString("carfol");
	}
	public static String getLaneChangeModel() {
		return getPreferenceString("lanechange");
	}
	public static boolean getSmoothGeometry() {
		return getPreferenceBoolean("smoothGeometry");
	}
	public static boolean getSimplifyGeometry() {
		return getPreferenceBoolean("simplifyGeometry");
	}
	public static boolean getDrawDebug() {
		return getPreferenceBoolean("drawDebug");
	}
	public static boolean getStatsVisible() {
		return getPreferenceBoolean("statsVisible");
	}
	public static boolean getModelParamsVisible() {
		return getPreferenceBoolean("modelParamsVisible");
	}
	public static boolean getSimulParamsVisible() {
		return getPreferenceBoolean("simulParamsVisible");
	}
	public static float getOriginLatitude() {
		return getPreferenceFloat("originLatitude");
	}	
	public static float getOriginLongitude() {
		return getPreferenceFloat("originLongitude");
	}	

	//Setters
	public static void setvSync(Boolean vSync) {
		setPreference("vSync", vSync);
	}
	
	public static void setFullScreen(Boolean fullScreen) {
		setPreference("fullScreen", fullScreen);
	}
	
	public static void setFPS(Boolean fps) {
		setPreference("FPS", fps);
	}
	
	public static void setFlow(float flow) {
		setPreference("desFlow", flow);
	}

	public static void setVel(float vel) {
		setPreference("desVel", vel);
	}
	
	public static void setMaxVel(float maxVel) {
		setPreference("maxVel", maxVel);
	}

	public static void setTrucks(Boolean trucks) {
		setPreference("trucks", trucks);
	}
	
	public static void setMapIndex(float mapIndex) {
		setPreference("map", mapIndex);
	}
	
	public static void setCarFolModel(String carFolModel) {
		setPreference("carfol", carFolModel);
	}
	
	public static void setLaneChangeModel(String laneChangeModel) {
		setPreference("lanechange", laneChangeModel);
	}
	public static void setSmoothGeometry(Boolean smoothGeometry) {
		setPreference("smoothGeometry", smoothGeometry);
	}
	public static void setSimplifyGeometry(Boolean simplifyGeometry) {
		setPreference("simplifyGeometry", simplifyGeometry);
	}
	public static void setDrawDebug(Boolean drawDebug) {
		setPreference("drawDebug", drawDebug);
	}
	public static void setOriginLatitude(float originLatitude) {
		setPreference("originLatitude", originLatitude);
	}
	public static void setOriginLongitude(float originLongitude) {
		setPreference("originLongitude", originLongitude);
	}
	public static void setCalibrationConstants() {
		for (int i = 0; i < W74CarFollowing.calibrationParameters.length; i++) {
			setPreference(W74CarFollowing.calibrationParameters[i].getName(), W74CarFollowing.calibrationParameters[i].getCurrentVal());
		}
	}
	
	public static float getPreferenceFloat(String name) {
		return Gdx.app.getPreferences(Main.TITLE).getFloat(name);
	}
	
	public static String getPreferenceString(String name) {
		return Gdx.app.getPreferences(Main.TITLE).getString(name);
	}
	public static boolean getPreferenceBoolean(String name) {
		return Gdx.app.getPreferences(Main.TITLE).getBoolean(name);
	}
	public static long getPreferenceLong(String name) {
		return Gdx.app.getPreferences(Main.TITLE).getLong(name);
	}
	
	public static void setPreference(String name, String string) {
		Gdx.app.getPreferences(Main.TITLE).putString(name, string);
	}
	
	public static void setPreference(String name, boolean bool) {
		Gdx.app.getPreferences(Main.TITLE).putBoolean(name, bool);
	}
	
	public static void setPreference(String name, float value) {
		Gdx.app.getPreferences(Main.TITLE).putFloat(name, value);
	}
	
	public static void setPreference(String name, long value) {
		Gdx.app.getPreferences(Main.TITLE).putLong(name, value);
	}
	
	private static void setPreferencesDefaultCarFollowingParams() {
		for (int i = 0; i < W74CarFollowing.calibrationParameters.length; i++) {
			setPreference(W74CarFollowing.calibrationParameters[i].getName(), W74CarFollowing.calibrationParameters[i].getDefaultVal());
		}
	}
}
