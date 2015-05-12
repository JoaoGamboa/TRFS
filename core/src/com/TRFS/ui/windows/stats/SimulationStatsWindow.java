package com.TRFS.ui.windows.stats;

import com.TRFS.scenarios.Scenario;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.ui.general.windows.SlidingWindow;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**Wraps the code to deploy on the creation of the Simulation Statistics window.
 * @author jgamboa
 */
public class SimulationStatsWindow {

	private static Skin skin = AssetsMan.uiSkin;
	private static Label simulationTimeLabel, elapsedTimeLabel, vehicleCountLabel;
	private static Scenario scenario;
	
	public static void create (SlidingWindow window, Scenario currentScenario) {
		scenario = currentScenario;
		
		//TODO decide if tabbed window is necessary;
		//TODO graphs;
		
		Table table = new Table(skin);
		
		table.add(new Label("Elapsed time:", skin, "smallLabel"));
		elapsedTimeLabel = new Label("", skin, "smallLabel");
		table.add(elapsedTimeLabel).row();
		
		table.add(new Label("Elapsed time:", skin, "smallLabel"));
		simulationTimeLabel = new Label("", skin, "smallLabel");
		simulationTimeLabel.setPosition(360, 20);
		table.add(simulationTimeLabel).row();
		
		table.add(new Label("Vehicles on Netwerok:", skin, "smallLabel"));
		vehicleCountLabel = new Label("", skin, "smallLabel");
		vehicleCountLabel.setPosition(480, 20);
		table.add(vehicleCountLabel).row();
		
		
		ScrollPane sp = new ScrollPane(table, skin);
		window.add(sp).fill();
	}
	
	public static void render() {
		elapsedTimeLabel.setText(String.format("%.1f", (float) scenario.elapsedTime/1000));
		simulationTimeLabel.setText(String.format("%.1f", scenario.simulationTime));
		vehicleCountLabel.setText("" + scenario.trafficManager.inFlowsManager.vehicleCount);
	}
	
}
