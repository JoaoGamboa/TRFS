package com.TRFS.scenarios;

import com.TRFS.models.TrafficManager;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.MapPreview;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.simulator.world.GraphicsManager;
import com.TRFS.simulator.world.WorldCamera;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author jgamboa
 *
 */

public class Scenario {
	
	private GraphicsManager graphicsManager;
	private TrafficManager trafficManager;
	
	private Array<Array<Vehicle>> vehicleLayers;
	//private Array<Stage> stages;
	
	public Map map;
	
	private long startTime = TimeUtils.millis(), elapsedTime = 0;
	private float simulationTime = 0;
	
	private WorldCamera camera;
	private SpriteBatch batch;
						
	public Scenario (MapPreview mapPreview) {
		this.map = new Map(mapPreview);
		this.camera = new WorldCamera(this);
		this.batch = new SpriteBatch();
		
		vehicleLayers = new Array<Array<Vehicle>>();
		for (@SuppressWarnings("unused") Integer zLevel : map.zLevels) {
			this.vehicleLayers.add(new Array<Vehicle>());			
		}
		
		/*this.stages = new Array<Stage>();
		for (@SuppressWarnings("unused") Integer zLevel : map.getzLevels()) {
			this.stages.add(new Stage(new ScreenViewport(this.camera),this.batch));			
		}*/
		
		this.graphicsManager = new GraphicsManager(this, this.camera, this.batch);
		this.trafficManager = new TrafficManager(this);
		
	}
	
	public void render(float delta) {
		
		if (delta > 0.25f) delta = 0.25f;
		
		float speedFactor = SimulationParameters.simSpeed.getCurrentVal();
		float trafficDelta = speedFactor > 0 ? delta * speedFactor : 0;

		if (!SimulationParameters.paused && speedFactor > 0) {
			trafficManager.update(trafficDelta, simulationTime);
			simulationTime += trafficDelta;
		
		}
				
		elapsedTime = TimeUtils.timeSinceMillis(startTime); //Milliseconds	
		graphicsManager.render(delta);		
		
	}
	
	public void resize(int width, int height) {
		graphicsManager.resize(width, height);
	}
	
	public void dispose() {
		graphicsManager.dispose();
	}

	public GraphicsManager getGraphicsManager() {
		return graphicsManager;
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}

	public float getSimulationTime() {
		return simulationTime;
	}
	
	public Array<Array<Vehicle>> getVehicleLayers() {
		return vehicleLayers;
	}
	
	public TrafficManager getTrafficManager() {
		return trafficManager;
	}
			
}
