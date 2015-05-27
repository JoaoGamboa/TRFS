package com.TRFS.simulator.screens;

import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.MiscUtils;
import com.TRFS.ui.general.TopBarTable;
import com.TRFS.ui.general.UIButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * @author jgamboa
 *
 */
public class AboutScreen implements Screen {
	//UI 
	private Stage stage = new Stage(new ScreenViewport());

	private TopBarTable topBarTable;
	private UIButton buttonBack = new UIButton("BACK", "MainMenu", true, stage);
	
	@Override
	public void render(float delta) {
		MiscUtils.clearScreen();
		stage.act(delta);stage.draw();//Draw stage
	}

	@Override
	public void resize(int width, int height) {
		MiscUtils.checkResize(width, height, stage);
		topBarTable.resize(width, height);
	}
	
	Array<String> strings = new Array<String>();

	@Override
	public void show() {
		MiscUtils.fadeIn(stage);
		Gdx.input.setInputProcessor(stage); //Allows the stage to look for input
				
		topBarTable = new TopBarTable("ABOUT TRFS", stage, buttonBack);
		
		Label a = new Label(
				"TRFS, short for Traffic Simulator was developed as a tool for implementing and studying traffic simulation related models such as car following, lane changng and path finding models.",
				AssetsMan.uiSkin);
		Label b = new Label(
				"Developed as a complement for a Masters degree thesis on traffic simulation, TRFS began with the objetive of providing a visual perception of the effect of Wiedemann's car following model's calibration parameters on traffic flow.",
				AssetsMan.uiSkin);
		Label c = new Label(
				"As it grew, the application of TRFS as a learning tool became clear as a platform for other students to improve on with better and new functionality such as new traffic simulation models and the production of more useful outputs.",
				AssetsMan.uiSkin);
		Label d = new Label(
				"This software was developed with LibGDX, a Java game development framework.",
				AssetsMan.uiSkin);
		Label e = new Label("Author: João Gamboa", AssetsMan.uiSkin);
		Label f = new Label("E-mail: jpgamboa@outlook.com", AssetsMan.uiSkin);
		Label g = new Label(
				"ISEL - Instituto Superior de Engenharia de Lisboa",
				AssetsMan.uiSkin);
	
		Table stringsTable = new Table();
		
		stringsTable.add(a).row();
		stringsTable.add(b).row();
		stringsTable.add(c).row();
		stringsTable.add(d).row();
		stringsTable.add(e).row();
		stringsTable.add(f).row();
		stringsTable.add(g).row();
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
