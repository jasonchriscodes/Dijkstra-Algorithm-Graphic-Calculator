package nz.ac.auckland.softeng281.a4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * You cannot add new fields.
 */
public class Graph {

	/**
	 * Each node maps to a list of all the outgoing edges from that node
	 */
	private HashMap<Node, EdgesLinkedList> adjacencyMap;
	/**
	 * root of the graph, to know where to start the DFS or BFS
	 */
	private Node root;

	/**
	 * !!!!!! You cannot change this method !!!!!!!
	 */
	public Graph(List<String> edges, List<String> weights) {
		if (edges.isEmpty() || weights.isEmpty()) {
			throw new IllegalArgumentException("edges and weights are empty");
		}
		adjacencyMap = new HashMap<>();
		int i = 0;
		for (String edge : edges) {
			String[] split = edge.split(",");
			Node source = new Node(split[0]);
			Node target = new Node(split[1]);
			Edge edgeObject = new Edge(source, target, Integer.parseInt(weights.get(i)));
			if (!adjacencyMap.containsKey(source)) {
				adjacencyMap.put(source, new EdgesLinkedList());
			}
			adjacencyMap.get(source).append(edgeObject);
			if (i == 0) {
				root = source;
			}
			i++;
		}
	}

	/**
	 * find a particular node, note that a node might not have outgoing edges but
	 * only in going edges so you need to check also the target nodes of the edges
	 *
	 * @param node
	 * @return true if adjacencyMap contains the node, false otherwise.
	 */
	public boolean isNodeInGraph(Node node) {
		for (Node sourceNode : adjacencyMap.keySet()) {
			EdgesLinkedList edgeLinkedList = adjacencyMap.get(sourceNode);
			int i = 0;
			while (i < edgeLinkedList.size()) {
				Edge currentEdge = edgeLinkedList.get(i);
				Node nodeSource = currentEdge.getSource();
				Node nodeTarget = currentEdge.getTarget();
				if (node.equals(nodeSource) || node.equals(nodeTarget)) {
					return true;
				}
				i++;
			}
		}
		return false;
	}

	/**
	 * This method finds an edge with a specific weight, if there are more than one
	 * you need to return the first you encounter. You must use Breath First Search
	 * (BFS) strategy starting from the root.
	 * <p>
	 * You can create a data structure to keep track of the visited nodes Set<Node>
	 * visited = new HashSet<>(); If you don't keep track of the visited nodes the
	 * method will run forever!
	 * <p>
	 * <p>
	 * In addition to the data structure visited you can only create new data
	 * structures of type EdgesLinkedList and NodesStackAndQue
	 *
	 * @param weight
	 * @return the Edge with the specific weight, null if no edge with the specific
	 *         weight exists in the graph
	 */
	public Edge searchEdgeByWeight(int weight) {
//		 System.out.println("-------Search Edge by Weight-----------");
		Set<Node> visitedNode = new HashSet<>();
		Node referenceNode = root;
		int p = 0;
		while (!visitedNode.contains(referenceNode)) {
//			 System.out.println("iteration " + p + ", referenceNode = " + referenceNode);
			visitedNode.add(referenceNode);
			EdgesLinkedList currentEdge = adjacencyMap.get(referenceNode);
			int i = 0;
			while (i < currentEdge.size()) {
				Edge returnEdge = currentEdge.get(i);
//				 System.out.println("iteration edge: " + i + ", edge" + edge);
				if (returnEdge.getWeight() == weight) {
					return returnEdge;
				}
				referenceNode = returnEdge.getTarget();
				i++;
			}
			p++;
		}
//		 System.out.println("Visited node:" + visited);
		return null;
	}

	/**
	 * Returns the weight of the Edge with Node source and Node target if the given
	 * Edge is inside the graph. If there is no edge with the specified source and
	 * target, the method returns -1 You must use Depth First Search (DFS) strategy
	 * starting from the root.
	 * <p>
	 * RULES You can create a data structure to keep track of the visited nodes
	 * Set<Node> visited = new HashSet<>(); If you don't keep track of the visited
	 * nodes the method will run forever!
	 * <p>
	 * In addition to the data structure visited you can only create new data
	 * structures of type
	 * <p>
	 * NodesStackAndQueue and EdgesLinkedList
	 *
	 * @param source
	 * @param target
	 * @return the weight of the first encountered edge with source and target, -1
	 *         if no edge with the given source and target exists
	 */
	public int searchWeightByEdge(Node source, Node target) {
//		System.out.println("------Search Weight by Edge---------");
		// visited as source/reference not target
		Set<Node> visitedNode = new HashSet<>();
		ArrayList<Node> tempList = new ArrayList<Node>();
		Node referenceNode = root;
		while (!visitedNode.contains(referenceNode)) {
			visitedNode.add(referenceNode);
//			System.out.println("visited:" + visited);
			tempList.add(referenceNode);
			EdgesLinkedList currentEdge = adjacencyMap.get(referenceNode);
			int i = 0;
			while (i < currentEdge.size()) {
				Edge returnEdge = currentEdge.get(i);
//				System.out.println("iteration edge: " + i + ", edge" + edge);
				if (source.equals(returnEdge.getSource()) && target.equals(returnEdge.getTarget())) {
					return returnEdge.getWeight();
				}
				referenceNode = returnEdge.getTarget();
				i++;
			}
		}
		return -1;
	}

	/*
	 * Helper to get minimum distance from source
	 */
	private Node getMinimumDistanceNode(Map<Node, Integer> distanceValue) {
		Node returnValue = null;
		int tempValue = Integer.MAX_VALUE;
		for (Node node : distanceValue.keySet()) {
			if (distanceValue.get(node) < tempValue) {
				tempValue = distanceValue.get(node);
				returnValue = node;
			}
		}
		return returnValue;
	}

	/**
	 * Helper for get set all nodes in graph
	 */
	public Set<Node> getAllNodesFromGraph() {
		Set<Node> returnValue = new HashSet<>();
		for (Node sourceNode : adjacencyMap.keySet()) {
			returnValue.add(sourceNode);
			EdgesLinkedList edgesLinkedList = adjacencyMap.get(sourceNode);
			int i = 0;
			while (i < edgesLinkedList.size()) {
				Edge edge = edgesLinkedList.get(i);
				returnValue.add(edge.getTarget());
				i++;
			}
		}
		return returnValue;
	}

	/**
	 * Given a source Node and a target Node it returns the shortest path between
	 * source and target
	 * <p>
	 * A Path is represented as an ordered sequence of nodes, together with the
	 * total weight of the path. (see Path.java class)
	 *
	 * @param source
	 * @param target
	 * @return the shortest path between source and target
	 */
	public Path computeShortestPath(Node source, Node target) {
//		System.out.println("------Compute Shortest Path---------");

		// Initialization
		Set<Node> nodeSet = this.getAllNodesFromGraph();
		Map<Node, Integer> distanceFromSource = new HashMap<Node, Integer>();
		Map<Node, Node> prevNode = new HashMap<Node, Node>();
		Map<Node, Integer> processedNodeDistance = new HashMap<Node, Integer>();
		for (Node node : nodeSet) {
			distanceFromSource.put(node, Integer.MAX_VALUE);
			prevNode.put(node, null);
			processedNodeDistance.put(node, Integer.MAX_VALUE);
		}
		distanceFromSource.put(source, 0);
		processedNodeDistance.put(source, 0);

		// Iteration
		while (!processedNodeDistance.isEmpty()) {
//			System.out.println(processedNodeDistance);
			Node referenceNode = getMinimumDistanceNode(processedNodeDistance);
			EdgesLinkedList edgesLinkedList = adjacencyMap.get(referenceNode);
			int i = 0;
			if (edgesLinkedList != null) {
				while (i < edgesLinkedList.size()) {

					Edge edge = edgesLinkedList.get(i);
					int newDistance = distanceFromSource.get(edge.getSource()) + edge.getWeight();
					if (newDistance < distanceFromSource.get(edge.getTarget())) {
						distanceFromSource.put(edge.getTarget(), newDistance);
						processedNodeDistance.put(edge.getTarget(), newDistance);
						prevNode.put(edge.getTarget(), edge.getSource());
					}
					i++;
				}
			}
			processedNodeDistance.remove(referenceNode);
		}
//		System.out.println("Prev Node: " + prevNode);
//		System.out.println("Node Distance: " + distanceFromSource);

		// Perform relaxation
		// Prepare list node by tracing back the node until no node has previous
		// Return will be in target to source list
		List<Node> targetToSourceNodeList = new ArrayList<Node>();
		targetToSourceNodeList.add(target);
		Node tracebackNode = prevNode.get(target);
		while (tracebackNode != null) {
			targetToSourceNodeList.add(tracebackNode);
			tracebackNode = prevNode.get(tracebackNode);
		}
//		System.out.println("Target to Source:" + targetToSourceNodeList);

		// Re-arrange node list (source to target)
		List<Node> sourceToTargetNodeList = new ArrayList<Node>();
		int i = 0;
		while (i < targetToSourceNodeList.size()) {
			sourceToTargetNodeList.add(targetToSourceNodeList.get(targetToSourceNodeList.size() - i - 1));
			i++;
		}
//		System.out.println("Source to target list: " + sourceToTargetNodeList);
		Path returnValue = new Path(distanceFromSource.get(target), sourceToTargetNodeList);
		return returnValue;
	}
}
