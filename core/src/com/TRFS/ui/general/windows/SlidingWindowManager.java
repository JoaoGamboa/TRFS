package com.TRFS.ui.general.windows;

import java.util.ArrayList;

public class SlidingWindowManager {
	
	private ArrayList<SlidingWindow> list;

	public SlidingWindowManager() {
		
		list = new ArrayList<SlidingWindow> ();

	}

	public void manage(SlidingWindow clickedWindow) {
		
		//TODO: 	implement overlap detection/hide windows if they overlap;		
		
		if (!clickedWindow.isVisible()) {
			
			for (SlidingWindow closingWindow : list) {
				if (!closingWindow.equals(clickedWindow) && closingWindow.isVisible() && 
					!closingWindow.isRepositioned() && closingWindow.isDockLeft() == clickedWindow.isDockLeft()) {
					closingWindow.hide(closingWindow);

				}
			}
			clickedWindow.show(clickedWindow);

		
		} else if (clickedWindow.isVisible()) {
			clickedWindow.hide(clickedWindow);
		}
		
	}
	
	public void resize(int width, int height) {
		for (SlidingWindow window : list) {
			window.resize(width, height);
		}
	}
	
	public void add(SlidingWindow window) {
		list.add(window);
	}

	public ArrayList<SlidingWindow> getList() {
		return list;
	}
	
}
