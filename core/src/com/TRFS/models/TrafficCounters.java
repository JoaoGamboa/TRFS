package com.TRFS.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.TRFS.vehicles.Vehicle;

public class TrafficCounters {
	
	public static int[] counterLinkIDs = new int[] {1,2,4,5}; //IDs of the links in which to place the counters. This should integrated in the JSON reader in the future. To allow the user to choose where to count.
		
	private FileWriter fileWriter;
	
	private int entryCounter;
	
	public boolean ended;
	
	public TrafficCounters() {
		File dir = new File(System.getProperty("user.home"), "Desktop");
		File file = new File(dir, new SimpleDateFormat("HH-mm").format(new Date()) + ".csv");
		try {
			fileWriter = new FileWriter(file);
			
			//Field Names
			fileWriter.append(makeFields());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(float delta, Vehicle vehicle) {
		int linkID = atLinkWithCounter(vehicle);
		if (linkID == 0) return;
		
		if (alreadyCountedAtThisLink(vehicle, linkID)) return;
		
		entryCounter++;
		addRecord(vehicle);
		
		setCounted(vehicle, linkID);
	}
	
	public void end() {
		try {
			fileWriter.flush();
			fileWriter.close();
			ended = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String makeFields() {
		return "id,VehicleID,VehicleType,CurrentLinkID,OriginLinkID,DestinationLinkID,Speed,Acceleration,TimeTravelled,DistanceTravelled,BrakingTime,AccelerationTime,StoppedTime,DesiringToChangeLaneTime";
	}
	
	private void addRecord(Vehicle vehicle){
		try {
			fileWriter.append("\n " + entryCounter + "," + vehicle.id + ","
					+ vehicle.stats.type + "," + vehicle.stats.currentLinkID
					+ "," + vehicle.stats.originLinkID + ","
					+ vehicle.stats.destinationLinkID + ","
					+ vehicle.stats.speed + "," + vehicle.stats.acceleration
					+ "," + vehicle.stats.travelTime + ","
					+ vehicle.stats.distanceTravelled + ","
					+ vehicle.stats.brakingTime + ","
					+ vehicle.stats.accelerationTime + ","
					+ vehicle.stats.stoppedTime + ","
					+ vehicle.stats.desireToChangeLaneTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private int atLinkWithCounter(Vehicle vehicle) {
		for (int i = 0; i < counterLinkIDs.length; i++) {
			if (vehicle.stats.currentLinkID == counterLinkIDs[i]) return i;
		}
		return 0;
	}
	
	private boolean alreadyCountedAtThisLink(Vehicle vehicle, int linkID) {
		for (int id : vehicle.stats.counterRecord) {
			if (id == linkID) return true;
		}
		return false;
	}
	
	private void setCounted(Vehicle vehicle, int linkID) {
		vehicle.stats.counterRecord.add(linkID);
	}
}