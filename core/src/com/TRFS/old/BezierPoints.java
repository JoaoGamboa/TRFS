//package com.TRFS.old;
//
//import java.util.ArrayList;
//
//import com.TRFS.scenarios.map.Coordinate;
//
//public class BezierPoints {
//	
//	ArrayList<Coordinate> newCoordinates;
//
//	ArrayList<Coordinate> firstControlPoints;
//	ArrayList<Coordinate> secondControlPoints;
//	
//	public BezierPoints(ArrayList<Coordinate> coordinates, boolean keepKnots) {
//		int initialSize = coordinates.size();
//		
//		newCoordinates = new ArrayList<Coordinate>();
//		firstControlPoints = new ArrayList<Coordinate>();
//		secondControlPoints = new ArrayList<Coordinate>();
//				
//		//Add first point
//		Coordinate firstCoordinate = new Coordinate(coordinates.get(0).getX(), coordinates.get(0).getY());
//		newCoordinates.add(firstCoordinate);
//		
//		if(coordinates.get(0).equals(firstCoordinate)) { System.out.println("first Coordinate it's the same as old"); }
//		else { System.out.println("first Coordinate it's not the same as old"); }
//		
//		if (initialSize > 2) {
//						
//			float rightVector[] = new float[initialSize - 1];
//			rightVector[0] = coordinates.get(0).getX() + 2 * coordinates.get(1).getX();
//			for (int i = 1; i < initialSize - 2; i++) {
//				rightVector[i] = 4 * coordinates.get(i).getX() + 2 * coordinates.get(i + 1).getX();
//			}
//			rightVector[initialSize - 2] = 8 * coordinates.get(initialSize - 2).getX() + 2 * coordinates.get(initialSize - 1).getX();
//			
//			float x[] = getFirstControlPoints(rightVector);
//			
//			rightVector[0] = coordinates.get(0).getY() + 2 * coordinates.get(1).getY();
//			for (int i = 1; i < initialSize - 2; i++) {
//				rightVector[i] = 4 * coordinates.get(i).getY() + 2 * coordinates.get(i + 1).getY();
//			}
//			rightVector[initialSize - 2] = 8 * coordinates.get(initialSize - 2).getY() + 2 * coordinates.get(initialSize - 1).getY();
//			
//			float y[] = getFirstControlPoints(rightVector);
//			
//			for (int i = 0; i < initialSize - 2; ++i) {
//				
//				if (keepKnots && i != 0) {
//					Coordinate knot = new Coordinate(coordinates.get(i).getX(), coordinates.get(i).getY());
//					newCoordinates.add(knot);
//				
//					System.out.println("at index " + i + " we put the old knot: " + newCoordinates.get(i).toString());
//					if(coordinates.get(i).equals(knot)) { System.out.println("it's the same as old"); }
//					else { System.out.println("it's not the same as old"); }
//
//				}
//				
//				// First control point
//				Coordinate firstCP = new Coordinate(x[i], y[i]);
//				newCoordinates.add(firstCP);
//				
//				System.out.println("we add the first controlPoint: " + firstCP.toString());
//				
//				// Second control point
//				Coordinate secondCP = null;
//				if (i < initialSize - 1) {
//					secondCP = new Coordinate(2 * coordinates.get(i + 1).getX() - x[i + 1], 
//							coordinates.get(i+1).getY() - y[i+1]);
//				} else {
//					secondCP = new Coordinate((coordinates.get(initialSize - 1).getX() + x[initialSize - 2]) / 2, 
//														(coordinates.get(initialSize - 1).getY() + y[initialSize - 2]) / 2);
//				}
//				System.out.println("we add the second controlPoint: " + secondCP.toString());
//				newCoordinates.add(secondCP);
//			}
//			
//		}
//		
//		//Add last point
//		newCoordinates.add(new Coordinate(coordinates.get(initialSize-1).getX(), coordinates.get(initialSize-1).getY()));
//		System.out.println("last coordinate : " + coordinates.get(initialSize - 1).toString());
//		System.out.println("is now : " + newCoordinates.get(newCoordinates.size()-1).toString());
//		System.out.println("end of link");
//	}
//	
//	private static float[] getFirstControlPoints(float[] rightVector) {
//		int n = rightVector.length;
//		float x [] = new float[n];
//		float tmp [] = new float[n];
//		
//		float b = 2.0f;
//		
//		x[0] = rightVector[0] / b;
//		
//		for (int i = 1; i < n; i++) {
//			tmp[i] = 1 / b;
//			b = (i < n - 1 ? 4.0f : 3.5f) - tmp[i];
//			x[i] = (rightVector[i] - x[i-1]) / b;
//		}
//		
//		for (int i = 1; i < n; i++) {
//			x[n - i - 1] -= tmp[n - i] * x[n - i];
//		}
//		
//		return x;
//	}
//
//	public ArrayList<Coordinate> getNewCoordinates() {
//		return newCoordinates;
//	}
//}
