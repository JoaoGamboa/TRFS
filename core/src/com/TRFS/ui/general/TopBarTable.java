package com.TRFS.ui.general;

import com.TRFS.simulator.AssetsMan;
import com.TRFS.simulator.PreferencesManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TopBarTable extends Table {

	private String title;
	private Stage stage;

	private static Skin skin = AssetsMan.uiSkin;

	private Label fps;

	private static int bezel = 4;

	public static float height = 60 + bezel;

	// Constructor for multiple buttons
	/**
	 * The reocurring bar in every {@link Screen}.
	 * 
	 * @param title
	 * @param stage
	 * @param buttons
	 */
	public TopBarTable(String title, Stage stage, UIButton[] buttons) {
		super(skin);
		this.stage = stage;
		this.title = title;

		mainStructure1();
		float buttonWidth = (stage.getWidth() / 2) / (buttons.length);

		for (int i = 0; i < buttons.length; i++) {

			this.add(buttons[i]).width(buttonWidth).center().padRight(10);
		}
		int span = 5 + buttons.length;
		mainStructure2(span);

	}

	/**
	 * The reocurring bar in every {@link Screen}.
	 * 
	 * @param title
	 * @param stage
	 * @param buttons
	 */
	public TopBarTable(String title, Stage stage, UIButton button) {
		super(skin);
		this.stage = stage;
		this.title = title;

		mainStructure1();
		this.add(button).width(150).center().padRight(10);
		this.row();
		int span = 6;
		mainStructure2(span);
	}

	public void mainStructure1() {
		this.setSize(stage.getWidth(), 60 + bezel);

		this.setPosition(0, stage.getHeight() - 60 - bezel);

		Image logo = new Image(skin, "oneLetterLogo");

		this.add(logo).pad(3, 2, 3, 4);
		this.add(new Image(skin, "verBezel")).width(4).fill();
		this.add(title, "boldTitle").left().padLeft(30);

		fps = new Label("", skin, "smallLabel");
		if (PreferencesManager.getFPSOn())
			fps.setText("FPS: "
					+ Integer.toString(Gdx.graphics.getFramesPerSecond()));

		this.add(fps).pad(10);

		this.add().expandX();
	}

	public void mainStructure2(int span) {
		this.row();

		this.add(new Image(skin, "horBezel")).fill().height(bezel)
				.colspan(span);

		stage.addActor(this);
	}

	public void resize(int width, int height) {
		this.setSize(width, 60 + bezel);
		this.setPosition(0, height - 60 - bezel);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
