package com.TRFS.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class VehicleInputProcessor {
	
	private static Vehicle cVehicle;
	private Vector2 tmp1;

	private static float accelMagnitude = 0, angularAccel = 0;
	private final static float maxAngularAccel = 60;
	
	public VehicleInputProcessor() {
		tmp1 = new Vector2();
	}
	
	public void listenToInput(){
		
		if (cVehicle != null) {
			boolean travelFWD = cVehicle.getFwdDirection().dot(cVehicle.getVelocity()) > 0 ? true : false;
			
			if(Gdx.input.isKeyPressed(Input.Keys.W)){
				if (travelFWD) accelMagnitude = 5;
				if (!travelFWD) accelMagnitude = 10;
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				if (travelFWD) accelMagnitude = 10;
				if (!travelFWD) accelMagnitude = -4;
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.A)) angularAccel += 1;
			if(Gdx.input.isKeyPressed(Input.Keys.D)) angularAccel -= 1;
			
			if(!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S)) accelMagnitude = 0;
			if(!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) angularAccel =0;
						
			updateVehicle();

			if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) setVehicle(null);
		}
			
			
	}
	
	public static void setVehicle(Vehicle vehicle) {
		if (vehicle != null) {
			cVehicle = vehicle;
			cVehicle.setColor(Color.ORANGE);
			accelMagnitude = cVehicle.getAccelMagnitude();
		} else {
			cVehicle.setColor(cVehicle.defaultColor);
			cVehicle.setUserControlled(false);
			cVehicle = null;
		}
	}

	private void updateVehicle() {
		tmp1.set(cVehicle.getFwdDirection()).nor();
		tmp1.scl(accelMagnitude);
		MathUtils.clamp(angularAccel, -maxAngularAccel, maxAngularAccel);
		tmp1.rotate(angularAccel);
		cVehicle.setAcceleration(tmp1);
	}



}
