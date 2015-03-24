package com.TRFS.scenarios.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.TRFS.scenarios.json.JSONCoordinates;
import com.TRFS.scenarios.json.JSONFeatureCollection;
import com.TRFS.scenarios.json.JSONFeatures;
import com.TRFS.scenarios.json.JSONProperties;
import com.TRFS.simulator.MiscTools;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class Map {
	
	private MapPreview mapPreview;
	
	private String name;
	private Json json;
	
	private Array<Link> links;
	private Array<Node> nodes;
	
	private Array<Link> inFlowLinks;
	private Array<Integer> zLevels;
	
	private List<JSONFeatures> features;
	
	private List<Double> xValues;
	private List<Double> yValues;
	
	private double mapWidth;
	private double mapHeight;
	
	private Coordinate centroid;
	private Coordinate center;
			
	public Map(MapPreview mapPreview) {
		this.name = mapPreview.getName();
		this.setMapPreview(mapPreview);
		
		this.links = new Array<Link>();
		this.nodes = new Array<Node>();
		this.setInFlowLinks(new Array<Link>());
		this.xValues = new ArrayList<Double>();
		this.yValues = new ArrayList<Double>();
		this.zLevels = new Array<Integer>();
		
		buildMap();
	}
	
	private void buildMap() {
		
		if (this.getMapPreview().isFromJSON()) {
			//Initiate Json and get features
			json = new Json();
			features = json.fromJson(JSONFeatureCollection.class, mapPreview.getFileHandle()).getFeatures();
			
			//Get pan values
			ArrayList<Double> coordinatetestX = new ArrayList<Double>();
			ArrayList<Double> coordinatetestY = new ArrayList<Double>();
			for (int j = 0; j < features.size(); j++) {
				List<?> coordinatesList = features.get(j).getGeometry().getCoordinates();
				for (int l = 0; l < coordinatesList.size(); l++) {
					JSONCoordinates jsonCoordinate = new JSONCoordinates(coordinatesList.get(l));
					coordinatetestX.add((double) jsonCoordinate.getXYPoint().getX());
					coordinatetestY.add((double) jsonCoordinate.getXYPoint().getY());
				}
			}
			
			float panX = -MiscTools.average(coordinatetestX);
			float panY = -MiscTools.average(coordinatetestY);
			
			for (int j = 0; j < features.size(); j++) {
				//Get coordinates from each feature
				List<?> coordinatesList = features.get(j).getGeometry().getCoordinates();
				
				//Get properties
				JSONProperties properties = features.get(j).getProperties();
				
				//Create Link with extracted data. Coordinate are added later.
				Link link = new Link(j, properties.getHierarchy(),properties.getInFlow(), 
						properties.getLanes(), properties.getMaxspeed(), properties.getOneway(), properties.getZ());
				
				if (!zLevels.contains(link.getZ(), true)) zLevels.add(link.getZ());
				
				this.links.add(link);
				//If this link has inflows data, add it to an array for posterity.
				if (link.getInFlow() > 0) inFlowLinks.add(link);
				
				ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
				for (int k = 0; k < coordinatesList.size(); k++) {
					boolean isFirstPoint = k == 0 ? true : false;
					boolean isLastPoint = k == coordinatesList.size()-1 ? true : false;
					
					//JSONCoordinates object handles the conversion from string to double, and handles the coordinate system. 
					//Creates a Coordinate object with XY data.
					JSONCoordinates jsonCoordinate = new JSONCoordinates(coordinatesList.get(k));
					
					Coordinate pannedCoordinate = new Coordinate(jsonCoordinate.getXYPoint().getX()+panX, jsonCoordinate.getXYPoint().getY()+panY);
					
					//If it's a first or last point, then it's a node						
					if (isFirstPoint || isLastPoint) {
						//addNode(jsonCoordinate.getXYPoint(), isFirstPoint, link);
						addNode(pannedCoordinate, isFirstPoint, link);
					}
					
					//coordinates.add(jsonCoordinate.getXYPoint());
					coordinates.add(pannedCoordinate);
				}
				
				//Set link's coordinates
				link.setCoordinates(coordinates);
			}
		}
		
		populateXYArrays();
		//Collections.sort(Arrays.asList(li));
		//links.sort();
		
		mapWidth = (float) (getMaxX()-getMinX());
		mapHeight = (float) (getMaxY()-getMinY());

		center = new Coordinate((float) (getMinX()+(getMapWidth())/2), (float) (getMinY()+(getMapHeight())/2));
		centroid = new Coordinate(MiscTools.average(xValues), MiscTools.average(yValues));
		
	}
	
	public void addNode(Coordinate coordinates, boolean isFirstPoint, Link fromLink) {
		boolean isRepeated = false;
		Node finalNode = null;
		
		for (Link link : links) {
			for (Node node : link.getNodes()) {
				if (coordinates.equals(node.getCoordinates())) {
					isRepeated = true;
					finalNode = node;
				}
			}
		}

		if (isRepeated) {
			//This node already exists, so update it.
			finalNode.getFromLinks().add(fromLink);
		} else {
			//This is a new node, so add it.
			finalNode = new Node(coordinates, nodes.size+1, fromLink);
			nodes.add(finalNode);
		}
		
		if (isFirstPoint) {
			fromLink.setFromNode(finalNode);
		} else { 
			fromLink.setToNode(finalNode);
		}
	}
		
	private void populateXYArrays() {
		if (!this.xValues.isEmpty()) this.xValues.clear();
		if (!this.yValues.isEmpty()) this.yValues.clear();
		
		for (Link link : this.links) {
			for (Coordinate coordinate : link.getCoordinates()) {
				this.xValues.add((double) coordinate.getX());
				this.yValues.add((double) coordinate.getY());
			}			
		}
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Json getJson() {
		return json;
	}

	public void setJson(Json json) {
		this.json = json;
	}

	public List<JSONFeatures> getFeatures() {
		return features;
	}

	public void setFeatures(List<JSONFeatures> features) {
		this.features = features;
	}

	public Array<Node> getNodes() {
		if (links.size == 0) {
			getLinks();
		}
		return nodes;
	}

	public void setNodes(Array<Node> nodes) {
		this.nodes = nodes;
	}
	
	public Array<Link> getLinks() {
		return links;
	}
	
	public void setLinks(Array<Link> links) {
		this.links = links;
	}

	public double getMaxX() {
		return Collections.max(xValues);
	}

	public double getMaxY() {
		return Collections.max(yValues);
	}

	public double getMinX() {
		return Collections.min(xValues);
	}

	public double getMinY() {
		return Collections.min(yValues);
	}

	public Array<Link> getInFlowLinks() {
		return inFlowLinks;
	}

	public void setInFlowLinks(Array<Link> inFlowLinks) {
		this.inFlowLinks = inFlowLinks;
	}

	public List<Double> getxValues() {
		return xValues;
	}

	public List<Double> getyValues() {
		return yValues;
	}

	public double getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(double mapWidth) {
		this.mapWidth = mapWidth;
	}

	public double getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(double mapHeight) {
		this.mapHeight = mapHeight;
	}

	public Coordinate getCentroid() {
		return centroid;
	}

	public void setCentroid(Coordinate centroid) {
		this.centroid = centroid;
	}

	public Coordinate getCenter() {
		return center;
	}

	public void setCenter(Coordinate center) {
		this.center = center;
	}

	public MapPreview getMapPreview() {
		return mapPreview;
	}

	public void setMapPreview(MapPreview mapPreview) {
		this.mapPreview = mapPreview;
	}

	public Array<Integer> getzLevels() {
		return zLevels;
	}

	public void setzLevels(Array<Integer> zLevels) {
		this.zLevels = zLevels;
	}

}
