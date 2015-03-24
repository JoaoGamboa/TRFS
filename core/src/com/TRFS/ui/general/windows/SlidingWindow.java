package com.TRFS.ui.general.windows;

import com.TRFS.simulator.AssetsMan;
import com.TRFS.ui.general.TopBarTable;
import com.TRFS.ui.general.UIButton;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class SlidingWindow extends Window {
	
	private static Skin skin = AssetsMan.uiSkin;
	
	private Stage stage;
	
	private float targetWidth;

	private float targetHeight;
	
	private float previousScreenHeight;
	
	private UIButton button;
	
	private boolean dockLeft = false;
	private boolean dockDown = true;
	
	private boolean repositioned = false;
	
	private float currentX;
	private float currentY;
	
	private float hideX;
	
	private float startX;
	private float startY;
	
	public SlidingWindow(String title, float targetWidth, float targetHeight, Stage stage, boolean dockLeft, boolean dockDown) {
		super(title, skin);
		this.targetWidth = targetWidth;
		this.targetHeight = targetHeight;
		this.stage = stage;
		this.dockLeft = dockLeft;
		this.dockDown = dockDown;
		
		this.previousScreenHeight = stage.getHeight();
		
		this.padTop(30);

		this.setVisible(false);
		this.setSize(0, targetHeight);

		stage.addActor(this);
	}
		
	public void show(final SlidingWindow clickedWindow) {
		this.setVisible(true);
		this.button.setChecked(true);
		this.toFront();
		this.addAction(	Actions.parallel(Actions.sizeTo(this.targetWidth, this.targetHeight, 0.25f), Actions.fadeIn(0.25f)));
		
		this.setCurrentX(getX()); this.setCurrentY(getY());
				
	}
	
	public void hide(final SlidingWindow closingWindow) {
		this.addAction(Actions.sequence(
							Actions.parallel(Actions.sizeTo(0, this.targetHeight, 0.25f), Actions.moveTo(this.hideX, this.startY, 0.25f)),	
							Actions.run(new Runnable() {@Override public void run() {closingWindow.setVisible(false); 
																					 closingWindow.button.setChecked(false);
																					 closingWindow.setRepositioned(false);}})));
	}
	
	public void resize(int width, int height) {
		setHidingConditions(width, height);

	
		this.targetHeight = this.targetHeight + (height-this.previousScreenHeight);
		
		this.previousScreenHeight = height;
		
		setPosition(this.startX, this.startY);
		setSize(this.targetWidth, this.targetHeight);
	}
	
	public void setHidingConditions(float width, float height) {
		if (dockDown) {	this.startY = 0;} 
			else {		this.startY = height-TopBarTable.height-height;}
		if (dockLeft) {	this.startX = 0; hideX = 0;} 
			else {		this.startX = width; hideX = width;}
	}
		
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isDragging()){
			float threshold;
			if (!this.dockLeft){
				threshold = getX()+this.targetWidth;
			}else {
				threshold = getX();
			}
			
			if (Math.abs(threshold-this.startX) > this.targetWidth) {
				setRepositioned(true);
			} else {
				setRepositioned(false);
			}
			
			if (this.dockLeft) {
				if (getX() + this.targetWidth == stage.getWidth()) {
					setDockLeft(false);
					setHidingConditions(stage.getWidth(), stage.getHeight());
				}
			}else if (!this.dockLeft) {
				if(getX() == 0) {
					setDockLeft(true);
					setHidingConditions(stage.getWidth(), stage.getHeight());
				}
			}

			setY(this.startY);
			this.setCurrentX(getX());
			this.setCurrentY(getY());
		}
				
		super.draw(batch, parentAlpha);
	}

	public boolean isDockDown() {
		return dockDown;
	}

	public void setDockDown(boolean dockDown) {
		this.dockDown = dockDown;
	}

	public boolean isDockLeft() {
		return dockLeft;
	}

	public void setDockLeft(boolean dockRight) {
		this.dockLeft = dockRight;
	}

	public boolean isRepositioned() {
		return repositioned;
	}

	public void setRepositioned(boolean repositioned) {
		this.repositioned = repositioned;
	}

	public void setButton(UIButton button) {
		this.button = button;
	}
	
	public UIButton getButton() {
		return button;
	}

	public float getCurrentX() {
		return currentX;
	}

	public void setCurrentX(float currentX) {
		this.currentX = currentX;
	}

	public float getCurrentY() {
		return currentY;
	}

	public void setCurrentY(float currentY) {
		this.currentY = currentY;
	}
	
	public float getTargetWidth() {
		return targetWidth;
	}

	public void setTargetWidth(float targetWidth) {
		this.targetWidth = targetWidth;
	}
	
}
