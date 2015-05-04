package com.TRFS.simulator.screens;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * @author jgamboa
 *
 */
public class Settings implements Screen {
	//UI
	private Skin menuSkin = AssetsMan.uiSkin;
	
	private Stage stage = new Stage(new ScreenViewport());
	
	private Table table = new Table(menuSkin);
	
	private TopBarTable topBarTable;
		
	private UIButton buttonBack = new UIButton("BACK", "MainMenu", true, stage);
	
	private CheckBox vSyncBox = new CheckBox("Enable vSync", menuSkin), 
					 fullScreenBox = new CheckBox("Enable FullScreen", menuSkin), 
					 fpsBox = new CheckBox("Show FPS", menuSkin),
					 debugBox = new CheckBox("Draw Debug", menuSkin);
	
	private Label changesLabel = new Label (null, menuSkin, "mediumWarning");
	
	@Override
	public void render(float delta) {
		MiscUtils.clearScreen();
		stage.act(delta);stage.draw();//Draw stage
	}

	@Override
	public void resize(int width, int height) {
		
		MiscUtils.checkResize(width, height, stage);
		topBarTable.resize(width, height);
		
		handleTable(width, height, false);
	}

	@Override
	public void show() {
		
		MiscUtils.fadeIn(stage);
		Gdx.input.setInputProcessor(stage); //Allows the stage to look for input
		
		topBarTable = new TopBarTable("SETTINGS", stage, buttonBack);
		
		handleTable(stage.getWidth(), stage.getHeight(), true);
		handleUI();
	}

	@Override
	public void hide() {
		//Use when exiting to save all settings
		dispose();}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void dispose() {
		Gdx.app.getPreferences(Main.TITLE).flush();
		stage.dispose();
	}
	
	private void handleUI() {
		//Preselect
		SimulationParameters.drawDebug = debugBox.isChecked();
		
		ClickListener buttonHandler = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Actor actor = event.getListenerActor();
				if (actor == fullScreenBox) {
					changesLabel.setText("Please restart the software for changes to take effect.");
					PreferencesManager.setFullScreen(fullScreenBox.isChecked());
				} else if (actor == vSyncBox) {
					changesLabel.setText("Please restart the software for changes to take effect.");
					PreferencesManager.setvSync(vSyncBox.isChecked());
				} else if (actor == fpsBox) {
					PreferencesManager.setFPS(fpsBox.isChecked());
				} else if (actor == debugBox) {
					SimulationParameters.drawDebug = debugBox.isChecked();
					PreferencesManager.setDrawDebug(debugBox.isChecked());
				} 
			}
		};
		
		fullScreenBox.addListener(buttonHandler);
		vSyncBox.addListener(buttonHandler);
		fpsBox.addListener(buttonHandler);
		debugBox.addListener(buttonHandler);
	}
	
	private void handleTable(float stageWidth, float stageHeight, boolean onCreate) {
		int bevelWidth = 4;
		table.setSize(stageWidth,stageHeight-(60 + bevelWidth)); 
		table.setPosition(0, 0);

		if (onCreate) {
			table.add("Graphics:", "mediumLabel").left().width(stage.getWidth()*(3/4f)).pad(20).colspan(4).row();
			
			table.add(fullScreenBox).uniform();	
				fullScreenBox.setChecked(PreferencesManager.getFullScreenOn());
				
			table.add(vSyncBox).uniform();
				vSyncBox.setChecked(PreferencesManager.getVSyncOn());
			
			table.add(fpsBox).uniform();
				fpsBox.setChecked(PreferencesManager.getFPSOn());
				
			table.add(debugBox).uniform().row();
				debugBox.setChecked(PreferencesManager.getDrawDebug());	
			
			table.add("Sound:", "mediumLabel").left().pad(20).colspan(4).row();
			table.add().uniform();	
			table.add().uniform();
			table.add().uniform().row();
			table.add("Files:", "mediumLabel").left().pad(20).colspan(4).row();
			table.add().uniform();	
			table.add().uniform();
			table.add().uniform().row();
			table.add(changesLabel).center().height(200).colspan(4);
			stage.addActor(table);	
		}
	}
	
	public static void setVSync() {
		Gdx.graphics.setVSync(PreferencesManager.getVSyncOn());
	}
	
	public static void setScreenSize() {
		if(PreferencesManager.getFullScreenOn()) {
			
			int width = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
			int height = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
			
			Gdx.graphics.setDisplayMode(width, height, false);			
		}
	}
	
}
