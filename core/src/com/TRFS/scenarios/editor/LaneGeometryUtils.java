package com.TRFS.scenarios.editor;

import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LaneGeometryUtils {
	
	public static void makeLaneGeometry(Link link){
		
		Array<Coordinate> originalCoordinates = link.coordinates;

		for (int l = 0; l < link.lanes.size; l++) {

			Lane lane = link.lanes.get(l);
			int laneNr = l + 1;

			float rightOffset;
			float leftOffset;
			float centreOffset;
			
			if (link.lanes.size % 2 != 0) {//ODD
				int centrelane = (link.lanes.size + 1) / 2;
				int difToCenter = centrelane - laneNr;
				
				rightOffset = difToCenter*link.laneWidth + link.laneWidth/2;
				leftOffset = difToCenter*link.laneWidth - link.laneWidth/2;
				centreOffset = difToCenter*link.laneWidth;
				
			} else {//EVEN
				int lastRight = link.lanes.size/2;
				int difTolastRight = lastRight - laneNr;
				
				rightOffset = (difTolastRight + 1)*link.laneWidth;
				leftOffset = (difTolastRight)*link.laneWidth;
				centreOffset = (difTolastRight + 1/2f)*link.laneWidth;
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
					vector.set(c1.x, c1.y).sub(c0.x, c0.y).nor();
					
				} else {
					Coordinate c1 = originalCoordinates.get(i-1);
					vector.set(c0.x, c0.y).sub(c1.x, c1.y).nor();
					
				}
				
				//CenterLine
				Vector2 v0 = new Vector2(vector);				
				v0.set(v0.y, -v0.x).nor().scl(centreOffset);
				centerLineC.add(new Coordinate(c0.x + v0.x, c0.y + v0.y));
				
				//Right Perpendicular
				Vector2 v1 = new Vector2(vector);
				v1.set(v1.y, -v1.x).nor().scl(rightOffset);
				leftOffsetC.add(new Coordinate(c0.x + v1.x, c0.y + v1.y));
				
				//Left Perpendicular
				Vector2 v2 = new Vector2(vector);
				v2.set(v2.y, -v2.x).nor().scl(leftOffset);
				rightOffsetC.add(new Coordinate(c0.x + v2.x, c0.y + v2.y));
				
			}	
			
			lane.setCenterLine(centerLineC);
			lane.leftOffset = leftOffsetC;
			lane.rightOffset = rightOffsetC;
		}		
	}

}
