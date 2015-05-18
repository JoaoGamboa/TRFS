package com.TRFS.models;

import com.TRFS.models.general.InFlowsManager;
import com.TRFS.models.path.PathFinder;
import com.TRFS.scenarios.Scenario;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.utils.Array;

/**
 * @author jgamboa
 *
 */

public class TrafficManager {
	//private Scenario scenario;
	public InFlowsManager inFlowsManager;
	public Array<Vehicle> vehicles, tmpNeighbours, tmpIntercecters;
	public PathFinder pathFinder;

	public TrafficManager(Scenario scenario) {
		//this.scenario = scenario;
		this.inFlowsManager = new InFlowsManager(scenario);
		this.pathFinder = new PathFinder(scenario.map);
		
		this.vehicles = new Array<Vehicle>();	
		tmpNeighbours = new Array<Vehicle>();	
		tmpIntercecters = new Array<Vehicle>();	
		
		this.vehicles.ensureCapacity(300);
	}
	
	public void update(float delta, float simulationTime) {
		int vehicleIndex = 0;
		
		for (Vehicle vehicle : vehicles) {
			
			evaluateEnvironment(vehicle);
			
			vehicle.update(delta);
			if (vehicle.behavior.pathFollowing.state.finished) vehicles.removeIndex(vehicleIndex);
			vehicleIndex++;
		}
		
		inFlowsManager.vehicleCount = vehicleIndex;
		inFlowsManager.update(delta, simulationTime);
	}
	
	/**Detects this vehicle's neighbors and passes them to the behavior class.
	 * @param vA
	 */
	private void evaluateEnvironment(Vehicle vA) {
		Vehicle leader = null;
		Vehicle frontOnTargetLane = null;
		Vehicle rearOnTargetLane = null;
		Vehicle lastOnPriorityLink = null;
		Vehicle firstOnNextLink = null;
		
		for (Vehicle vB : vehicles) {
			//Check if vehicles are near each other
			float dX = vB.physics.position.x - vA.physics.position.x ;
			if (Math.abs(dX) > 50) continue;
			float dY = vB.physics.position.y - vA.physics.position.y ;
			if (Math.abs(dY) > 50) continue;
			if (vB.id == vA.id) continue;
			
			//Check if they're at the same link
			if (vB.behavior.currentLink.quickEquals(vB.behavior.currentLink)) {
				if (vB.behavior.pathFollowing.state.distanceOnPath > vA.behavior.pathFollowing.state.distanceOnPath) {
					if (leader == null) leader = vB;
					if(vB.behavior.pathFollowing.state.distanceOnPath < leader.behavior.pathFollowing.state.distanceOnPath) leader = vB;
				}
				
				//Find neighbors
				if (vA.behavior.laneChangingBehaviour.desireToChange) {
					int targetLane = vA.behavior.laneChangingBehaviour.targetLaneIndex;
					if (vB.behavior.currentLane.index == targetLane) {
						if (frontOnTargetLane == null) leader = vB;	if (rearOnTargetLane == null) leader = vB;
						if (vB.behavior.pathFollowing.state.distanceOnPath < frontOnTargetLane.behavior.pathFollowing.state.distanceOnPath
								&& vB.behavior.pathFollowing.state.distanceOnPath > vA.behavior.pathFollowing.state.distanceOnPath)
							frontOnTargetLane = vB;
						if (vB.behavior.pathFollowing.state.distanceOnPath > rearOnTargetLane.behavior.pathFollowing.state.distanceOnPath
								&& vB.behavior.pathFollowing.state.distanceOnPath < vA.behavior.pathFollowing.state.distanceOnPath)
							rearOnTargetLane = vB;
					}
				}
				
			//If approaching the end of the link, check if they're headed to the same intersection
			} else if (vA.behavior.currentLink.toNode.toLinks.size > 0) {
				if (vA.behavior.pathFollowing.state.approachingLinkEnd) { 
					//If vB is one the link with priority over us
					if (vB.behavior.currentLink.toNode.quickEquals(vA.behavior.currentLink.toNode)) {
						//If he has priority over us, add him to the intersecting array
						if (vB.behavior.currentLink.hasPriorityOver(vA.behavior.currentLink)) {
							if (lastOnPriorityLink == null) lastOnPriorityLink = vB;
							if (vB.behavior.pathFollowing.state.distanceOnPath > lastOnPriorityLink.behavior.pathFollowing.state.distanceOnPath)
								lastOnPriorityLink = vB;
						}
					}
					//If VB is on our target link
					if (vB.behavior.currentLink.quickEquals(vA.behavior.pathFollowing.nextLink())) {
						if (firstOnNextLink == null) firstOnNextLink = vB;
						if (vB.behavior.pathFollowing.state.distanceOnPath < firstOnNextLink.behavior.pathFollowing.state.distanceOnPath)
							firstOnNextLink = vB;
					}
				}
			}
		}
		
		vA.behavior.leader = leader;
		vA.behavior.frontOnTargetLane = frontOnTargetLane;
		vA.behavior.rearOnTargetLane = rearOnTargetLane;
		vA.behavior.lastOnPriorityLink = lastOnPriorityLink;
		vA.behavior.firstOnNextLink = firstOnNextLink;
	}
	
}
