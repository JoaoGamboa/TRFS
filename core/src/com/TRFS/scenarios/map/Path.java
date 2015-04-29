package com.TRFS.scenarios.map;

import com.TRFS.models.path.PathFollowing;
import com.TRFS.models.path.PathFollowing.PathFollowingState;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * A {@code Path} provides methods to assist on the {@link PathFollowing} model.
 * It's composed of a series of segments, each with two waypoints (
 * {@link Coordinate}). Adapted from Gdx.ai.
 * 
 * @author J.P.Gamboa jpgamboa@outlook.com
 */

public class Path {
	private Array<PathSegment> segments;
	private float length;
	// Temporary vectors
	private Vector2 temp1, temp2, temp3, temp4;

	/**
	 * @param wayPoints
	 */
	public Path(Array<Vector2> wayPoints) {

		createSegments(wayPoints);
		initTempVectors();

	}

	/**
	 * Updates the provided vector with the position of the target.
	 * 
	 */
	public void setTargetPosition(Vector2 vectorToUpdate, float targetDistance) {

	}

	/**
	 * Calculates the shortest distance (squared) between a segment (defined by
	 * it's start and finish points) and a given point. Also updates a given
	 * {@link Vector2} ({@code outNearestPoint}) with the position of the
	 * nearest point on the segment.
	 * 
	 * @param outNearestPoint
	 *            The vector to update with the nearest point position.
	 * @param segmentStart
	 * @param segmentFinish
	 * @param point
	 * @return The shortest squared distance.
	 */
	public float shortestSquareDistanceToSegment(Vector2 outNearestPoint,
			PathSegment pathSegment, Vector2 point) {
		temp1.set(pathSegment.start);
		temp2.set(pathSegment.finish);
		temp3.set(point);

		Vector2 distStartFinish = temp2.sub(pathSegment.start);
		float t = (temp3.sub(pathSegment.finish)).dot(distStartFinish)
				/ distStartFinish.len2();
		t = MathUtils.clamp(t, 0, 1);
		outNearestPoint.set(temp1.add(distStartFinish.scl(t)));

		temp1.set(outNearestPoint);
		Vector2 pointToNearest = temp1.sub(point);
		return pointToNearest.len2();
	}

	/**
	 * Calculates the shortest distance between a segment (defined by it's start
	 * and finish points) and a given point. Also updates a given
	 * {@link Vector2} ({@code outNearestPoint}) with the position of the
	 * nearest point on the segment.
	 * 
	 * @param outNearestPoint
	 *            The vector to update with the nearest point position.
	 * @param segmentStart
	 * @param segmentFinish
	 * @param point
	 * @return The shortest squared distance.
	 */
	public float shortestDistanceToSegment(Vector2 outNearestPoint,
			PathSegment pathSegment, Vector2 point) {
		return (float) Math.sqrt(shortestSquareDistanceToSegment(
				outNearestPoint, pathSegment, point));
	}

	/**
	 * Returns the nearest {@link PathSegment} to a given point and updates a
	 * provided vector with the coordinates of the nearest point on the path.
	 * 
	 * @param state
	 *            Contains the current segment index and the vector to update
	 *            with the nearest point position.
	 * @param point
	 * @return the nearest {@link PathSegment}
	 */
	public PathSegment findNearestSegment(PathFollowingState state,
			Vector2 point) {
		float smallestDistanceSq = Float.POSITIVE_INFINITY;

		int startIndex = state.isUpdated() ? state.getCurrentSegmentIndex() : 0;

		PathSegment nearestSegment = null;
		for (int i = startIndex; i < segments.size; i++) {
			float distanceSq = shortestSquareDistanceToSegment(temp4,
					segments.get(i), point);

			if (distanceSq < smallestDistanceSq) {
				state.getNearestPointOnPath().set(temp4);
				smallestDistanceSq = distanceSq;
				nearestSegment = segments.get(i);
				state.setCurrentSegmentIndex(i);
				state.setUpdated(true);
			}
		}

		return nearestSegment;
	}

	/**
	 * Returns the distance from the nearest point on path to the path start.
	 * Updates a provided vector with the coordinates of the nearest point on
	 * the path.
	 * 
	 * @param state
	 *            Contains the current segment index and the vector to update
	 *            with the nearest point position.
	 * @param point
	 * @return the distance from path start.
	 */
	public float distanceFromPathStart(PathFollowingState state, Vector2 point) {

		PathSegment nearestSegment = findNearestSegment(state, point);
		
		float distanceFromPathStart = nearestSegment.cumulativeLength
				- state.getNearestPointOnPath().dst(nearestSegment.finish);
		state.setDistanceOnPath(distanceFromPathStart);
		return distanceFromPathStart;
	}

	/**
	 * Updates the target position to be followed by the agent.
	 * 
	 * @param agentPosition
	 *            The position of the agent.
	 * @param targetPosition
	 *            The vector to be updated with the target position.
	 * @param state
	 *            The subclass containing the current path following parameters.
	 * @param targetOffset
	 *            The distance of the target from the agent.
	 */
	public void updateTargetPosition(Vector2 agentPosition,
			Vector2 targetPosition, PathFollowingState state, float targetOffset) {

		float targetDistance = distanceFromPathStart(state, agentPosition)
				+ targetOffset;
	
		if (targetDistance > length)
			targetDistance = length;
		if (targetDistance < 0)
			targetDistance = 0;

		PathSegment desiredSegment = getSegmentFromDistanceFromStart(state,
				targetDistance);
		
		float targetDistanceOnSegment = desiredSegment.cumulativeLength
				- targetDistance;

		targetPosition.set(desiredSegment.start).sub(desiredSegment.finish)
				.scl(targetDistanceOnSegment / desiredSegment.length)
				.add(desiredSegment.finish);
	}

	public PathSegment getSegmentFromDistanceFromStart(
			PathFollowingState state, float targetDistance) {
		for (int i = state.getCurrentSegmentIndex(); i < segments.size; i++) {
			PathSegment segment = segments.get(i);
			if (segment.cumulativeLength >= targetDistance) {
				return segment;
			}
		}
		return null;
	}

	public float getLength() {
		return length;
	}

	public Vector2 getStartPoint() {
		return segments.first().start;
	}
	
	public Vector2 getSecondPoint() {
		return segments.first().finish;
	}

	public Vector2 getFinishPoint() {
		return segments.peek().finish;
	}

	private void initTempVectors() {
		temp1 = new Vector2();
		temp2 = new Vector2();
		temp3 = new Vector2();
		temp4 = new Vector2();
	}

	private void createSegments(Array<Vector2> wayPoints) {
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
		 * 
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
