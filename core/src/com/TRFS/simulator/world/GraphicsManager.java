package com.TRFS.simulator.world;

import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Map;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * @author jgamboa
 *
 */

public class GraphicsManager {

	private WorldCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	
	private Array<Array<Vehicle>> vehicleLayers;
	private Map map;
	
	public GraphicsManager(Scenario scenario, WorldCamera camera,
			SpriteBatch batch) {
		this.map = scenario.map;
		this.camera = camera;
		this.batch = batch;
		this.vehicleLayers = scenario.getVehicleLayers();
		this.shapeRenderer = new ShapeRenderer();

	}

	public void resize(int width, int height) {
		camera.resize(width, height);
	}

	public void render(float delta) {
		batch.setProjectionMatrix(camera.combined);

		for (Integer zLevel : map.zLevels) {
			MapRenderer.render(map, zLevel);
			for (Vehicle vehicle : vehicleLayers.get(zLevel)) {
				batch.begin();
				vehicle.draw(batch);
				batch.end();
			}
		}

		if (SimulationParameters.drawDebug){
			shapeRenderer.setProjectionMatrix(camera.combined);
			MapRenderer.renderDebug(map, shapeRenderer);
			for (Integer zLevel : map.zLevels) {
				for (Vehicle vehicle : vehicleLayers.get(zLevel)) {
					vehicle.drawVehicleDebug(shapeRenderer);
				}
			}
		}
	}

	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
	}

	public WorldCamera getCamera() {
		return camera;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

}
