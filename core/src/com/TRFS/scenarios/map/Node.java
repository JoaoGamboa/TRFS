package com.TRFS.scenarios.map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Node {
	
	public int internalID;
	public Array<Link> fromLinks;
	public Array<Link> toLinks;
	public Coordinate coordinate;
	public int zLevel;
	
	public Node (Coordinate coordinates, int internalID, Link fromLink) {
		this.coordinate = coordinates;
		this.internalID = internalID;
		
		this.fromLinks = new Array<Link>();
		this.toLinks = new Array<Link>();
		this.fromLinks.add(fromLink);
		this.zLevel = fromLink.z;
	}
	
	public void renderDebugPoints(ShapeRenderer shapeRenderer) {
		shapeRenderer.circle(this.coordinate.x, this.coordinate.y, 1f);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Node other = (Node) obj;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		if (internalID != other.internalID)
			return false;
		return true;
	}

}
