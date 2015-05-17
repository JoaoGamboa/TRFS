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
	public Array<Vehicle> vehicles, tmpNeighbours;
	public PathFinder pathFinder;

	public TrafficManager(Scenario scenario) {
		//this.scenario = scenario;
		this.inFlowsManager = new InFlowsManager(scenario);
		this.pathFinder = new PathFinder(scenario.map);
		
		this.vehicles = new Array<Vehicle>();	
		this.vehicles.ensureCapacity(300);
	}
	
	public void update(float delta, float simulationTime) {
		int vehicleIndex = 0;
		
		for (Vehicle vehicle : vehicles) {
			
			checkEnvironment(vehicle);
			
			vehicle.update(delta);
			if (vehicle.behavior.pathFollowing.state.finished) vehicles.removeIndex(vehicleIndex);
			vehicleIndex++;
		}
		
		inFlowsManager.vehicleCount = vehicleIndex;
		inFlowsManager.update(delta, simulationTime);
	}
	
	private void checkEnvironment(Vehicle vA) {
		Vehicle leader = null;
		for (Vehicle vB : vehicles) {
			//Check if vehicles are near each other
			float dX = vB.physics.position.x - vA.physics.position.x ;
			if (Math.abs(dX) > 50) continue;
			float dY = vB.physics.position.y - vA.physics.position.y ;
			if (Math.abs(dY) > 50) continue;
			
			//Check if they're the same
			if (vB.id == vA.id) continue;
			
			//Check if they're on the same link
			if (vB.behavior.currentLink.quickEquals(vB.behavior.currentLink)) {
				
	
			//If not, check if they're headed to the same intersection
			} else if (vB.behavior.currentLink.toNode.quickEquals(vA.behavior.currentLink.toNode)) {
			
				
			}
		}
		
		vA.behavior.leader = leader;
	}
	
}
