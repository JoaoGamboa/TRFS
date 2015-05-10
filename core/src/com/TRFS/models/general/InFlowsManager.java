package com.TRFS.models.general;

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
	
	private float[] counters;
	private int vehicleCount;
	private boolean debugOneVeh = false;
	
	private Vehicle vehicle;
	
	public InFlowsManager (Scenario scenario) {
		this.scenario = scenario;
		this.inflowLinks = scenario.getMap().getInFlowLinks();
		this.counters = new float[this.inflowLinks.size];
		
		//Clear counters
		for (int i = 0; i < inflowLinks.size; i++) counters[i] = 0f;
		
	}
	
	public void update(float dT, float simTime) {
		if (!debugOneVeh) {
			int i = 0;
			for (Link link : this.inflowLinks) {
				float flowDT = link.getInFlowParam().getCurrentVal() / 3600 * dT;
				counters[i] += flowDT;

				if (counters[i] >= 1) {
					addVehicle(link);
					counters[i] -= 1;
				}

				i++;
			}
		} else if (vehicleCount == 0) {
			addVehicle(this.inflowLinks.get(0));
		}

	}
	
	public void addVehicle(Link link){
		boolean enoughSpace = true;
		
		//link.getvehicle at 0 ?
		//Check if there is space, then create new vehicle and then add
		
		if(enoughSpace) {
			//DECIDE WHICH LANE 
			int lane = 0;
			//Decide weather to add a car or a truck			
			if (MathUtils.random(100) > SimulationParameters.truckPercent.getCurrentVal()) {
				vehicle = new Car();
			} else {
				vehicle = new Truck();
			}
			
			vehicle.behavior.setInitialLocation(link, link.getLanes().get(lane));
			scenario.getVehicleLayers().get(link.getZ()).add(vehicle);
			
			//this.scenario.getStages().get(link.getZ()).addActor(vehicle);
			vehicleCount += 1;
						
		}
	}
		
	public int getVehicleCount() {
		return vehicleCount;
	}
	
}
