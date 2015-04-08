package com.TRFS.ui.windows.models;

import java.util.ArrayList;

import com.TRFS.models.Behavior;
import com.TRFS.models.carFollowing.FritzscheCarFollowing;
import com.TRFS.models.carFollowing.W74CarFollowing;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.ui.general.parameters.DynamicParamSlider;
import com.TRFS.ui.general.parameters.DynamicSimParam;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

/**Wraps the procedure for creating the Model Parameters sliders.
 * @author jgamboa
 *
 */
public class ModelParameterSliders {

	private static Skin skin = AssetsMan.uiSkin;
	private static TextButton resetButton;
	private static ArrayList<DynamicParamSlider> sliderList;
	private static Table outterTable;
	private static Array<DynamicSimParam[]> calParams;
	
	/**Creates the table inside of which the sliders live.
	 * @return
	 */
	public static Table create() {

		sliderList= new ArrayList<DynamicParamSlider>();
		resetButton = new TextButton("RESET TO DEFAULTS", skin, "mainButton");
		outterTable = new Table(skin);
		
		outterTable.add(SimulationParameters.currentCarFolModel + " Simulation Parameters", "smallLabel").pad(5,0,5,0).row();
				
		int model = 0;
		for (int i = 0; i < Behavior.carFolModels.length; i++) {
			if (SimulationParameters.currentCarFolModel.equals(Behavior.carFolModels[i])) {
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
				resetParameters();
				resetButton.setChecked(false);
			}
		});
		
		return outterTable;
	}
	
	/** Resets the sliders to the default value.	
	 */
	public static void resetParameters() {
		for (int i = 0; i < calParams.get(0).length; i++) {
			calParams.get(0)[i].setCurrentVal(calParams.get(0)[i].getDefaultVal());
			sliderList.get(i).setValue(calParams.get(0)[i].getDefaultVal());
			
		}
	}
	
}
