package com.TRFS.scenarios.map;

import com.TRFS.scenarios.editor.LaneGeometryUtils;
import com.TRFS.scenarios.editor.LinkAttributes;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.badlogic.gdx.utils.Array;

public class Link {

	public Array<Coordinate> coordinates;
	public int internalID, hierarchy, inFlow, maxspeed, nrOfLanes, oneway, z, laneCapacity;
	public DynamicSimParam inFlowParam;
	public float laneWidth, flowAtraction;
	public Node fromNode, toNode;
	public Array<Lane> lanes;

	public Link() {	
		
	}
	
	public void finalizeBuild() {
		
		if (inFlow > 0) 
			inFlowParam = new DynamicSimParam("Desired Flow " + internalID, 0, 4000, inFlow, 1, "####", "Veh/h");

		this.lanes = new Array<Lane>();
		for (int i = 0; i < this.nrOfLanes; i++) {
			this.lanes.add(new Lane(i));
		}
		
		LaneGeometryUtils.makeLaneGeometry(this);
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
