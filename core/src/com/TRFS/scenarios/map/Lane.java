package com.TRFS.scenarios.map;

import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Lane {
	
	//private Array<Vehicle> vehicles;	
	
	private Array<Coordinate> leftOffset, rightOffset;
	private Array<Vector2> wayPoints;
	
	public Lane() {
		wayPoints = new Array<Vector2>();
		leftOffset = new Array<Coordinate>();
		rightOffset = new Array<Coordinate>();
		//vehicles = new Array<Vehicle>();
		
		
	}

	/*public Array<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(Array<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}*/
	
	public void renderDegubLines(ShapeRenderer shapeRenderer){
		for (int i = 0; i < this.wayPoints.size; i++) {
			int next = (i + 1) % this.wayPoints.size;
			//Centerline
			//if (next != 0) shapeRenderer.line(this.wayPoints.get(i).x, this.wayPoints.get(i).y,this.wayPoints.get(next).x, this.wayPoints.get(next).y);
			//Left Offset	
			if (next != 0) shapeRenderer.line(this.getLeftOffset().get(i).getX(), this.getLeftOffset().get(i).getY(), this.getLeftOffset().get(next).getX(), this.getLeftOffset().get(next).getY());
			//Right Offset
			if (next != 0) shapeRenderer.line(this.getRightOffset().get(i).getX(), this.getRightOffset().get(i).getY(), this.getRightOffset().get(next).getX(), this.getRightOffset().get(next).getY());
			
		}	
	}
	
	public void renderWayPoints(ShapeRenderer shapeRenderer) {
		for (int i = 0; i < wayPoints.size; i++) 
			shapeRenderer.circle(wayPoints.get(i).x, wayPoints.get(i).y, 0.5f);
	}

	public Array<Vector2> getWayPoints() {
		return wayPoints;
	}

	public void setWayPoints(Array<Vector2> wayPoints) {
		this.wayPoints = wayPoints;
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

}
