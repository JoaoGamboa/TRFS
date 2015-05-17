package com.TRFS.scenarios.map;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Node {
	
	public int id, zLevel;
	public Array<Link> fromLinks, toLinks;
	public Array<Array<Link>> availablePaths;
 	public Coordinate coordinate;
	public boolean networkEntrance, networkExit;
		
	public Node (Coordinate coordinates, int internalID, Link sourceLink) {
		this.coordinate = coordinates;
		this.id = internalID;
		this.fromLinks = new Array<Link>();
		this.toLinks = new Array<Link>();
		this.zLevel = sourceLink.z;
	}
	
	public void finalizeBuild() {
		if (fromLinks.size < 1) networkEntrance = true;
		if (toLinks.size < 1)   networkExit = true;
		
		if (networkEntrance && availablePaths == null) availablePaths = new Array<Array<Link>>();
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
		if (id != other.id)
			return false;
		return true;
	}
	
	public boolean quickEquals(Node other) {
		if (id != other.id) return false;
		return true;
	}

}
