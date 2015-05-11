package com.TRFS.scenarios.json;

import com.TRFS.scenarios.map.Coordinate;

public class JSONCoordinates {

	private double longitude;
	private double latitude;
	
	private int earthRadius = 6371; //km
	
	private Coordinate coordinates;
	private Coordinate xyCoordinates;
		
	public String JsonOutoput;
	
	public JSONCoordinates(Object coordinates) {
		this.JsonOutoput = coordinates.toString();
		
		double x = earthRadius * 1000 * Math.cos(Math.toRadians(getLatitude())) * Math.cos(Math.toRadians(getLongitude())); 
		double y = earthRadius * 1000 * Math.cos(Math.toRadians(getLatitude())) * Math.sin(Math.toRadians(getLongitude())); 
		
		this.xyCoordinates = new Coordinate((float) x, (float) y);
	}
	
	public double getX() {
		return this.xyCoordinates.x;
	}
	
	public double getY() {
		return this.xyCoordinates.y;
	}
	
	public double getLongitude() {
		this.longitude = splitString("lon");
		return this.longitude;
	}
	
	public double getLatitude() {
		this.latitude = splitString("lat");
		return this.latitude;
	}
	
	public Coordinate getXYPoint() {
		return this.xyCoordinates;
	}
	
	public Coordinate getLongLatPoint() {
		this.coordinates = new Coordinate((float) getLongitude(), (float) getLatitude());
		return this.coordinates;
	}
	
	//Tools
	private double splitString (String coord) {
		int i = 0;
		if (coord.equals("lon")) { i = 0; } else if (coord.equals("lat")) { i = 1; } 
		
		String s = this.JsonOutoput.toString();
		s = s.substring(1, s.length() - 1); 

		String[] parts = s.split(", ");

		double value = Double.parseDouble(parts[i]);
		return value;
	}
	
}
