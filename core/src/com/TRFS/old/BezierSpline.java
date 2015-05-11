//package com.TRFS.old;
//
//import java.util.ArrayList;
//
//import com.TRFS.scenarios.map.Coordinate;
//
//public class BezierSpline {
//	
//	private static final float AP = 0.5f;
//	private ArrayList<Coordinate> newCoordinates;
//	
//
//	public BezierSpline(ArrayList<Coordinate> oldCoordinates) {
//		int n = oldCoordinates.size();
//		
//		newCoordinates = new ArrayList<Coordinate>();
//		
//		//If there are less than 3 points, return the same points
//		if (n<3) {
//			for (Coordinate coordinate : oldCoordinates) {
//				newCoordinates.add(new Coordinate(coordinate.getX(), coordinate.getY()));
//			}
//			return;
//		}
//		
//		float p1X, p1Y;
//		float p2X = oldCoordinates.get(0).getX();
//		float p2Y = oldCoordinates.get(0).getY();
//		float p3X = oldCoordinates.get(1).getX();
//		float p3Y = oldCoordinates.get(1).getY();
//
//		for (int i = 0; i < n - 2; i++) {
//			p1X = p2X;
//	        p1Y = p2Y;
//	        p2X = p3X;
//	        p2Y = p3Y;
//		    p3X = oldCoordinates.get(i + 2).getX();
//		    p3Y = oldCoordinates.get(i + 2).getY();
//		    
//		    float l12X = p2X - p1X;
//		    float l12Y = p2Y - p1Y;
//		    float l13X = p3X - p1X;
//		    float l13Y = p3Y - p1Y;
//		    float l13 = (float) Math.sqrt(l13X * l13X + l13Y * l13Y);
//		    
//		    l13X = l13X /l13;
//		    l13Y = l13Y /l13;
//
//		    float proj = l12X * l13X + l12Y * l13Y;
//		    proj = proj < 0 ? -proj : proj;
//		    
//		    float apX = proj * l13X;
//		    float apY = proj * l13Y;
//
//		    float pAX = p2X - AP * apX;
//		    float pAY = p2Y - AP * apY;
//		    
//		    newCoordinates.add(2 * i, new Coordinate(pAX, pAY));
//
//		    l13X = -l13X;
//		    l13Y = -l13Y;
//		    float l32X = p2X - p3X;
//		    float l32Y = p2Y - p3Y;
//		    
//		    proj = l32X * l13X + l32Y * l13Y;
//		    proj = proj < 0 ? -proj : proj;
//		    
//		    apX = proj * l13X;
//		    apY = proj * l13Y;
//
//		    float pBX = p2X - AP * apX;
//		    float pBY = p2Y - AP * apY;
//		    newCoordinates.add(2 * i + 1, new Coordinate(pBX, pBY));			
//		}
//		
//	}
//
//	public ArrayList<Coordinate> getNewCoordinates() {
//		return newCoordinates;
//	}
//}
