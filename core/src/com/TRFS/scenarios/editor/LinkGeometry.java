package com.TRFS.scenarios.editor;

import java.util.ArrayList;

import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.TRFS.simulator.SimulationParameters;
import com.badlogic.gdx.math.Vector2;

public class LinkGeometry {
	
	public static ArrayList<Coordinate> simplifyGeometry(ArrayList<Coordinate> coordinates, float tolerance) {
		if (SimulationParameters.simplifyGeometry) {
			ArrayList<Coordinate> newCoordinates = new ArrayList<Coordinate>(); 
						
			Coordinate point;
			Coordinate prevPoint = coordinates.get(0);
			
			newCoordinates.add(prevPoint);
			
		    for (int i = 1; i < coordinates.size()-1; i++) {
		        point = coordinates.get(i);
		        
		        float dx = point.getX() - prevPoint.getX(), 
		        	  dy = point.getY() - prevPoint.getY();
		        float distSQ = dx * dx + dy * dy;
		        
		        if (distSQ > tolerance) {
		        	newCoordinates.add(point);
		            prevPoint = point;
		        }
		    }
		    newCoordinates.add(coordinates.get(coordinates.size()-1));
			return newCoordinates;
		} else { return coordinates; }
	}
	
	public static ArrayList<Coordinate> smoothGeometry(ArrayList<Coordinate> coordinates, int iterations, int method) {
		coordinates = simplifyGeometry(coordinates, 80f);
		if (SimulationParameters.smoothGeometry) {
			ArrayList<Coordinate> newCoordinates = new ArrayList<Coordinate>(); 
			
			if (iterations > 0) {
				if (method == 1) {
					if (coordinates.size() > 2) {
						//Add First Point
						newCoordinates.add(new Coordinate(coordinates.get(0).getX(), coordinates.get(0).getY()));
						
						for (int j = 0; j < coordinates.size()-1; j++) {
							
							Coordinate p0 = coordinates.get(j);
							Coordinate p1 = coordinates.get(j+1);
							
							//Create two new points between p0 and p1 with Chaikin's smoothing algorithm and add to new array
							Coordinate n1 = new Coordinate(0.75f * p0.getX() + 0.25f * p1.getX(), 0.75f * p0.getY() + 0.25f * p1.getY());
							Coordinate n2 = new Coordinate(0.25f * p0.getX() + 0.75f * p1.getX(), 0.25f * p0.getY() + 0.75f * p1.getY());
							
							newCoordinates.add(n1);
							newCoordinates.add(n2);
						}	
						
						//Add last point
						newCoordinates.add(new Coordinate(coordinates.get(coordinates.size()-1).getX(), 
								coordinates.get(coordinates.size()-1).getY()));
					} else newCoordinates = coordinates;	
				}
			} else newCoordinates = coordinates;
			
			return newCoordinates;
		} else {return coordinates;}
	}
	
	public static void makeLinkOffsets(Link link) {
		// TODO Eliminate leftOffset and rightOffset on link, rely on lanes for drawing
		
		ArrayList<Coordinate> originalCoordinates = link.getCoordinates();
						
		Vector2 vector = new Vector2();	
		
		for (int i = 0; i < originalCoordinates.size(); i++) {
			int next = (i + 1) % originalCoordinates.size();
			
			Coordinate c0 = originalCoordinates.get(i);
			
			//Last point will use the same vector as the point before
			if (next != 0) {
				Coordinate c1 = originalCoordinates.get(i+1);
				vector.set(c1.getX(), c1.getY()).sub(c0.getX(), c0.getY()).nor();
				
			} else {
				Coordinate c1 = originalCoordinates.get(i-1);
				vector.set(c0.getX(), c0.getY()).sub(c1.getX(), c1.getY()).nor();
				
			}
			
			vector.scl(link.getLinkWidth()/2);
			
			//Left Perpendicular
			vector.set(-vector.y, vector.x);
			link.getLeftOffset().add(new Coordinate(c0.getX() + vector.x, c0.getY() + vector.y));
			
			//Right Perpendicular
			vector.set(-vector.x, -vector.y);
			link.getRightOffset().add(new Coordinate(c0.getX() + vector.x, c0.getY() + vector.y));
			
			
		}
	}
	
	public static void makeLaneGeometry(Link link){
		
		ArrayList<Coordinate> originalCoordinates = link.getCoordinates();
		
		for (int l = 0; l < link.getLanes().size; l++) {
			Lane lane = link.getLanes().get(l);
			
			int laneNr = l + 1;

			float rightOffset;
			float leftOffset;
			float centreOffset;
			
			if (link.getLanes().size % 2 != 0) {//ODD
				int centrelane = (link.getLanes().size + 1) / 2;
				int difToCenter = centrelane - laneNr;
				
				rightOffset = difToCenter*link.getLaneWidth() + link.getLaneWidth()/2;
				leftOffset = difToCenter*link.getLaneWidth() - link.getLaneWidth()/2;
				centreOffset = difToCenter*link.getLaneWidth();
				
			} else {//EVEN
				int lastRight = link.getLanes().size/2;
				int difTolastRight = lastRight - laneNr;
				
				rightOffset = (difTolastRight + 1)*link.getLaneWidth();
				leftOffset = (difTolastRight)*link.getLaneWidth();
				centreOffset = (difTolastRight + 1/2f)*link.getLaneWidth();
			}

			Vector2 vector = new Vector2();	
			
			for (int i = 0; i < originalCoordinates.size(); i++) {
				int next = (i + 1) % originalCoordinates.size();
				
				Coordinate c0 = originalCoordinates.get(i);
				
				//Last point will use the same vector as the point before
				if (next != 0) {
					Coordinate c1 = originalCoordinates.get(i+1);
					vector.set(c1.getX(), c1.getY()).sub(c0.getX(), c0.getY()).nor();
					
				} else {
					Coordinate c1 = originalCoordinates.get(i-1);
					vector.set(c0.getX(), c0.getY()).sub(c1.getX(), c1.getY()).nor();
					
				}
								
				//Waypoints
				Vector2 v0 = new Vector2(vector);				
				v0.set(v0.y, -v0.x).nor().scl(centreOffset);
				lane.getWayPoints().add(new Vector2(c0.getX() + v0.x, c0.getY() + v0.y));
				
				//Right Perpendicular
				Vector2 v1 = new Vector2(vector);
				v1.set(v1.y, -v1.x).nor().scl(rightOffset);
				lane.getRightOffset().add(new Coordinate(c0.getX() + v1.x, c0.getY() + v1.y));
				
				//Left Perpendicular
				Vector2 v2 = new Vector2(vector);
				v2.set(v2.y, -v2.x).nor().scl(leftOffset);
				lane.getLeftOffset().add(new Coordinate(c0.getX() + v2.x, c0.getY() + v2.y));
			}	
		}		
	}
	
	
	public static void handleHierarchy(Link link) {
		int hierarchy = link.getHierarchy();
		
		//defaults
		float laneWidth = 3.0f;
		int lanes = 1;
		int laneCapacity = 600;
		int maxSpeed = 70;
		
		switch (hierarchy) {
			case 1:
				laneWidth = 3.6f;
				maxSpeed = 120;
				laneCapacity = 1200;
				lanes = 3;
				break;
			case 2:
				laneWidth = 3.6f;
				maxSpeed = 100;
				laneCapacity = 1000;
				lanes = 2;
				break;
			case 3:
				laneWidth = 3.4f;
				maxSpeed = 90;
				laneCapacity = 800;
				lanes = 2;
				break;
			case 4:
				laneWidth = 3.0f;
				maxSpeed = 70;
				laneCapacity = 600;
				lanes = 1;
				break;
			case 5:
				laneWidth = 2.5f;
				maxSpeed = 50;
				laneCapacity = 400;
				lanes = 1;
				break;
		}
		
		if (link.getHierarchy() == 0 ) link.setHierarchy(4);
		if (link.getNrOfLanes() == 0) link.setNrOfLanes(lanes);
		if (link.getMaxspeed() == 0) link.setMaxspeed(maxSpeed);
		
		link.setLaneWidth(laneWidth);
		link.setLinkWidth(link.getNrOfLanes() * laneWidth);
		link.setLaneCapacity(laneCapacity);

	}
	
	
	
}
