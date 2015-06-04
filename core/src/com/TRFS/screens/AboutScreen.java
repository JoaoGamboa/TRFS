package com.TRFS.screens;

import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.MiscUtils;
import com.TRFS.ui.general.TopBarTable;
import com.TRFS.ui.general.UIButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
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
	private Table stringsTable;
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
		stringsTable.setSize(stage.getWidth()*0.9f,stage.getHeight()-(64));
	}
	
	Array<String> strings = new Array<String>();

	@Override
	public void show() {
		MiscUtils.fadeIn(stage);
		Gdx.input.setInputProcessor(stage); //Allows the stage to look for input
				
		topBarTable = new TopBarTable("ABOUT TRFS", stage, buttonBack);
		
		String newLine = System.getProperty("line.separator");
		
		Label a = new Label(
				"TRFS, short for Traffic Simulator was developed as a tool for "
						+ "implementing and studying traffic simulation "
						+ "related models such as car following, "
						+ "lane changng and path finding models."
						+ newLine + newLine
						+ "Developed as a complement for a Masters degree thesis on traffic simulation, "
						+ "TRFS began with the objetive of providing a visual perception of the effect of Wiedemann's "
						+ "car following model's calibration parameters on traffic flow."
						+ newLine + newLine
						+ "As it grew, the application of TRFS as a learning tool became clear "
						+ "as a platform for other students to improve on with better and new "
						+ "functionality such as new traffic simulation models and the "
						+ "production of more useful outputs.",
				AssetsMan.uiSkin, "mediumLabel"); a.setFontScale(0.6f); a.setWrap(true); 
		
		Label b = new Label(newLine + "This software was developed with LibGDX, a Java game development framework."
				,AssetsMan.uiSkin, "mediumLabel"); b.setFontScale(0.6f);
		
		Label c = new Label(  
				newLine + "Author: João Gamboa"
				+ newLine + newLine
				+ "E-mail: jpgamboa@outlook.com"
				+ newLine + newLine
				+ "ISEL - Instituto Superior de Engenharia de Lisboa"
				, AssetsMan.uiSkin, "mediumLabel"); c.setFontScale(0.6f);
			
		stringsTable = new Table();
		
		stringsTable.setPosition(0.05f*stage.getWidth(), 0);
		stringsTable.setSize(stage.getWidth()*0.9f,stage.getHeight()-(64)); 
		
		stringsTable.add(a).width(stringsTable.getWidth()).align(Align.left).colspan(3).row();
		stringsTable.add(b).expandX().align(Align.left).colspan(3).row();
		stringsTable.add(c).expandX().align(Align.left).colspan(3).row();
		stringsTable.add().colspan(3).height(20).row();
		stringsTable.add(new Image(AssetsMan.manager.get(AssetsMan.TRFSAbout, Texture.class)));
		stringsTable.add(new Image(AssetsMan.manager.get(AssetsMan.ISELAbout, Texture.class)));
		stringsTable.add(new Image(AssetsMan.manager.get(AssetsMan.LibGDXLogo, Texture.class)));
		stringsTable.align(Align.center);
		stage.addActor(stringsTable);
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
