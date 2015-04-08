package com.TRFS.scenarios.map;

import com.TRFS.scenarios.editor.LaneGeometryUtils;
import com.TRFS.scenarios.editor.LinkAttributes;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.badlogic.gdx.utils.Array;

public class Link {

	// Coordinates
	private Array<Coordinate> coordinates;

	// Properties
	private int internalID, hierarchy, inFlow, maxspeed, nrOfLanes, oneway, z;

	private DynamicSimParam inFlowParam;

	private float laneWidth;
	private int laneCapacity;

	// Neighbourhood
	private Node fromNode, toNode;

	// Lanes
	private Array<Lane> lanes;

	public Link(int internalID, int hierarchy, int inFlow, int nrOfLanes,
			int maxspeed, int oneway, int z) {
		this.internalID = internalID;
		this.hierarchy = hierarchy;
		this.inFlow = inFlow;
		this.nrOfLanes = nrOfLanes;
		this.maxspeed = maxspeed;
		this.oneway = oneway;
		this.z = z;

		LinkAttributes.handleHierarchy(this);
		
		if (inFlow > 0) {
			inFlowParam = new DynamicSimParam("Desired Flow " + internalID, 0,
					4000, inFlow, 1, "####", "Veh/h");
		}

		// Lanes
		lanes = new Array<Lane>();
		for (int i = 0; i < this.nrOfLanes; i++) {
			this.lanes.add(new Lane());
		}
	}

	/*public void calculateLegnth() {
		this.lenght = 0;
		for (int i = 0; i < coordinates.size - 1; i++) {
			Coordinate c0 = coordinates.get(i);
			Coordinate c1 = coordinates.get(i + 1);
			float dist = (float) Math.sqrt(Math.pow(c1.getX() - c0.getX(), 2)
					+ Math.pow(c1.getY() - c0.getY(), 2));
			this.lenght += dist;
		}
	}*/

	public Array<Lane> getLanes() {
		return lanes;
	}

	public Array<Coordinate> getCoordinates() {
		return coordinates;
	}

	public int getNrOfLanes() {
		return nrOfLanes;
	}

	public void setNrOfLanes(int nrOfLanes) {
		this.nrOfLanes = nrOfLanes;
	}

	public void setCoordinates(Array<Coordinate> coordinates) {
		this.coordinates = coordinates;
		//calculateLegnth();
		LaneGeometryUtils.makeLaneGeometry(this);
	}

	public int getInternalID() {
		return internalID;
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
		this.fromNode = fromNode;
	}

	public Node getToNode() {
		return toNode;
	}

	public void setToNode(Node toNode) {
		this.toNode = toNode;
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

	public DynamicSimParam getInFlowParam() {
		return inFlowParam;
	}

	@Override
	public String toString() {
		return "Link [coordinates=" + coordinates + ", internalID="
				+ internalID + ", hierarchy=" + hierarchy + ", inFlow="
				+ inFlow + ", lanes=" + lanes + ", maxspeed=" + maxspeed
				+ ", oneway=" + oneway + ", fromNode=" + fromNode + ", toNode="
				+ toNode + ", isEntrance=" + "]";
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
