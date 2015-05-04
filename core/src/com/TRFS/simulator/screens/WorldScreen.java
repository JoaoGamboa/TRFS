package com.TRFS.simulator.screens;

import com.TRFS.scenarios.Scenario;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.MiscUtils;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * @author jgamboa
 *
 */
public class WorldScreen implements Screen {
	
	//UI
	private Stage stage;
	private TabbedWindow modelParametersWindow; 
	private SlidingWindow simParamWindow, statsWindow;
	private SlidingWindowManager slidingWindowManager;
	private Label mouseCoordinatesLabel;
	private Vector3 tmp = new Vector3(), tmp2 = new Vector3();
	private UIButton 	buttonBack,	modelParamButton, simParamButton, statsButton;
	private TopBarTable topBarTable;
	private InputMultiplexer inputMultiplexer;
	private WorldInputProcessor worldInputHandler;
	
	//Simulation
	private Scenario scenario;

	@Override
	public void render(float delta) {
		MiscUtils.clearScreen();
		
		//Update and render Scenario
		scenario.render(delta);
		worldInputHandler.listen();
				
		//Render UI on top
		renderUI();
		stage.act(delta);	
		stage.draw();		
	}
	
	@Override
	public void resize(int width, int height) {
		MiscUtils.checkResize(width, height, stage);
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
		worldInputHandler = new WorldInputProcessor(scenario);
		
		stage.addCaptureListener(new EventListener() {
			@Override public boolean handle(Event event) {
				if (event instanceof InputEvent) {
					Actor actor = event.getTarget();
					if (((InputEvent) event).getType() == Type.enter) {
						stage.setScrollFocus(actor);
						return true;
					} else if (((InputEvent) event).getType() == Type.exit
							&& actor.equals(stage.getScrollFocus())) {
						if (actor instanceof ScrollPane) {
							stage.setScrollFocus(null);
							return true;
						}
					} return false;
				} return false;
			}
		});
		
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(worldInputHandler);
		Gdx.input.setInputProcessor(inputMultiplexer); 
	}
	
	public void implementUI() {
		stage = new Stage(new ScreenViewport(), scenario.getGraphicsManager().getBatch());
				
		MiscUtils.fadeIn(stage);
		
		// TODO Make windows instanceable instead of static
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
	}
	
	private void renderUI() {	
		tmp.set(Gdx.input.getX(), Gdx.input.getY(),1);
		tmp2.set(scenario.getGraphicsManager().getCamera().unproject(tmp));
		mouseCoordinatesLabel.setText(String.format("%.1f, %.1f",tmp2.x, tmp2.y));
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
