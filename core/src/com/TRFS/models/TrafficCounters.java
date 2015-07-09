package com.TRFS.models;

import com.TRFS.vehicles.Vehicle;

public class TrafficCounters {
	
	public static int[] counterLinkIDs = new int[] {1,2,4,5}; //IDs of the links in which to place the counters. This should integrated in the JSON reader in the future. To allow the user to choose where to count.
		
	public TrafficCounters() {
		
	}
	
	public void update(float delta, Vehicle vehicle) {
		for (int i = 0; i < counterLinkIDs.length; i++) {
			if (vehicle.stats.currentLinkID != counterLinkIDs[i]) return;
		}


	}
	
	private 
	
	

}