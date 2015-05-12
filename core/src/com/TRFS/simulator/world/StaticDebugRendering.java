package com.TRFS.simulator.world;

import com.TRFS.scenarios.map.Node;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class StaticDebugRendering {
	
	public ShapeRenderer renderer;
	
	public Array<Node> nodes;

	public StaticDebugRendering(ShapeRenderer renderer) {
		this.renderer = renderer;
		this.nodes = new Array<Node>();
	}
	
	public void render(){
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.WHITE);
		for (Node node : nodes) {
			renderer.circle(node.coordinate.x, node.coordinate.y, 5f);
		}
		renderer.end();
	}

}
