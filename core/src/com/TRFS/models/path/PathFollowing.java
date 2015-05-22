package com.TRFS.models.path;

import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Path;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.MathUtils;
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
	private int currentLinkIndex = 0;
	
	private Vector2 targetPosition;
	private float targetOffset=5f;
	
	public PathFollowing() {
		this.state = new PathFollowingState();
		this.targetPosition = new Vector2();
		this.linkSequence = new Array<Link>();
	}
	
	public float update(Vehicle vehicle) {
		
		path.updateTargetPosition(vehicle.physics.position, targetPosition, state, targetOffset);		
		vehicle.targetPos.set(targetPosition); //TODO delete debug only
		
		//Set aim to the target
		float targetHeading = (float) Math.atan2(targetPosition.x - vehicle.physics.position.x, targetPosition.y - vehicle.physics.position.y);
		
		float steerAngle = -targetHeading - vehicle.physics.heading;
		if (steerAngle > MathUtils.PI) steerAngle -= MathUtils.PI2;
		
		return steerAngle;
	}

	public void setPath(Path path) {
		this.path = path;
		this.state.updated = false;
	}
	
	public void changeLane(int laneIndex) {
		setPath(state.currentLink.lanes.get(laneIndex).path);
	}
	
	public void goToNextLink(Vehicle vehicle) {
		if (currentLinkIndex < linkSequence.size) {
			
			int lane = 0; //TODO decide somehow to which lane of the next link the vehicle should go. perhaps a "toLane" int stored in each lane.
			
			setPath(linkSequence.get(currentLinkIndex).lanes.get(lane).path);
			currentLinkIndex++;
		} else {
			state.finished = true;
		}
	}
	
	public Link nextLink() {
		if (currentLinkIndex + 1 < linkSequence.size) return linkSequence.get(currentLinkIndex + 1);
		return linkSequence.get(linkSequence.size - 1);
	}

	/**Contains parameters regarding the current position of the agent on the
	 * path used to reduce the scope of the lookup when finding the current
	 * position.
	 * 
	 * @author J.P.Gamboa jpgamboa@outlook.com
	 */
	public class PathFollowingState {

		public Link currentLink;
		public Lane currentLane;
		public int currentSegmentIndex;
		public float distanceOnPath;
		public Vector2 nearestPointOnPath;
		public boolean updated, finished, approachingLinkEnd, atLinkEnd;

		public PathFollowingState() {
			nearestPointOnPath = new Vector2();
		}
		
		public float update(Vehicle vehicle) {
			float brake = 0f;
			
			approachingLinkEnd = ((state.distanceOnPath) > path.length - 20) ? true : false;
			atLinkEnd = ((state.distanceOnPath + targetOffset * 0.5f) > path.length) ? true : false;
			
			//At the end of the path, so go to next path.			
			if (atLinkEnd) goToNextLink(vehicle);
			
			if(approachingLinkEnd) {
				float turnAngle = Math.abs(currentLink.finishHeadingRad - nextLink().startHeadingRad);
				float percentageOnPath = distanceOnPath/path.length;
	
				if (turnAngle > 45 * MathUtils.degreesToRadians) {
					brake = (float) MathUtils.clamp(percentageOnPath, 0, 0.6);
				}
			}

			return brake;
		}
		
	}

}
