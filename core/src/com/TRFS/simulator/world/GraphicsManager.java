package com.TRFS.simulator.world;

import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Map;
import com.TRFS.simulator.SimulationParameters;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

/**
 * @author jgamboa
 *
 */

public class GraphicsManager {

	private WorldCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private Array<Stage> stages;

	// private Scenario scenario;
	private Map map;

	public GraphicsManager(Scenario scenario, WorldCamera camera,
			SpriteBatch batch) {
		this.map = scenario.getMap();
		this.camera = camera;
		this.batch = batch;
		this.stages = scenario.getStages();
		this.shapeRenderer = new ShapeRenderer();
	}

	public void resize(int width, int height) {
		camera.resize(width, height);
	}

	public void render(float delta) {
		batch.setProjectionMatrix(camera.combined);

		for (Integer zLevel : map.getzLevels()) {
			MapRenderer.render(map, zLevel);

			stages.get(zLevel).draw();
		}

		if (SimulationParameters.drawDebug)
			MapRenderer.renderDebug(map, shapeRenderer, camera);

	}

	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		for (Stage stage : stages) {
			stage.dispose();
		}
	}

	public WorldCamera getCamera() {
		return camera;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

}
