package com.TRFS.scenarios.map;

import com.badlogic.gdx.utils.Array;

public class Lane {

	private Array<Coordinate> leftOffset, rightOffset, centerLine;
	private Path path;

	public Lane() {
		//path = new Path(wayPoints);


	}

	public Array<Coordinate> getCenterLine() {
		return centerLine;
	}

	public void setCenterLine(Array<Coordinate> centerLine) {
		this.centerLine = centerLine;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public void setRightOffset(Array<Coordinate> rightOffset) {
		this.rightOffset = rightOffset;
	}

	public void setLeftOffset(Array<Coordinate> leftOffset) {
		this.leftOffset = leftOffset;
	}

	public Array<Coordinate> getLeftOffset() {
		return leftOffset;
	}

	public Array<Coordinate> getRightOffset() {
		return rightOffset;
	}

}
