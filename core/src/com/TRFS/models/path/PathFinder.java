package com.TRFS.models.path;

import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.Node;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.utils.Array;

public class PathFinder {
	
	private Map map;

//	private Vehicle vehicle;
	
	public PathFinder (Map map) {
		this.map = map;
	}
	
	public Array<Link> findRandomPath(Link start) {
		return findRandomPath(start.fromNode);
	}
		
	public Array<Link> findRandomPath(Node node) {
		Array<Link> links = new Array<Link>();
		
		links.add(value);
		
		return links;
	}
	
	

}
