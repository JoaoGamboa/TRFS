package com.TRFS.scenarios.editor;

import com.TRFS.scenarios.map.Link;

public class LinkAttributes {
		
	public static void handleHierarchy(Link link) {
		int hierarchy = link.getHierarchy();
		
		//defaults
		float laneWidth = 3.0f;
		int lanes = 1;
		int laneCapacity = 600;
		int maxSpeed = 70;
		
		switch (hierarchy) {
			case 1:
				laneWidth = 3.6f;
				maxSpeed = 120;
				laneCapacity = 1200;
				lanes = 3;
				break;
			case 2:
				laneWidth = 3.6f;
				maxSpeed = 100;
				laneCapacity = 1000;
				lanes = 2;
				break;
			case 3:
				laneWidth = 3.4f;
				maxSpeed = 90;
				laneCapacity = 800;
				lanes = 2;
				break;
			case 4:
				laneWidth = 3.0f;
				maxSpeed = 70;
				laneCapacity = 600;
				lanes = 1;
				break;
			case 5:
				laneWidth = 2.5f;
				maxSpeed = 50;
				laneCapacity = 400;
				lanes = 1;
				break;
		}
		
		if (link.getHierarchy() == 0 ) link.setHierarchy(4);
		if (link.getNrOfLanes() == 0) link.setNrOfLanes(lanes);
		if (link.getMaxspeed() == 0) link.setMaxspeed(maxSpeed);
		
		link.setLaneWidth(laneWidth);
		link.setLinkWidth(link.getNrOfLanes() * laneWidth);
		link.setLaneCapacity(laneCapacity);

	}
	
	
	
}
