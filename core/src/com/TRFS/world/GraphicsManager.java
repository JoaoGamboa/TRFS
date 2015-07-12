package com.TRFS.world;

import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Link;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @author jgamboa
 *
 */

public class GraphicsManager {

	private Scenario scenario;
	public ShapeRenderer shapeRenderer;
	private BitmapFont font;
	//public static StaticDebugRendering staticRendering;
	
	public GraphicsManager(Scenario scenario) {
		this.scenario = scenario;
		this.shapeRenderer = new ShapeRenderer();
		this.font = new BitmapFont(Gdx.files.internal(AssetsMan.mediumFont));
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
			drawLabels();
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
	
	public void drawLabels() {
		font.setScale(0.4f);
		scenario.batch.begin();
		for (Link link : scenario.map.links) {
			int midPoint = Math.round(link.coordinates.size/2);
			font.draw(scenario.batch, Integer.toString(link.id), link.coordinates.get(midPoint).x, link.coordinates.get(midPoint).y);
		}
		scenario.batch.end();
	}

}
