package com.TRFS.scenarios.map;

import com.TRFS.scenarios.editor.MapGeometryUtils;
import com.TRFS.scenarios.json.JSONMapUtils;
import com.badlogic.gdx.utils.Array;

public class Map {
	
	public MapPreview mapPreview;

	public Array<Link> links, inFlowLinks;
	public Array<Node> nodes;

	public Array<Integer> zLevels;

	/** Map attributes */
	public String name;
	public Coordinate centroid, center, dimensions, topRight, bottomLeft;
			
	public Map(MapPreview mapPreview) {
		this.mapPreview = mapPreview;
		this.name = mapPreview.name;
		
		this.links = new Array<Link>();
		this.nodes = new Array<Node>();
		this.inFlowLinks = new Array<Link>();
		this.zLevels = new Array<Integer>();
		
		if (this.mapPreview.fromJSON) {
			JSONMapUtils.makeLinksAndNodes(this, mapPreview.fileHandle);
		}
		MapGeometryUtils.setMapAttributes(this);
	}

	/*public Array<Node> getNodes() {
		if (links.size == 0) {
			getLinks();
		}
		return nodes;
	}*/

}
