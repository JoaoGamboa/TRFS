package com.TRFS.vehicles;

import com.TRFS.scenarios.map.Lane;
import com.TRFS.scenarios.map.Link;


public class Car extends Vehicle {
	
	public Car(Link link, Lane lane) {
		super(link, lane);
		setWidth(2.5f);
		setLenght(4);
		setSize(super.getWidth(), super.getHeight());
		
	}
	
	public void update(float dT) {
		super.update(dT);
		
		
	}
	

}
