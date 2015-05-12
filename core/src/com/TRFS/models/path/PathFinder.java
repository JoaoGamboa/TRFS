package com.TRFS.models.path;

import java.util.LinkedList;
import java.util.Random;

import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.Node;
import com.TRFS.vehicles.Vehicle;
import com.badlogic.gdx.utils.Array;

public class PathFinder {
	
	private Map map;
	private Array<Node> exitNodes, tmpVisitedNodes, tmpNeighbourNodes;
	
	public PathFinder (Map map) {
		this.map = map;
		this.tmpVisitedNodes = new Array<Node>();
		this.tmpNeighbourNodes = new Array<Node>();
		this.exitNodes = new Array<Node>();
		
		for (Node node : map.nodes) {
			if (node.networkExit == true) exitNodes.add(node);
		}
	}
	
	public Array<Link> findRandomPath(Link start) {
		return findRandomPath(start.fromNode);
	}
		
	/**Returns a random non-weighted path using the Depth-First Search (DFS) algorithm. 
	 * @param entranceNode
	 * @return An {@link Array} of {@link Link}s defining the path. 
	 */
	public Array<Link> findRandomPath(Node entranceNode) {
		Array<Link> links = new Array<Link>();
		
		//Pick random exit node
		Node exitNode = exitNodes.get(new Random().nextInt(exitNodes.size));
		
		//Depth-First Search (DFS)
		tmpVisitedNodes.add(entranceNode);
		System.out.println(entranceNode.coordinate);
		System.out.println(exitNode.coordinate);
		depthFirst(exitNode, tmpVisitedNodes);
		
		tmpVisitedNodes.clear();
		return links;
	}
	
	private void depthFirst(Node exitNode, Array<Node> visited) {
		
		for (Link link : visited.peek().toLinks) tmpNeighbourNodes.add(link.toNode);
		
		for (Node node : tmpNeighbourNodes) {
			if(tmpVisitedNodes.contains(node, false)) continue;
			
			if(node.equals(exitNode)) {
				tmpVisitedNodes.add(node);
				
				for (Node visNode : tmpVisitedNodes) {
					System.out.print(visNode.internalID +": " + visNode.coordinate);
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
