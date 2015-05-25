package com.TRFS.scenarios;

import com.TRFS.scenarios.map.MapPreview;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class ScenariosManager {
	
	//Scenarios
	public static Array<MapPreview> maps = new Array<MapPreview>();
	public static Array<String> mapNames = new Array<String>();
	
	public static void listScenarios() {
		FileHandle[] files = null;
		
		if(Gdx.files.internal("./bin/scenarios/").list().length > 0) {
			files = Gdx.files.internal("./bin/scenarios/").list();
		} else {
			files = new FileHandle[2];
			FileHandle handle = Gdx.files.internal("scenarios/Cloverleaf Interchange.geojson");
			files[0] = handle;
			FileHandle handle2 = Gdx.files.internal("scenarios/Neighbourhood.geojson");
			files[1] = handle2;
		}
			
								
		for (int i = 0; i < files.length; i++) {
			
			if (files[i].extension().equals("geojson")) {
				
				String imagePath = "scenarios/"+ files[i].nameWithoutExtension() +".png";
				
				if (!Gdx.files.internal(imagePath).exists())  imagePath = "scenarios/default.png"; 
								
				maps.add(new MapPreview(files[i].nameWithoutExtension(), files[i].name(), true));
				mapNames.add(files[i].nameWithoutExtension());
			}
		}
	}
}
