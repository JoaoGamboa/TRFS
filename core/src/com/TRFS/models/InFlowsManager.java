package com.TRFS.models;

import java.util.Random;

import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.vehicles.Car;
import com.TRFS.vehicles.Truck;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * @author J.P.Gamboa jpgamboa@outlook.com
 *
 */
public class InFlowsManager {
	public static int nextVehicleID;
	
	private Scenario scenario;
	private Array<Link> inflowLinks; 
	
	private float[] timeCounters;
	public int vehicleCount;
	public long totalVehicleCount;
	private int[] queueOutsideNetwork;
	private boolean debugOneVeh = false;
	
	private Vehicle vehicle;
	
	public InFlowsManager (Scenario scenario) {
		this.scenario = scenario;
		this.inflowLinks = scenario.map.inFlowLinks;
		this.timeCounters = new float[this.inflowLinks.size];
		this.queueOutsideNetwork = new int[this.inflowLinks.size];
		
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
					int lane = chooseLane(link);
					if (lane >= 0)	{
						addVehicle(link, lane);
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
	
	/**Randomly selects a {@link Lane} from the provided {@link Link} and checks for enough space for another vehicle to be added.
	 * Is the randomly selected lane has no space available, the remaining lanes will be evaluated starting from the rightmost one. 
	 * @param link
	 * @return The lane index or -1 if no lane available
	 */
	public int chooseLane(Link link){
		int lane = new Random().nextInt((link.nrOfLanes));
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
	
	/** Checks the provided {@link Lane} for space for another vehicle to be added.
	 * @param link
	 * @param lane
	 * @return True is there is enough space on this lane.
	 */
	private float minimumAvailableSpace = 6f;
	
	public boolean checkLaneForSpace (Link link, int lane) {
		for (Vehicle vehicle : scenario.trafficManager.vehicles) {
			if (vehicle.behavior.pathFollowing.state.currentLink.quickEquals(link)) {
				if (vehicle.behavior.pathFollowing.state.currentLane.index == lane) {
					if (vehicle.behavior.pathFollowing.state.distanceOnPath < minimumAvailableSpace) {
						return false;
					}}}}
		return true;
	}
	
	/**Adds a new vehicle to the network, located at the beginning of the provided {@link Lane}.
	 * Decides what kind of vehicle to be added based on the truck percentage defined by the user.
	 * @param link
	 * @param lane
	 */
	public void addVehicle(Link link, int lane){
		
		if (MathUtils.random(100) > SimulationParameters.truckPercent.getCurrentVal())
			vehicle = new Car();
		else
			vehicle = new Truck();
		
		vehicle.behavior.setInitialLocation(link, link.lanes.get(lane));
		vehicle.behavior.pathFollowing.linkSequence = scenario.trafficManager.pathFinder.getRandomPathFromNode(link.fromNode);
		vehicle.physics.speed = link.maxspeed / 3.6f;
		scenario.trafficManager.vehicles.add(vehicle);
		totalVehicleCount++;				
	}	
}
