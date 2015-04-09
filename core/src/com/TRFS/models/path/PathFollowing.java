package com.TRFS.models.path;

import com.TRFS.scenarios.map.Path;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author J.P.Gamboa jpgamboa@outlook.com
 *
 */
public class PathFollowing {

	private Path path;
	private PathFollowingState state;

	private Vector2 targetPosition;
	private float targetOffset, predictionTime;

	public PathFollowing() {
		this.state = new PathFollowingState();
		this.targetPosition = new Vector2();
	}

	public void update(Vector2 agentPosition) {
		
		path.updateTargetPosition(agentPosition, targetPosition, state, targetOffset);
		// TODO set aim to the target
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
