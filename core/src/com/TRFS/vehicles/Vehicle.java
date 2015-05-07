package com.TRFS.vehicles;

import com.TRFS.models.Behavior;
import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.MiscUtils;
import com.TRFS.simulator.SimulationParameters;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
		
		//Vector2 fwd = new Vector2().set(physics.forward).nor().scl(4).add(physics.position);
		renderer.setColor(Color.WHITE);
		renderer.line(physics.position, physics.forward);
		
		
		Vector2 steer = new Vector2().set(0,1).scl(0.5f).rotateRad(physics.steerAngle+physics.heading).add(physics.faPosition);
		renderer.setColor(Color.CYAN);
		renderer.line(physics.faPosition, steer);
		renderer.end();
		
		renderer.begin(ShapeType.Filled);		
		renderer.setColor(Color.BLUE);
		renderer.circle(physics.position.x, physics.position.y, 0.3f);
		//renderer.setColor(Color.WHITE);
		//renderer.circle(physics.cgPosition.x, physics.cgPosition.y, 0.4f);
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
					
	public class VehiclePhysics {
		//Control
		public int throttleInputFwd, throttleInputBck, steerInputLeft, steerInputRight, movingFwd; // 0, 1 (for user input)
		
		public float throttle, brake, steer; //Percentage of max force
		
		public float engineForce, brakeForce, steerAngle, speed; //Actual values
		
		//Linear
		public Vector2  position = new Vector2(), /*cgPosition = new Vector2(),*/ faPosition = new Vector2(),
						velocity = new Vector2(), localVelocity = new Vector2(), 
						acceleration = new Vector2(), /*localAcceleration = new Vector2(), */
						traction = new Vector2(), drag = new Vector2(),	totalForces = new Vector2(),
						forward = new Vector2();
		
		public Vector2 frontWPos = new Vector2(), rearWPos = new Vector2(), deltaPosition = new Vector2();
		
		//Angular
		public float 	heading, angularVelocity;
		
		//copy
		public float yawRate = 0;
		
		
		public VehicleConfig config;
		
		public VehiclePhysics(Vehicle vehicle) {
			this.config = vehicle.config;
			//this.frontWPos = new Vector2(0,config.wheelBase/2);
			//this.rearWPos = new Vector2(0,-config.wheelBase/2);
			
		}
		
		public void updatePhysics(float delta, float engineForce, float brakeForce, float steerAngle) {
			//System.out.println(engineForce+ ",  " + brakeForce+ ",  " + steerAngle+ ",  " + velocity.len());
			float traction = engineForce + brakeForce;
			float drag = (-VehicleConfig.rollDragConst * localVelocity.y) + (- VehicleConfig.airDragConst * localVelocity.y * speed);
			
			float accel = (traction + drag) / config.mass;
			
			localVelocity.add(0, accel * delta);
			speed = localVelocity.len();
			
			if (speed < 0.5 && engineForce == 0) {
				localVelocity.setZero();
			}
			
			float distance = localVelocity.y * delta;
					
			frontWPos.set(0,config.wheelBase/2).add(distance * (float) Math.sin(-steerAngle), distance * (float) Math.cos(-steerAngle));
			rearWPos.set(0,-config.wheelBase/2).add(0, distance);
			
			deltaPosition.set((frontWPos.x + rearWPos.x)/2, (frontWPos.y + rearWPos.y)/2).rotateRad(heading);
			heading -= (float) Math.atan2(frontWPos.x - rearWPos.x, frontWPos.y - rearWPos.y);
			
			
			position.add(deltaPosition);
			velocity.set(localVelocity).rotateRad(heading);
			//Update center of gravity and front axis positions
			faPosition.set(0, 0.3f*config.length).rotateRad(heading).add(position);
			forward.set(0, 1).scl(4).rotateRad(heading).add(position);
			
			config.rotateShape(heading, position);
			movingFwd = localVelocity.y > 0 ? 1 : localVelocity.y == 0 ? 0 : -1;
			
		}
		
		public void updateoldPhysics(float delta, float engineForce, float brakeForce, float steerAngle) {
			//System.out.println(engineForce+ ",  " + brakeForce+ ",  " + steerAngle+ ",  " + velocity.len() + ",  " + acceleration.len());
		
			localVelocity.set(velocity).rotateRad(-heading);
			speed = localVelocity.len();
			
			float yawSpeed = angularVelocity * config.cgToFrontAxle;
			float slipAngleFront = (float) Math.atan2(localVelocity.x + yawSpeed, Math.abs(localVelocity.y)) - (localVelocity.y > 0 ? 1 : -1) * steerAngle;
			float slipAngleRear = (float) Math.atan2(localVelocity.x - yawSpeed, Math.abs(localVelocity.y));
			
			float frictionForceFront = -VehicleConfig.cornerStiffness * slipAngleFront * config.axlWeight;
			float frictionForceRear = -VehicleConfig.cornerStiffness * slipAngleRear * config.axlWeight;
			
			//Traction
			traction.x = 0;
			traction.y = engineForce + brakeForce;

			drag.x = (-VehicleConfig.rollDragConst * localVelocity.x) + (- VehicleConfig.airDragConst * localVelocity.x * speed);
			drag.y = (-VehicleConfig.rollDragConst * localVelocity.y) + (- VehicleConfig.airDragConst * localVelocity.y * speed);
			
			totalForces.x = traction.x + drag.x + (float) Math.sin(steerAngle) * frictionForceFront + frictionForceRear; 
			totalForces.y = traction.y + drag.y;	
			
			//Update Angular components	
			float angularAcc = (frictionForceFront * config.cgToFrontAxle - frictionForceRear * config.cgToRearAxle) / config.mass;
			angularVelocity = angularAcc * delta;// TODO
			
			//Update linear components
			acceleration.set(totalForces.x/config.mass, totalForces.y/config.mass).rotateRad(heading);
			velocity.mulAdd(acceleration, delta);
			
			if (speed < 0.5 && engineForce == 0) {
				velocity.setZero();	angularVelocity = 0;
			}
			
			//Update heading and position
			heading += angularVelocity * delta;
			position.mulAdd(velocity, delta);
			
			//Update center of gravity and front axis positions
			faPosition.set(0, 0.3f*config.length).rotateRad(heading).add(position);
			forward.set(0, 1).scl(4).rotateRad(heading).add(position);
						
			config.rotateShape(heading, position);
			movingFwd = localVelocity.y > 0 ? 1 : localVelocity.y == 0 ? 0 : -1;
			System.out.println(forward.angle() + "   ,   " + acceleration.angle()  + "   ,   " + velocity.angle()+ "   ,   " + heading);
			//System.out.println(acc.angle());
		}
				
		public void updateAI(float delta) {
			 //float throttle, steerAngle; 
			 this.engineForce = 0;
			 this.brakeForce =0;
			 this.steerAngle = 0;
			// updatePhysics(delta, engineForce, brakeForce, steerAngle);
		}
		
		public void updateUser(float delta) {
			
			//Steering
			int steerInput = steerInputLeft - steerInputRight; //Negative if steering right, positive if steering left.
						
			float steerMult = 0.8f;
			if (Math.abs(steerInput) != 0) {
				steer = MathUtils.clamp(steerInput * delta * steerMult + steer, -1, 1) * (1 - getSpeed()/280);
			} else { 
				steer = MathUtils.lerp(steer, 0, delta * steerMult * 5);
				if (Math.abs(steer) < 0.001) steer = 0;
			}

			steerAngle = steer * VehicleConfig.maxSteeringAngle;

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
			return this.speed;
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
			this.config.rotateShape(heading, position);
		}
	}
	
	public class VehicleConfig {
		
		public boolean selected, userControlled;
		
		public float width, length, mass;
		public float cgToFrontAxle, cgToRearAxle, axlWeight, wheelBase;
		
		public Array<Coordinate> localVertices;
		public Array<Vector2> globalVertices;
		
		public Color defaultColor, color;
		
		public static final float engineForce = 8000, brakeForce = 12000, airDragConst = 0.426f, rollDragConst = 12.8f;//(N)
		public static final float tireGrip = 10, cornerStiffness = 10; //TODO
		public static final float maxLinearSpeed = 55; //(m/s)
		public static final float maxSteeringAngle = 45 * MathUtils.degRad; //Radians
		
		public float maxLinearAcceleration, maxBrakeAcceleration, drag;
		
		
		//COPY
		public float axleWeightRatioFront = 0.0f;  // % car weight on the front axle
		public float axleWeightRatioRear = 0.0f;  // % car weight on the rear axle
		
	
		//COPY------------
			
		public VehicleConfig(Vehicle vehicle, float width, float length, float mass, Color color) {
			
			this.width = width;
			this.length = length;
			this.mass = mass;
			this.defaultColor = color;
			this.color = color;
			
			this.cgToFrontAxle = 0.3f * length;
			this.cgToRearAxle = 0.3f * length;
			this.wheelBase = 0.6f * length;
			this.axlWeight = mass * 0.5f * 9.8f;
			
			//copy
			this.axleWeightRatioFront = this.cgToRearAxle / this.wheelBase; // % car weight on the front axle
			this.axleWeightRatioRear = this.cgToFrontAxle / this.wheelBase;
			//copy
			
			
			this.localVertices = new Array<Coordinate>();
			this.localVertices.add(new Coordinate(-width/2,-length/2));
			this.localVertices.add(new Coordinate(width/2,-length/2));
			this.localVertices.add(new Coordinate(width/2,length/2));
			this.localVertices.add(new Coordinate(-width/2,length/2));
			
			this.globalVertices = new Array<Vector2>();
			for (int i = 0; i < localVertices.size; i++) {
				this.globalVertices.add(new Vector2());
			}
			
			this.maxLinearAcceleration = engineForce/mass;
			this.maxBrakeAcceleration = brakeForce/mass; 		
		}
		
		public void rotateShape(float rotation, Vector2 origin) {
			for (int i = 0; i < localVertices.size; i++) {
				globalVertices.get(i).set(localVertices.get(i).x, localVertices.get(i).y).rotateRad(rotation).add(origin);
			}			
		}	
	}
}
