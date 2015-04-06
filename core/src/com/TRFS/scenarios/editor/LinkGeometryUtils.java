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

			float dx = point.getX() - prevPoint.getX(), dy = point.getY()
					- prevPoint.getY();
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
				newCoordinates.add(new Coordinate(coordinates.get(0).getX(),
						coordinates.get(0).getY()));

				for (int j = 0; j < coordinates.size - 1; j++) {

					Coordinate p0 = coordinates.get(j);
					Coordinate p1 = coordinates.get(j + 1);

					// Create two new points between p0 and p1 with Chaikin's
					// smoothing algorithm and add to new array
					Coordinate n1 = new Coordinate(0.75f * p0.getX() + 0.25f
							* p1.getX(), 0.75f * p0.getY() + 0.25f * p1.getY());
					Coordinate n2 = new Coordinate(0.25f * p0.getX() + 0.75f
							* p1.getX(), 0.25f * p0.getY() + 0.75f * p1.getY());

					newCoordinates.add(n1);
					newCoordinates.add(n2);
				}

				// Add last point
				newCoordinates.add(new Coordinate(coordinates.get(
						coordinates.size - 1).getX(), coordinates.get(
						coordinates.size - 1).getY()));
			} else
				newCoordinates = coordinates;
		} else
			newCoordinates = coordinates;

		return newCoordinates;
	}

}
