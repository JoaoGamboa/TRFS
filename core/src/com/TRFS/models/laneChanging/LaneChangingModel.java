package com.TRFS.models.laneChanging;

import com.TRFS.vehicles.Vehicle;


public abstract class LaneChangingModel {
	
	public static final String[] laneChangeModels = new String[]{"TRFS Lane Changing"};
	
	public int currentLaneIndex, targetLaneIndex;
	public boolean desireToChange;
	
	public LaneChangingModel () {
		
	}
	
	public void update() {
		
	}
	
	/**Sets the {@link Vehicle}'s {@link LaneChangingModel} to the desired choice.
	 * @param carFollowingBehavior
	 */
	public static LaneChangingModel set(String laneChangingModel) {
		
		int model = 0;
		for (int i = 0; i < LaneChangingModel.laneChangeModels.length; i++) {
			if (laneChangingModel.equals(LaneChangingModel.laneChangeModels[i])) {
				model = i;
			}
		}
		
		switch (model) {
		case 0:
			return new TRFSLaneChanging();
		}
		
		return null;
	}
}
