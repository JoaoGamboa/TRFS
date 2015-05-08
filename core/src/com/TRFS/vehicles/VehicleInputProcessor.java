package com.TRFS.vehicles;

import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.simulator.MiscUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public class VehicleInputProcessor {
	
	private Vehicle tVehicle, sVehicle;
	private Coordinate localMouseClick;
		
	public VehicleInputProcessor() {
		localMouseClick = new Coordinate();
	}
	
	public void listenToInput(){
		
		if (sVehicle != null) {

			sVehicle.physics.throttleInputFwd = Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0;
			sVehicle.physics.throttleInputBck = Gdx.input.isKeyPressed(Input.Keys.S) ? 1 : 0;
			sVehicle.physics.steerInputLeft = Gdx.input.isKeyPressed(Input.Keys.A) ? 1 : 0;
			sVehicle.physics.steerInputRight = Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0;
						
			if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) setVehicle(null);
		}
	}
	
	public void setVehicle(Vehicle vehicle) {
		if (vehicle != null) {
			sVehicle = vehicle;
			sVehicle.config.color = Color.ORANGE;
			sVehicle.config.userControlled = true;
		} else {
			sVehicle.config.color = sVehicle.config.defaultColor;
			sVehicle.config.userControlled = false;
			sVehicle.config.selected = false;
			sVehicle = null;
		}
	}
	
	public void findClicked(Array<Array<Vehicle>> vehicleLayers, float worldX, float worldY) {
		
		//Loop through layers starting from the topmost one
		for (int i = vehicleLayers.size - 1; i >=0; i--) {
			if (vehicleLayers.get(i).size == 0) continue;
			for (Vehicle vehicle : vehicleLayers.get(i)) {
				if (isTouched(vehicle, worldX, worldY)) {
					tVehicle = vehicle;
					break;
				}
			}
		}
	}
	
	public void confirmClicked(float worldX, float worldY) {
		//Is the mouse pointer is still inside the vehicle, confirm the selection
		if (tVehicle != null) {
			if (isTouched(tVehicle, worldX, worldY)) {
				setVehicle(tVehicle);
			}
			tVehicle = null;
		}
	}
	
	public boolean isTouched(Vehicle vehicle, float worldX, float worldY){
		//Quick test
		if (Math.abs(worldX - vehicle.physics.position.x) > 10 ) return false;
		if (Math.abs(worldY - vehicle.physics.position.y) > 10 ) return false;
		
		//Precision test
		localMouseClick.set(worldX, worldY);
		MiscUtils.globalToLocalOut(localMouseClick, vehicle.physics.heading, vehicle.physics.position);
		
		if (localMouseClick.x > -vehicle.config.width/2
				&& localMouseClick.x < vehicle.config.width/2) {
			if (localMouseClick.y > -vehicle.config.length/2
					&& localMouseClick.y < vehicle.config.length/2) {
				return true;
			}
		}
		return false;
	}
}
