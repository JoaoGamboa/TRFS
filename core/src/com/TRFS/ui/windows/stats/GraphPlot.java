package com.TRFS.ui.windows.stats;

import com.TRFS.scenarios.Scenario;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class GraphPlot extends Widget{
	
	private Stage stage;
	private Scenario scenario;
	private ShapeRenderer renderer;
	
	private float minX, maxX, minY, maxY;
	private float width, height;
	
	public GraphPlot(Stage stage, Scenario scenario) {
		this.stage = stage;
		this.scenario = scenario;
		this.renderer = new ShapeRenderer();
	}
	
	@Override
	public void act(float delta) {
		
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
	
	
	
}
