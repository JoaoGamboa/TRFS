package com.TRFS.scenarios.json;

import java.util.ArrayList;
import java.util.List;

import com.TRFS.scenarios.editor.LinkGeometryUtils;
import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.Node;
import com.TRFS.simulator.MiscUtils;
import com.TRFS.simulator.SimulationParameters;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class JSONMapUtils {

	public static void makeLinksAndNodes(Map map, FileHandle fileHandle) {

		Json json = new Json();
		List<JSONFeatures> features = json.fromJson(
				JSONFeatureCollection.class, fileHandle).getFeatures();

		Coordinate mapRelocation = averageCoordinate(features);

		for (int j = 0; j < features.size(); j++) {
			// Get coordinates from each feature
			List<?> coordinatesList = features.get(j).getGeometry().getCoordinates();

			// Get properties
			JSONProperties properties = features.get(j).getProperties();

			// Create Link with extracted data. Coordinate are added later.
			Link link = new Link(j, properties.getHierarchy(),
					properties.getInFlow(), properties.getLanes(),
					properties.getMaxspeed(), properties.getOneway(),
					properties.getZ());

			if (!map.getzLevels().contains(link.getZ(), true))
				map.getzLevels().add(link.getZ());

			map.getLinks().add(link);
			// If this link has inflows data, add it to an array for posterity.
			if (link.getInFlow() > 0)
				map.getInFlowLinks().add(link);

			Array<Coordinate> coordinates = new Array<Coordinate>();
			for (int k = 0; k < coordinatesList.size(); k++) {
				boolean isFirstPoint = k == 0 ? true : false;
				boolean isLastPoint = k == coordinatesList.size() - 1 ? true
						: false;

				// JSONCoordinates object handles the conversion from string to
				// double, and handles the coordinate system.
				// Creates a Coordinate object with XY data.
				JSONCoordinates jsonCoordinate = new JSONCoordinates(
						coordinatesList.get(k));

				Coordinate pannedCoordinate = new Coordinate(jsonCoordinate
						.getXYPoint().getX() + mapRelocation.getX(), jsonCoordinate
						.getXYPoint().getY() + mapRelocation.getY());

				// If it's a first or last point, then it's a node
				if (isFirstPoint || isLastPoint) {
					addNode(map, pannedCoordinate, isFirstPoint, link);
				}

				coordinates.add(pannedCoordinate);
			}

			// Set link coordinates
			if (SimulationParameters.simplifyGeometry)
				coordinates = LinkGeometryUtils.simplifyGeometry(coordinates, 80f);
			if (SimulationParameters.smoothGeometry)
				coordinates = LinkGeometryUtils.smoothGeometry(coordinates, 4);
				
			link.setCoordinates(coordinates);
		}
	}
	
	
	public static void addNode(Map map, Coordinate coordinates, boolean isFirstPoint, Link fromLink) {
		boolean isRepeated = false;
		Node finalNode = null;
		
		for (Link link : map.getLinks()) {
			if (link.getFromNode() != null) {
				if (coordinates.equals(link.getFromNode().getCoordinates())) {
					isRepeated = true;
					finalNode = link.getFromNode();
				}
			}
			if (link.getToNode() != null) {
				if (coordinates.equals(link.getToNode().getCoordinates())) {
					isRepeated = true;
					finalNode = link.getToNode();
				}
			}
		}

		if (isRepeated) {
			//This node already exists, so update it.
			finalNode.getFromLinks().add(fromLink);
		} else {
			//This is a new node, so add it.
			finalNode = new Node(coordinates, map.getNodes().size+1, fromLink);
			map.getNodes().add(finalNode);
		}
		
		if (isFirstPoint) {
			fromLink.setFromNode(finalNode);
		} else { 
			fromLink.setToNode(finalNode);
		}
	}

	/**
	 * Used to pan the whole map in order to reduce the number of digits
	 * required to store a coordinate.
	 */
	private static Coordinate averageCoordinate(List<JSONFeatures> features) {

		ArrayList<Double> coordinatesX = new ArrayList<Double>();
		ArrayList<Double> coordinatesY = new ArrayList<Double>();
		for (int j = 0; j < features.size(); j++) {
			List<?> coordinatesList = features.get(j).getGeometry()
					.getCoordinates();
			for (int l = 0; l < coordinatesList.size(); l++) {
				JSONCoordinates jsonCoordinate = new JSONCoordinates(
						coordinatesList.get(l));
				coordinatesX.add((double) jsonCoordinate.getXYPoint().getX());
				coordinatesY.add((double) jsonCoordinate.getXYPoint().getY());
			}
		}
		return new Coordinate(-MiscUtils.average(coordinatesX),
				-MiscUtils.average(coordinatesY));
	}

}
