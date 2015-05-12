package com.TRFS.scenarios;

import com.TRFS.models.TrafficManager;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.MapPreview;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.simulator.world.GraphicsManager;
import com.TRFS.simulator.world.WorldCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author jgamboa
 *
 */

public class Scenario {
	
	public GraphicsManager graphicsManager;
	public TrafficManager trafficManager;
		
	public Map map;
	
	public long startTime = TimeUtils.millis(), elapsedTime = 0;
	public float simulationTime = 0;
	
	public WorldCamera camera;
	public SpriteBatch batch;
						
	public Scenario (MapPreview mapPreview) {
		this.map = new Map(mapPreview);
		this.camera = new WorldCamera(this);
		this.batch = new SpriteBatch();
		this.graphicsManager = new GraphicsManager(this);
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
		batch.dispose();
	}
			
}
