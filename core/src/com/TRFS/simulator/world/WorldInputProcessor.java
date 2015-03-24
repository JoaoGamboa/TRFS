package com.TRFS.simulator.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

public class WorldInputProcessor implements InputProcessor {
	
	private int pressedButton;
	private float startX, startY;
	public Vector3 mouseCoordinates = new Vector3();	
	
	private WorldCamera camera;
	
	public WorldInputProcessor(WorldCamera camera) {
		this.camera = camera;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer,
			int button) {		
		this.pressedButton = button;
		this.startX = screenX;
		this.startY = screenY;
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pressedButton == Input.Buttons.MIDDLE){
			this.camera.pan(this.startX, this.startY, screenX, screenY);
			this.startX = screenX;
			this.startY = screenY;
		}
				
		return false;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouseCoordinates.set(screenX, screenY, 0);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		/*if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT))*/ 
		
		if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT)) {
			camera.rotateScroll(amount);
		} else/*if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT))*/  {
			camera.zoomToPoint(amount);
		}

		return true;
	}


}
