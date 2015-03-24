package com.TRFS.models;

import java.util.Comparator;

import com.TRFS.models.general.InFlowsManager;
import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

/**
 * @author jgamboa
 *
 */

public class TrafficManager {
	
	private InFlowsManager inFlowsManager;

	//private Scenario scenario;
	private float simTime = 0;
	private Array<Link> links;
	private Array<Stage> stages;

	public TrafficManager(Scenario scenario) {
		//this.scenario = scenario;
		inFlowsManager = new InFlowsManager(scenario);
		this.links = scenario.getMap().getLinks();
		this.stages = scenario.getStages();
	}
	
	public void update(float timeStep) {
		simTime += timeStep;
		
		/*//Sort vehicles by position for each lane
		for (Link link : links) {
			for (Lane lane : link.getLanes()) {
				lane.getVehicles().sort(new VehiclePositionOnLinkComparator());
			}
		}*/
		
		//Update all actors
		for (Stage stage : stages) {
			stage.act();
		}
		
		inFlowsManager.update(timeStep, simTime);
		
	}
		
	/*class VehiclePositionOnLinkComparator implements Comparator<Vehicle> {
		public int compare(Vehicle veh1, Vehicle veh2) {
			if (veh1.getPositionOnLink() > veh2.getPositionOnLink()) return 1;
			if (veh1.getPositionOnLink() < veh2.getPositionOnLink()) return -1;		
			return 0;
		}
		
	}*/
	
	public float getSimTime() {
		return simTime;
	}
	public InFlowsManager getInFlowsManager() {
		return inFlowsManager;
	}

}
