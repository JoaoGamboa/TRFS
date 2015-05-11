package com.TRFS.scenarios.editor;

import java.util.ArrayList;
import java.util.Collections;

import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.simulator.MiscUtils;

public class MapGeometryUtils {

	public static void setMapAttributes(Map map){
		
		ArrayList<Double> xValues = new ArrayList<Double>(); 	
		ArrayList<Double> yValues = new ArrayList<Double>(); 
		for (Link link : map.links) {
			for (Coordinate coordinate : link.coordinates) {
				xValues.add((double) coordinate.x);
				yValues.add((double) coordinate.y);
			}			
		}
		
		double maxX = Collections.max(xValues);
		double minX = Collections.min(xValues);
		double maxY = Collections.max(yValues);
		double minY = Collections.min(yValues);

		map.topRight = new Coordinate((float) maxX, (float) maxY);
		map.bottomLeft = new Coordinate((float) minX, (float) minY);
		
		map.dimensions = new Coordinate((float) (maxX - minX), (float) (maxY - minY));
		
		map.center = new Coordinate((float) (minX + (map.dimensions.x) / 2),(float) (minY + (map.dimensions.y) / 2));

		map.centroid = new Coordinate(MiscUtils.average(xValues), MiscUtils.average(yValues));
		
	}
	
}
