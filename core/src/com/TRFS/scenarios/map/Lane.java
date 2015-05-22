package com.TRFS.scenarios.map;

import com.TRFS.scenarios.editor.PathBuilder;
import com.badlogic.gdx.utils.Array;

public class Lane {
	public int index;
	public Array<Coordinate> leftOffset, rightOffset, centerLine;
	public Path path;

	public Lane(int index) {
		this.index = index;
	}

	public void setCenterLine(Array<Coordinate> centerLine) {
		this.centerLine = centerLine;
		this.path = PathBuilder.buildPath(centerLine);
	}
}
