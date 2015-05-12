package com.TRFS.scenarios.editor;

public class DoubleCoordinate {

	public double x;
	public double y;
	
	public DoubleCoordinate () {
		this.x = 0;
		this.y = 0;
	}
	
	public DoubleCoordinate (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

}
