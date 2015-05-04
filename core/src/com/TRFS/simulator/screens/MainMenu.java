package com.TRFS.simulator.screens;

import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.MiscUtils;
import com.TRFS.ui.general.UIButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * @author jgamboa
 *
 */
public class MainMenu implements Screen {
	
	private Skin menuSkin = AssetsMan.uiSkin;
	private Stage stage = new Stage(new ScreenViewport());
	private Image logo = new Image(menuSkin, "mediumLogo");
	private Table table;
	private UIButton 	buttonStart = new UIButton("START", "StartMenu", true, stage), 
						buttonSettings = new UIButton("SETTINGS", "Settings", true, stage), 
						buttonAbout = new UIButton("ABOUT", "AboutScreen", true, stage), 
						buttonExit = new UIButton("EXIT", true);
	
	//TESTSSSS
	SpriteBatch batch = new SpriteBatch();
	TextureRegion region = new TextureRegion(AssetsMan.uiSkin.getRegion("testcar"));
	ShapeRenderer renderer = new ShapeRenderer();
	int i;
	float rotation;
	Vector2 v1 = new Vector2();
	Vector2 v2 = new Vector2();
	
	Vector2 localCG = new Vector2();
	Vector2 globalCG = new Vector2();
	Vector2 localFrontAxis = new Vector2();
	Vector2 globalFrontAxis = new Vector2();
	Vector2 origin = new Vector2();
	//END TESTS
	
	@Override
	public void render(float delta) {
		MiscUtils.clearScreen();
		stage.act(delta);
		stage.draw();
		
		//TESTS
		float width = 60;
		float length = 100f;
		float x = 150;
		float y = 200;
		
		origin.set(width/2, 0.2f*length);
		
		v1.set(-600,-10);
		v2.set(700,700);
		float rotation2 = new Vector2().set(v2).sub(v1).angle();
		

		
		i += 5;
		rotation = i;
		
		MiscUtils.localToGlobalOut(localCG.set(0, 0.3f * length), rotation
				* MathUtils.degRad, new Vector2(x + origin.x, y + origin.y));
		MiscUtils.localToGlobalOut(localFrontAxis.set(0, 0.55f * length),
				rotation * MathUtils.degRad, new Vector2(x + origin.x, y
						+ origin.y));
		
		batch.setColor(Color.YELLOW);
		batch.begin();
		batch.draw(region, x, y, origin.x, origin.y, width, length, 1f, 1f, rotation);
		batch.end();
		
		renderer.setColor(Color.GREEN);
		renderer.begin(ShapeType.Line);
		renderer.line(v1, v2);
		renderer.end();
		
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.BLUE);
		renderer.circle(x, y, 5);
		renderer.setColor(Color.GREEN);
		renderer.circle(x+origin.x, y+origin.y, 5);
		renderer.circle(v1.x, v1.y, 5);
		renderer.circle(v2.x, v2.y, 5);
		renderer.circle(localCG.x, localCG.y, 5);
		renderer.circle(localFrontAxis.x, localFrontAxis.y, 5);
		renderer.end();
		//TESTS--------------
	}

	@Override
	public void resize(int width, int height) {
		MiscUtils.checkResize(width, height, stage);
		table.invalidateHierarchy();table.setSize(width, height);//Prevent objects from being resized
	}

	@Override
	public void show() {
		MiscUtils.fadeIn(stage);
		Gdx.input.setInputProcessor(stage); //Allows the stage to look for input
		
		//Create table and set bounds
		table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
							
		//Placing buttons in the table and setting their size
		int buttonW = 200, buttonH = 40, spaceBottom = 10;
		table.add(logo).size(388,116).spaceBottom(10*spaceBottom).row();
		table.add(buttonStart).size(buttonW, buttonH).spaceBottom(spaceBottom).row();
		table.add(buttonSettings).size(buttonW, buttonH).spaceBottom(spaceBottom).row();
		table.add(buttonAbout).size(buttonW, buttonH).spaceBottom(spaceBottom).row();
		table.add(buttonExit).size(buttonW, buttonH);
				
		stage.addActor(table);
		
		// TODO add ISEL logo
		// TODO add author name
		
	}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		stage.dispose();

	}
}
