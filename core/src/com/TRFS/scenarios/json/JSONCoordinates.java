package com.TRFS.scenarios.json;

import com.TRFS.scenarios.editor.DoubleCoordinate;

public class JSONCoordinates {

	private int earthRadius = 6371; //km

	public DoubleCoordinate latLongCoordinates;
	public DoubleCoordinate catersianCoordinates;
	

	public JSONCoordinates(Object coordinates) {
		String JsonOutput = coordinates.toString();
		
		this.latLongCoordinates = new DoubleCoordinate(splitString(JsonOutput, "lat"), splitString(JsonOutput, "lon"));

		double x = earthRadius * 1000 * Math.sin(Math.toRadians(latLongCoordinates.x)) * Math.sin(Math.toRadians(latLongCoordinates.y)); 
		double y = earthRadius * 1000 * Math.sin(Math.toRadians(latLongCoordinates.x)) * Math.cos(Math.toRadians(latLongCoordinates.y)); 

		this.catersianCoordinates = new DoubleCoordinate(x, y);
	}

	//Tools
	private double splitString (String JsonOutput, String coord) {
		int i = 0;

		if (coord.equals("lon")) i = 0;
			else if (coord.equals("lat")) i = 1; 

		String s = JsonOutput.toString();
		s = s.substring(1, s.length() - 1); 

		String[] parts = s.split(", ");

		double value = Double.parseDouble(parts[i]);
		return value;

	}
}
