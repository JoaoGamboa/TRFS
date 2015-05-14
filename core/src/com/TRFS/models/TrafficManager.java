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
	public Array<Array<Vehicle>> vehicleLayers;
	public PathFinder pathFinder;

	public TrafficManager(Scenario scenario) {
		//this.scenario = scenario;
		this.inFlowsManager = new InFlowsManager(scenario);
		this.pathFinder = new PathFinder(scenario.map);
		
		this.vehicleLayers = new Array<Array<Vehicle>>();
		for (@SuppressWarnings("unused") Integer zLevel : scenario.map.zLevels) {
			this.vehicleLayers.add(new Array<Vehicle>());			
		}
	}
	
	public void update(float delta, float simulationTime) {
		
		//Update all actors
		for (Array<Vehicle> layer : vehicleLayers) {
			for (Vehicle vehicle : layer) {
				vehicle.update(delta);
			}
		}
		
		inFlowsManager.update(delta, simulationTime);
		
	}

}
