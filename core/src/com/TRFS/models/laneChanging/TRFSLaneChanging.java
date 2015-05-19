package com.TRFS.models.laneChanging;

import com.TRFS.models.path.PathFollowing;
import com.TRFS.vehicles.Vehicle;

public class TRFSLaneChanging extends LaneChangingModel{

	public TRFSLaneChanging(Vehicle vehicle) {
		super(vehicle);

	}
	
	@Override
	public void update() {
		
		PathFollowing pF = vehicle.behavior.pathFollowing;
		
		if (leader != null) {
			if (leader.physics.speed < vehicle.physics.speed) {
				desireToChange = true;
				targetLaneIndex = pF.state.currentLane.index + 1; //Moves one lane to the left to overtake
			} else desireToChange = false;
		}
		
		//Might need to change lanes to for an intersection
		if (pF.state.approachingLinkEnd && pF.state.currentLink.toNode.toLinks.size > 1) {
			if (pF.nextLink().toTheRightOf(pF.state.currentLink) && 
					pF.state.currentLane.index > 0) {
				desireToChange = true;
				targetLaneIndex = pF.state.currentLane.index - 1;
			} else if (pF.nextLink().toTheLeftOf(pF.state.currentLink) && 
					pF.state.currentLane.index < pF.state.currentLink.nrOfLanes - 1) {
				desireToChange = true;
				targetLaneIndex = pF.state.currentLane.index + 1;
			}
		}
		
		if (desireToChange) {
			//Check GapAccepance
		}
		
		
		
		if (desireToChange && gapAccepted) pF.changeLane(targetLaneIndex);
				
	}

}
