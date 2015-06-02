package com.TRFS.ui.windows.stats;

import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Array;

public class CarFollowingGraphPlot extends Widget{
	
	private Stage stage;
	private Scenario scenario;
	private ShapeRenderer renderer;
	
	private float minX, maxX, minY, maxY;
	private float width, height;
	
	private final int nPoints = 20;
	private Array<Coordinate> points;
	
	private Vehicle taggedVeh;
	
	public CarFollowingGraphPlot(Stage stage, Scenario scenario) {
		this.stage = stage;
		this.scenario = scenario;
		this.renderer = new ShapeRenderer();
		
		this.points = new Array<Coordinate>(nPoints);
	}
	
	
	int pointIndex, deleteTimer;
	@Override
	public void act(float delta) {
		
		if (taggedVeh != null && taggedVeh.behavior.carFollowingBehaviour.leader != null) {
			points.add(new Coordinate(taggedVeh.behavior.carFollowingBehaviour.dV, taggedVeh.behavior.carFollowingBehaviour.dX));
			if (points.size > nPoints) points.removeIndex(0);
		} else {
			while (deleteTimer > 0.2) {
				points.removeIndex(0);
				deleteTimer = 0;
			}
			deleteTimer += delta;
		}

		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		renderer.setProjectionMatrix(stage.getCamera().combined);
		
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.WHITE);
		renderer.line(minX, 0, maxX, 0);
		renderer.line(minY, 0, maxY, 0);
		renderer.end();
		
		super.draw(batch, parentAlpha);
	}
	
	public void setVehicle(Vehicle vehicle) {
		if (vehicle == null) {
			points.clear();
		}
		this.taggedVeh = vehicle;
	}
	
	
	
}
