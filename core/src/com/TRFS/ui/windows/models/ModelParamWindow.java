package com.TRFS.ui.windows.models;

import java.util.ArrayList;

import com.TRFS.simulator.AssetsMan;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**Wraps the code to deploy on the creation of the Model Parameters window.
 * @author jgamboa
 */
public class ModelParamWindow {
	
	private static Skin skin = AssetsMan.uiSkin;
				
	private static ArrayList<Table> tables;
		

	public static ArrayList<Table> create() {
		
		tables = new ArrayList<Table>();
		
//----------------------------------------	
		//CAR FOLLOWING	
		tables.add(ModelParameterSliders.create());
//----------------------------------------	
		//LANE-CHANGING
		
		Table dummyTable = new Table(skin);
		dummyTable.add("PLACEHOLDER FOR LANE CHANGE","smallLabel").expand();
		dummyTable.setVisible(false);
		tables.add(dummyTable);
			
		return tables;
	}
		
}
