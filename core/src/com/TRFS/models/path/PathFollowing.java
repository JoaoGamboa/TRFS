package com.TRFS.models.path;

import com.TRFS.scenarios.map.Path;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * @author J.P.Gamboa jpgamboa@outlook.com
 *
 */
public class PathFollowing {

	private Path path;
	private PathFollowingState state;

	private Vector2 targetPosition, frontAxisToTarget;
	private float targetOffset=5f;

	public PathFollowing() {
		this.state = new PathFollowingState();
		this.targetPosition = new Vector2();
		this.frontAxisToTarget = new Vector2();
	}
	
	//linearAcceleration is here if needed to update in the future, but not yet used
	public float update(Vehicle vehicle, float linearAcceleration, float steerAngle) {
		path.updateTargetPosition(vehicle.physics.cgPosition, targetPosition, state, targetOffset);

		//Set aim to the target
		steerAngle = frontAxisToTarget.set(targetPosition).sub(vehicle.physics.faPosition).angleRad();
		
		return steerAngle * MathUtils.degRad;
	}

	public void setPath(Path path) {
		this.path = path;
		this.state.setUpdated(false);
	}

	public PathFollowingState getState() {
		return state;
	}

	/**Contains parameters regarding the current position of the agent on the
	 * path used to reduce the scope of the lookup when finding the current
	 * position.
	 * 
	 * @author J.P.Gamboa jpgamboa@outlook.com
	 *
	 */
	public class PathFollowingState {

		private int currentSegmentIndex;
		private float distanceOnPath;
		private Vector2 nearestPointOnPath;
		private boolean updated;

		public PathFollowingState() {
			this.nearestPointOnPath = new Vector2();
		}

		public Vector2 getNearestPointOnPath() {
			return nearestPointOnPath;
		}

		public void setNearestPointOnPath(Vector2 nearestPointOnPath) {
			this.nearestPointOnPath = nearestPointOnPath;
		}

		public int getCurrentSegmentIndex() {
			return currentSegmentIndex;
		}

		public void setCurrentSegmentIndex(int currentSegmentIndex) {
			this.currentSegmentIndex = currentSegmentIndex;
		}

		public float getDistanceUntilCurrentSegment() {
			return distanceOnPath;
		}

		public void setDistanceOnPath(float distanceOnPath) {
			this.distanceOnPath = distanceOnPath;
		}

		public boolean isUpdated() {
			return updated;
		}

		public void setUpdated(boolean updated) {
			this.updated = updated;
		}
	}

}
