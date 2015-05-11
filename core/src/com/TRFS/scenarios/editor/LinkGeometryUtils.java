package com.TRFS.scenarios.editor;

import com.TRFS.scenarios.map.Coordinate;
import com.badlogic.gdx.utils.Array;

public class LinkGeometryUtils {
	
	public static Array<Coordinate> simplifyGeometry(
			Array<Coordinate> coordinates, float tolerance) {

		Array<Coordinate> newCoordinates = new Array<Coordinate>();

		Coordinate point;
		Coordinate prevPoint = coordinates.get(0);

		newCoordinates.add(prevPoint);

		for (int i = 1; i < coordinates.size - 1; i++) {
			point = coordinates.get(i);

			float dx = point.x - prevPoint.x, dy = point.y
					- prevPoint.y;
			float distSQ = dx * dx + dy * dy;

			if (distSQ > tolerance) {
				newCoordinates.add(point);
				prevPoint = point;
			}
		}
		newCoordinates.add(coordinates.get(coordinates.size - 1));
		return newCoordinates;

	}

	public static Array<Coordinate> smoothGeometry(
			Array<Coordinate> coordinates, int iterations) {

		Array<Coordinate> newCoordinates = new Array<Coordinate>();

		if (iterations > 0) {
			if (coordinates.size > 2) {
				// Add First Point
				newCoordinates.add(new Coordinate(coordinates.get(0).x,
						coordinates.get(0).y));

				for (int j = 0; j < coordinates.size - 1; j++) {

					Coordinate p0 = coordinates.get(j);
					Coordinate p1 = coordinates.get(j + 1);

					// Create two new points between p0 and p1 with Chaikin's
					// smoothing algorithm and add to new array
					Coordinate n1 = new Coordinate(0.75f * p0.x + 0.25f
							* p1.x, 0.75f * p0.y + 0.25f * p1.y);
					Coordinate n2 = new Coordinate(0.25f * p0.x + 0.75f
							* p1.x, 0.25f * p0.y + 0.75f * p1.y);

					newCoordinates.add(n1);
					newCoordinates.add(n2);
				}

				// Add last point
				newCoordinates.add(new Coordinate(coordinates.get(
						coordinates.size - 1).x, coordinates.get(
						coordinates.size - 1).y));
			} else
				newCoordinates = coordinates;
		} else
			newCoordinates = coordinates;

		return newCoordinates;
	}

}
