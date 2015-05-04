package com.TRFS.vehicles;

import java.util.Iterator;

import com.TRFS.models.Behavior;
import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.MiscUtils;
import com.TRFS.simulator.SimulationParameters;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;


/**
 * Class holding the physical characteristics and mechanics of a vehicle. Decisions
 * are made by the Behavior class.
 * 
 * @author jgamboa
 */

public class Vehicle /*extends Actor*/ {

	// Properties
	public VehiclePhysics physics;
	public VehicleConfig config;
	public Behavior behavior;
	
	// Shape & Aspect
	protected TextureRegion region;
	
	/**
	 * Creates a new vehicle.
	 * @param width The width of the vehicle
	 * @param length The length of the vehicle
	 * @param color The color of the vehicle
	 * @param textureName The name of the texture to use
	 */
	public Vehicle(float width, float length, float mass, Color color, String textureName) {
		
		this.config = new VehicleConfig(this, width, length, mass, color);
		this.physics = new VehiclePhysics(this);
		
		this.region = new TextureRegion(AssetsMan.uiSkin.getRegion(textureName));//TODO make draw method
						
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
		if (config.userControlled) {
			physics.updateUser(delta);
		} else {
			behavior.update(delta);
			physics.updateAI(delta);
		}
	}

	public void draw(Batch batch) {
		// TODO Auto-generated method stub
		batch.setColor(config.color);
		
		float x = config.globalVertices.get(0).x;
		float y = config.globalVertices.get(0).y;
		float originX = config.width/2;
		float originY = config.length * 0.2f;
		
		batch.draw(region, config.globalVertices.get(0).x,
				config.globalVertices.get(0).y, 0, 0, config.width,
				config.length, 1, 1, physics.heading * MathUtils.radDeg);
	}
	
	public void drawVehicleDebug(ShapeRenderer renderer) {
		renderer.begin(ShapeType.Line);
		renderer.setColor(config.color);
		for (int i = 0; i < config.globalVertices.size - 1; i++) {
			renderer.line(config.globalVertices.get(i), config.globalVertices.get(i+1));
		}
		renderer.line(config.globalVertices.peek(), config.globalVertices.first());	
		
		Vector2 vel = new Vector2().set(physics.velocity).nor().scl(3).add(physics.position);
		renderer.setColor(Color.YELLOW);
		renderer.line(physics.position, vel);
		
		Vector2 acl = new Vector2().set(physics.acceleration).nor().scl(2).add(physics.position);
		renderer.setColor(Color.BLUE);
		renderer.line(physics.position, acl);
		
		Vector2 fwd = new Vector2().set(physics.forward).nor().scl(1).add(physics.position);
		renderer.setColor(Color.RED);
		renderer.line(physics.position, fwd);
		
		
		Vector2 steer = new Vector2().set(0,1).scl(0.5f).rotateRad(physics.steerAngle+physics.heading).add(physics.faPosition);
		renderer.setColor(Color.CYAN);
		renderer.line(physics.faPosition, steer);
		renderer.end();
		
		renderer.begin(ShapeType.Filled);		
		renderer.setColor(Color.BLUE);
		renderer.circle(physics.position.x, physics.position.y, 0.3f);
		renderer.setColor(Color.WHITE);
		renderer.circle(physics.cgPosition.x, physics.cgPosition.y, 0.4f);
		renderer.setColor(Color.PINK);
		renderer.circle(physics.faPosition.x, physics.faPosition.y, 0.3f);
		renderer.setColor(Color.YELLOW);
		renderer.circle(config.globalVertices.get(0).x, config.globalVertices.get(0).y, 0.35f);
		renderer.setColor(Color.RED);
		renderer.circle(config.globalVertices.get(1).x, config.globalVertices.get(1).y, 0.35f);
		renderer.setColor(Color.CYAN);
		renderer.circle(config.globalVertices.get(2).x, config.globalVertices.get(2).y, 0.3f);
		renderer.setColor(Color.GREEN);
		renderer.circle(config.globalVertices.get(3).x, config.globalVertices.get(3).y, 0.3f);
		renderer.end();
	}
	
	/**Updates the velocity vector with the current value of the acceleration vector.
	 * Limits the velocity to the maximum linear speed defined.
	 * @param delta time
	 */
	public void updateVelocityTODELETE(float delta) {
		//velocity.mulAdd(acceleration, delta).limit(maxLinearSpeed).scl(drag);
	}
	
	/**Updates the rotation of the actor according to the direction of the velocity vector.
	 */
	public void updateRotationTODELETE(float delta) {
		/*
		//The vehicle can't turn if it isn't moving.
		if (!velocity.isZero(0.001f)) {
			//The vehicle will want to face the same direction as the velocity vector
			float targetRotation = vectorToAngle(velocity);
			
			//If the velocity vector points more than 90 degrees away from the forward direction, then the vehicle is moving backwards.
			targetRotation = fwdDirection.dot(velocity) > 0 ? targetRotation : targetRotation + MathUtils.PI;
			velocity.
			
			//float rotation = targetRotation - this.rotation > MathUtils.PI/2f ? 
					
			//if ((targetRotation - getRotation()) > MathUtils.PI/2f) float targetRotation -= MathUtils.PI2;
			
			//float rotation = (targetRotation - getRotation()) % MathUtils.PI2;
			
			//if (rotation > MathUtils.PI) rotation -= MathUtils.PI2;	
			
			//this.rotation += rotation* MathUtils.radiansToDegrees * delta;	
			//super.setRotation(this.rotation);
			angularAcceleration = MathUtils.clamp(angularAcceleration, -maxAngularAcceleration, maxAngularAcceleration);
			
			angularVelocity = MathUtils.clamp(angularAcceleration * delta, -maxAngularSpeed, maxAngularSpeed);
			
			float deltaRotation = angularVelocity * delta;
						
			this.rotation += deltaRotation * MathUtils.radiansToDegrees; //TODO to degrees
			super.setRotation(this.rotation);
			
		}
		
		//Update the forward direction vector to point to the front of the vehicle.
		this.fwdDirection.set(0,1).rotate(rotation);
		*/
	}
	
	/**Updates the vehicle's position according to the velocity vector for the delta time provided.
	 * Also sets the position of the actor to the new coordinates.
	 * @param delta time
	 */
	public void updatePositionTODELETE(float delta) {

	}
			
	public class VehiclePhysics {
		//Control
		public int throttleInputFwd, throttleInputBck, steerInputLeft, steerInputRight, movingFwd; // 0, 1 (for user input)
		
		public float throttle, brake, steer; //Percentage of max force
		
		public float engineForce, brakeForce, steerAngle; //Actual values
		
		//Linear
		public Vector2  position = new Vector2(), cgPosition = new Vector2(), faPosition = new Vector2(),
						velocity = new Vector2(), localVelocity = new Vector2(), 
						acceleration = new Vector2(), localAcceleration = new Vector2(), 
						traction = new Vector2(), drag = new Vector2(),	totalForces = new Vector2(),
						forward = new Vector2(), steerPointer = new Vector2();
		
		//Angular
		public float 	heading, angularVelocity;
		
		public VehicleConfig config;
		
		public VehiclePhysics(Vehicle vehicle) {
			this.config = vehicle.config;
		}
		
		public void updatePhysics(float delta, float engineForce, float brakeForce, float steerAngle) {
			
			localVelocity.set(velocity).rotateRad(heading);
			
			float accelerationValue = (engineForce + brakeForce) / config.mass;
			
			localAcceleration.set(0,accelerationValue).rotateRad(steerAngle);
			//System.out.println(localAcceleration.x);
			angularVelocity += localAcceleration.x * 0.6 * config.length * delta;
			heading += angularVelocity * delta;
			
			acceleration.set(localAcceleration).rotateRad(-heading);
			
			//Set forward vector to the updated heading
			MiscUtils.localToGlobalOut(forward.set(0,1), heading, position);
			
			velocity.mulAdd(acceleration, delta).clamp(-20, VehicleConfig.maxLinearSpeed);
			
			position.mulAdd(velocity, delta);
			
			//Update center of gravity and front axis positions
			MiscUtils.localToGlobalOut(cgPosition.set(0, 0.3f*config.length), heading, position);
			MiscUtils.localToGlobalOut(faPosition.set(0, 0.6f*config.length), heading, position);
			
			config.rotateShape(heading, position);
			movingFwd = localVelocity.y > 0 ? 1 : localVelocity.y == 0 ? 0 : -1;
		}
		
		public void updateAI(float delta) {
			 float throttle, steerAngle;
			 this.engineForce = 0;
			 this.brakeForce =0;
			 this.steerAngle = 0;
			// updatePhysics(delta, engineForce, brakeForce, steerAngle);
		}
		
		public void updateUser(float delta) {
			
			//Steering
			int steerInput = steerInputLeft - steerInputRight; //Negative if steering right, positive if steering left.
			float steerMultiplier = 2f;
			if (Math.abs(steerInput) != 0) {
				steer = MathUtils.clamp(steerInput * delta * steerMultiplier + steer, -1, 1);
			} else { 
				steer = MathUtils.lerp(steer, 0, delta * steerMultiplier);
				if (Math.abs(steer) < 0.001) steer = 0;
			}
			System.out.println(steer);
			steer = steer /** (1 - getSpeed()*3.6f/280)*/;
			System.out.println(1 - getSpeed()*3.6f/280);
			
			steerAngle = steer * VehicleConfig.maxSteeringAngle;
			System.out.println(steerAngle*MathUtils.radDeg);
			
			//Throttle
			float throttleMultiplier = 3f, brakeMultiplier = 10f;
			int throttleDir =  throttleInputFwd - throttleInputBck; //Defines the direction of the engine or brake force
			int throttleMult = movingFwd * throttleDir; //Defines if the vehicle is braking (-1), accelerating (1) or free (0)
			
			if (Math.abs(movingFwd) != 0) {
				if (throttleMult > 0) {
					throttle = MathUtils.clamp(throttleDir * throttleMultiplier * delta + throttle, -1, 1);
				} else if (throttleMult < 0) {
					brake = MathUtils.clamp(throttleDir * brakeMultiplier * delta + brake, -1, 1);
				} else {
					throttle = brake = 0;
				}
			} else {
				throttle = MathUtils.clamp(throttleDir * throttleMultiplier * delta, -1, 1);
				brake = 0;
			}
			
			engineForce = throttle * VehicleConfig.engineForce;
			brakeForce = brake * VehicleConfig.brakeForce;
								
			updatePhysics(delta, engineForce, brakeForce, steerAngle);
		}
				
		public float getSpeed() {
			return velocity.len();
		}

		public float getAccelMagnitude() {
			return acceleration.len();
		}
		
		/** Sets the position of the vehicle to a given vector with a given heading.
		 * @param position The {@link Vector2} holding the position.
		 */
		public void setLocation(Vector2 position, float heading) {
			this.position.set(position.x, position.y);
			this.heading = heading;
		}
	}
	
	public class VehicleConfig {
		
		public boolean selected, userControlled;
		
		public float width, length, mass;
		//public float cgToFront, cgToRear, cgToFrontAxle, cgToRearAxle; //(m)
		
		public Array<Coordinate> localVertices;
		public Array<Vector2> globalVertices;
		
		public Color defaultColor, color;
		
		public static final float engineForce = 8000, brakeForce = 12000, airDrag = 2.5f, rollDrag = 8;//(N)
		public static final float maxLinearSpeed = 55; //(m/s)
		public static final float maxSteeringAngle = 60 * MathUtils.degRad; //Radians
		
		public float maxLinearAcceleration, maxBrakeAcceleration, drag;
	
		//public float maxAngularSpeed = 5, maxAngularAcceleration = 60;
		
		public VehicleConfig(Vehicle vehicle, float width, float length, float mass, Color color) {
			
			this.width = width;
			this.length = length;
			this.mass = mass;
			this.defaultColor = color;
			this.color = color;
			
			/*this.cgToFront = length/2;
			this.cgToRear = length/2;
			this.cgToFrontAxle = length/2 - 0.2f*length;
			this.cgToRearAxle =  length/2 - 0.2f*length;*/
			
			this.localVertices = new Array<Coordinate>();
			this.localVertices.add(new Coordinate(-width/2,-0.2f*length));
			this.localVertices.add(new Coordinate(width/2,-0.2f*length));
			this.localVertices.add(new Coordinate(width/2,0.8f*length));
			this.localVertices.add(new Coordinate(-width/2,0.8f*length));
			
			this.globalVertices = new Array<Vector2>();
			for (int i = 0; i < localVertices.size; i++) {
				this.globalVertices.add(new Vector2());
			}
			
			this.maxLinearAcceleration = engineForce/mass;
			this.maxBrakeAcceleration = brakeForce/mass; 		
		}
		
		public void rotateShape(float rotation, Vector2 origin) {
			for (int i = 0; i < localVertices.size; i++) {
				MiscUtils.localToGlobal(localVertices.get(i).x, localVertices.get(i).y, 
						globalVertices.get(i), rotation, origin);
			}			
		}	
	}
}
