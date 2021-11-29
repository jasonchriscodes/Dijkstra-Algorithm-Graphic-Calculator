package nz.ac.auckland.softeng281.a4;

/**
 * The Linked List Class has only one head pointer to the start edge (head)
 * Edges are indexed starting from 0. The list goes from 0 to size-1. Note that
 * the List does not have a maximum size.
 *
 * @author Partha Roop
 */
public class EdgesLinkedList {
	// the head of the linked list
	private Edge headEdge;

	public EdgesLinkedList() {
		headEdge = null;
	}

	/**
	 * This method adds an edge as the start edge of the list
	 *
	 * @param edge to prepend
	 */
	public void prepend(Edge edge) {
		Edge newHead = edge;
		newHead.setNext(headEdge);
		headEdge = newHead;
	}

	/**
	 * This method adds an edge as the end edge of the list
	 *
	 * @param edge to append
	 */
	public void append(Edge edge) {
		Edge newEdge = edge;
		if (headEdge == null) {
			headEdge = newEdge;
			return;
		}
		Edge last = headEdge;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.setNext(newEdge);
	}

	/**
	 * This method gets the edge at a given position
	 *
	 * @param pos: an integer, which is the position
	 * @return the Edge at the position pos
	 */
	public Edge get(int pos) throws InvalidPositionException {
		if (pos < 0 || pos > size() - 1) {
			throw new InvalidPositionException("Position " + pos + " outside the list boundary");
		}
		int i = 0;
		Edge newEdge = headEdge;
		while (i != pos) {
			++i;
			newEdge = newEdge.getNext();
		}
		return newEdge;
	}

	/**
	 * This method adds an edge at a given position in the List
	 *
	 * @param pos:  an integer, which is the position
	 * @param edge: the edge to add
	 * @throws InvalidPositionException
	 */
	public void insert(int pos, Edge edge) throws InvalidPositionException {
		if (pos < 0 || pos > size() - 1) {
			throw new InvalidPositionException("Position " + pos + " outside the list boundary");
		} else {
			Edge nextEdge = null;
			if (pos == 0) {
				nextEdge = headEdge;
				headEdge = edge;
				edge.setNext(nextEdge);
			} else {
				Edge pointer = headEdge;
				Edge prevEdge = null;
				int i = 0;
				while (i < pos) {
					prevEdge = pointer;
					pointer = pointer.getNext();
					i++;
				}
				prevEdge.setNext(edge);
				edge.setNext(pointer);
			}
		}
	}

	/**
	 * This method removes an edge at a given position
	 *
	 * @param pos: an integer, which is the position
	 */
	public void remove(int pos) throws InvalidPositionException {
		int count = 0;
		Edge tempEdge = headEdge;
		if (pos < 0 || pos > size() - 1) {
			throw new InvalidPositionException("Position " + pos + " outside the list boundary");
		}
		if (headEdge == null)
			return;
		if (pos == 0) {
			headEdge = tempEdge.getNext();
			return;
		}
		// Find previous edge of the edge to be deleted
		while (tempEdge != null && count < pos - 1) {
			tempEdge = tempEdge.getNext();
			if (tempEdge == null || tempEdge.getNext() == null) {
				return;
			}
			count++;
		}
		Edge next = tempEdge.getNext().getNext();
		tempEdge.setNext(next);
	}

	/**
	 * This method returns the size of the Linked list
	 *
	 * @return the size of the list
	 */

	public int size() {
		int i = 0;

		Edge t = headEdge;
		while (t != null) {
			++i;
			t = t.getNext();
		}
		return i;
	}

	/**
	 * This method is used for printing the data in the list from head till the last
	 * node
	 */
	public void print() {
		Edge edge = headEdge;
		while (edge != null) {
			System.out.println(edge);
			edge = edge.getNext();
		}
	}
}
