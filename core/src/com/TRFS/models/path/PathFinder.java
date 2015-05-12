package com.TRFS.models.path;

import java.util.Random;

import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.Node;
import com.badlogic.gdx.utils.Array;

public class PathFinder {
	
	private Map map;
	
	private Array<Node> exitNodes, entranceNodes, tmpVisitedNodes, tmpNeighbourNodes;
	
	private Array<Link> tmpVisitedLinks, tmpNeighbourLinks;
	public Array<Array<Link>> possiblePaths;
	
	public PathFinder (Map map) {
		this.map = map;
		
		this.tmpVisitedNodes = new Array<Node>();
		this.tmpNeighbourNodes = new Array<Node>();
		
		this.tmpVisitedLinks = new Array<Link>();
		this.tmpNeighbourLinks = new Array<Link>();
		
		this.possiblePaths = new Array<Array<Link>>();
		
		this.entranceNodes = new Array<Node>();
		this.exitNodes = new Array<Node>();
		
		for (Node node : map.nodes) {
			if (node.networkEntrance == true) entranceNodes.add(node);
			if (node.networkExit == true) exitNodes.add(node);
		}
		
		for (Node entranceNode : entranceNodes) {
			for (Node exitNode : exitNodes) {
				possiblePaths.add(findDFSPath(entranceNode, exitNode));
			}
			
			int indexOfShortestPath = 0;
			for (int i = 0; i < possiblePaths.size; i++) {
				if (possiblePaths.get(i).size < possiblePaths.get(indexOfShortestPath).size) indexOfShortestPath = i;
			}
			
		}
	}
			
	/**Returns a random non-weighted path using the Depth-First Search (DFS) algorithm. 
	 * @param entranceNode
	 * @return An {@link Array} of {@link Link}s defining the path. 
	 */
	public Array<Link> findDFSPath(Node entranceNode, Node exitNode) {
		Array<Link> links = new Array<Link>();
		
		//Depth-First Search (DFS)
		//tmpVisitedNodes.add(entranceNode);
		
		//The entrance node should never have more than one "toLink", so it's safe to add the first in the array.
		tmpVisitedLinks.add(entranceNode.toLinks.get(0));
		depthFirstLink(exitNode, tmpVisitedLinks);

		//depthFirst(exitNode, tmpVisitedNodes);
		
		tmpVisitedLinks.clear();
		//tmpVisitedNodes.clear();
		return links;
	}
	
	private void depthFirstLink(Node exitNode, Array<Link> visited) {
		
		for (Link link : visited.peek().toLinks) tmpNeighbourNodes.add(link.toNode);
		
		for (Link link : visited.peek().toNode.toLinks)
		
		for (Node node : tmpNeighbourNodes) {
			if(tmpVisitedNodes.contains(node, false)) continue;
			
			if(node.equals(exitNode)) {
				tmpVisitedNodes.add(node);
				
				for (Node visNode : tmpVisitedNodes) {
					System.out.println(visNode.internalID +": " + visNode.coordinate);
				}
				
				visited.pop();
			}
		}
		
		for (Node node : tmpNeighbourNodes) {
			if (visited.contains(node, false) || node.equals(exitNode)) continue;
			
			visited.add(node);
			depthFirst(exitNode, visited);
			visited.pop();
		}
	}
	
	private void depthFirst(Node exitNode, Array<Node> visited) {
		
		for (Link link : visited.peek().toLinks) tmpNeighbourNodes.add(link.toNode);
		
		for (Node node : tmpNeighbourNodes) {
			if(tmpVisitedNodes.contains(node, false)) continue;
			
			if(node.equals(exitNode)) {
				tmpVisitedNodes.add(node);
				
				for (Node visNode : tmpVisitedNodes) {
					System.out.println(visNode.internalID +": " + visNode.coordinate);
				}
				
				visited.pop();
			}
		}
		
		for (Node node : tmpNeighbourNodes) {
			if (visited.contains(node, false) || node.equals(exitNode)) continue;
			
			visited.add(node);
			depthFirst(exitNode, visited);
			visited.pop();
		}
	}
}
