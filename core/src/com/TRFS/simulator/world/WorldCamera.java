package com.TRFS.simulator.world;

import com.TRFS.scenarios.Scenario;
import com.TRFS.scenarios.map.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class WorldCamera extends OrthographicCamera {
	
	private Map map;
	private float rotationAngle;
	
	public WorldCamera(Scenario scenario) {
		super();
		this.map = scenario.getMap();
	}
	
	public void resize(float width, float height) {
		this.viewportWidth = width;
		this.viewportHeight = height;
		this.position.set(this.map.getCentroid().getX(),this.map.getCentroid().getY(),0);
		this.update();
	}
	
	private void scrollZoom (int amount) {
		int direction = (amount > 0) ? 1 : -1; 
		float zoomFactor = (float) (0.05f * this.zoom + 0.005f);
		this.zoom +=  (direction * zoomFactor);
		this.zoom = MathUtils.clamp(this.zoom, 0.005f, 2f);
		this.update();
	}
		
	public void zoomToPoint (int amount) {
		float oldZ = this.zoom;
		
		this.scrollZoom(amount);
		
        float x = (Gdx.input.getX() - (float) this.viewportWidth/2f) * (oldZ - this.zoom);
        float y = (-Gdx.input.getY() + (float) this.viewportHeight/2f) * (oldZ - this.zoom);
        
        this.translate(x, y, 0);
        this.update();
	}
	
	public void rotateScroll(int amount) {
		int direction = (amount > 0) ? 1 : -1; 
		this.rotate(direction *2.5f);
		
		float newAngle = (float) Math.atan2(this.up.x, this.up.y)*MathUtils.radiansToDegrees;
		
		newAngle = (newAngle <= 0 && newAngle < -180) ? -newAngle : 360 - newAngle;
		this.rotationAngle = (newAngle / 360 >= 1) ? newAngle - 360 * ((float) Math.floor(newAngle / 360)) : newAngle;

		this.update();
	}
	
	public void pan (float startX, float startY, int screenX, int screenY) {
		float deltaX = (- screenX + startX) * this.zoom;
		float deltaY = (- startY + screenY) * this.zoom;
		
		float alpha = (float) Math.atan2(deltaX, deltaY)*MathUtils.radiansToDegrees;

		alpha = 180 + ((alpha / 360 >= 1) ? alpha - 360 * ((float) Math.floor(alpha / 360)) : alpha);
		
		float newAlpha = (this.rotationAngle <= alpha) ? alpha - this.rotationAngle : alpha - 360 - this.rotationAngle; 
		
		float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		
		float nDX = length * (float) Math.sin(Math.toRadians(newAlpha));
		float nDY = length * (float) Math.cos(Math.toRadians(newAlpha));
		
		this.translate(-nDX, -nDY);
		
		//Pan limits
		this.position.x = MathUtils.clamp(this.position.x, (float) (map.getMinX()-200),(float) (map.getMaxX()+200));
		this.position.y = MathUtils.clamp(this.position.y, (float) (map.getMinY()-200),(float) (map.getMaxY()+200));
		
		this.update();
	}

}
