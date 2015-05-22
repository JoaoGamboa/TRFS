package com.TRFS.models;

import com.TRFS.models.carFollowing.CarFollowingModel;
import com.TRFS.models.laneChanging.LaneChangingModel;
import com.TRFS.models.path.PathFollowing;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Path;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.Vector2;

/**
 * The behaviour class aggregates all behavior models and decisions to be made
 * by the vehicle, returning the resulting acceleration value for the class
 * Vehicle to process.
 * 
 * @author jgamboa
 */
public class Behavior {

	public CarFollowingModel carFollowingBehaviour;
	public LaneChangingModel laneChangingBehaviour;
	public PathFollowing pathFollowing;

	public Vehicle vehicle, lastOnPriorityLink, firstOnNextLink;
		
	/**The {@link Behavior} of a vehicle aggregates all behaviour models affecting the {@link Vehicle}.
	 * @param vehicle This vehicle.
	 * @param carFollowingBehaviour Car-following behavior for this vehicle.
	 * @param laneChangingBehaviour Lane changing behavior for this vehicle.
	 */

	public Behavior(Vehicle vehicle, String carFollowingBehavior, String laneChangingBehavior) {
		this.vehicle = vehicle;
		this.pathFollowing = new PathFollowing();
		
		this.carFollowingBehaviour = CarFollowingModel.set(carFollowingBehavior, vehicle);
		this.laneChangingBehaviour = LaneChangingModel.set(laneChangingBehavior, vehicle);
		
	}

	/**Updates the behaviour of this HVE.
	 * @param dT delta time
	 * @return accelVector, the resulting acceleration vector
	 */
	public void update(float dT) {
		// From -1 to 1, to be passed to the updatePhysics method as a % of the total vehicle capability
		float throttle = 0, brake = 0, steerAngle = 0; 
				
		//Update CarFollowing (returns an amount of throttle or brake ranging from -1 to 1)
		float carFollowingResult = carFollowingBehaviour.update();
	
		float carFollowingThrottle = carFollowingResult > 0 ? carFollowingResult : 0;
		//float carFollowingBrake = carFollowingResult < 0 ? carFollowingResult : 0 + carFollowingBehaviour.avoidColision();
		
		laneChangingBehaviour.update();
				
		//Updates the target and returns the angle between the target and the vehicle's CG
		float pathRelatedSteering = pathFollowing.update(vehicle);
				
		//This method updates all path related states and returns a brake value
		float pathRelatedBrake = pathFollowing.state.update(vehicle);
		
		
		throttle = carFollowingThrottle;
		brake = pathRelatedBrake /*+ carFollowingBrake*/;
		steerAngle = pathRelatedSteering;
		
		vehicle.physics.updateAI(dT, throttle, brake, steerAngle);
	}
			
	/** Sets the initial location ({@link Link} and {@link Lane}) of the vehicle.
	 * Updates the vehicle {@link PathFollowing}'s {@link Path} and sets the {@link Vehicle}'s position.
	 * @param startLink
	 * @param startLane
	 */
	public void setInitialLocation(Link startLink, Lane startLane) {
		pathFollowing.state.currentLink = startLink;
		pathFollowing.state.currentLane = startLane;
		pathFollowing.setPath(startLane.path);
		float rotation = (float) (new Vector2().set(startLane.path.getSecondPoint()).sub(startLane.path.getStartPoint()).angleRad()-(Math.PI/2f));
		vehicle.physics.setLocation(startLane.path.getStartPoint(), rotation);
	}
	
	public void rellocateAfterUserControlled() {
		//TODO find nearest link/lane, set it as path
	}
	
	
		
}
