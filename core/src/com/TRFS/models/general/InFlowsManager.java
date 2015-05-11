package com.TRFS.models.general;

import java.util.Random;

import com.TRFS.models.path.PathFinder;
import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Link;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.vehicles.Car;
import com.TRFS.vehicles.Truck;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class InFlowsManager {
	
	private Scenario scenario;
	private Array<Link> inflowLinks; 
	
	private float[] timeCounters;
	public int vehicleCount;
	private int[] queueOutsideNetwork;
	private boolean debugOneVeh = false;
	private PathFinder pathFinder;
	
	private Vehicle vehicle;
	
	public InFlowsManager (Scenario scenario) {
		this.scenario = scenario;
		this.inflowLinks = scenario.map.inFlowLinks;
		this.timeCounters = new float[this.inflowLinks.size];
		this.pathFinder = new PathFinder(scenario.map);
		
		//Clear counters
		for (int i = 0; i < inflowLinks.size; i++) {
			timeCounters[i] = 0f;
			queueOutsideNetwork[i] = 0;
		}
	}
	
	public void update(float dT, float simTime) {
		if (!debugOneVeh) {
			int i = 0;
			for (Link link : this.inflowLinks) {
				float flowDT = link.inFlowParam.getCurrentVal() / 3600 * dT;
				timeCounters[i] += flowDT;

				if (timeCounters[i] >= 1) {
					int laneWithSpace = chooseLane(link);
					if (laneWithSpace >= 0)	{
						addVehicle(link, laneWithSpace);
						if (queueOutsideNetwork[i] > 0) queueOutsideNetwork[i] -= 1;
					} else {
						queueOutsideNetwork[i] += 1;
					}
					timeCounters[i] -= 1;
				}

				i++;
			}
		} else if (vehicleCount == 0) {
			addVehicle(this.inflowLinks.get(0), 0);
		}

	}
	
	public int chooseLane(Link link){
		int lane = new Random().nextInt((link.nrOfLanes) + 1);
		if (checkLaneForSpace(link, lane)) {
			return lane;
		} else {
			for (int i = 0; i < link.nrOfLanes; i++) {
				if (i != lane) {
					if (checkLaneForSpace(link, i)) return i;
				}
			}
		}
		return -1;
	}
	
	public boolean checkLaneForSpace (Link link, int lane) {
		
		for (Array<Vehicle> layer : scenario.getVehicleLayers()) {
			for (Vehicle vehicle : layer) {
				if (vehicle.behavior.currentLink.internalID == link.internalID) {
					if (vehicle.behavior.currentLane.index == lane) {
						if (vehicle.behavior.pathFollowing.state.distanceOnPath < 6f) {
							return false;
						}}}}}
		return true;
	}
	
	public void addVehicle(Link link, int lane){
		boolean enoughSpace = true;
		
		//link.getvehicle at 0 ?
		//Check if there is space, then create new vehicle and then add
		
		if(enoughSpace) {
			//DECIDE WHICH LANE 
			lane = 0;
			//Decide weather to add a car or a truck			
			if (MathUtils.random(100) > SimulationParameters.truckPercent.getCurrentVal()) {
				vehicle = new Car();
			} else {
				vehicle = new Truck();
			}
			
			vehicle.behavior.setInitialLocation(link, link.lanes.get(lane));
			scenario.getVehicleLayers().get(link.z).add(vehicle);
			
			//this.scenario.getStages().get(link.getZ()).addActor(vehicle);
			
			vehicleCount += 1;
						
		}
	}	
}
