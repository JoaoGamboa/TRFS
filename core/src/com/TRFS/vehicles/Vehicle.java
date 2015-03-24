package com.TRFS.vehicles;

import com.TRFS.models.Behavior;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.SimulationParameters;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This class represents the physical characteristics of a vehicle. Decisions are made by Behavior class. 
 * 
 * @author jgamboa
 *
 */
public class Vehicle extends Actor implements Steerable<Vector2>{
	
	//Properties
	//private static Skin skin = AssetsMan.uiSkin;
	//Shape & Aspect
	private float width, lenght;
	private TextureRegion region;
	//private Image image;
	private Color color;
	
	//Behaviour
	private Behavior behavior;
	
	//Position (back left)
	private Link currentLink;
	private Lane currentLane;	
	
	//Movement
	public Vector2 position;
	//private float positionOnLink;
	
	public float acceleration;
	public Vector2 linearVelocity;
	
	float angularVelocity;
	float boundingRadius;
	boolean tagged;
	
	float maxLinearSpeed = 160;
	float maxLinearAcceleration = 200;
	float maxAngularSpeed = 5;
	float maxAngularAcceleration = 10;
				
	public Vehicle(Link link, Lane lane) {
		this.position = new Vector2(0,0);
		this.linearVelocity = new Vector2(1,1);
		this.region = new TextureRegion(AssetsMan.uiSkin.getRegion("testcar"));
		this.color = new Color(Color.RED);

		this.behavior = new Behavior(this, SimulationParameters.currentCarFolModel, SimulationParameters.currentLaneChangeModel);
		
		positionVehicleStart(link, lane);

	}
		
	public void update (float delta) {
		
		Vehicle leader = null;
		
		//Behaviour decisions
		behavior.update(delta, leader);
		
		//Physics decisions & update
		acceleration = MathUtils.clamp(behavior.getAcceleration(), -maxLinearAcceleration, maxLinearAcceleration);
		
		linearVelocity.scl(acceleration).limit(maxLinearSpeed);
		position.mulAdd(linearVelocity, delta);
		this.setPosition(this.position.x, this.position.y);
		
		//Might not be necessary if we use ray casting
		//positionOnLink += linearVelocity.scl(delta).len();
		
		//System.out.println(linearVelocity);
		
	}
	
	
	@Override
	public void act(float delta) {
		update(delta);
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		color.set(color.GREEN);
		batch.setColor(color.r, color.g, color.b, parentAlpha);
		batch.draw(region, getX(), getY(), width, lenght);
	}
	
	//Getters & Setters	
	/*public float getPositionOnLink() {
		return positionOnLink;
	}


	public void setPositionOnLink(float positionOnLink) {
		this.positionOnLink = positionOnLink;
	}*/
	
	public void positionVehicleStart(Link link, Lane lane) {
		setCurrentLink(link);
		setCurrentLane(lane);
		this.behavior.setChangedLink(true);

		this.position.set(lane.getWayPoints().first());
		this.setPosition(this.position.x, this.position.y);
	}
	
	public Link getCurrentLink() {
		return currentLink;
	}


	public void setCurrentLink(Link link) {
		this.currentLink = link;		
	}


	public Lane getCurrentLane() {
		return currentLane;
	}


	public void setCurrentLane(Lane currentLane) {
		this.currentLane = currentLane;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getLenght() {
		return lenght;
	}
	
	public void setLenght(float lenght) {
		this.lenght = lenght;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public float getAcceleration() {
		return acceleration;
	}
	
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}
	
	public Behavior getCarFollowingBehavior() {
		return behavior;
	}
	
	@Override
	public float getMaxLinearSpeed() {
		return maxAngularSpeed;
	}

	@Override
	public void setMaxLinearSpeed (float maxLinearSpeed) {
		this.maxLinearSpeed = maxLinearSpeed;
	}

	@Override
	public float getMaxLinearAcceleration() {
		return maxLinearAcceleration;
	}

	@Override
	public void setMaxLinearAcceleration (float maxLinearAcceleration) {
		this.maxLinearAcceleration = maxLinearAcceleration;
	}

	@Override
	public float getMaxAngularSpeed() {
		return maxAngularSpeed;
	}

	@Override
	public void setMaxAngularSpeed (float maxAngularSpeed) {
		this.maxAngularSpeed = maxAngularSpeed;
	}

	@Override
	public float getMaxAngularAcceleration() {
		return maxAngularAcceleration;
	}

	@Override
	public void setMaxAngularAcceleration (float maxAngularAcceleration) {
		this.maxAngularAcceleration = maxAngularAcceleration;
	}

	@Override
	public Vector2 getPosition() {
		return this.position;
	}

	@Override
	public float getOrientation() {
		return this.getRotation();
	}

	@Override
	public Vector2 getLinearVelocity() {
		return this.linearVelocity;
	}

	@Override
	public float getAngularVelocity () {
		return angularVelocity;
	}

	@Override
	public float getBoundingRadius () {
		return boundingRadius;
	}

	@Override
	public boolean isTagged () {
		return tagged;
	}

	@Override
	public void setTagged (boolean tagged) {
		this.tagged = tagged;
	}

	@Override
	public Vector2 newVector () {
		return new Vector2();
	}

	@Override
	public float vectorToAngle (Vector2 vector) {
		return (float)Math.atan2(-vector.x, vector.y);
	}

	@Override
	public Vector2 angleToVector (Vector2 outVector, float angle) {
		outVector.x = -(float)Math.sin(angle);
		outVector.y = (float)Math.cos(angle);
		return outVector;
	}

}
