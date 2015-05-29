package com.TRFS.vehicles;

import com.TRFS.simulator.AssetsMan;
import com.badlogic.gdx.graphics.Color;

public class Truck extends Vehicle{

	public Truck() {
		super(2.5f, 12f, 5000, Color.WHITE, AssetsMan.truckTexture);
	}
	
}
