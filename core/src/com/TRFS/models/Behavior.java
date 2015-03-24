package com.TRFS.models;

import java.util.Random;

import com.TRFS.models.carFollowing.CarFollowingModel;
import com.TRFS.models.carFollowing.FritzscheCarFollowing;
import com.TRFS.models.carFollowing.W74CarFollowing;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.TRFS.vehicles.Vehicle;

/**
 * The behaviour class aggregates all behavior models and decisions to be made by the vehicle, 
 * returning the resulting acceleration value for the class Vehicle to process.
 * 
 * @author jgamboa
 */

public class Behavior {
	
	public static final String[] carFolModels = new String[]{"Wiedemann '74", "Fritzsche"};
	public static DynamicSimParam[] calibrationParameters;
	
	private CarFollowingModel carFollowingModel;
	
	private Vehicle vehicle;

	private float desiredSpeed;
	private float maxSpeed;
	
	private int model;
	
	private float dX;
	private float dV;
	private float Vn;

	//Leader
	private float Vn1;
	private float an1;
	private float length1;
	
	private float acceleration;
	
	private boolean changedLink;
	
	/**
	 * @param vehicle - This vehicle.
	 * @param carFollowingBehaviour - Car-following behavior for this vehicle.
	 * @param laneChangingBehaviour - Lane changing behavior for this vehicle.
	 */
	
	public Behavior(Vehicle vehicle, String carFollowingBehavior, String laneChangingBehavior) {
		this.vehicle = vehicle;
		
		for (int i = 0; i < carFolModels.length; i++) {
			if (carFollowingBehavior.equals(carFolModels[i])) {
				this.model = i;
			}
		}
		
		switch (model) {
		case 0:
			carFollowingModel = new W74CarFollowing();
			calibrationParameters = W74CarFollowing.calibrationParameters;
		case 1:
			carFollowingModel = new FritzscheCarFollowing();
			
		}
		
	}
	
	
	public void update(float dT, Vehicle leader) {
		//Update all necessary variables
		
		if (changedLink) {
			setMaxSpeed(vehicle.getCurrentLink().getMaxspeed());
			setDesiredSpeed(maxSpeed);
		}
		
		
		Vn = vehicle.getLinearVelocity().len();
		
		if (leader != null) {
			
			Vn1 = leader.getLinearVelocity().len();
			an1 = leader.getAcceleration();
			length1 = leader.getLenght();
			
			dV = Vn1 - Vn;
			dX = leader.getPosition().dst(vehicle.getPosition());
			
			acceleration = carFollowingModel.update(dX, dV, length1, Vn, Vn1, an1, maxSpeed, desiredSpeed);	
		} else {
			acceleration = vehicle.getMaxLinearAcceleration();
		}
		
	}
	
	public float getAcceleration() {
		return acceleration;
	}

	public void setDesiredSpeed(float maxSpeed) {
		this.desiredSpeed = (float) (new Random().nextGaussian()*20 + maxSpeed);
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}


	public boolean isChangedLink() {
		return changedLink;
	}


	public void setChangedLink(boolean changedLink) {
		this.changedLink = changedLink;
	}
}
