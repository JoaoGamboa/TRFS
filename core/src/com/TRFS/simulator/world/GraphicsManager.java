package com.TRFS.simulator.world;

import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Map;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	//private Array<Stage> stages;

	// private Scenario scenario;
	private Map map;
	
	public GraphicsManager(Scenario scenario, WorldCamera camera,
			SpriteBatch batch) {
		this.map = scenario.getMap();
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

		for (Integer zLevel : map.getzLevels()) {
			MapRenderer.render(map, zLevel);
			
			for (Vehicle vehicle : vehicleLayers.get(zLevel)) {
				vehicle.draw(batch);
			}

		}

		if (SimulationParameters.drawDebug){
			shapeRenderer.setProjectionMatrix(camera.combined);
			MapRenderer.renderDebug(map, shapeRenderer);
			for (Integer zLevel : map.getzLevels()) {
				for (Vehicle vehicle : vehicleLayers.get(zLevel)) {
					shapeRenderer.begin(ShapeType.Line);
					vehicle.drawVehicleDebug(shapeRenderer);
					shapeRenderer.end();
				}
				
				/*for (Actor actor : stages.get(zLevel).getActors()) {
					if (actor instanceof Vehicle)
						shapeRenderer.begin(ShapeType.Line);
						((Vehicle) actor).drawVehicleDebug(shapeRenderer);
						shapeRenderer.end();
				}*/
			}
		}
	}

	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		/*for (Stage stage : stages) {
			stage.dispose();
		}*/
	}

	public WorldCamera getCamera() {
		return camera;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

}
