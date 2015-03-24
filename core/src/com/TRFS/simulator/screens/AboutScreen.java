package com.TRFS.simulator.screens;

import com.TRFS.simulator.MiscTools;
import com.TRFS.ui.general.TopBarTable;
import com.TRFS.ui.general.UIButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class AboutScreen implements Screen {
	//UI 
	private Stage stage = new Stage(new ScreenViewport());

	private TopBarTable topBarTable;
	private UIButton buttonBack = new UIButton("BACK", "MainMenu", true, stage);
	
	@Override
	public void render(float delta) {
		MiscTools.clearScreen();
		stage.act(delta);stage.draw();//Draw stage
	}

	@Override
	public void resize(int width, int height) {
		MiscTools.checkResize(width, height, stage);
		topBarTable.resize(width, height);
	}

	@Override
	public void show() {
		MiscTools.fadeIn(stage);
		Gdx.input.setInputProcessor(stage); //Allows the stage to look for input
				
		topBarTable = new TopBarTable("ABOUT TRFS", stage, buttonBack);
	}

	@Override
	public void hide() {dispose();}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
