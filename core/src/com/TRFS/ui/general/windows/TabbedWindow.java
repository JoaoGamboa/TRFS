package com.TRFS.ui.general.windows;

import java.util.ArrayList;

import com.TRFS.simulator.AssetsMan;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TabbedWindow extends SlidingWindow {

	private Skin skin = AssetsMan.uiSkin;

	private ArrayList<TextButton> buttonList;

	private ArrayList<Table> tableList;

	private Cell<Table> theCell;

	/**
	 * A {@link TabbedWindow} extends the {@link SlidingWindow}, providing the
	 * same functionality plus the ability to hold multiple tabs.
	 * 
	 * @param title
	 * @param targetWidth
	 * @param targetHeight
	 * @param stage
	 * @param dockLeft
	 * @param dockDown
	 * @param buttons
	 * @param tables
	 */
	public TabbedWindow(String title, float targetWidth, float targetHeight,
			Stage stage, boolean dockLeft, boolean dockDown, String[] buttons) {
		super(title, targetWidth, targetHeight, stage, dockLeft, dockDown);

		super.setTitle(title);

		buttonList = new ArrayList<TextButton>();
		tableList = new ArrayList<Table>();

		ClickListener listener = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				int index = buttonList.indexOf(event.getListenerActor());
				Table clickedTable = tableList.get(index);
				Cell<Table> cell = TabbedWindow.super.getCell(clickedTable);
				changeTab(event.getListenerActor(), cell);
			}
		};

		for (int i = 0; i < buttons.length; i++) {
			TextButton button = new TextButton(buttons[i], skin, "mainButton");
			super.add(button);
			Cell<TextButton> cell = super.getCell(button);

			float bWidth = super.getTargetWidth() / buttons.length - 10;

			cell.width(bWidth);

			if (i == 1) {
				cell.pad(5, 0, 5, 1);
			} else if (i == buttons.length - 1) {
				cell.pad(5, 1, 5, 0).row();
			} else {
				cell.pad(5, 0, 5, 0);
			}

			// .pad(5, 0, 5, 1)
			buttonList.add(button);
			button.addListener(listener);
		}

		super.add(new Image(skin, "horBezel")).fill().height(4)
				.colspan(buttons.length).row();
	}
	
	public void build(ArrayList<Table> tables) {
		super.add(tables.get(0)).colspan(buttonList.size()).expand();

		theCell = super.getCell(tables.get(0));

		for (Table table : tables) {
			table.setVisible(false);
			tableList.add(table);
		}
		setTab(buttonList.get(0));
	}

	private void changeTab(Actor clickedActor, final Cell<Table> cell) {
		final Table clickedTable = tableList.get(buttonList
				.indexOf(clickedActor));

		for (final Table table : tableList) {
			if (table.isVisible() && !table.equals(clickedTable)) {
				table.addAction(Actions.sequence(
						Actions.parallel(Actions.fadeOut(0.25f),
								Actions.moveBy(200, 0, 0.25f)),
						Actions.run(new Runnable() {
							@Override
							public void run() {
								buttonList.get(tableList.indexOf(table))
										.setChecked(false);
								table.setVisible(false);
								theCell.setActor(clickedTable);
							}
						})));
				break;
			}
		}

		setTab(clickedActor);
	}

	private void setTab(Actor clickedActor) {
		Table clickedTable = tableList.get(buttonList.indexOf(clickedActor));
		clickedTable.addAction(Actions.moveBy(200, 0));
		clickedTable.setVisible(true);
		((TextButton) clickedActor).setChecked(true);
		clickedTable.addAction(Actions.parallel(Actions.moveBy(-200, 0, 0.25f),
				Actions.fadeIn(0.25f)));
	}
}
