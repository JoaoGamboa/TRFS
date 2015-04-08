package com.TRFS.models;

import java.util.Random;

import com.TRFS.models.carFollowing.CarFollowingModel;
import com.TRFS.models.carFollowing.FritzscheCarFollowing;
import com.TRFS.models.carFollowing.W74CarFollowing;
import com.TRFS.models.path.PathFollowing;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Path;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * The behaviour class aggregates all behavior models and decisions to be made
 * by the vehicle, returning the resulting acceleration value for the class
 * Vehicle to process.
 * 
 * @author jgamboa
 */

public class Behavior {

	public static final String[] carFolModels = new String[] { "Wiedemann '74", "Fritzsche" };

	public static DynamicSimParam[] calibrationParameters;
	private CarFollowingModel carFollowingModel;
	private PathFollowing pathFollowing;

	private Vehicle vehicle;

	private float desiredSpeed, desiredSpeedFactor;
	
	private boolean changedLink;
	private Link currentLink;
	private Lane currentLane;

	/**The {@link Behavior} of a vehicle aggregates all behaviour models affecting the {@link Vehicle}.
	 * @param vehicle
	 *            This vehicle.
	 * @param carFollowingBehaviour
	 *            Car-following behavior for this vehicle.
	 * @param laneChangingBehaviour
	 *            Lane changing behavior for this vehicle.
	 */

	public Behavior(Vehicle vehicle, String carFollowingBehavior,
			String laneChangingBehavior) {
		this.vehicle = vehicle;
		this.desiredSpeedFactor = (new Random().nextInt((150 - 70) + 1) + 70)/100f;
		
		this.pathFollowing = new PathFollowing();
		
		setCarFollowingModel(carFollowingBehavior);
		
	}

	/**Updates the behaviour of this HVE.
	 * @param dT delta time
	 * @return accelVector, the resulting acceleration vector
	 */
	@SuppressWarnings("unused")
	public Vector2 update(float dT) {

		if (changedLink) {
			setDesiredSpeed(currentLink.getMaxspeed() * desiredSpeedFactor);
		}
		
		Vehicle leader = null; // TODO
		float linearAccelMagnitude = updateCarFollowing(leader);

		
		Vector2 accelVector = new Vector2();
		
		//Build vector
		// TODO make linearVelocity point to next waypoint. Use the resulting
		// vector to update position.
		// TODO when changing lanes, must find a way to make the velocity point
		// to the target lane
		//vehicle.getAcceleration().
		
		return accelVector;
	}
	
	/**Updates the {@link Vehicle}'s {@link CarFollowingModel} regarding it's leader.
	 * @param leader
	 * @return Updated acceleration float.
	 */
	private float updateCarFollowing(Vehicle leader) {
		//Update car-following behaviour
		if (leader != null) {
			return MathUtils.clamp(carFollowingModel.update(leader
					.getPosition().dst(vehicle.getPosition()),
					leader.getSpeed() - vehicle.getSpeed(), leader.getHeight(),
					vehicle.getSpeed(), leader.getSpeed(), leader
							.getAccelMagnitude(), currentLink.getMaxspeed(), desiredSpeed),
					-vehicle.getMaxLinearAcceleration(), vehicle
							.getMaxLinearAcceleration());
		} else {
			return vehicle.getMaxLinearAcceleration();
		}
	}
	
	/**Sets this {@link Vehicle}'s {@link CarFollowingModel}.
	 * @param carFollowingBehavior
	 */
	private void setCarFollowingModel(String carFollowingBehavior) {
		int model = 0;
		for (int i = 0; i < carFolModels.length; i++) {
			if (carFollowingBehavior.equals(carFolModels[i])) {
				model = i;
			}
		}

		switch (model) {
		case 0:
			carFollowingModel = new W74CarFollowing();
			calibrationParameters = W74CarFollowing.calibrationParameters;
		case 1:
			carFollowingModel = new FritzscheCarFollowing();
		}
	}

	/**Sets this {@link Vehicle}'s desired speed randomly with {@link Random#nextGaussian()}.
	 * @param maxSpeed
	 */
	public void setDesiredSpeed(float maxSpeed) {
		this.desiredSpeed = (float) (new Random().nextGaussian() * 20 + maxSpeed);
	}

	public boolean isChangedLink() {
		return changedLink;
	}

	public void setChangedLink(boolean changedLink) {
		this.changedLink = changedLink;
	}

	public Link getCurrentLink() {
		return currentLink;
	}

	/** Sets the initial location ({@link Link} and {@link Lane}) of the vehicle.
	 * Updates the vehicle {@link PathFollowing}'s {@link Path} and sets the {@link Vehicle}'s position.
	 * @param startLink
	 * @param startLane
	 */
	public void setInitialLocation(Link startLink, Lane startLane) {
		setCurrentLink(startLink);
		setCurrentLane(startLane);
		pathFollowing.setWaypoints(startLane.getWayPoints());
		vehicle.setPosition(currentLane.getWayPoints().first());
	}
	
	public void setCurrentLink(Link currentLink) {
		this.currentLink = currentLink;
	}

	public Lane getCurrentLane() {
		return currentLane;
	}

	public void setCurrentLane(Lane currentLane) {
		this.currentLane = currentLane;
	}
}
