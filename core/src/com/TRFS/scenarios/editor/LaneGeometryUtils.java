package com.TRFS.scenarios.editor;

import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LaneGeometryUtils {
	
	public static void makeLaneGeometry(Link link){
		
		Array<Coordinate> originalCoordinates = link.getCoordinates();

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

			Array<Coordinate> centerLineC = new Array<Coordinate>();
			Array<Coordinate> leftOffsetC = new Array<Coordinate>();
			Array<Coordinate> rightOffsetC = new Array<Coordinate>();
			
			for (int i = 0; i < originalCoordinates.size; i++) {
				int next = (i + 1) % originalCoordinates.size;
				Coordinate c0 = originalCoordinates.get(i);
				
				Vector2 vector = new Vector2();	
				//Last point will use the same vector as the point before
				if (next != 0) {
					Coordinate c1 = originalCoordinates.get(i+1);
					vector.set(c1.getX(), c1.getY()).sub(c0.getX(), c0.getY()).nor();
					
				} else {
					Coordinate c1 = originalCoordinates.get(i-1);
					vector.set(c0.getX(), c0.getY()).sub(c1.getX(), c1.getY()).nor();
					
				}
				
				//CenterLine
				Vector2 v0 = new Vector2(vector);				
				v0.set(v0.y, -v0.x).nor().scl(centreOffset);
				centerLineC.add(new Coordinate(c0.getX() + v0.x, c0.getY() + v0.y));
				
				//Right Perpendicular
				Vector2 v1 = new Vector2(vector);
				v1.set(v1.y, -v1.x).nor().scl(rightOffset);
				leftOffsetC.add(new Coordinate(c0.getX() + v1.x, c0.getY() + v1.y));
				
				//Left Perpendicular
				Vector2 v2 = new Vector2(vector);
				v2.set(v2.y, -v2.x).nor().scl(leftOffset);
				rightOffsetC.add(new Coordinate(c0.getX() + v2.x, c0.getY() + v2.y));
				
			}	
			
			lane.setCenterLine(centerLineC);
			lane.setLeftOffset(leftOffsetC);
			lane.setRightOffset(rightOffsetC);
		}		
	}

}
