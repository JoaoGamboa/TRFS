package com.TRFS.models.carFollowing;

import com.TRFS.vehicles.Vehicle;


/**
 * @author jgamboa
 * Base class for car-following models.
 */
public abstract class CarFollowingModel {
	
	public static final String[] carFolModels = new String[] { "Wiedemann '74", "Fritzsche" };
	
	public CarFollowingModel() {}
	
	/**
	 * @param dX - Distance between leader and this
	 * @param dV - Speed dif. between leader and this
	 * @param leaderLength
	 * @param Vn
	 * @param Vn1
	 * @param an1
	 */
	public float update(float dX, float dV, float leaderLength, float Vn, float Vn1, float an1, float maxSpeed, float desiredSpeed) {
		return 0;
	}

	
	/**Sets the {@link Vehicle}'s {@link CarFollowingModel} to the desired choice.
	 * @param carFollowingBehavior
	 */
	public static CarFollowingModel set(String carFollowingModel) {
		
		int model = 0;
		for (int i = 0; i < CarFollowingModel.carFolModels.length; i++) {
			if (carFollowingModel.equals(CarFollowingModel.carFolModels[i])) {
				model = i;
			}
		}
		switch (model) {
		
		case 0:
			return new W74CarFollowing();
		case 1:
			return new FritzscheCarFollowing();
		}
		
		return null;
	}
}
