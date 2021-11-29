package nz.ac.auckland.softeng281.a4;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;

public class NodesStackAndQueueTest {

	NodesStackAndQueue stack;

	@Before
	public void setUp() {
		stack = new NodesStackAndQueue();
	}

	@Test
	public void isEmptyEmptyStack() {
		assertTrue(stack.isEmpty());
	}

	@Test
	public void isEmptyNotEmpty() {
		stack.append(new Node("4"));
		assertFalse(stack.isEmpty());
	}

	@Test
	public void testAppendPosition() {
		stack.append(new Node("4"));
		stack.append(new Node("3"));

		ArrayList<Node> expected = new ArrayList<Node>();
		expected.add(new Node("3"));
		expected.add(new Node("4"));

		assertEquals(expected, stack.nodeList);
	}

	@Test
	public void testAppendSize() {
		stack.append(new Node(null));
		stack.append(new Node("6"));
		stack.append(new Node("3"));

		ArrayList<Node> expected = new ArrayList<Node>();
		expected.add(new Node(null));
		expected.add(new Node("8"));
		expected.add(new Node("3"));

		assertEquals(expected.size(), stack.nodeList.size());
	}

	@Test
	public void testPushPosition() {
		stack.push(new Node("7"));
		stack.push(new Node("8"));

		ArrayList<Node> expected = new ArrayList<Node>();
		expected.add(new Node("7"));
		expected.add(new Node("8"));

		assertEquals(expected, stack.nodeList);
	}

	@Test
	public void testPushSize() {
		stack.push(new Node("7"));
		stack.push(new Node("8"));
		stack.push(new Node("4"));

		ArrayList<Node> expected = new ArrayList<Node>();
		expected.add(new Node("7"));
		expected.add(new Node("8"));
		expected.add(new Node("9"));

		assertEquals(expected.size(), stack.nodeList.size());
	}

	@Test
	public void testPopPosition() {
		try {
			stack.push(new Node("7"));
			stack.push(new Node("8"));
			stack.push(new Node("4"));
			stack.pop();

			ArrayList<Node> expected = new ArrayList<Node>();
			expected.add(new Node("7"));
			expected.add(new Node("8"));
			expected.add(new Node("4"));
			expected.remove(new Node("4"));

			assertEquals(expected, stack.nodeList);
		} catch (StackEmptyException e) {
			fail();
		}
	}

	@Test
	public void testPopEmpty() {
		try {
			stack.push(new Node("4"));
			stack.pop();
			stack.pop();
			fail();
		} catch (StackEmptyException e) {

		}
	}

	@Test
	public void testPeek() {
		stack.push(new Node("4"));
		stack.push(new Node("6"));
		Node actual = stack.peek();

		Node expected = new Node("6");

		assertEquals(expected, actual);
	}

	@Test
	public void testPeekCombination() {
		try {
			stack.push(new Node("4"));
			stack.push(new Node("6"));
			stack.pop();
			stack.append(new Node("6"));
			stack.append(new Node("6"));
			Node actual = stack.peek();

			Node expected = new Node("4");

			assertEquals(expected, actual);
		} catch (StackEmptyException e) {
			fail();
		}
	}
}
