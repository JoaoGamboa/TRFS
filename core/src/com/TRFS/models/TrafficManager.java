package com.TRFS.models;

import com.TRFS.models.general.InFlowsManager;
import com.TRFS.scenarios.Scenario;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

/**
 * @author jgamboa
 *
 */

public class TrafficManager {
	
	private InFlowsManager inFlowsManager;

	private Array<Stage> stages;

	public TrafficManager(Scenario scenario) {
		//this.scenario = scenario;
		inFlowsManager = new InFlowsManager(scenario);
		this.stages = scenario.getStages();
	}
	
	public void update(float delta, float simulationTime) {
		
		//Update all actors
		for (Stage stage : stages) {
			stage.act(delta);
		}
		
		inFlowsManager.update(delta, simulationTime);
		
	}
		
	public InFlowsManager getInFlowsManager() {
		return inFlowsManager;
	}

}
