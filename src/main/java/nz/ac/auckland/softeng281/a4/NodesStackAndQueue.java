package nz.ac.auckland.softeng281.a4;

import java.util.ArrayList;

public class NodesStackAndQueue {
	ArrayList<Node> nodeList;

	public NodesStackAndQueue() {
		super();
		nodeList = new ArrayList<Node>();
	}

	public boolean isEmpty() {
		return nodeList.isEmpty();
	}

	/**
	 * Push operation refers to inserting an element on the Top of the stack.
	 *
	 * @param node
	 */
	public void push(Node node) {
		nodeList.add(node);
	}

	/**
	 * pop an element from the top of the stack (removes and returns the tope
	 * element)
	 *
	 * @return
	 */
	public Node pop() throws StackEmptyException {
		if (nodeList.isEmpty()) {
			throw new StackEmptyException("Stack is empty cannot pop");
		} else {
			Node temp = nodeList.get(nodeList.size() - 1);
			nodeList.remove(nodeList.size() - 1);
			return temp;
		}
	}

	/**
	 * get the element from the top of the stack without removing it
	 *
	 * @return
	 */
	public Node peek() {
		if (nodeList.isEmpty()) {
			return null;
		} else {
			Node temp = nodeList.get(nodeList.size() - 1);
			return temp;
		}
	}

	/**
	 * append an element at the end of the stack
	 *
	 * @param node
	 */
	public void append(Node node) {
		ArrayList<Node> temp = new ArrayList<Node>();
		temp.add(node);
		for (Node node2 : nodeList) {
			temp.add(node2);
		}
		nodeList = temp;
	}
}
