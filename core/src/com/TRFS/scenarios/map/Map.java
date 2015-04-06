package com.TRFS.scenarios.map;

import com.TRFS.scenarios.editor.MapGeometryUtils;
import com.TRFS.scenarios.json.JSONMapUtils;
import com.badlogic.gdx.utils.Array;

public class Map {
	
	private MapPreview mapPreview;

	private Array<Link> links, inFlowLinks;
	private Array<Node> nodes;

	private Array<Integer> zLevels;

	/** Map attributes */
	private String name;
	private Coordinate centroid, center, dimensions, topRight, bottomLeft;
			
	public Map(MapPreview mapPreview) {
		this.name = mapPreview.getName();
		this.setMapPreview(mapPreview);
		
		this.links = new Array<Link>();
		this.nodes = new Array<Node>();
		this.inFlowLinks = new Array<Link>();
		this.zLevels = new Array<Integer>();
		
		buildMap();
	}
	
	private void buildMap() {
		
		if (this.mapPreview.isFromJSON()) {
			JSONMapUtils.makeLinksAndNodes(this, mapPreview.getFileHandle());
		}
				
		MapGeometryUtils.setMapAttributes(this);
		
	}
					
	public String getName() {
		return name;
	}

	public Array<Node> getNodes() {
		if (links.size == 0) {
			getLinks();
		}
		return nodes;
	}
	
	public Array<Link> getLinks() {
		return links;
	}

	public Array<Link> getInFlowLinks() {
		return inFlowLinks;
	}

	public Coordinate getCentroid() {
		return centroid;
	}

	public Coordinate getCenter() {
		return center;
	}

	public MapPreview getMapPreview() {
		return mapPreview;
	}

	public void setMapPreview(MapPreview mapPreview) {
		this.mapPreview = mapPreview;
	}

	public Array<Integer> getzLevels() {
		return zLevels;
	}

	public Coordinate getDimensions() {
		return dimensions;
	}

	public void setDimensions(Coordinate dimensions) {
		this.dimensions = dimensions;
	}

	public Coordinate getTopRight() {
		return topRight;
	}

	public void setTopRight(Coordinate topRight) {
		this.topRight = topRight;
	}

	public Coordinate getBottomLeft() {
		return bottomLeft;
	}

	public void setBottomLeft(Coordinate bottomLeft) {
		this.bottomLeft = bottomLeft;
	}

	public void setCentroid(Coordinate centroid) {
		this.centroid = centroid;
	}

	public void setCenter(Coordinate center) {
		this.center = center;
	}

}
