package com.TRFS.models.path;

import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Path;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author J.P.Gamboa jpgamboa@outlook.com
 *
 */
public class PathFollowing {

	public Path path;
	public PathFollowingState state;
	public Array<Link> linkSequence;
	private int currentLinkIndex = -1;
	
	private Vector2 targetPosition;
	private float targetOffset=5f;
	
	public PathFollowing() {
		this.state = new PathFollowingState();
		this.targetPosition = new Vector2();
		this.linkSequence = new Array<Link>();
	}
	
	//linearAcceleration is here if needed to update in the future, but not yet used
	public float update(Vehicle vehicle) {
		
		path.updateTargetPosition(vehicle.physics.position, targetPosition, state, targetOffset);		
		vehicle.targetPos.set(targetPosition); //TODO delete debug only
		
		//Set aim to the target
		float targetHeading = (float) Math.atan2(targetPosition.x - vehicle.physics.position.x, targetPosition.y - vehicle.physics.position.y);

		return -targetHeading;
	}

	public void setPath(Path path) {
		this.path = path;
		this.currentLinkIndex++;
		this.state.updated = false;
	}
	
	public void goToNextLink(Vehicle vehicle) {
		if (currentLinkIndex < linkSequence.size) {
			int lane = 0; //TODO decide somehow to which lane of the next link the vehicle should go. perhaps a "toLane" int stored in each lane.
			setPath(linkSequence.get(currentLinkIndex).lanes.get(lane).path);
		} else {
			state.finished = true;
		}
	}

	/**Contains parameters regarding the current position of the agent on the
	 * path used to reduce the scope of the lookup when finding the current
	 * position.
	 * 
	 * @author J.P.Gamboa jpgamboa@outlook.com
	 */
	public class PathFollowingState {

		public int currentSegmentIndex;
		public float distanceOnPath;
		public Vector2 nearestPointOnPath;
		public boolean updated, finished;

		public PathFollowingState() {
			nearestPointOnPath = new Vector2();
		}
		
		public float update(Vehicle vehicle) {
			float brake = 0f;
			
			//Near the end of the path, so go to next path.
			if ((state.distanceOnPath + targetOffset * 1.5f) > path.getLength()) goToNextLink(vehicle);

			return brake;
		}
		
	}

}
