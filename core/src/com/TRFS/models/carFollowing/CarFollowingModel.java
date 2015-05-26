package com.TRFS.models.carFollowing;

import com.TRFS.vehicles.Vehicle;


/**
 * @author jgamboa
 * Base class for car-following models.
 */
public class CarFollowingModel {
	
	public static final String[] carFolModels = new String[] { "Wiedemann '74"};
	
	public Vehicle vehicle, leader;
	
	//Global variables because they're common to every car-following model and can be used by other methods.
	public float dX, dV; 
	
	public CarFollowingModel(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	/**
	 * @param dX - Distance between leader and this
	 * @param dV - Speed dif. between leader and this
	 * @param leaderLength
	 * @param Vn
	 * @param Vn1
	 * @param an1
	 */
	public float update() {
		return 0;
	}
	
	public float avoidColision() {
		if (leader != null && vehicle.behavior.carFollowingBehaviour.dX < 5) return 1f;
		return 0;
	}

	
	/**Sets the {@link Vehicle}'s {@link CarFollowingModel} to the desired choice.
	 * @param carFollowingBehavior
	 */
	public static CarFollowingModel set(String carFollowingModel, Vehicle vehicle) {
		
		int model = 0;
		for (int i = 0; i < CarFollowingModel.carFolModels.length; i++) {
			if (carFollowingModel.equals(CarFollowingModel.carFolModels[i])) {
				model = i;
			}
		}
		switch (model) {
		
		case 0:
			return new W74CarFollowing(vehicle);
		case 1:
			return new FritzscheCarFollowing(vehicle);
		}
		
		return null;
	}
}
