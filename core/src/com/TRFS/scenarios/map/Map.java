package com.TRFS.scenarios.map;

import com.TRFS.scenarios.editor.MapGeometryUtils;
import com.TRFS.scenarios.json.JSONMapUtils;
import com.badlogic.gdx.utils.Array;

public class Map {
	
	public MapPreview mapPreview;
	public Array<Link> links;
	public Array<Node> nodes;
	public Array<Integer> zLevels;
	
	//Holders for other methods
	public Array<Link> inFlowLinks;
	public Array<Node> exitNodes, entranceNodes;

	/** Map attributes */
	public String name;
	public Coordinate centroid, center, dimensions, topRight, bottomLeft;
			
	public Map(MapPreview mapPreview) {
		this.mapPreview = mapPreview;
		this.name = mapPreview.name;
		this.links = new Array<Link>();
		this.nodes = new Array<Node>();
		this.zLevels = new Array<Integer>();

		this.inFlowLinks = new Array<Link>();
		this.entranceNodes = new Array<Node>();
		this.exitNodes = new Array<Node>();
		
		if (this.mapPreview.fromJSON) {
			JSONMapUtils.makeLinksAndNodes(this, mapPreview.fileHandle);
		}
		
		finalizeBuild();
	}
	
	public void finalizeBuild() {
		MapGeometryUtils.setMapAttributes(this);
		
		if (links.size > 0) for (Link link : links) link.finalizeBuild();
		
		if (nodes.size > 0) {
			for (Node node : nodes) {
				node.finalizeBuild();
				if (node.networkEntrance == true) entranceNodes.add(node);
				if (node.networkExit == true) exitNodes.add(node);
			}
		}
	}
}
