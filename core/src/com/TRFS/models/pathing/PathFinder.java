package com.TRFS.models.pathing;

import java.util.Random;

import com.TRFS.scenarios.map.Link;
import com.TRFS.scenarios.map.Map;
import com.TRFS.scenarios.map.Node;
import com.badlogic.gdx.utils.Array;

public class PathFinder {
			
	/**Returns a random non-weighted path using the Depth-First Search (DFS) algorithm. 
	 * @param entranceNode
	 * @return An {@link Array} of {@link Link}s defining the path. 
	 */
	public PathFinder (Map map) {
						
		for (Node entranceNode : map.entranceNodes) {
			for (Node exitNode : map.exitNodes) {
				//Find a possible path
				Array<Link> path = iterDepthFirst(entranceNode, exitNode);
				
				if (path != null) {
					//Store it within the entrance node if any solution was found
					entranceNode.availablePaths.add(path);	
				}
			}
		}
	}
	
	public Array<Link> getRandomPathFromNode(Node entranceNode) {
		if (entranceNode.availablePaths.size > 0) {
			return entranceNode.availablePaths.get(new Random().nextInt(entranceNode.availablePaths.size));			
		}
		return null;
	}

	/**Currently returns the first path found.
	 * @param entranceNode
	 * @param exitNode
	 * @return
	 */
	private Array<Link> iterDepthFirst(Node entranceNode, Node exitNode) {
		Array<Array<Link>> results = new Array<Array<Link>>();
		
		Array<Node> visited = new Array<Node>();
		Array<Node> stack = new Array<Node>();
		stack.add(entranceNode);
		
		Node current, neighbour;
		
		boolean justVisitedExit = false;
				
		while (stack.size > 0) {
			current = stack.peek();
			
			neighbour = getUnvisitedNeighbourNode(current, visited, exitNode, justVisitedExit);
						
			if (neighbour != null) {
				visited.add(neighbour);
				stack.add(neighbour);
				
				if (neighbour.equals(exitNode)) { //Save stack
					justVisitedExit = true;
					results.add(nodeStackToLinkArray(stack));
					stack.pop(); 
				}
			} else stack.pop();
		}
		
		return cheapestPath(results);
	}
	
	/**Returns the first unvisited node found. Will return the exit node if it is an option.
	 * @param node
	 * @param visited
	 * @param exitNode
	 * @return
	 */
	private Node getUnvisitedNeighbourNode(Node node, Array<Node> visited, Node exitNode, boolean justVisitedExit) {
		for (Link link : node.toLinks) {
			if (!visited.contains(link.toNode, false)) return link.toNode;
			if (link.toNode.quickEquals(exitNode)) {
				if (justVisitedExit) {
					justVisitedExit = false;
					return null;
				} else return link.toNode;
			}
		}
		return null;
	}
		
	private Array<Link> nodeStackToLinkArray (Array<Node> nodes) {
		Array<Link> linkSequence = new Array<Link>();
		
		for (int i = 0; i < nodes.size - 1; i++) {
			Link link = null;
			Node nodeA = nodes.get(i);
			Node nodeB = nodes.get(i + 1);
			
			for (int j = 0; j < nodeA.toLinks.size; j++) {
				link = nodeA.toLinks.get(j);
				if (link.toNode.equals(nodeB)) break;
			}
			linkSequence.add(link);
		}
		return linkSequence;
	}
	
	private Array<Link> cheapestPath(Array<Array<Link>> results) {
		float minTotalCost = Float.MAX_VALUE;
		Array<Link> cheapestPath = null;
		
		for (Array<Link> array : results) {
			float totalCost = 0;
			for (Link link : array) {
				totalCost += link.cost;
			}
			
			if (totalCost < minTotalCost) {
				minTotalCost = totalCost;
				cheapestPath = array;
			}
		}
		
		return cheapestPath;
	}
	
	/*private Array<Link> iterDepthFirstBackup(Node entranceNode, Node exitNode) {
		Array<Array<Link>> results = new Array<Array<Link>>();
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
				
				if (neighbour.equals(exitNode)) { //Save stack
					return nodeStackToLinkArray(stack);
				}
			} else stack.pop();
		}
		return null;
	}*/
}
