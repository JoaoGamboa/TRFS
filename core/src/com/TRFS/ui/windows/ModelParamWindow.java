package com.TRFS.ui.windows;

import java.util.ArrayList;

import com.TRFS.models.carFollowing.CarFollowingModel;
import com.TRFS.models.carFollowing.FritzscheCarFollowing;
import com.TRFS.models.carFollowing.W74CarFollowing;
import com.TRFS.scenarios.Scenario;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.ui.general.parameters.DynamicParamSlider;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.TRFS.ui.general.windows.TabbedWindow;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

/**Wraps the code to deploy on the creation of the Model Parameters window.
 * @author jgamboa
 */
public class ModelParamWindow extends TabbedWindow{
	
	private Skin skin = AssetsMan.uiSkin;
				
	private TextButton resetButton;
	private Table outterTable;
	private ArrayList<Table> tables;
	private ArrayList<DynamicParamSlider> sliderList;
	private Array<DynamicSimParam[]> calParams;
		
	public ModelParamWindow(String title, float targetWidth, float targetHeight,
			Stage stage, boolean dockLeft, boolean dockDown, String[] buttons, Scenario scenario) {
		super(title, targetWidth, targetHeight,	stage, dockLeft, dockDown, buttons);
		
		tables = new ArrayList<Table>();
		
//----------------------------------------	
		//CAR FOLLOWING	
		tables.add(buildModelParamSliders());
//----------------------------------------	
		//LANE-CHANGING
		
		Table dummyTable = new Table(skin);
		dummyTable.add("PLACEHOLDER FOR LANE CHANGE","smallLabel").expand();
		dummyTable.setVisible(false);
		tables.add(dummyTable);
		
		super.build(tables);
		
	}
	
	private Table buildModelParamSliders() {
		sliderList= new ArrayList<DynamicParamSlider>();
		resetButton = new TextButton("RESET TO DEFAULTS", skin, "mainButton");
		outterTable = new Table(skin);
		
		outterTable.add(SimulationParameters.currentCarFolModel + " Simulation Parameters", "smallLabel").pad(5,0,5,0).row();
				
		int model = 0;
		for (int i = 0; i < CarFollowingModel.carFolModels.length; i++) {
			if (SimulationParameters.currentCarFolModel.equals(CarFollowingModel.carFolModels[i])) {
				model = i;
			}
		}
		calParams = new Array<DynamicSimParam[]>();
		switch (model) {
		case 0:
			calParams.add(W74CarFollowing.calibrationParameters);
			
		case 1:
			calParams.add(FritzscheCarFollowing.calibrationParameters);
		}
		
		Table innerTable = new Table(skin);
		ScrollPane sp = new ScrollPane(innerTable, skin);
		sp.setFadeScrollBars(false);
		for (int i = 0; i < calParams.get(0).length; i++) {
			
			sliderList.add( new DynamicParamSlider(calParams.get(0)[i]));
			
			DynamicParamSlider slider = sliderList.get(i);
			
			innerTable.add(slider.getLabel()).row();
			innerTable.add(slider).width(320-70).pad(10).row();
			
		}
		
		outterTable.add(sp).pad(2,0,2,0).fill().width(320).row();
		
		outterTable.add(new Image(skin, "horBezel")).height(4).fill().row();
		
		outterTable.add(resetButton).pad(10);
		
		outterTable.setVisible(false);
		
		resetButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				resetModelParamSliders();
				resetButton.setChecked(false);
			}
		});
		
		return outterTable;
	}
	
	
	/** Resets the sliders to the default value.	
	 */
	private void resetModelParamSliders() {
		for (int i = 0; i < calParams.get(0).length; i++) {
			calParams.get(0)[i].setCurrentVal(calParams.get(0)[i].getDefaultVal());
			sliderList.get(i).setValue(calParams.get(0)[i].getDefaultVal());
		}
	}
		
}
