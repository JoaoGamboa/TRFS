package com.TRFS.ui.windows;

import java.util.ArrayList;

import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Link;
import com.TRFS.screens.ScreensManager;
import com.TRFS.screens.WorldScreen;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.ui.general.parameters.DynamicParamSlider;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.TRFS.ui.general.windows.SlidingWindow;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Wraps the code to deploy on the creation of the Simulation Parameters window.
 * 
 * @author jgamboa
 */
public class SimulationParamWindow extends SlidingWindow{

	private Table table, outterTable;
	private ScrollPane sp;
	private Skin skin = AssetsMan.uiSkin;
	private ArrayList<DynamicParamSlider> sliderListBase, sliderListFlows;
	private TextButton resetButton;
	
	public SimulationParamWindow(String title, float targetWidth,
			float targetHeight, Stage stage, boolean dockLeft, boolean dockDown) {
		super(title, targetWidth, targetHeight, stage, dockLeft, dockDown);

		create();
		
	}

	public void create() {

		table = new Table(skin);
		sp = new ScrollPane(table, skin);
		sp.setFadeScrollBars(false);
		outterTable = new Table(skin);

		resetButton = new TextButton("RESET TO DEFAULTS", skin, "mainButton");

		sliderListBase = new ArrayList<DynamicParamSlider>();
		sliderListFlows = new ArrayList<DynamicParamSlider>();

		Scenario currentScenario = ((WorldScreen) ScreensManager
				.getWorldScreen()).getScenario();

		for (int i = 0; i < SimulationParameters.simParamsListBase.length; i++) {

			sliderListBase.add(new DynamicParamSlider(
					SimulationParameters.simParamsListBase[i]));

			DynamicParamSlider slider = sliderListBase.get(i);
			table.add(slider.getLabel()).row();

			table.add(slider).width(250).pad(10).row();

			slider.setValue(slider.getParameter().getCurrentVal());
		}

		for (Link link : currentScenario.map.inFlowLinks) {
			DynamicParamSlider slider = new DynamicParamSlider(
					link.inFlowParam);
			sliderListFlows.add(slider);

			SimulationParameters.simParamsListFlows.add(link.inFlowParam);

			table.add(slider.getLabel()).row();
			table.add(slider).width(250).pad(10).row();
			slider.setValue(slider.getParameter().getCurrentVal());
		}

		outterTable.add(sp).pad(2, 0, 2, 0).fill().width(320).row();

		outterTable.add(new Image(skin, "horBezel")).height(4).fill().row();

		outterTable.add(resetButton).pad(10);

		resetButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				resetParameters();
				resetButton.setChecked(false);
			}
		});

		super.add(outterTable).fill();

	}

	public void resetParameters() {
		for (int i = 0; i < SimulationParameters.simParamsListBase.length; i++) {
			DynamicSimParam param = SimulationParameters.simParamsListBase[i];
			param.setCurrentVal(param.getDefaultVal());
			sliderListBase.get(i).setValue(param.getDefaultVal());
		}

		for (int i = 0; i < ((WorldScreen) ScreensManager.getWorldScreen())
				.getScenario().map.inFlowLinks.size; i++) {
			DynamicSimParam param = SimulationParameters.simParamsListFlows
					.get(i);
			param.setCurrentVal(param.getDefaultVal());
			sliderListFlows.get(i).setValue(param.getDefaultVal());
		}
	}
}
