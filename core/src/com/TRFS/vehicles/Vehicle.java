package com.TRFS.vehicles;

import com.TRFS.models.Behavior;
import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.SimulationParameters;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Class holding the physical characteristics and mechanics of a vehicle. Decisions
 * are made by the Behavior class.
 * 
 * @author jgamboa
 */

public class Vehicle extends Actor {

	// Properties
	public VehiclePhysics physics;
	public VehicleConfig config;
	public Behavior behavior;
	
	// Shape & Aspect
	protected TextureRegion region;
	
	/*//DEBUG TODO delete
	private static int counter;
	private int id, iterCounter;
	private float distanceTraveled;
	private Vector2 laspos,  tmp1, tmp2, tmp3;
	//endDEBUG*/
	

	/**
	 * Creates a new vehicle.
	 * @param width The width of the vehicle
	 * @param length The length of the vehicle
	 * @param color The color of the vehicle
	 * @param textureName The name of the texture to use
	 */
	public Vehicle(float width, float length, float weight, Color color, String textureName) {
		
		this.config = new VehicleConfig(this, width, length, weight, color);
		this.physics = new VehiclePhysics(this);
		
		
		this.region = new TextureRegion(AssetsMan.uiSkin.getRegion(textureName));//TODO make draw method
		
		//Actor properties
		this.setSize(width, length);
		this.setOrigin(Align.center);
		this.setColor(color);
		

		

		
		updateShape();
		
		this.behavior = new Behavior(this,
				SimulationParameters.currentCarFolModel,
				SimulationParameters.currentLaneChangeModel);
		
		setupInputListener();
		
		//DEBUG TODO delete
		this.tmp1 = new Vector2();
		this.tmp2 = new Vector2();
		this.tmp3 = new Vector2();
		//endDEBUG

	}

	/**
	 * Updates the vehicle behaviour, acceleration, velocity and position. Called every frame.
	 * @param delta time 
	 */
	public void update(float delta) {

		// AI Behaviour
		if (!config.userControlled)
			behavior.update(delta, acceleration);
			//acceleration.set(behavior.update(delta));
		
		physics.update(delta);
		config.update();

	}

	@Override
	public void act(float delta) {
		update(delta);
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		
		//batch.setColor(getColor().r, getColor().g, getColor().b, parentAlpha);
		
		//batch.draw(region, getX(), getY(),0,0, getWidth(), getHeight(), 1,1,super.getRotation());
	}
	
	public void drawVehicleDebug(ShapeRenderer renderer) {
		//Calc position with rotation
		renderer.setColor(getColor());
		renderer.line(vA, vB);
		renderer.line(vB, vC);
		renderer.line(vC, vD);
		renderer.line(vD, vA);	
		
		renderer.setColor(Color.BLUE);
		renderer.rect(position.x, position.y, centerPosition.x, centerPosition.y, width, length, 1f, 1f, rotation);

		renderer.setColor(Color.GREEN);
		
		//renderer.rect(getX(), getY(), centerPosition.x, centerPosition.y, getWidth(), getHeight(), 1f, 1f, getRotation());


		tmp1.set(acceleration).nor().scl(2).add(centerPosition);
		renderer.setColor(Color.BLUE);
		renderer.line(centerPosition, tmp1);
		tmp2.set(velocity).nor().add(centerPosition);
		renderer.setColor(Color.YELLOW);
		renderer.line(centerPosition, tmp2);
		tmp3.set(fwdDirection).add(centerPosition);
		renderer.setColor(Color.RED);
		renderer.line(centerPosition, tmp3);
		renderer.end();
		
		renderer.begin(ShapeType.Filled);
		renderer.setColor(getColor());
		renderer.circle(vA.x, vA.y, 0.3f);
		renderer.circle(vB.x, vB.y, 0.3f);
		
		renderer.setColor(Color.GREEN);
		renderer.circle(getX(), getY(), 0.3f);
		renderer.setColor(Color.BLUE);
		renderer.circle(position.x, position.y, 0.3f);
		renderer.setColor(Color.RED);
		renderer.circle(centerPosition.x, centerPosition.y, 0.3f);
	}
	


	/**Updates the velocity vector with the current value of the acceleration vector.
	 * Limits the velocity to the maximum linear speed defined.
	 * @param delta time
	 */
	public void updateVelocity(float delta) {
		velocity.mulAdd(acceleration, delta).limit(maxLinearSpeed).scl(drag);
	}
	
	/**Updates the rotation of the actor according to the direction of the velocity vector.
	 */
	public void updateRotation(float delta) {
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
		
	}
	
	/**Updates the vehicle's position according to the velocity vector for the delta time provided.
	 * Also sets the position of the actor to the new coordinates.
	 * @param delta time
	 */
	public void updatePosition(float delta) {
		centerPosition.mulAdd(velocity, delta);
		this.position.set(centerPosition.x - width/2, centerPosition.y - length/2);
		super.setPosition(this.position.x, this.position.y);
	}
	
	/** Sets the position of the vehicle to a given vector. Also updates the actor position for drawing.
	 * @param position The {@link Vector2} holding the position.
	 */
	public void setLocation(Vector2 centerPosition, float rotation) {
		this.centerPosition.set(centerPosition);
		this.position.set(centerPosition.x - width/2, centerPosition.y - length/2);
		super.setPosition(this.position.x, this.position.y);
		this.rotation = rotation;
		super.setRotation(rotation);
	}
	
	public void setupInputListener() {
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if (!config.userControlled) {
					VehicleInputProcessor.setVehicle(Vehicle.this);
					config.userControlled = true;
				} else {
					VehicleInputProcessor.setVehicle(null);
					config.userControlled = false;
				}
			}
		});
	}
		


	
	private class VehiclePhysics {
		
		//Linear
		public Vector2  position = new Vector2(), 
						velocity = new Vector2(),
						localVelocity = new Vector2(), 
						acceleration = new Vector2(),
						localAcceleration = new Vector2(), 
						traction = new Vector2(),
						drag = new Vector2(),
						totalForces = new Vector2();
		
		//Angular
		public float 	heading = 0, 
						steerAngle = 0, 
						angularVelocity = 0, 
						angularAcceleration = 0,
						throttle = 0,
						brake = 0;
		
		public VehicleConfig config;
		
		public VehiclePhysics(Vehicle vehicle) {
			this.config = vehicle.config;
			
		}
		
		public void update(float delta) {
			
			
			localVelocity.set(velocity).rotateRad(heading);
			boolean movingFwd = localVelocity.y > 0 ? true : false;
			
			//float brakeAccel = 
			//localVelocity.scl(-config.drag);
			
			float brakeAcceleration = Math.min(a, config.maxBrakeAcceleration);
			float linearAcceleration = MathUtils.clamp(value, -config.maxLinearAcceleration, config.maxLinearAcceleration)
			
			localAcceleration.x = 0;
			localAcceleration.y = throttle - (movingFwd ? brake : -brake);
						
			//localAcceleration.set(totalForces).scl(1/config.weight);
			
			acceleration.set(localAcceleration).rotateRad(-heading);
			
			velocity.mulAdd(acceleration, delta).clamp(-20, config.maxLinearSpeed);
			
			angularVelocity += angularAcceleration * delta;
			
			heading += angularVelocity * delta;
			
			position.mulAdd(velocity, delta);
			
		}
		
		public float getSpeed() {
			return velocity.len();
		}

		public float getAccelMagnitude() {
			return acceleration.len();
		}
		
		public float vectorToAngle(Vector2 vector) {
			return (float) Math.atan2(-vector.x, vector.y);
		}
		
		public Vector2 angleToVector(Vector2 outVector, float angle) {
			outVector.x = -(float) Math.sin(angle);
			outVector.y = (float) Math.cos(angle);
			return outVector;
		}
	}
	
	private class VehicleConfig {
		
		public boolean userControlled;
		
		public float width, length, weight, cgToFront, cgToRear, cgToFrontAxle, cgToRearAxle; //(m)
		
		/*public Vector2 vA, vB, vC, vD;
		public Coordinate cA, cB, cC, cD, turningCenter;*/
		
		public Color defaultColor;
		
		public static final float maxEngineForce = 8000, brakeForce = 12000, airDrag = 2.5f, rollDrag = 8;//(N)
		public static final float maxLinearSpeed = 55; //(m/s)
		
		public float maxLinearAcceleration, maxBrakeAcceleration, drag;
	
		public float maxTurningAngle = 60, maxAngularSpeed = 5, maxAngularAcceleration = 60;
		
		public VehicleConfig(Vehicle vehicle, float width, float length, float weigth, Color color) {
			
			this.width = width;
			this.length = length;
			this.weight = weigth;
			
			this.cgToFront = length/2;
			this.cgToRear = length/2;
			this.cgToFrontAxle = length/2 - 0.2f*length;
			this.cgToRearAxle =  length/2 - 0.2f*length;
			
			this.maxLinearAcceleration = maxEngineForce/weigth;
			this.maxBrakeAcceleration = brakeForce/weigth;
			//this.drag = (airDrag + rollDrag) * weigth;
			
			
			//Shape
			/*this.vA = new Vector2();
			this.vB = new Vector2();
			this.vC = new Vector2();
			this.vD = new Vector2();
			this.cA = new Coordinate(-width/2, length/2);
			this.cB = new Coordinate(width/2, length/2);
			this.cC = new Coordinate(width/2, -length/2);
			this.cD = new Coordinate(-width/2, -length/2);
			this.turningCenter = new Coordinate(width/2, 0.2f*length);*/
			
		}
		
		public void update() {		
			/*float angle = rotation;
			vA.set(cA.getX(), cA.getY()).rotate(angle).add(centerPosition);
			vB.set(cB.getX(), cB.getY()).rotate(angle).add(centerPosition);
			vC.set(cC.getX(), cC.getY()).rotate(angle).add(centerPosition);
			vD.set(cD.getX(), cD.getY()).rotate(angle).add(centerPosition);*/

		}
		
	}
}
