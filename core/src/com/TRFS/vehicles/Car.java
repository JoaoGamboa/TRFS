package com.TRFS.vehicles;

import com.TRFS.simulator.AssetsMan;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Car extends Vehicle {
	
	private TextureRegion carTexture, leftBlinkerTexture, rightBlinkerTexture, redGlowTexture, whiteGlowTexture;
			
	public Car() {
		super(2.5f, 4f, 2000, Color.WHITE);
		
		this.carTexture = AssetsMan.carTexture;
		this.leftBlinkerTexture = AssetsMan.carLeftBlinker;
		this.rightBlinkerTexture = AssetsMan.carRightBlinker;
		this.redGlowTexture = AssetsMan.carRedGlow;
		this.whiteGlowTexture = AssetsMan.carWhiteGlow;
	}
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch, carTexture, leftBlinkerTexture, rightBlinkerTexture, redGlowTexture, whiteGlowTexture);
	}
	
}
