package com.TRFS.scenarios.editor;

import java.util.ArrayList;
import java.util.Collections;

import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.simulator.MiscTools;

public class MapGeometryUtils {

	public static void setMapAttributes(Map map){
		
		ArrayList<Double> xValues = new ArrayList<Double>(); 	
		ArrayList<Double> yValues = new ArrayList<Double>(); 
		for (Link link : map.getLinks()) {
			for (Coordinate coordinate : link.getCoordinates()) {
				xValues.add((double) coordinate.getX());
				yValues.add((double) coordinate.getY());
			}			
		}
		
		double maxX = Collections.max(xValues);
		double minX = Collections.min(xValues);
		double maxY = Collections.max(yValues);
		double minY = Collections.min(yValues);

		map.setTopRight(new Coordinate((float) maxX, (float) maxY));
		map.setBottomLeft(new Coordinate((float) minX, (float) minY));
		
		map.setDimensions(new Coordinate((float) (maxX - minX), (float) (maxY - minY)));
		map.setCenter(new Coordinate((float) (minX + (map.getDimensions()
				.getX()) / 2),(float) (minY + (map.getDimensions().getY()) / 2)));

		map.setCentroid(new Coordinate(MiscTools.average(xValues), MiscTools.average(yValues)));
		
	}
	
}
