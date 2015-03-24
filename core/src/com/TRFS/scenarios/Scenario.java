package com.TRFS.scenarios;

import com.TRFS.models.TrafficManager;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.MapPreview;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.simulator.world.GraphicsManager;
import com.TRFS.simulator.world.WorldCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * @author jgamboa
 *
 */

public class Scenario {
	
	private GraphicsManager graphicsManager;
	private TrafficManager trafficManager;
	private Array<Stage> stages;
	
	private Map map;
	
	private long startTime = TimeUtils.millis(), elapsedTime = 0;
	private float simulationTime = 0;
	private WorldCamera camera;
	private SpriteBatch batch;
						
	public Scenario (MapPreview mapPreview) {
		this.map = new Map(mapPreview);
		this.camera = new WorldCamera(this);
		this.batch = new SpriteBatch();
		
		this.stages = new Array<Stage>();
		for (@SuppressWarnings("unused") Integer zLevel : map.getzLevels()) {
			this.stages.add(new Stage(new ScreenViewport(this.camera),this.batch));			
		}
		
		this.graphicsManager = new GraphicsManager(this, this.camera, this.batch);
		this.trafficManager = new TrafficManager(this);
		
	}
	
	public void render(float delta) {
		elapsedTime = TimeUtils.timeSinceMillis(startTime); //Milliseconds	
		
		delta = delta * SimulationParameters.simSpeed.getCurrentVal();
		for (int i = 0; i < SimulationParameters.iterations.getCurrentVal(); i++) {
			trafficManager.update(delta/SimulationParameters.iterations.getCurrentVal());
		}
		
		simulationTime += delta;
		System.out.println(delta);
		
		graphicsManager.render(delta);
	}
	
	public void resize(int width, int height) {
		graphicsManager.resize(width, height);
	}
	
	public Map getMap() {
		return map;
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

	public Array<Stage> getStages() {
		return stages;
	}

	public TrafficManager getTrafficManager() {
		return trafficManager;
	}
			
}
