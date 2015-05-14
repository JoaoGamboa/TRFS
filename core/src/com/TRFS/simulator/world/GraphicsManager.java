package com.TRFS.simulator.world;

import com.TRFS.scenarios.Scenario;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @author jgamboa
 *
 */

public class GraphicsManager {

	private Scenario scenario;
	public ShapeRenderer shapeRenderer;
	//public static StaticDebugRendering staticRendering;
	
	public GraphicsManager(Scenario scenario) {
		this.scenario = scenario;
		this.shapeRenderer = new ShapeRenderer();
		//GraphicsManager.staticRendering = new StaticDebugRendering(shapeRenderer);
	}

	public void resize(int width, int height) {
		scenario.camera.resize(width, height);
	}

	public void render(float delta) {
		scenario.batch.setProjectionMatrix(scenario.camera.combined);
		
		boolean drawDebug = SimulationParameters.drawDebug;
		if (drawDebug) {
			shapeRenderer.setProjectionMatrix(scenario.camera.combined);
			MapRenderer.renderDebug(scenario.map, shapeRenderer);
		}
		
		//Loop through layers starting from the topmost one
		for (int i = scenario.map.zLevels.size - 1; i >= 0; i--) {
			int z = scenario.map.zLevels.get(i);
			
			MapRenderer.render(scenario.map, z);
			
			for (Vehicle vehicle : scenario.trafficManager.vehicles) {
				if (vehicle.physics.zLevel == z) {
					scenario.batch.begin();
					vehicle.draw(scenario.batch);
					scenario.batch.end();
					if (drawDebug) vehicle.drawVehicleDebug(shapeRenderer);
				}
			}
		}
	}

	public void dispose() {
		shapeRenderer.dispose();
	}

}
