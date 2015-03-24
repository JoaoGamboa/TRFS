package com.TRFS.simulator.screens;

import com.TRFS.scenarios.Scenario;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.MiscTools;
import com.TRFS.simulator.SimulationParameters;
import com.TRFS.simulator.world.WorldInputProcessor;
import com.TRFS.ui.general.TopBarTable;
import com.TRFS.ui.general.UIButton;
import com.TRFS.ui.general.windows.SlidingWindow;
import com.TRFS.ui.general.windows.SlidingWindowManager;
import com.TRFS.ui.general.windows.TabbedWindow;
import com.TRFS.ui.windows.models.ModelParamWindow;
import com.TRFS.ui.windows.simulation.SimulationParamWindow;
import com.TRFS.ui.windows.stats.SimulationStatsWindow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WorldScreen implements Screen {
	
	//UI
	private Stage stage;
	private TabbedWindow modelParametersWindow; 
	private SlidingWindow simParamWindow, statsWindow;
	private SlidingWindowManager slidingWindowManager;
	private Label mouseCoordinatesLabel;
	private Vector2 mouseC, unprojectedMouseCoordinates;
	private UIButton 	buttonBack,	modelParamButton, simParamButton, statsButton;
	private TopBarTable topBarTable;
	private InputMultiplexer inputMultiplexer;
	private WorldInputProcessor worldInputHandler;
	
	//Simulation
	private Scenario scenario;

	
	@Override
	public void render(float delta) {
		MiscTools.clearScreen();
		
		//Update and render Scenario
		scenario.render(delta);
				
		//Render UI on top
		renderUI();
		stage.act(delta);	
		stage.draw();		
	}
	
	@Override
	public void resize(int width, int height) {
		MiscTools.checkResize(width, height, stage);
		topBarTable.resize(width, height);
		slidingWindowManager.resize(width, height);
		scenario.resize(width, height);
	}

	@Override
	public void show() {
		implementScenario();
		implementCameraAndGraphics();
		
		implementUI();
		implementInput();
	}

	@Override
	public void hide() {
		dispose();
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public void dispose() {
		saveChanges();
		stage.dispose();
		scenario.dispose();
	}
	
	public void implementScenario() {
		scenario = new Scenario(SimulationParameters.currentMap);
	}
	public void implementCameraAndGraphics() {
		
	}
	
	public void implementInput() {
		inputMultiplexer = new InputMultiplexer();
		worldInputHandler = new WorldInputProcessor(scenario.getGraphicsManager().getCamera());
		stage.addCaptureListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent){
						Actor actor =  event.getTarget();
						if ( ((InputEvent) event).getType() == Type.enter) {
							stage.setScrollFocus(actor);
							return true;
					   } else if (((InputEvent) event).getType() == Type.exit && actor.equals(stage.getScrollFocus())) {
					         if (actor instanceof ScrollPane) {
					            stage.setScrollFocus(null);
					            return true;
					         }
					   }
						return false;
				}
				return false;
			}
		});
		
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(worldInputHandler);
		Gdx.input.setInputProcessor(inputMultiplexer); 
	}
	
	public void implementUI() {
		stage = new Stage(new ScreenViewport(), scenario.getGraphicsManager().getBatch());
				
		MiscTools.fadeIn(stage);
		
		String [] modelParamButtons = {"Car-Foll", "Lane Chang."};
		ModelParamWindow.create(modelParametersWindow);
		modelParametersWindow = new TabbedWindow("MODEL PARAMETERS", 320, stage.getHeight()-TopBarTable.height, stage, false, true, modelParamButtons, ModelParamWindow.getTables());
		
		simParamWindow = new SlidingWindow("SIMULATION PARAMETERS", 320, stage.getHeight()-TopBarTable.height, stage, false, true);
		SimulationParamWindow.create(simParamWindow);
		
		statsWindow = new SlidingWindow("SIMULATION STATS", 320, stage.getHeight()-TopBarTable.height, stage, true, true);
		SimulationStatsWindow.create(statsWindow, scenario);
		
		slidingWindowManager = new SlidingWindowManager();
		
		buttonBack = new UIButton("BACK", "StartMenu", true, stage);
		modelParamButton = new UIButton("MODEL PARAM.", modelParametersWindow, slidingWindowManager, stage);
		simParamButton = new UIButton("SIMUL. PARAM.", simParamWindow, slidingWindowManager, stage);
		statsButton = new UIButton("SIMUL. STATS", statsWindow, slidingWindowManager, stage);
		
		UIButton[] topBarButtons = {statsButton, modelParamButton, simParamButton,buttonBack};
		topBarTable = new TopBarTable(SimulationParameters.currentMap.getName(), stage, topBarButtons);
		
		mouseCoordinatesLabel = new Label("", AssetsMan.uiSkin, "smallLabel");
		mouseCoordinatesLabel.setPosition(20, 20);
		stage.addActor(mouseCoordinatesLabel);
		unprojectedMouseCoordinates = new Vector2();
		mouseC = new Vector2();

	}
	
	private void renderUI() {	
		mouseC.set(Gdx.input.getX(), Gdx.input.getY());
		unprojectedMouseCoordinates.set(scenario.getStages().get(0).getViewport().unproject(mouseC));
		mouseCoordinatesLabel.setText(String.format("%.1f, %.1f",unprojectedMouseCoordinates.x, unprojectedMouseCoordinates.y));
		SimulationStatsWindow.render();
		

	}

	private void saveChanges() {

	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}
	    
}
