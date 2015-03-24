package com.TRFS.scenarios.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MapPreview {

	private String name;
	private FileHandle fileHandle;
	private boolean fromJSON;
	private Image image;
	
	public MapPreview(String name, String fileName, boolean fromJSON) {
		this.name = name;
		this.setFileHandle(Gdx.files.internal("scenarios/" + fileName));
		this.setFromJSON(fromJSON);
	}

	public Image getImage() {
		if (this.image == null) {
			String imagePath = "scenarios/"+ this.name +".png";
			if (!Gdx.files.internal(imagePath).exists())  imagePath = "scenarios/default.png"; 
			this.image = new Image( new Texture(Gdx.files.internal(imagePath)));
		}
		return image;
	}

	public FileHandle getFileHandle() {
		return fileHandle;
	}

	public void setFileHandle(FileHandle fileHandle) {
		this.fileHandle = fileHandle;
	}

	public boolean isFromJSON() {
		return fromJSON;
	}

	public void setFromJSON(boolean fromJSON) {
		this.fromJSON = fromJSON;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
}