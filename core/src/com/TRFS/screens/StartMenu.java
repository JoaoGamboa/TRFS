package com.TRFS.screens;

import com.TRFS.models.carFollowing.CarFollowingModel;
import com.TRFS.models.laneChanging.LaneChangingModel;
import com.TRFS.scenarios.ScenariosManager;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.Main;
import com.TRFS.simulator.MiscUtils;
import com.TRFS.simulator.PreferencesManager;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.ui.general.TopBarTable;
import com.TRFS.ui.general.UIButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * @author jgamboa
 *
 */
public class StartMenu implements Screen {
		
	//UI
	private Skin menuSkin;
	private Stage stage;
	private Image 	verBezel, scenPreview;
	private Table mapTable, centerTable;
	private TopBarTable topBarTable;
	private UIButton loadMapButton , editMapButton, resumeButton, newRunButton;
	private List<String> mapList;
	private ScrollPane scrollPane;
	private SelectBox<String> carFolSelBox, laneChangeSelBox;
	private Label flowLabel, desVelLabel, maxVelLabel;
	private Slider flowSlider, desVelSlider, maxVelSlider;
	private CheckBox trucksCheckBox, reservedCheckBox1, reservedCheckBox2, smoothGeometryCheckBox, simplifyGeometryCheckBox;
		
	@Override
	public void render(float delta) {
		
		MiscUtils.clearScreen();
		stage.act(delta);
		stage.draw();
	}
 
	@Override
	public void resize(int width, int height) {
		MiscUtils.checkResize(width, height, stage);
		
		topBarTable.resize(width, height);
		handleMapTable(stage.getWidth(), stage.getHeight(), false);
		handleCenterTable(stage.getWidth(), stage.getHeight(), false);
	}

	@Override
	public void show() {			
		initUIActors();
		MiscUtils.fadeIn(stage);
		Gdx.input.setInputProcessor(stage); //Allows the stage to look for input
		
		topBarTable = new TopBarTable("SIMULATION SETUP", stage, new UIButton("BACK", "MainMenu", true, stage));
		
		//maps = ScenariosManager.maps;
		
		handleMapTable(stage.getWidth(), stage.getHeight(), true);
		handleCenterTable(stage.getWidth(), stage.getHeight(), true);
		
		handleUIEvents();						
	}
	
	private void initUIActors() {
		menuSkin = AssetsMan.uiSkin;
		
		stage = new Stage(new ScreenViewport());
		verBezel = new Image(menuSkin, "verBezel");
		mapTable = new Table(menuSkin);
		centerTable = new Table(menuSkin);
				
		loadMapButton = new UIButton("LOAD", "MainMenu", true, stage);
		editMapButton = new UIButton("EDITOR", "MainMenu", true, stage);
		resumeButton = new UIButton("RESUME", "WorldScreen", false, stage);
		newRunButton = new UIButton("RUN", "WorldScreen", true, stage);
		
		mapList = new List<String>(menuSkin, "smallList");
		scrollPane = new ScrollPane(mapList, menuSkin);
		
		carFolSelBox = new SelectBox<String>(menuSkin);
		laneChangeSelBox = new SelectBox<String>(menuSkin);
		
		flowLabel = new Label(null, menuSkin, "smallLabel");
		desVelLabel = new Label(null, menuSkin, "smallLabel");
		maxVelLabel = new Label(null, menuSkin, "smallLabel");
		
		flowSlider = new Slider(0, 2500, 1, false, menuSkin);
		desVelSlider = new Slider(0, 150, 1, false, menuSkin);
		maxVelSlider = new Slider(0, 150, 1, false, menuSkin);
		
		trucksCheckBox = new CheckBox("Allow Heavy Vehicles", menuSkin);
		smoothGeometryCheckBox = new CheckBox("Smooth", menuSkin);
		simplifyGeometryCheckBox = new CheckBox("Simplify", menuSkin);
		reservedCheckBox1 = new CheckBox("Reserved Space 1", menuSkin);
		reservedCheckBox2 = new CheckBox("Reserved Space 2", menuSkin);
	}
	
	private void handleUIEvents() {	
				
		//Preselect simulation parameters in case the user doesn't change them
		SimulationParameters.desFlow.setCurrentVal(flowSlider.getValue());
		SimulationParameters.maxVelocity.setCurrentVal(maxVelSlider.getValue());
		SimulationParameters.desVelocity.setCurrentVal(desVelSlider.getValue()); 
		
		SimulationParameters.allowHOV= trucksCheckBox.isChecked();
		SimulationParameters.currentCarFolModel=carFolSelBox.getSelected();
		SimulationParameters.currentLaneChangeModel=laneChangeSelBox.getSelected();
		SimulationParameters.currentMap = ScenariosManager.maps.get(mapList.getSelectedIndex());
		SimulationParameters.smoothGeometry= smoothGeometryCheckBox.isChecked();
		SimulationParameters.simplifyGeometry= simplifyGeometryCheckBox.isChecked();
		
		//Register user changes
		ChangeListener changeHandler = new ChangeListener() {
			@Override
            public void changed(ChangeEvent event, Actor actor) {
				Actor changeActor = event.getListenerActor();
				if (changeActor == flowSlider) {
					float dflow = flowSlider.getValue();
					flowLabel.setText(String.format("DESIRED FLOW: %d Veh/lane/h", (int) dflow));
					SimulationParameters.desFlow.setCurrentVal(dflow);
				} else if (changeActor == desVelSlider) {
					float dVel = desVelSlider.getValue();
					desVelLabel.setText(String.format("DESIRED VELOCITY: %.1f Km/h", dVel));
					SimulationParameters.desVelocity.setCurrentVal(dVel);
				} else if (changeActor == maxVelSlider) {
					float mVel = maxVelSlider.getValue();
					maxVelLabel.setText(String.format("MAXIMUM VELOCITY: %.1f Km/h", mVel));
					SimulationParameters.maxVelocity.setCurrentVal(mVel);
				} else if (changeActor == trucksCheckBox) { 
					boolean trucks = trucksCheckBox.isChecked();
					SimulationParameters.allowHOV = trucks;
				} else if (changeActor == scrollPane) {
					int ind = mapList.getSelectedIndex();
					mapTable.getCell(scenPreview).setActor(scenPreview = ScenariosManager.maps.get(ind).getImage());
					SimulationParameters.currentMap = ScenariosManager.maps.get(ind);
				} else if (changeActor == carFolSelBox) {
					String cf = carFolSelBox.getSelected();
					SimulationParameters.currentCarFolModel = cf;
				} else if (changeActor == laneChangeSelBox) {
					String lc = laneChangeSelBox.getSelected();
					SimulationParameters.currentLaneChangeModel = lc;
				} else if (changeActor == smoothGeometryCheckBox) {
					boolean smooth = smoothGeometryCheckBox.isChecked();
					SimulationParameters.smoothGeometry = smooth;
				} else if (changeActor == simplifyGeometryCheckBox) {
					boolean simplify = simplifyGeometryCheckBox.isChecked();
					SimulationParameters.simplifyGeometry = simplify;
				} 
				saveChanges();
			}};
			
		scrollPane.addListener(changeHandler);
		flowSlider.addListener(changeHandler);
		carFolSelBox.addListener(changeHandler);
		desVelSlider.addListener(changeHandler);
		maxVelSlider.addListener(changeHandler);
		trucksCheckBox.addListener(changeHandler);	
		laneChangeSelBox.addListener(changeHandler);
		smoothGeometryCheckBox.addListener(changeHandler);
		simplifyGeometryCheckBox.addListener(changeHandler);

	}

	private void saveChanges() {
		PreferencesManager.setFlow(flowSlider.getValue());
		PreferencesManager.setVel(desVelSlider.getValue());
		PreferencesManager.setMaxVel(maxVelSlider.getValue());
		PreferencesManager.setTrucks(trucksCheckBox.isChecked());
		PreferencesManager.setMapIndex(mapList.getSelectedIndex());
		PreferencesManager.setCarFolModel(carFolSelBox.getSelected());
		PreferencesManager.setLaneChangeModel(laneChangeSelBox.getSelected());
		PreferencesManager.setSmoothGeometry(smoothGeometryCheckBox.isChecked());
		PreferencesManager.setSimplifyGeometry(simplifyGeometryCheckBox.isChecked());
		Gdx.app.getPreferences(Main.TITLE).flush();
	}

	@Override
	public void hide() {	
		saveChanges();
		dispose();}
	
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void dispose() {
		stage.dispose();}

	private void handleMapTable(float stageWidth, float stageHeight, boolean onCreate) {
		int bevelWidth = 4;
		mapTable.setSize(stageWidth/4f, stageHeight-TopBarTable.height);
		
		if (onCreate) {
			
			int mapIndex;
			if ((int) PreferencesManager.getMapIndex() >= ScenariosManager.maps.size || PreferencesManager.getMapIndex() < 0) {	mapIndex = 0;
			} else {	mapIndex = (int) PreferencesManager.getMapIndex();	}
			String[] mapNames = new String[ScenariosManager.maps.size];
			
			for (int i = 0; i < ScenariosManager.mapNames.size; i++) {
				mapNames[i] = ScenariosManager.mapNames.get(i);
			}
			
			mapList.setItems(mapNames);
				mapList.setSelectedIndex(mapIndex);		
					
			mapTable.top();
			mapTable.add("SCENARIO:","smallLabel").pad(10).left().colspan(2).row(); //Label
			mapTable.add( new Image(menuSkin, "horBezel")).fill().height(bevelWidth).colspan(2).row();
			mapTable.add(scrollPane).fill().height(mapTable.getHeight()/4).colspan(2).padLeft(10).row();
			mapTable.add( new Image(menuSkin, "horBezel")).fill().height(bevelWidth).colspan(2).row();
			mapTable.add(scenPreview).size(mapTable.getWidth()-20,(mapTable.getWidth()-20)/(4/3f)).pad(10).expandY().colspan(2).row();
				mapTable.getCell(scenPreview).setActor(scenPreview = ScenariosManager.maps.get(mapIndex).getImage());
				
			mapTable.add( new Image(menuSkin, "horBezel")).fill().height(bevelWidth).colspan(2).row();
			
			mapTable.add("GEOMETRY:","smallLabel").pad(10).left().top().row();
						
			mapTable.add(smoothGeometryCheckBox).pad(5).padBottom(10).width(mapTable.getWidth()/2-20);
				smoothGeometryCheckBox.setChecked(PreferencesManager.getSmoothGeometry());
			mapTable.add(simplifyGeometryCheckBox).pad(5).padBottom(10).width(mapTable.getWidth()/2-20).left().row();
				simplifyGeometryCheckBox.setChecked(PreferencesManager.getSimplifyGeometry());
			
			mapTable.add( new Image(menuSkin, "horBezel")).fill().height(bevelWidth).colspan(2).row();
			mapTable.add("CUSTOM SCENARIO:","smallLabel").pad(10).left().colspan(2).row();
			mapTable.add(editMapButton).pad(5).padBottom(10).width(mapTable.getWidth()/2-20);
			mapTable.add(loadMapButton).pad(5).padBottom(10).width(mapTable.getWidth()/2-20);
			
			stage.addActor(mapTable);
			stage.addActor(verBezel);
		}
		
		mapTable.setPosition(0, 0); 
		mapTable.getCell(scrollPane).fill().height(mapTable.getHeight()/4).expandX().padLeft(10);
		mapTable.getCell(scenPreview).size(mapTable.getWidth()-20,(mapTable.getWidth()-20)/(4/3f)).pad(10).expand();
		verBezel.setPosition(mapTable.getWidth(), 0); 
		verBezel.setSize(bevelWidth, mapTable.getHeight());
	}
	
	private void handleCenterTable(float stageWidth, float stageHeight, boolean onCreate) {
		int bevelWidth = 4;
		
		if (onCreate) {
			carFolSelBox.setItems(CarFollowingModel.carFolModels);
			laneChangeSelBox.setItems(LaneChangingModel.laneChangeModels);
			
			centerTable.top();
			centerTable.add("Simulation Models", "mediumWarning").expandX().left().colspan(3).pad(10).row();
			centerTable.add("CAR-FOLLOWING MODEL:","smallLabel").left().pad(10).uniformX();
			centerTable.add("LANE CHANGING MODEL:","smallLabel").left().pad(10).uniformX();
			centerTable.add().left().pad(10).uniformX().uniformY().row();
			centerTable.add(carFolSelBox).pad(10).left().width(300);
				carFolSelBox.setSelected(PreferencesManager.getCarFolModel());
				
			centerTable.add(laneChangeSelBox).pad(10).left().width(300);
				laneChangeSelBox.setSelected(PreferencesManager.getLaneChangeModel());
				
			centerTable.add().pad(10).left().width(300).row();
			
			centerTable.add( new Image(menuSkin, "horBezel")).fill().height(bevelWidth).colspan(3).row();
			centerTable.add("General Simulation Parameters", "mediumWarning").left().colspan(3).pad(10).row();
			centerTable.add(flowLabel).left().pad(10);
			centerTable.add(desVelLabel).left().pad(10);
			centerTable.add(maxVelLabel).left().pad(10).row();
			centerTable.add(flowSlider).fillX().pad(10);
				flowSlider.setValue(PreferencesManager.getFlow());
				flowLabel.setText(String.format("DESIRED FLOW: %d Veh/lane/h", (int) flowSlider.getValue()));
				
			centerTable.add(desVelSlider).fillX().pad(10).padRight(10);
				desVelSlider.setValue(PreferencesManager.getDesVel());
				desVelLabel.setText(String.format("DESIRED VELOCITY: %.1f Km/h", desVelSlider.getValue()));
				
			centerTable.add(maxVelSlider).fillX().pad(10).row();
				maxVelSlider.setValue(PreferencesManager.getMaxVel());
				maxVelLabel.setText(String.format("MAXIMUM VELOCITY: %.1f Km/h", maxVelSlider.getValue()));
				
			centerTable.add( new Image(menuSkin, "horBezel")).fill().height(bevelWidth).colspan(3).row();
			centerTable.add(trucksCheckBox).center().pad(10);
				trucksCheckBox.setChecked(PreferencesManager.getTruckPref());
				
			centerTable.add(reservedCheckBox1).center().pad(10);
			centerTable.add(reservedCheckBox2).center().pad(10).row();
			
			if (ScreensManager.isWorldRunning()) {
				centerTable.add(resumeButton).width(200).pad(10).colspan(2).right().bottom();
				centerTable.add(newRunButton).width(200).pad(10).expandY().right().bottom();
			} else {
				centerTable.add(newRunButton).width(200).pad(10).colspan(3).expandY().right().bottom();			
			}
			stage.addActor(centerTable);
		}  
		
		centerTable.setPosition(mapTable.getWidth()+bevelWidth, 0); 
		centerTable.setSize(stageWidth-mapTable.getWidth(), stageHeight-TopBarTable.height);
	}
	
	
}