package com.TRFS.models.carFollowing;


/**
 * @author jgamboa
 * Base class for car-following models.
 */
public abstract class CarFollowingModel {
	
	public CarFollowingModel() {
	}
	
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

}
