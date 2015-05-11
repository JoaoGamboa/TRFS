package com.TRFS.scenarios.map;

import com.TRFS.scenarios.editor.LaneGeometryUtils;
import com.TRFS.scenarios.editor.LinkAttributes;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.badlogic.gdx.utils.Array;

public class Link {

	// Coordinates
	public Array<Coordinate> coordinates;

	// Properties
	public int internalID, hierarchy, inFlow, maxspeed, nrOfLanes, oneway, z;

	public DynamicSimParam inFlowParam;

	public float laneWidth, atraction;
	public int laneCapacity;

	// Neighbourhood
	public Node fromNode, toNode;

	// Lanes
	public Array<Lane> lanes;

	public Link() {	}
	
	public void finalizeBuild() {
		
		if (inFlow > 0) {
			inFlowParam = new DynamicSimParam("Desired Flow " + internalID, 0, 4000, inFlow, 1, "####", "Veh/h");
		}

		// Lanes
		lanes = new Array<Lane>();
		for (int i = 0; i < this.nrOfLanes; i++) {
			this.lanes.add(new Lane(i));
		}
		
	}
	
	public void setAttributes(int internalID, int hierarchy, int inFlow, int nrOfLanes, int maxspeed, int oneway, int z) {
		this.internalID = internalID;
		this.hierarchy = hierarchy;
		this.inFlow = inFlow;
		this.nrOfLanes = nrOfLanes;
		this.maxspeed = maxspeed;
		this.oneway = oneway;
		this.z = z;
		LinkAttributes.handleHierarchy(this);
	}


	public void setCoordinates(Array<Coordinate> coordinates) {
		this.coordinates = coordinates;
		LaneGeometryUtils.makeLaneGeometry(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)	return true;
		if (obj == null)	return false;
		if (getClass() != obj.getClass())	return false;
		
		Link other = (Link) obj;
		if (internalID != other.internalID)
			return false;
		return true;
	}
}
