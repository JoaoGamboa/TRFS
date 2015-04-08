package com.TRFS.simulator.screens;

import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.MiscTools;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * @author jgamboa
 *
 */
public class SplashScreen implements Screen {

	private Stage stage = new Stage(new ScreenViewport());

	private Image logo = new Image(AssetsMan.manager.get(AssetsMan.TRFSLogo,
			Texture.class));

	public boolean animationComplete = false;

	@Override
	public void render(float delta) {
		MiscTools.clearScreen();
		stage.act(delta);
		stage.draw();// Draw stage
		if (AssetsMan.update()) { // If all files are loaded
			if (animationComplete) { // When the splash finishes
				AssetsMan.setup();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new MainMenu());
			}
		}
	}

	@Override
	public void resize(int width, int height) {

		MiscTools.checkResize(width, height, stage);

		logo.setSize(388, 116);
		logo.setPosition(width / 2f, height / 2f, 1);
	}

	@Override
	public void show() {

		stage.addActor(logo);

		// Making the stage fade in and out.
		stage.addAction(Actions.sequence(Actions.alpha(0),
				Actions.fadeIn(1.5f), Actions.fadeOut(1.5f),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						animationComplete = true;
					}
				})));

		// Loading assets into queue
		AssetsMan.loadQueue();
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
		stage.dispose();

	}
}
