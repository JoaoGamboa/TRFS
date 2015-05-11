package com.TRFS.models;

import com.TRFS.models.general.InFlowsManager;
import com.TRFS.scenarios.Scenario;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.utils.Array;

/**
 * @author jgamboa
 *
 */

public class TrafficManager {
	
	private InFlowsManager inFlowsManager;

	private Array<Array<Vehicle>> vehicleLayers;

	public TrafficManager(Scenario scenario) {
		inFlowsManager = new InFlowsManager(scenario);
		this.vehicleLayers = scenario.getVehicleLayers();
		
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
		
	public InFlowsManager getInFlowsManager() {
		return inFlowsManager;
	}

}
