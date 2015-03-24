package com.TRFS.ui.windows.models;

import java.util.ArrayList;

import com.TRFS.simulator.AssetsMan;
import com.TRFS.ui.general.windows.TabbedWindow;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ModelParamWindow {
	
	private static Skin skin = AssetsMan.uiSkin;
				
	private static ArrayList<Table> tables;
		

	public static void create(final TabbedWindow window) {
		
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
			
	}
	
	public static ArrayList<Table> getTables() {
		return tables;
	}
	
}
