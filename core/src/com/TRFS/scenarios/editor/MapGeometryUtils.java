package com.TRFS.scenarios.editor;

import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.simulator.MiscUtils;
import com.badlogic.gdx.utils.Array;

public class MapGeometryUtils {

	public static void setMapAttributes(Map map){
		
		Array<Float> xValues = new Array<Float>(); 	
		Array<Float> yValues = new Array<Float>(); 
		
		for (Link link : map.links) {
			for (Coordinate coordinate : link.coordinates) {
				xValues.add(coordinate.x);
				yValues.add(coordinate.y);
			}			
		}

		float maxX = MiscUtils.floatArrayMaxMin(xValues, true);
		float minX = MiscUtils.floatArrayMaxMin(xValues, false);
		float maxY = MiscUtils.floatArrayMaxMin(yValues, true);
		float minY = MiscUtils.floatArrayMaxMin(yValues, false);

		map.topRight = new Coordinate(maxX, maxY);
		map.bottomLeft = new Coordinate(minX, minY);
		map.dimensions = new Coordinate((maxX - minX), (maxY - minY));
		map.center = new Coordinate((minX + (map.dimensions.x) / 2), (minY + (map.dimensions.y) / 2));
		map.centroid = new Coordinate(MiscUtils.averageFloat(xValues), MiscUtils.averageFloat(yValues));
		
	}
	
}
