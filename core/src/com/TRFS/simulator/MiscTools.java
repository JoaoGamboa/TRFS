package com.TRFS.simulator;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class MiscTools {
	
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
	
	public static float average(List <Double> list) {
		double sum = 0;
		if(!list.isEmpty()) {
			for (Double value : list) {
				sum += value;
			}
			return (float) (sum / list.size());
		}
		return (float) sum;
	}
}
