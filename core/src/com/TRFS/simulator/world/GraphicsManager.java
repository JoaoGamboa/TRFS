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
	
	public GraphicsManager(Scenario scenario) {
		this.scenario = scenario;
		this.shapeRenderer = new ShapeRenderer();

	}

	public void resize(int width, int height) {
		scenario.camera.resize(width, height);
	}

	public void render(float delta) {
		scenario.batch.setProjectionMatrix(scenario.camera.combined);

		for (Integer zLevel : scenario.map.zLevels) {
			MapRenderer.render(scenario.map, zLevel);
			for (Vehicle vehicle : scenario.trafficManager.vehicleLayers.get(zLevel)) {
				scenario.batch.begin();
				vehicle.draw(scenario.batch);
				scenario.batch.end();
			}
		}

		if (SimulationParameters.drawDebug){
			shapeRenderer.setProjectionMatrix(scenario.camera.combined);
			MapRenderer.renderDebug(scenario.map, shapeRenderer);
			for (Integer zLevel : scenario.map.zLevels) {
				for (Vehicle vehicle : scenario.trafficManager.vehicleLayers.get(zLevel)) {
					vehicle.drawVehicleDebug(shapeRenderer);
				}
			}
		}
	}

	public void dispose() {
		shapeRenderer.dispose();
	}

}
