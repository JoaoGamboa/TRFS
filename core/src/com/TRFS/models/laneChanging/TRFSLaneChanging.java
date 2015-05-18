package com.TRFS.models.laneChanging;

import com.TRFS.vehicles.Vehicle;

public class TRFSLaneChanging extends LaneChangingModel{

	public TRFSLaneChanging(Vehicle vehicle) {
		super(vehicle);

	}
	
	@Override
	public void update() {
		
		if (leader != null) {
			if (leader.physics.speed < vehicle.physics.speed) {
				desireToChange = true;
				targetLaneIndex = vehicle.behavior.currentLane.index + 1; //Moves one lane to the left to overtake
			} else desireToChange = false;
			
		}
		
		//Might need to change lanes to for an intersection
		if (vehicle.behavior.pathFollowing.state.approachingLinkEnd) {
			if (vehicle.behavior.pathFollowing.nextLink().toTheRightOf(vehicle.behavior.currentLink) && 
					vehicle.behavior.currentLane.index > 0) {
				desireToChange = true;
				targetLaneIndex = vehicle.behavior.currentLane.index - 1;
			} else if (vehicle.behavior.pathFollowing.nextLink().toTheLeftOf(vehicle.behavior.currentLink) && 
					vehicle.behavior.currentLane.index < vehicle.behavior.currentLink.nrOfLanes - 1) {
				desireToChange = true;
				targetLaneIndex = vehicle.behavior.currentLane.index + 1;
			}
		}
				
	}

}
