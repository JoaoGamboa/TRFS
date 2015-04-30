package com.TRFS.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class VehicleInputProcessor {
	
	private static Vehicle cVehicle;
	private Vector2 tmp1;

	private static float accelMagnitude = 0;
	
	public VehicleInputProcessor() {
		tmp1 = new Vector2();
	}
	
	public void listenToInput(){
		
		if (cVehicle != null) {
			boolean travelingFWD = cVehicle.fwdDirection.dot(cVehicle.physics.velocity) > 0 ? true : false;
			
			if(Gdx.input.isKeyPressed(Input.Keys.W)){
				if (travelingFWD) accelMagnitude = 5;
				if (!travelingFWD) accelMagnitude = 10;
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				if (travelingFWD) accelMagnitude = 10;
				if (!travelingFWD) accelMagnitude = -4;
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.A)) cVehicle.physics.angularAcceleration += 1;
			if(Gdx.input.isKeyPressed(Input.Keys.D)) cVehicle.physics.angularAcceleration -= 1;
			
			if(!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S)) MathUtils.lerp(accelMagnitude, 0, 1f);
			if(!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) MathUtils.lerp(cVehicle.physics.angularAcceleration, 0, 1f);
						
			
			tmp1.set(cVehicle.fwdDirection).nor();
			tmp1.scl(accelMagnitude);
			cVehicle.physics.acceleration.set(tmp1);
			
			if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) setVehicle(null);
		}
			
	}
	
	public static void setVehicle(Vehicle vehicle) {
		if (vehicle != null) {
			cVehicle = vehicle;
			cVehicle.config.color = Color.ORANGE;
			accelMagnitude = cVehicle.physics.getAccelMagnitude();
		} else {
			cVehicle.config.color = cVehicle.config.defaultColor;
			cVehicle.config.userControlled = false;
			cVehicle.config.selected = false;
			cVehicle = null;
		}
	}
	
	public boolean isTouched(Vehicle vehicle, float worldX, float worldY){
		//nao é preciso o fwd vector. ver este link 
		//http://www.emanueleferonato.com/2012/03/09/algorithm-to-determine-if-a-point-is-inside-a-square-with-mathematics-no-hit-test-involved/
		tmp1.set(vehicle.physics.forward).rotateRad(vehicle.physics.heading);
		
		float v0 = vehicle.physics.position
		
		return false;
	}

}
