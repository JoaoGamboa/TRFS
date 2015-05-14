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
	public Array<Vehicle> vehicles;
	public PathFinder pathFinder;

	public TrafficManager(Scenario scenario) {
		//this.scenario = scenario;
		this.inFlowsManager = new InFlowsManager(scenario);
		this.pathFinder = new PathFinder(scenario.map);
		
		this.vehicles = new Array<Vehicle>();	
		this.vehicles.ensureCapacity(300);
	}
	
	public void update(float delta, float simulationTime) {
		
		//Update all actors
		int vehicleIndex = 0;
		for (Vehicle vehicle : vehicles) {
			vehicle.update(delta);
			
			if (vehicle.behavior.pathFollowing.state.finished) vehicles.removeIndex(vehicleIndex);
			
			vehicleIndex++;
		}
		
		inFlowsManager.vehicleCount = vehicleIndex;
				
		inFlowsManager.update(delta, simulationTime);
	}
	
}
