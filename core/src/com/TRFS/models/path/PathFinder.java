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
	
	public PathFinder (Map map) {
		//this.map = map;
		
		this.tmpVisitedNodes = new Array<Node>();
		this.tmpNeighbourNodes = new Array<Node>();
		
		this.tmpVisitedLinks = new Array<Link>();
		this.tmpNeighbourLinks = new Array<Link>();
		
		this.possiblePaths = new Array<Array<Link>>();
				
		for (Node entranceNode : map.entranceNodes) {
			System.out.println("new entrance");
			for (Node exitNode : map.exitNodes) {
				//Find all possible paths.
				findDFSPath(entranceNode, exitNode);
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
			
	/**Returns a random non-weighted path using the Depth-First Search (DFS) algorithm. 
	 * @param entranceNode
	 * @return An {@link Array} of {@link Link}s defining the path. 
	 */
	public void findDFSPath(Node entranceNode, Node exitNode) {		
		//Depth-First Search (DFS)
		//tmpVisitedNodes.add(entranceNode);
		
		//The entrance node should never have more than one "toLink", so it's safe to add the first in the array.
		//tmpVisitedLinks.add(entranceNode.toLinks.get(0));
		//depthFirstLink(exitNode, tmpVisitedLinks);
		//depthFirst(exitNode, tmpVisitedNodes);
		iterDepthFirst(entranceNode, exitNode);
		//tmpVisitedLinks.clear();
		//tmpNeighbourLinks.clear();
		//tmpVisitedNodes.clear();
	}
	
	private void depthFirstLink(Node exitNode, Array<Link> visited) {
		tmpNeighbourLinks.clear();		
		for (Link link : tmpVisitedLinks.peek().toNode.toLinks) tmpNeighbourLinks.add(link);
		System.out.println(tmpNeighbourLinks.size);
		for (int i = 0; i < tmpNeighbourLinks.size; i++) {
			if(tmpVisitedLinks.contains(tmpNeighbourLinks.get(i), false)) continue;
			
			if(tmpNeighbourLinks.get(i).toNode.equals(exitNode)) {
				visited.add(tmpNeighbourLinks.get(i));
				
				Array<Link> path = new Array<Link>();
				for (Link visitedLink : visited) {
					path.add(visitedLink);
				}
				possiblePaths.add(path);
				
				tmpVisitedLinks.pop();
			}
			
		}
		
		/*for (Link link : tmpVisitedLinks) {
		}*/
		
		for (int i = 0; i < tmpNeighbourLinks.size; i++) {
			if (visited.contains(tmpNeighbourLinks.get(i), false) || tmpNeighbourLinks.get(i).toNode.equals(exitNode)) continue;
			visited.add(tmpNeighbourLinks.get(i));
			depthFirstLink(exitNode, visited);
			visited.pop();
		}
		
		/*for (Link link : tmpNeighbourLinks) {
			if (tmpVisitedLinks.contains(link, false) || link.toNode.equals(exitNode)) continue;
			
		}*/
	}
	
	public Array<Link> getRandomPathFromNode(Node entranceNode) {
		return entranceNode.availablePaths.get(new Random().nextInt(entranceNode.availablePaths.size));
	}
	
	private void iterDepthFirst(Node entranceNode, Node exitNode) {
		Array<Node> visited = new Array<Node>();
		visited.add(entranceNode);
		
		Array<Node> stack = new Array<Node>();
		stack.add(entranceNode);
		
		Node v, w;
		
		while (stack.size > 0) {
			v = stack.pop();
			if (!v.equals(entranceNode)) visited.add(v);
			
			if (!visited.contains(v, false)) {
				visited.add(v);
				
				System.out.print(v.coordinate + " ");
				
				for (Link link : v.toLinks) {
					w = link.toNode;
					if (!visited.contains(w, false)) {
						stack.add(w);
						visited.add(w);
						
						/*if (w.equals(exitNode)) {
							for (Node node : stack) {
								System.out.println(node.coordinate);
								System.out.println("new exit");
							}
						}*/
					}
				}
			}			
		}
		
	}
	
	
	private void depthFirst(Node exitNode, Array<Node> visited) {

		for (Link link : visited.peek().toLinks) tmpNeighbourNodes.add(link.toNode);
		
		for (Node node : tmpNeighbourNodes) {
			if(tmpVisitedNodes.contains(node, false)) continue;
			
			if(node.equals(exitNode)) {
				visited.add(node);
				
				for (int i = 0; i < visited.size; i++) {
					System.out.println(visited.get(i).coordinate + ",  ");
					//if (i > 0 && i < tmpVisitedNodes.size-1) System.out.print(tmpVisitedNodes.get(i).internalID + ",  ");
					//if (i == tmpVisitedNodes.size-1) System.out.print(tmpVisitedNodes.get(i).coordinate + ",  ");
				}
				
				/*for (Node visNode : tmpVisitedNodes) {
					
					System.out.print(visNode.internalID + ",  ");
				}*/
				
				System.out.println();
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
