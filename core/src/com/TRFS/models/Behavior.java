package com.TRFS.models;

import java.util.Random;

import com.TRFS.models.carFollowing.CarFollowingModel;
import com.TRFS.models.laneChanging.LaneChangingModel;
import com.TRFS.models.path.PathFollowing;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Path;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * The behaviour class aggregates all behavior models and decisions to be made
 * by the vehicle, returning the resulting acceleration value for the class
 * Vehicle to process.
 * 
 * @author jgamboa
 */
public class Behavior {

	//public static DynamicSimParam[] calibrationParameters;
	private CarFollowingModel carFollowingBehaviour;
	public LaneChangingModel laneChangingBehaviour;
	public PathFollowing pathFollowing;

	private Vehicle vehicle;
	public Vehicle leader, frontOnTargetLane, rearOnTargetLane, lastOnPriorityLink, firstOnNextLink;
	public Array<Vehicle> neighbours;

	private float desiredSpeedFactor;
	
	public boolean changedLink;
	public Link currentLink;
	public Lane currentLane;
	
	private float maxDesiredAcceleration = 5; //TODO should be variable for each driver
	
	/**The {@link Behavior} of a vehicle aggregates all behaviour models affecting the {@link Vehicle}.
	 * @param vehicle This vehicle.
	 * @param carFollowingBehaviour Car-following behavior for this vehicle.
	 * @param laneChangingBehaviour Lane changing behavior for this vehicle.
	 */

	public Behavior(Vehicle vehicle, String carFollowingBehavior, String laneChangingBehavior) {
		this.vehicle = vehicle;
		this.pathFollowing = new PathFollowing();
		
		this.carFollowingBehaviour = CarFollowingModel.set(carFollowingBehavior);
		this.laneChangingBehaviour = LaneChangingModel.set(laneChangingBehavior);
		
		this.desiredSpeedFactor = (new Random().nextInt((150 - 70) + 1) + 70)/100f;
	}

	/**Updates the behaviour of this HVE.
	 * @param dT delta time
	 * @return accelVector, the resulting acceleration vector
	 */
	public void update(float dT) {
		//From -1 to 1, to be passed to the updatePhysics method as a % of the total vehicle capability
		float throttle = 0, brake = 0, steerAngle = 0; 
				
		//Update CarFollowing (returns an amount of throttle or brake ranging from -1 to 1)
		float carFollowingThrottle = updateCarFollowing(leader);
		
		//TODO other constraints that might affect the throttle magnitude.
		throttle = carFollowingThrottle;
		
		//Updates the target and returns the angle between the target and the vehicle's CG
		float targetHeading = pathFollowing.update(vehicle);
		steerAngle = targetHeading - vehicle.physics.heading;
		if (steerAngle > MathUtils.PI) steerAngle -= MathUtils.PI2;
		
		//This method updates all path related states and returns a brake value
		brake += pathFollowing.state.update(vehicle);
		
		//Update Vehicle
		vehicle.physics.updateAI(dT, throttle, brake, steerAngle);
	}
	
	/**Updates the {@link Vehicle}'s {@link CarFollowingModel} regarding it's leader.
	 * @param leader
	 * @return Updated acceleration float.
	 */
	private float updateCarFollowing(Vehicle leader) {
		if (leader != null) return carFollowingBehaviour.update(leader.physics.position.dst(vehicle.physics.position),
					leader.physics.speed - vehicle.physics.speed, leader.config.length,	vehicle.physics.speed, leader.physics.speed, 
					leader.physics.acceleration, currentLink.maxspeed, currentLink.maxspeed * desiredSpeedFactor);
		return maxDesiredAcceleration;
	}
		
	/** Sets the initial location ({@link Link} and {@link Lane}) of the vehicle.
	 * Updates the vehicle {@link PathFollowing}'s {@link Path} and sets the {@link Vehicle}'s position.
	 * @param startLink
	 * @param startLane
	 */
	public void setInitialLocation(Link startLink, Lane startLane) {
		currentLink = startLink;
		currentLane = startLane;
		pathFollowing.setPath(startLane.path);
		float rotation = (float) (new Vector2().set(startLane.path.getSecondPoint()).sub(startLane.path.getStartPoint()).angleRad()-(Math.PI/2f));
		vehicle.physics.setLocation(startLane.path.getStartPoint(), rotation);
	}
	
	public void rellocateAfterUserControlled() {
		//TODO find nearest link/lane, set it as path
	}
	
	
		
}
