package com.TRFS.models.carFollowing;

import com.TRFS.ui.general.parameters.DynamicSimParam;

public class FritzscheCarFollowing extends CarFollowingModel{
	
	public static DynamicSimParam[] calibrationParameters = {};

	public FritzscheCarFollowing() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public float update(float dX, float dV, float leaderLength, float Vn,
			float Vn1, float an1, float maxSpeed, float desiredSpeed) {
		// TODO Auto-generated method stub
		return super.update(dX, dV, leaderLength, Vn, Vn1, an1, maxSpeed, desiredSpeed);
	}
	
	

}
