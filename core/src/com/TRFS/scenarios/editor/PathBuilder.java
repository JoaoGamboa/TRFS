package com.TRFS.scenarios.editor;

import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.scenarios.map.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PathBuilder {
	
	/**
	 * Generates a path from the given coordinates
	 * @param coordinates
	 */
	public static Path buildPath(Array<Coordinate> coordinates) {
		
		//Build Vector2 array for waypoints
		Array<Vector2> vectors = new Array<Vector2>();
		
		for (Coordinate coord : coordinates) {
			vectors.add(new Vector2(coord.x, coord.y));
		}
		
		return new Path(vectors);
	}

}
