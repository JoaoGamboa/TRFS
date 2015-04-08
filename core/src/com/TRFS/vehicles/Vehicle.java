package com.TRFS.vehicles;

import com.TRFS.models.Behavior;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.SimulationParameters;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Class holding the physical characteristics and mechanics of a vehicle. Decisions
 * are made by the Behavior class.
 * 
 * @author jgamboa
 */

public class Vehicle extends Actor /* implements Steerable<Vector2> */{

	// Properties
	boolean userControlled;
	
	// Shape & Aspect
	protected TextureRegion region;

	private Behavior behavior;

	private Vector2 position, velocity, acceleration;
	/*private float , angularVelocity, boundingRadius*/;
	private float maxLinearSpeed = 160/3.6f, maxLinearAcceleration = 5/*, maxAngularSpeed = 5, maxAngularAcceleration = 10*/;

	/**
	 * Creates a new vehicle.
	 * @param width The width of the vehicle
	 * @param length The length of the vehicle
	 * @param color The color of the vehicle
	 * @param textureName The name of the texture to use
	 */
	public Vehicle(float width, float length, Color color, String textureName) {
		this.region = new TextureRegion(AssetsMan.uiSkin.getRegion(textureName));
		this.setSize(width, length);
		this.setOrigin(width/2, length/2);
		this.setColor(color);
		
		this.position = new Vector2(0, 0);
		this.velocity = new Vector2(0, 0); // TODO depends on initial speed
		this.acceleration = new Vector2(0, 0);

		this.behavior = new Behavior(this,
				SimulationParameters.currentCarFolModel,
				SimulationParameters.currentLaneChangeModel);
	}

	/**
	 * Updates the vehicle behaviour, acceleration, velocity and position. Called every frame.
	 * @param delta time 
	 */
	public void update(float delta) {

		// AI Behaviour
		if (!userControlled)
			acceleration.set(behavior.update(delta));
		// User Behaviour
		if (userControlled) {
		}
		
		updateVelocity(delta);
		updateRotation();
		updatePosition(delta);

	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		update(delta);
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		
		batch.setColor(getColor().r, getColor().g, getColor().b, parentAlpha);
		batch.draw(region, getX(), getY(), getWidth(), getHeight());
	}

	/*
	 * public Vector2 getAcceleration() { return accel; }
	 * 
	 * public void setAcceleration(Vector2 acceleration) { this.acceleration =
	 * acceleration; }
	 * 
	 * public Behavior getCarFollowingBehavior() { return behavior; }
	 * 
	 * @Override public float getMaxLinearSpeed() { return maxAngularSpeed; }
	 * 
	 * @Override public void setMaxLinearSpeed (float maxLinearSpeed) {
	 * this.maxLinearSpeed = maxLinearSpeed; }
	 * 
	 * @Override public float getMaxLinearAcceleration() { return
	 * maxLinearAcceleration; }
	 * 
	 * @Override public void setMaxLinearAcceleration (float
	 * maxLinearAcceleration) { this.maxLinearAcceleration =
	 * maxLinearAcceleration; }
	 * 
	 * @Override public float getMaxAngularSpeed() { return maxAngularSpeed; }
	 * 
	 * @Override public void setMaxAngularSpeed (float maxAngularSpeed) {
	 * this.maxAngularSpeed = maxAngularSpeed; }
	 * 
	 * @Override public float getMaxAngularAcceleration() { return
	 * maxAngularAcceleration; }
	 * 
	 * @Override public void setMaxAngularAcceleration (float
	 * maxAngularAcceleration) { this.maxAngularAcceleration =
	 * maxAngularAcceleration; }
	 * 
	 * @Override public Vector2 getPosition() { return this.position; }
	 * 
	 * @Override public float getOrientation() { return this.getRotation(); }
	 * 
	 * @Override public Vector2 getLinearVelocity() { return
	 * this.linearVelocity; }
	 * 
	 * @Override public float getAngularVelocity () { return angularVelocity; }
	 * 
	 * @Override public float getBoundingRadius () { return boundingRadius; }
	 * 
	 * @Override public boolean isTagged () { return tagged; }
	 * 
	 * @Override public void setTagged (boolean tagged) { this.tagged = tagged;
	 * }
	 * 
	 * @Override public Vector2 newVector () { return new Vector2(); }
	 * 
	 * @Override public float vectorToAngle (Vector2 vector) { return
	 * (float)Math.atan2(-vector.x, vector.y); }
	 * 
	 * @Override public Vector2 angleToVector (Vector2 outVector, float angle) {
	 * outVector.x = -(float)Math.sin(angle); outVector.y =
	 * (float)Math.cos(angle); return outVector; }
	 */

	/**Updates the velocity vector with the current value of the acceleration vector.
	 * Limits the velocity to the maximum linear speed defined.
	 * @param delta time
	 */
	public void updateVelocity(float delta) {
		//TODO apply rotation. save here then rotate after
		velocity.mulAdd(acceleration, delta).limit(maxLinearSpeed);
	}
	
	/**Updates the rotation of the actor according to the direction of the velocity vector.
	 */
	public void updateRotation() {
		float orientation = (float) Math.atan2(-velocity.x, velocity.y);
		setRotation(orientation*MathUtils.radiansToDegrees);
	}
	
	/**Updates the vehicle's position according to the velocity vector for the delta time provided.
	 * Also sets the position of the actor to the new coordinates.
	 * @param delta time
	 */
	public void updatePosition(float delta) {
		position.mulAdd(velocity, delta);
		this.setPosition(this.position.x, this.position.y);
	}
	
	/** Sets the position of the vehicle to a given vector. Also updates the actor position for drawing.
	 * @param position The {@link Vector2} holding the position.
	 */
	public void setPosition(Vector2 position) {
		this.position.set(position);
		super.setPosition(position.x, position.y);
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getAcceleration() {
		return acceleration;
	}

	public float getSpeed() {
		return velocity.len();
	}

	public float getAccelMagnitude() {
		return acceleration.len();
	}

	public float getMaxLinearSpeed() {
		return maxLinearSpeed;
	}

	public float getMaxLinearAcceleration() {
		return maxLinearAcceleration;
	}

	public Behavior getBehavior() {
		return behavior;
	}
}
