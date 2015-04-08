package com.TRFS.scenarios.map;

import com.badlogic.gdx.ai.steer.utils.paths.LinePath.Segment;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Path {
	private Array<PathSegment> segments;
	//private int pointCount;
	private float length;
	//Temporary vectors
	private Vector2 tmp1, tmp2, tmp3;
	
	public Path(Array<Vector2> wayPoints) {
		
		createSegments(wayPoints);
		initTempVectors();
		
		
		
	}
	
	public PathSegment getSegmentFromDistanceTraveled(float distanceTraveled) {
		/*
		// clip or wrap given path distance according to cyclic flag
	    float remaining = pathDistance;
	    if (cyclic)
	    {
	        remaining = fmod (pathDistance, totalPathLength);
	    }
	    else
	    {
	        if (pathDistance < 0) remaining = 0;
	        if (pathDistance > totalPathLength) remaining = totalPathLength;
	    }

	    // step through segments, subtracting off segment lengths until
	    // locating the segment that contains the original pathDistance.
	    // Interpolate along that segment to find 3d point value to return.
	    Vec3 result;
	    for (int i = 1; i < pointCount; i++)
	    {
	        segmentLength = lengths[i];
	        if (segmentLength < remaining)
	        {
	            remaining -= segmentLength;
	        }
	        else
	        {
	            float ratio = remaining / segmentLength;
	            result = interpolate (ratio, points[i-1], points[i]);
	            break;
	        }
	    }
	    return result;*/
	    
		return segments.get(index);
	}
	
	public float getDistanceFromPoint() {
		float distance = 0;
		
		return distance;
	}
	
	public float getLength() {
		return length;
	}
	
	public Vector2 getStartPoint() {
		return segments.first().start;
	}
	public Vector2 getFinishPoint() {
		return segments.peek().finish;
	}
	
	private void initTempVectors() {
		tmp1 = new Vector2(0, 0);
		tmp2 = new Vector2(0, 0);
		tmp3 = new Vector2(0, 0);
	}
	
	private void createSegments(Array<Vector2> wayPoints){
		segments = new Array<Path.PathSegment>();
		
		Vector2 previous = null;
		Vector2 current = wayPoints.first();
		for (int i = 1; i < wayPoints.size; i++) {
			previous = current;
			current = wayPoints.get(i);
			
			PathSegment segment = new PathSegment(current, previous);
			length += segment.length;
			segment.cumulativeLength = length;
			segments.add(segment);
					
		}
	}
	
	
	
	
	public class PathSegment {
		
		Vector2 start, finish;
		float length, cumulativeLength;
		
		/**
		 * Connects two consecutive waypoints of a {@link Path}
		 * @param start
		 * @param finish
		 */
		public PathSegment(Vector2 start, Vector2 finish) {
			this.start = start;
			this.finish = finish;
			this.length = start.dst(finish);
		}

		public Vector2 getStart() {
			return start;
		}

		public Vector2 getFinish() {
			return finish;
		}

		public float getLength() {
			return length;
		}

		public float getCumulativeLength() {
			return cumulativeLength;
		}
		
	}

}
