package com.TRFS.simulator.world;

import com.TRFS.scenarios.map.Coordinate;
import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.Node;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MapRenderer {

	public static void render(Map map, int zLevel) {
		
		// Start required graphic utils for rendering links
		for (Link link : map.getLinks()) {
			if (link.getZ() == zLevel) {
				/* link.render() */
			}
			;
		}
		// Render the nodes
		for (Node node : map.getNodes()) {
			if (node.getzLevel() == zLevel) {
				/* node.render(); */
			}
		}

	}

	public static void renderDebug(Map map, ShapeRenderer shapeRenderer,
			Camera camera) {

		shapeRenderer.setProjectionMatrix(camera.combined);
		// Render Lines
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		for (Link link : map.getLinks()) {
			for (Lane lane : link.getLanes()) {
				renderLine(lane.getLeftOffset(), shapeRenderer);
				renderLine(lane.getRightOffset(), shapeRenderer);
			}
			//renderLine(link.getCoordinates(), shapeRenderer);
		}
		shapeRenderer.end();

		// Render Circles
		shapeRenderer.begin(ShapeType.Filled);
		// Nodes
		shapeRenderer.setColor(Color.WHITE);
		for (Node node : map.getNodes()) {
			node.renderDebugPoints(shapeRenderer);
		}
		// Lane centerLine
		shapeRenderer.setColor(Color.RED);
		for (Link link : map.getLinks()) {
			for (Lane lane : link.getLanes()) {
				renderDebugPoints(lane.getCenterLine(), shapeRenderer);
			}
		}

		shapeRenderer.end();
	}

	public static void renderLine(Array<Coordinate> coordinates,
			ShapeRenderer shapeRenderer) {
		for (int i = 0; i < coordinates.size; i++) {
			int next = (i + 1) % coordinates.size;
			if (next != 0)
				shapeRenderer.line(coordinates.get(i).getX(), coordinates
						.get(i).getY(), coordinates.get(next).getX(),
						coordinates.get(next).getY());
		}
	}

	public static void renderDebugPoints(Array<Coordinate> coordinates,
			ShapeRenderer shapeRenderer) {
		for (int i = 0; i < coordinates.size; i++)
			shapeRenderer.circle(coordinates.get(i).getX(), coordinates.get(i)
					.getY(), 0.5f);
	}

	public static void renderDebugVectorPoints(Array<Vector2> vectors,
			ShapeRenderer shapeRenderer) {
		for (int i = 0; i < vectors.size; i++)
			shapeRenderer.circle(vectors.get(i).x, vectors.get(i).y, 0.5f);
	}

}
