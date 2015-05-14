package com.TRFS.models.path;

import java.util.Random;

import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.Node;
import com.badlogic.gdx.utils.Array;

public class PathFinder {
	
	//General
	public Array<Array<Link>> possiblePaths;
	
	//DFS
	private Array<Node> tmpVisitedNodes, tmpNeighbourNodes;
	private Array<Link> tmpVisitedLinks, tmpNeighbourLinks;
	
	/**Returns a random non-weighted path using the Depth-First Search (DFS) algorithm. 
	 * @param entranceNode
	 * @return An {@link Array} of {@link Link}s defining the path. 
	 */
	public PathFinder (Map map) {
		//this.map = map;
		
		this.tmpVisitedNodes = new Array<Node>();
		this.tmpNeighbourNodes = new Array<Node>();
		
		this.tmpVisitedLinks = new Array<Link>();
		this.tmpNeighbourLinks = new Array<Link>();
		
		this.possiblePaths = new Array<Array<Link>>();
				
		for (Node entranceNode : map.entranceNodes) {
			for (Node exitNode : map.exitNodes) {
				//Find all possible paths.
				iterDepthFirst(entranceNode, exitNode);
			}
			//Identify the shortest one
			int indexOfShortestPath = 0;
			for (int i = 0; i < possiblePaths.size; i++) {
				if (possiblePaths.get(i).size < possiblePaths.get(indexOfShortestPath).size) indexOfShortestPath = i;
			}
			System.out.println(possiblePaths.size);
			//Store it within the entrance node if any solution was found
			if (possiblePaths.size > 0) {
				entranceNode.availablePaths.add(possiblePaths.get(indexOfShortestPath));
				possiblePaths.clear();
			}
		}
	}
	
	public Array<Link> getRandomPathFromNode(Node entranceNode) {
		return entranceNode.availablePaths.get(new Random().nextInt(entranceNode.availablePaths.size));
	}
	
	private void iterDepthFirst(Node entranceNode, Node exitNode) {
		Array<Node> visited = new Array<Node>();
		Array<Node> stack = new Array<Node>();
		
		stack.add(entranceNode);
		
		Node current, neighbour;
		
		while (stack.size > 0) {
			
			current = stack.peek();
			
			neighbour = getUnvisitedNeighbourNode(current, visited);
			
			if (neighbour != null) {
				visited.add(neighbour);
				stack.add(neighbour);
			} else stack.pop();
	
			if (neighbour.equals(exitNode)) {
				
			}
			
			
			/*
			if (!visited.contains(v, false)) visited.add(v);
			
			if (v.equals(exitNode)) {
				//save path
				
				stack.pop();
				continue;
				//visited.removeValue(v, false);
			}
			
			if (v.toLinks.size == 0) stack.pop();
							
			for (Link link : v.toLinks) {
				w = link.toNode;
				if (!visited.contains(w, false)) {
					stack.add(w);
					break;
				} else {
					stack.pop();
				}
			}	*/	
		}
	}
	
	private Node getUnvisitedNeighbourNode(Node node, Array<Node> visited) {
		for (Link link : node.toLinks) {
			if (!visited.contains(link.toNode, false)) return link.toNode;
		}
		return null;
	}
}
