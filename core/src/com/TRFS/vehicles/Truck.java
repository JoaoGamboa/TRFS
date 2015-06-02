package com.TRFS.vehicles;

import com.TRFS.simulator.AssetsMan;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Truck extends Vehicle{
	
	private TextureRegion truckTexture, leftBlinkerTexture, rightBlinkerTexture, redGlowTexture, whiteGlowTexture;

	public Truck() {
		super(2.5f, 12f, 12000, Color.WHITE);
		
		this.truckTexture = AssetsMan.truckTexture;
		this.leftBlinkerTexture = AssetsMan.truckLeftBlinker;
		this.rightBlinkerTexture = AssetsMan.truckRightBlinker;
		this.redGlowTexture = AssetsMan.truckRedGlow;
		this.whiteGlowTexture = AssetsMan.truckWhiteGlow;
	}
	
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch, truckTexture, leftBlinkerTexture, rightBlinkerTexture, redGlowTexture, whiteGlowTexture);
	}
	
}
