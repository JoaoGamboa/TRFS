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

public class Vehicle extends Actor /* implements Steerable<Vector2> */{

	// Properties
	boolean userControlled;
	
	//DEBUG TODO delete
	private static int counter;
	private int id, iterCounter;
	private float distanceTraveled;
	private Vector2 laspos,  tmp1, tmp2, tmp3;
	//endDEBUG
	
	// Shape & Aspect
	protected TextureRegion region;
	public Color defaultColor;
	private float width, length;
	private Vector2 vA, vB, vC, vD;
	private Coordinate cA, cB, cC, cD;

	private Behavior behavior;

	private Vector2 position, centerPosition, velocity, acceleration, fwdDirection;
	//private float angularVelocity;/*, boundingRadius*/;
	private float rotation, maxLinearSpeed = 160/3.6f, maxLinearAcceleration = 5, drag = 0.99f/*, maxAngularSpeed = 5, maxAngularAcceleration = 10*/;
	
	/**
	 * Creates a new vehicle.
	 * @param width The width of the vehicle
	 * @param length The length of the vehicle
	 * @param color The color of the vehicle
	 * @param textureName The name of the texture to use
	 */
	public Vehicle(float width, float length, Color color, String textureName) {
		this.width = width; this.length = length;
		this.region = new TextureRegion(AssetsMan.uiSkin.getRegion(textureName));
		this.defaultColor = color;
		
		this.setSize(width, length);
		this.setOrigin(Align.center);
		this.setColor(color);
		
		this.position = new Vector2();
		this.centerPosition = new Vector2();
		this.velocity = new Vector2(); // TODO depends on initial speed
		this.acceleration = new Vector2();
		this.fwdDirection = new Vector2(0,1);
		
		//Shape
		this.vA = new Vector2();
		this.vB = new Vector2();
		this.vC = new Vector2();
		this.vD = new Vector2();
		this.cA = new Coordinate(-width/2, length/2);
		this.cB = new Coordinate(width/2, length/2);
		this.cC = new Coordinate(width/2, -length/2);
		this.cD = new Coordinate(-width/2, -length/2);
		
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
		if (!userControlled)
			behavior.update(delta, acceleration);
			//acceleration.set(behavior.update(delta));
		
		updateVelocity(delta);
		updatePosition(delta);
		updateRotation(delta);
		updateShape();

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
	
	public void updateShape() {		
		float angle = rotation;
		vA.set(cA.getX(), cA.getY()).rotate(angle).add(centerPosition);
		vB.set(cB.getX(), cB.getY()).rotate(angle).add(centerPosition);
		vC.set(cC.getX(), cC.getY()).rotate(angle).add(centerPosition);
		vD.set(cD.getX(), cD.getY()).rotate(angle).add(centerPosition);
	}
	
	public float vectorToAngle(Vector2 vector) {
		return (float) Math.atan2(-vector.x, vector.y);
	}
	
	public Vector2 angleToVector(Vector2 outVector, float angle) {
		outVector.x = -(float) Math.sin(angle);
		outVector.y = (float) Math.cos(angle);
		return outVector;
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
		if (!velocity.isZero(0.02f)) {
			float targetRotation = vectorToAngle(velocity)*MathUtils.radiansToDegrees;
			float a = targetRotation;
			targetRotation = fwdDirection.dot(velocity) > 0 ? targetRotation : targetRotation+180;
			float b = targetRotation;
			float e = getRotation();
			float rotation = (targetRotation - getRotation()) % MathUtils.PI2;
			float c = rotation;
			if (rotation > MathUtils.PI) rotation -= MathUtils.PI2;				
			float d = rotation;
			this.rotation = rotation * delta;
	
			System.out.println(a + "  "+ b + "  " + c  + "  " + d  + "  " + this.rotation);
						
			super.setRotation(this.rotation);
		}
		this.fwdDirection.set(0,1).rotate(rotation);
		
		//float rotation = (float) Math.atan2(fwdDirection.x, fwdDirection.y);//TODO
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
	public void setPosition(Vector2 centerPosition) {
		this.centerPosition.set(centerPosition);
		this.position.set(centerPosition.x - width/2, centerPosition.y - length/2);
		super.setPosition(this.position.x, this.position.y);
	}
	
	public void setupInputListener() {
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if (!userControlled) {
					VehicleInputProcessor.setVehicle(Vehicle.this);
					userControlled = true;
				} else {
					VehicleInputProcessor.setVehicle(null);
					userControlled = false;
				}
			}
		});
	}
	
	public Vector2 getFwdDirection() {
		return fwdDirection;
	}
	
	public void setUserControlled(boolean userControlled) {
		this.userControlled = userControlled;
	}
	
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
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
	
	public Vector2 getCenterPosition() {
		return centerPosition;
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
