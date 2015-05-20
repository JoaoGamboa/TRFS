package com.TRFS.scenarios.map;

import com.TRFS.scenarios.editor.LaneGeometryUtils;
import com.TRFS.scenarios.editor.LinkAttributes;
import com.TRFS.scenarios.editor.LinkGeometryUtils;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Link {

	public Array<Coordinate> coordinates;
	public int id, hierarchy, maxspeed, nrOfLanes, oneway, z, laneCapacity, inFlow = 0;
	public DynamicSimParam inFlowParam;
	public float cost, laneWidth, flowAtraction;
	public Node fromNode, toNode;
	public Array<Lane> lanes;
	public Vector2 startHeading, finishHeading, tmp;

	public Link() {	
		this.startHeading = new Vector2();
		this.finishHeading = new Vector2();
		this.tmp = new Vector2();
	}
	
	public void finalizeBuild() {
		
		if (cost == 0 && coordinates != null) cost = LinkGeometryUtils.length(this);
		
		if (inFlow > 0 & inFlowParam == null) 
			inFlowParam = new DynamicSimParam("Desired Flow " + id, 0, 4000, inFlow, 1, "####", "Veh/h");
		
		if (lanes == null) {
			lanes = new Array<Lane>();
			for (int i = 0; i < this.nrOfLanes; i++) {
				this.lanes.add(new Lane(i));
			}
		}
		
		// For comparison between links, both the start and ending heading are
		// calculated pointing outwards from the link they connect to
		int coordSize = coordinates.size;
		startHeading.set(coordinates.get(1).x - coordinates.get(0).x,
				coordinates.get(1).y - coordinates.get(0).y);
		finishHeading.set(coordinates.get(coordSize - 2).x - coordinates.get(coordSize-1).x,
						coordinates.get(coordSize - 2).y - coordinates.get(coordSize-1).y);

		LaneGeometryUtils.makeLaneGeometry(this);
	}
	
	public void setAttributes(int internalid, int hierarchy, int inFlow, int nrOfLanes, int maxspeed, int oneway, int z) {
		this.id = internalid;
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
	
	/**Checks if this Link has priority over the provided Link according to the priority-to-the-right rule and l.
	 * TODO Once traffic signs are implemented, check for those to decide the priority.
	 * @param link
	 * @return
	 */
	public boolean hasPriorityOver(Link link) {
		if (this.hierarchy > link.hierarchy) {
			return true;
		} else if (this.hierarchy < link.hierarchy) {
			return false;
		} else {
			return toTheRightOf(link);
		}
	}
	
	public boolean toTheRightOf(Link link) {
		//The finish heading vector points inwards from the end Node so right is left in this case
		if (this.finishHeading.dot(tmp) < 0) return true; 
		tmp.set(link.finishHeading).rotate(90);
		return false;
	}
	
	public boolean toTheLeftOf(Link link) {
		//The finish heading vector points inwards from the end Node so left is right in this case
		tmp.set(link.finishHeading).rotate(90);
		if (this.finishHeading.dot(tmp) > 0) return true;
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)	return true;
		if (obj == null)	return false;
		if (getClass() != obj.getClass())	return false;
		Link other = (Link) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public boolean quickEquals(Link other) {
		if (id != other.id)	return false;
		return true;
	}
}
