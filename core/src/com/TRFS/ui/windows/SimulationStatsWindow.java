package com.TRFS.ui.windows;

import java.util.ArrayList;

import com.TRFS.scenarios.Scenario;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.ui.general.windows.TabbedWindow;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**Wraps the code to deploy on the creation of the Simulation Statistics window.
 * @author jgamboa
 */
public class SimulationStatsWindow extends TabbedWindow{

	private Skin skin = AssetsMan.uiSkin;
	private Label simulationTimeLabel, elapsedTimeLabel, vehicleCountLabel, totalVehiclesLabel, simulationSpeedLabel;
	private Label speedLabel, idLabel, throttleLabel, brakeLabel;
	private Scenario scenario;
	
	private CarFollowingGraphPlot cfGP;
	
	private ArrayList<Table> tables = new ArrayList<Table>();
	
	public static Vehicle taggedVehicle;
	
	public SimulationStatsWindow(String title, float targetWidth, float targetHeight, Stage stage, boolean dockLeft, boolean dockDown, 
									String[] buttons, Scenario scenario) {
		super(title, targetWidth, targetHeight, stage, dockLeft, dockDown, buttons);
		this.scenario = scenario;
		this.tables = new ArrayList<Table>();
		
		create();
		
		super.build(tables);
	}
	
	private void create () {
				
		Table tableGeneral = new Table(skin);
		
		//tableGeneral.add(new Label("SIMULATION", skin, "smallLabel")).colspan(2).row();
		
		tableGeneral.add(new Label("Elapsed time:", skin, "smallLabel"));
		elapsedTimeLabel = new Label("", skin, "smallLabel");
		tableGeneral.add(elapsedTimeLabel).row();
		
		tableGeneral.add(new Label("Simulated time:", skin, "smallLabel"));
		simulationTimeLabel = new Label("", skin, "smallLabel");
		tableGeneral.add(simulationTimeLabel).row();
		
		tableGeneral.add(new Label("Simulation speed:", skin, "smallLabel"));
		simulationSpeedLabel = new Label("",  skin, "smallLabel");
		tableGeneral.add(simulationSpeedLabel).row();
		
		tableGeneral.add(new Label("Vehicles on network:", skin, "smallLabel"));
		vehicleCountLabel = new Label("", skin, "smallLabel");
		tableGeneral.add(vehicleCountLabel).row();
		
		tableGeneral.add(new Label("Total vehicles:", skin, "smallLabel"));
		totalVehiclesLabel = new Label("", skin, "smallLabel");
		tableGeneral.add(totalVehiclesLabel).row();
			
		//Vehicle
		Table outerTagged = new Table(skin);
				
		Table tableTagged = new Table(skin);
		
		tableTagged.add(new Image(skin, "horBezel")).fill().height(4).colspan(2).expandX().row();
		tableTagged.add(new Label("TAGGED VEHICLE", skin, "smallLabel")).colspan(2).row();
		
		tableTagged.add(new Label("ID:", skin, "smallLabel"));
		idLabel = new Label("", skin, "smallLabel");
		tableTagged.add(idLabel).row();
		
		tableTagged.add(new Label("Speed:", skin, "smallLabel"));
		speedLabel = new Label("", skin, "smallLabel");
		tableTagged.add(speedLabel).row();
		
		tableTagged.add(new Label("Throttle:", skin, "smallLabel"));
		throttleLabel = new Label("", skin, "smallLabel");
		tableTagged.add(throttleLabel).row();
		
		tableTagged.add(new Label("Brake:", skin, "smallLabel"));
		brakeLabel = new Label("", skin, "smallLabel");
		tableTagged.add(brakeLabel).row();
		
		/*cfGP = new CarFollowingGraphPlot(super.getStage(), scenario);
		
		tableTagged.add(cfGP).row();*///TODO
		
		ScrollPane spTagged = new ScrollPane(tableTagged, skin);
		
		outerTagged.add(spTagged).pad(2,0,2,0).fill().width(320).row();
		outerTagged.add(new Image(skin, "horBezel")).height(4).fill().expandX().row();
			
		//--Vehicle
		tables.add(tableGeneral);
		tables.add(outerTagged);
		
	}
	
	public void render() {
		elapsedTimeLabel.setText(String.format("%.1f", (float) scenario.elapsedTime/1000));
		simulationTimeLabel.setText(String.format("%.1f", scenario.simulationTime));
		simulationSpeedLabel.setText(String.format("%.1f times", SimulationParameters.simSpeed.getCurrentVal()));
		vehicleCountLabel.setText("" + scenario.trafficManager.inFlowsManager.vehicleCount);
		totalVehiclesLabel.setText("" + scenario.trafficManager.inFlowsManager.totalVehicleCount);
		
		if (taggedVehicle != null) {
			idLabel.setText("" + taggedVehicle.id);
			speedLabel.setText(String.format("%.2f", taggedVehicle.physics.speed*3.6f));
			throttleLabel.setText(String.format("%.1f", taggedVehicle.physics.throttle));
			brakeLabel.setText(String.format("%.1f", taggedVehicle.physics.brake));		
		}
	}
}
