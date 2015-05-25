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
		desireToChange = false;
		
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
			float rearGap = 2, frontGap = 1;
			boolean rearPass = false, frontPass = false;
			gapAccepted = false;
			if (rearOnTargetLane != null) {
				if ((rearOnTargetLane.behavior.pathFollowing.state.distanceOnPath + rearOnTargetLane.config.length/2) < pF.state.distanceOnPath - vehicle.config.length/2 - rearGap) rearPass = true;
			} else rearPass = true;
			if(frontOnTargetLane != null) {
				if ((frontOnTargetLane.behavior.pathFollowing.state.distanceOnPath - rearOnTargetLane.config.length/2) > pF.state.distanceOnPath - vehicle.config.length/2 + frontGap ) frontPass = true;
			} else frontPass = true;
			
			if (rearPass == frontPass == true) gapAccepted = true;
		}
		
		if (desireToChange && gapAccepted) {
			if (targetLaneIndex > 0 && targetLaneIndex < pF.state.currentLink.lanes.size) pF.changeLane(targetLaneIndex);
		}
	}

}
