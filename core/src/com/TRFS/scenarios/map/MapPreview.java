package com.TRFS.scenarios.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MapPreview {

	public String name;
	public FileHandle fileHandle;
	public boolean fromJSON;
	public Image image;
	
	public MapPreview(String name, String fileName, boolean fromJSON) {
		this.name = name;
		this.fileHandle = Gdx.files.internal("scenarios/" + fileName);
		this.fromJSON = fromJSON;
	}

	public Image getImage() {
		if (this.image == null) {
			String imagePath = "scenarios/"+ this.name +".png";
			if (!Gdx.files.internal(imagePath).exists())  imagePath = "scenarios/default.png"; 
			this.image = new Image( new Texture(Gdx.files.internal(imagePath)));
		}
		return image;
	}
	
}