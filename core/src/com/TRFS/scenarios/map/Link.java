package com.TRFS.scenarios.map;

import java.util.ArrayList;

import com.TRFS.scenarios.editor.LinkGeometry;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Link {
	
	//Coordinates
	private ArrayList<Coordinate> coordinates;
	private Array<Array<Vector2>> wayPoints;
	private Array<Vector2> triStrip;
	private Array<Coordinate> leftOffset;
	private Array<Coordinate> rightOffset;
	
	//Properties
	private int internalID;
	private int hierarchy;
	private int inFlow;
	private int maxspeed;
	private int nrOfLanes;
	private int oneway;
	private int z;
	
	private DynamicSimParam inFlowParam;
	
	//private Boolean isEntrance;
	private float laneWidth;
	private float linkWidth;
	private int laneCapacity;
	private float lenght;

	//Neighbourhood
	private Node fromNode;
	private Node toNode;
	private ArrayList<Node> nodes;

	//Vehicles
	//private Array<Array<Vehicle>> vehicles;
	
	//Lanes
	private Array<Lane> lanes;
	
	public Link(int internalID, int hierarchy, int inFlow,	int nrOfLanes, int maxspeed, int oneway, int z) {
		this.internalID = internalID;
		this.hierarchy = hierarchy;
		this.inFlow = inFlow;
		this.nrOfLanes = nrOfLanes;
		this.maxspeed = maxspeed;
		this.oneway = oneway;
		this.z = z;
		
		this.nodes = new ArrayList<Node>();
		
		if(inFlow > 0) {
			//this.isEntrance = true;
			inFlowParam = new DynamicSimParam("Desired Flow "+ internalID, 0, 4000,	inFlow, 1, "####", "Veh/h");
		}		
		
		LinkGeometry.handleHierarchy(this);
		
		//General Geometry
		this.leftOffset = new Array<Coordinate>();
		this.rightOffset = new Array<Coordinate>();
		this.triStrip = new Array<Vector2>();
		
		//Lanes
		lanes = new Array<Lane>();
		wayPoints = new Array<Array<Vector2>>();
		for (int i = 0; i < this.nrOfLanes; i++) {
			wayPoints.add(new Array<Vector2>());
			this.lanes.add(new Lane());
		}
		
	}
	
	public void renderDebugLines(ShapeRenderer shapeRenderer) {
		
		for (int i = 0; i < this.coordinates.size(); i++) {
			int next = (i + 1) % this.coordinates.size();
			//Centerline
			if (next != 0) shapeRenderer.line(this.coordinates.get(i).getX(), this.coordinates.get(i).getY(), 
												this.coordinates.get(next).getX(), this.coordinates.get(next).getY());
			//Left Offset	
			if (next != 0) shapeRenderer.line(this.getLeftOffset().get(i).getX(), this.getLeftOffset().get(i).getY(), 
												this.getLeftOffset().get(next).getX(), this.getLeftOffset().get(next).getY());
			//Right Offset
			if (next != 0) shapeRenderer.line(this.getRightOffset().get(i).getX(), this.getRightOffset().get(i).getY(), 
												this.getRightOffset().get(next).getX(), this.getRightOffset().get(next).getY());
			//Perpendiculars
			shapeRenderer.line(this.getLeftOffset().get(i).getX(), this.getLeftOffset().get(i).getY(), 
								this.getCoordinates().get(i).getX(), this.getCoordinates().get(i).getY());
			
			shapeRenderer.line(this.getRightOffset().get(i).getX(), this.getRightOffset().get(i).getY(), 
								this.getCoordinates().get(i).getX(), this.getCoordinates().get(i).getY());
		}	
	}
	
	public void renderDebugPoints (ShapeRenderer shapeRenderer) {
		for (int i = 0; i < this.coordinates.size(); i++) 
			shapeRenderer.circle(this.coordinates.get(i).getX(), this.coordinates.get(i).getY(), 0.5f);
	}
		
	public void calculateLegnth() {
		this.lenght = 0;
		for (int i = 0; i < coordinates.size()-1; i++) {
			Coordinate c0 = coordinates.get(i);
			Coordinate c1 = coordinates.get(i + 1);
			float dist = (float) Math.sqrt(Math.pow(c1.getX() - c0.getX(), 2) + Math.pow(c1.getY() - c0.getY(), 2));
			this.lenght += dist;
		}	
	}
	
	/*public void addVehicle(Vehicle vehicle, int lane) {
		this.lanes.get(lane).getVehicles().add(vehicle);
	}*/
	
	public Array<Lane> getLanes() {
		return lanes;
	}

	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}
	
	public float getLenght() {
		return lenght;
	}

	public int getNrOfLanes() {
		return nrOfLanes;
	}

	public void setNrOfLanes(int nrOfLanes) {
		this.nrOfLanes = nrOfLanes;
	}

	public void setCoordinates(ArrayList<Coordinate> coordinates) {
		this.coordinates = LinkGeometry.smoothGeometry(coordinates, 4, 1);
		calculateLegnth();
		
		LinkGeometry.makeLinkOffsets(this);
		LinkGeometry.makeLaneGeometry(this);
	}
	
	public int getInternalID() {
		return internalID;
	}
	
	public float getLinkWidth() {
		return linkWidth;
	}
	
	public void setLinkWidth(float linkWidth) {
		this.linkWidth = linkWidth;
	}
			
	public int getHierarchy() {
		return hierarchy;
	}
	
	public void setHierarchy(int hierarchy) {
		this.hierarchy = hierarchy;
	}
	
	public int getInFlow() {
		return inFlow;
	}
	
	public void setInFlow(int inFlow) {
		this.inFlow = inFlow;
	}
		
	public int getMaxspeed() {
		return maxspeed;
	}
	
	public void setMaxspeed(int maxspeed) {
		this.maxspeed = maxspeed;
	}
	
	public int getOneway() {
		return oneway;
	}
	
	public void setOneway(int oneway) {
		this.oneway = oneway;
	}
	
	public Node getFromNode() {
		return fromNode;
	}
	
	public void setFromNode(Node fromNode) {
		this.nodes.add(fromNode);
		this.fromNode = fromNode;
	}
	
	public Node getToNode() {
		return toNode;
	}
	
	public void setToNode(Node toNode) {
		this.nodes.add(toNode);
		this.toNode = toNode;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public float getLaneWidth() {
		return laneWidth;
	}

	public void setLaneWidth(float laneWidth) {
		this.laneWidth = laneWidth;
	}

	public int getLaneCapacity() {
		return laneCapacity;
	}

	public void setLaneCapacity(int laneCapacity) {
		this.laneCapacity = laneCapacity;
	}

	public Array<Vector2> getTriStrip() {
		return triStrip;
	}

	public void setTriStrip(Array<Vector2> triStrip) {
		this.triStrip = triStrip;
	}

	public Array<Coordinate> getLeftOffset() {
		return leftOffset;
	}

	public void setLeftOffset(Array<Coordinate> leftOffset) {
		this.leftOffset = leftOffset;
	}

	public Array<Coordinate> getRightOffset() {
		return rightOffset;
	}

	public void setRightOffset(Array<Coordinate> rightOffset) {
		this.rightOffset = rightOffset;
	}
	
	public Array<Array<Vector2>> getWayPoints() {
		return wayPoints;
	}

	public void setWayPoints(Array<Array<Vector2>> wayPoints) {
		this.wayPoints = wayPoints;
	}

	public DynamicSimParam getInFlowParam() {
		return inFlowParam;
	}

	@Override
	public String toString() {
		return "Link [coordinates=" + coordinates + ", internalID="
				+ internalID + ", hierarchy=" + hierarchy
				+ ", inFlow=" + inFlow + ", lanes=" + lanes + ", maxspeed="
				+ maxspeed + ", oneway=" + oneway + ", fromNode=" + fromNode
				+ ", toNode=" + toNode + ", nodes=" + nodes + ", isEntrance="
				+ /*isEntrance + */"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fromNode == null) ? 0 : fromNode.hashCode());
		result = prime * result + internalID;
		result = prime * result + ((toNode == null) ? 0 : toNode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (fromNode == null) {
			if (other.fromNode != null)
				return false;
		} else if (!fromNode.equals(other.fromNode))
			return false;
		if (internalID != other.internalID)
			return false;
		if (toNode == null) {
			if (other.toNode != null)
				return false;
		} else if (!toNode.equals(other.toNode))
			return false;
		return true;
	}
}
