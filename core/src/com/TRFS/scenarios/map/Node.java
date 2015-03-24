package com.TRFS.scenarios.map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Node {
	
	private int internalID;
	private Array<Link> fromLinks;
	private Array<Link> toLinks;
	private Coordinate coordinates;
	private int zLevel;
	
	public Node (Coordinate coordinates, int internalID, Link fromLink) {
		this.coordinates = coordinates;
		this.internalID = internalID;
		
		this.fromLinks = new Array<Link>();
		this.toLinks = new Array<Link>();
		this.fromLinks.add(fromLink);
		this.zLevel = fromLink.getZ();
	}
	
	public void renderDebugPoints(ShapeRenderer shapeRenderer) {
		shapeRenderer.circle(this.coordinates.getX(), this.coordinates.getY(), 1f);
	}

	public int getInternalID() {
		return internalID;
	}

	public Array<Link> getFromLinks() {
		return fromLinks;
	}

	public Array<Link> getToLinks() {
		return toLinks;
	}

	public Coordinate getCoordinates() {
		return coordinates;
	}

	public int getzLevel() {
		return zLevel;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coordinates == null) ? 0 : coordinates.hashCode());
		result = prime * result
				+ ((fromLinks == null) ? 0 : fromLinks.hashCode());
		result = prime * result + internalID;
		result = prime * result + ((toLinks == null) ? 0 : toLinks.hashCode());
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
		Node other = (Node) obj;
		if (coordinates == null) {
			if (other.coordinates != null)
				return false;
		} else if (!coordinates.equals(other.coordinates))
			return false;
		if (fromLinks == null) {
			if (other.fromLinks != null)
				return false;
		} else if (!fromLinks.equals(other.fromLinks))
			return false;
		if (internalID != other.internalID)
			return false;
		if (toLinks == null) {
			if (other.toLinks != null)
				return false;
		} else if (!toLinks.equals(other.toLinks))
			return false;
		return true;
	}

}
