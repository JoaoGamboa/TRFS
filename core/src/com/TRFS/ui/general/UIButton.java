package com.TRFS.ui.general;

import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.screens.ScreensManager;
import com.TRFS.ui.general.windows.SlidingWindow;
import com.TRFS.ui.general.windows.SlidingWindowManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIButton extends TextButton {
	
	private static Skin skin = AssetsMan.uiSkin;
	
	private static String styleName = "mainButton";
	
	//Change Screen
	public UIButton(String text, final String target, final boolean newScreen, final Stage stage) {
		super(text, skin, styleName);
		
		addListener(new ClickListener() {@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
			ScreensManager.swapScreen(target, stage, newScreen);
			setChecked(false);
		}
		
			
		});
	}
	
	// Open/close Sliding Windows
	public UIButton(String text, final SlidingWindow slidingWindow,final SlidingWindowManager slidingWindowManager,Stage stage) {
		super(text, skin, styleName);
		
		slidingWindowManager.getList().add(slidingWindow);
		slidingWindow.setButton(this);
		
		addListener(new ClickListener() {@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				slidingWindowManager.manage(slidingWindow);
			}
		});
	}
	
	// Exit
	public UIButton(String text, boolean exit) {
		super(text, skin, styleName);
		
		addListener(new ClickListener() {@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
			}
		});
	}

}
