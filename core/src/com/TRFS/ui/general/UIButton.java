package com.TRFS.ui.general;

import com.TRFS.screens.ScreensManager;
import com.TRFS.simulator.AssetsMan;
import com.TRFS.ui.general.windows.SlidingWindow;
import com.TRFS.ui.general.windows.SlidingWindowManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIButton extends TextButton {

	private static Skin skin = AssetsMan.uiSkin;

	private static String styleName = "mainButton";

	/**
	 * {@link UIButton} for changing to a different {@link Screen}. Passes the
	 * provided screen name to the {@link ScreensManager}.
	 * 
	 * @param text
	 *            Title
	 * @param target
	 *            {@link Screen}
	 * @param newScreen
	 *            Set create a new {@link Screen} on click.
	 * @param stage
	 */
	public UIButton(String text, final String target, final boolean newScreen,
			final Stage stage) {
		super(text, skin, styleName);

		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				ScreensManager.swapScreen(target, stage, newScreen);
				setChecked(false);
			}

		});
	}

	/**
	 * {@link UIButton} for opening or closing a {@link SlidingWindow}.
	 * Communicates with a {@link SlidingWindowManager} to decide how to take
	 * care of the remaining {@link SlidingWindow}s.
	 * 
	 * @param text
	 * @param slidingWindow
	 * @param slidingWindowManager
	 * @param stage
	 */
	public UIButton(String text, final SlidingWindow slidingWindow,
			final SlidingWindowManager slidingWindowManager, Stage stage) {
		super(text, skin, styleName);

		slidingWindowManager.getList().add(slidingWindow);
		slidingWindow.setButton(this);

		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				slidingWindowManager.manage(slidingWindow);
			}
		});
	}

	/**
	 * {@link UIButton} for closing the {@link Application}
	 * 
	 * @param text
	 * @param exit
	 */
	public UIButton(String text, boolean exit) {
		super(text, skin, styleName);

		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
			}
		});
	}

}
