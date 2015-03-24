package com.TRFS.scenarios.editor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class WaypointsCreator {
	
	private Array<Vector2> wayPoints;
	
	public WaypointsCreator() {
		this.wayPoints = new Array<Vector2>();
		
	}
		
	
	public Vector2 createVector(float x, float y) {
		Vector2 vector = new Vector2(x, y);
		return vector;		
	}
	
	
	public void addVector(Vector2 vector) {
		wayPoints.add(vector);
	}
	

}
