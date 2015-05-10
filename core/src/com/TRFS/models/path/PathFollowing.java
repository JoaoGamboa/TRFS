package com.TRFS.models.path;

import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Path;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sun.corba.se.impl.protocol.giopmsgheaders.TargetAddressHelper;

/**
 * @author J.P.Gamboa jpgamboa@outlook.com
 *
 */
public class PathFollowing {

	private Path path;
	private PathFollowingState state;
	private Array<Link> linkSequence;

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
		
		
		if ((state.distanceOnPath + targetOffset * 1.5f) > path.getLength()) nextLink();
		
		
		//Set aim to the target
		float targetHeading = (float) Math.atan2(targetPosition.x - vehicle.physics.position.x, targetPosition.y - vehicle.physics.position.y);

		return -targetHeading;
	}

	public void setPath(Path path) {
		this.path = path;
		//this.state.nearestPointOnPath = path.getStartPoint().cpy();
		this.state.updated = false;
	}
	
	public void nextLink() {
		//TODO set path as the next link on the link sequence
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

		public int currentSegmentIndex;
		public float distanceOnPath;
		public Vector2 nearestPointOnPath;
		public boolean updated;

		public PathFollowingState() {
			nearestPointOnPath = new Vector2();
		}

	}

}
