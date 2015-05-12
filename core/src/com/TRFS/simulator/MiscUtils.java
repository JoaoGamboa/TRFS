package com.TRFS.simulator;

import com.TRFS.scenarios.map.Coordinate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

public class MiscUtils {
	
	public static void clearScreen() {

		Gdx.gl.glClearColor(55/255f, 55/255f, 55/255f, 1);//Clear screen with gray color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//Clear Screen
	}
	
	public static void fadeIn(Stage stage) {
		stage.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.2f)));
	}
	
	public static void checkResize(int width, int height, Stage stage) {
		if (width < 1280 || height < 720) {
			Gdx.graphics.setDisplayMode(1280, 720, false);
		}
		stage.getViewport().update(width, height, true);
	}
	
	public static double averageDouble(Array<Double> list) {
		double sum = 0;
		if(list.size >= 1) {
			for (Double value : list) {
				sum += value;
			}
			return (sum / list.size);
		}
		return sum;
	}
	
	public static float averageFloat(Array<Float> list) {
		float sum = 0;
		if(list.size >= 1) {
			for (Float value : list) {
				sum += value;
			}
			return (sum / list.size);
		}
		return sum;
	}
	
	public static float floatArrayMaxMin (Array<Float> list, boolean max) {
		float maxMin = list.get(0);
		for (Float f : list) {
			if (max) if (f > maxMin) maxMin = f;
			if (!max) if (f < maxMin) maxMin = f;
		}
		return maxMin;
	}
	
	public static float vectorToAngle(Vector2 vector) {
		return (float) MathUtils.atan2(-vector.x, vector.y);
	}
	
	public static Vector2 angleToVector(Vector2 outVector, float angle) {
		outVector.x = - MathUtils.sin(angle);
		outVector.y = MathUtils.cos(angle);
		return outVector;
	}
		
	public static void localToGlobal (float localX, float localY, Coordinate point, float rotation, Vector2 origin) {
		float x =  (float) (Math.cos(rotation)*(localX) - Math.sin(rotation)*(localY)+origin.x);
		float y =  (float) (Math.sin(rotation)*(localX) + Math.cos(rotation)*(localY)+origin.y);
		point.set(x,y);
	}
		
	public static void globalToLocalOut (Coordinate point, float rotation, Vector2 origin) {
		float x =  (float) (Math.cos(-rotation)*(point.x-origin.x) - Math.sin(-rotation)*(point.y-origin.y));
		float y =  (float) (Math.sin(-rotation)*(point.x-origin.x) + Math.cos(-rotation)*(point.y-origin.y));
		point.set(x, y);
	}
}