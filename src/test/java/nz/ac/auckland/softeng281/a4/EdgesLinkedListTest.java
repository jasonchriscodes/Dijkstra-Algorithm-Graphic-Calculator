package nz.ac.auckland.softeng281.a4;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

public class EdgesLinkedListTest {

	EdgesLinkedList list;

	@Before
	public void setUp() {
		list = new EdgesLinkedList();
	}

	@Test
	public void testPrependEmptyList() {
		list.prepend(new Edge(new Node("1"), new Node("2"), 1));
		assertEquals(new Edge(new Node("1"), new Node("2"), 1), list.get(0));
	}

	@Test
	public void testPrependSize() {
		list.prepend(new Edge(new Node("1"), new Node("2"), 1));
		list.prepend(new Edge(new Node("4"), new Node("7"), 1));
		list.prepend(new Edge(new Node("5"), new Node("3"), 1));

		assertEquals(3, list.size());
	}

	@Test
	public void testPrependPosition() {
		try {
			list.prepend(new Edge(new Node("1"), new Node("2"), 1));
			list.prepend(new Edge(new Node("4"), new Node("7"), 1));
			list.prepend(new Edge(new Node("5"), new Node("3"), 1));

			EdgesLinkedList expected = new EdgesLinkedList();
			expected.append(new Edge(new Node("5"), new Node("3"), 1));
			expected.append(new Edge(new Node("4"), new Node("7"), 1));
			expected.append(new Edge(new Node("1"), new Node("2"), 1));

			assertEquals(expected.get(0), list.get(0));
		} catch (InvalidPositionException e) {
			fail();
		}
	}

	@Test
	public void testAppendEmptyList() {
		list.append(new Edge(new Node("1"), new Node("2"), 1));
		assertEquals(new Edge(new Node("1"), new Node("2"), 1), list.get(0));
	}

	@Test
	public void testAppendSize() {
		list.append(new Edge(new Node("1"), new Node("2"), 1));
		list.append(new Edge(new Node("2"), new Node("5"), 1));
		list.append(new Edge(new Node("5"), new Node("3"), 1));

		assertEquals(3, list.size());
	}

	@Test
	public void testRemoveSize() {
		try {
			list.append(new Edge(new Node("1"), new Node("2"), 1));
			list.prepend(new Edge(new Node("2"), new Node("5"), 1));
			list.append(new Edge(new Node("5"), new Node("3"), 1));
			list.remove(1);

			assertEquals(2, list.size());
		} catch (InvalidPositionException e) {
			fail();
		}
	}

	@Test
	public void testRemoveHead() {
		try {
			list.append(new Edge(new Node("1"), new Node("2"), 1));
			list.append(new Edge(new Node("2"), new Node("5"), 1));
			list.remove(0);

			EdgesLinkedList expected = new EdgesLinkedList();
			expected.append(new Edge(new Node("2"), new Node("5"), 1));

			assertEquals(expected.get(0), list.get(0));
		} catch (InvalidPositionException e) {
			fail();
		}
	}

	@Test
	public void testInsertList() {
		try {
			list.append(new Edge(new Node("1"), new Node("2"), 1));
			list.prepend(new Edge(new Node("1"), new Node("3"), 1));
			list.prepend(new Edge(new Node("5"), new Node("6"), 1));
			list.insert(2, (new Edge(new Node("3"), new Node("2"), 1)));
//			list.print();

			EdgesLinkedList expected = new EdgesLinkedList();
			expected.prepend((new Edge(new Node("3"), new Node("2"), 1)));

			assertEquals(expected.get(0), list.get(2));
		} catch (InvalidPositionException e) {
			fail();
		}
	}

	@Test
	public void testInsertEnd() {
		try {
			list.prepend(new Edge(new Node("1"), new Node("3"), 1));
			list.prepend(new Edge(new Node("5"), new Node("6"), 1));
			list.insert(2, (new Edge(new Node("3"), new Node("2"), 1)));

			EdgesLinkedList expected = new EdgesLinkedList();
			expected.prepend((new Edge(new Node("3"), new Node("2"), 1)));

			assertEquals(expected.get(0), list.get(2));
		} catch (InvalidPositionException e) {

		}
	}

	@Test
	public void testInsertEmpty() {
		assertThrows(InvalidPositionException.class, () -> list.insert(0, new Edge(new Node("9"), new Node("10"), 1)));
	}

	@Test
	public void testInsertLast() {
		list.append(new Edge(new Node("1"), new Node("2"), 1));
		list.append(new Edge(new Node("3"), new Node("4"), 1));

		assertThrows(InvalidPositionException.class, () -> list.insert(2, new Edge(new Node("9"), new Node("10"), 1)));
	}

	@Test
	public void testInsertNotEmpty() {
		list.append(new Edge(new Node("1"), new Node("2"), 1)); // 0
		list.append(new Edge(new Node("3"), new Node("4"), 1)); // 1
		System.out.println("Before: ");
		list.print();

		list.insert(1, new Edge(new Node("9"), new Node("10"), 1));
		System.out.println("After: ");
		list.print();

		assertEquals(new Edge(new Node("9"), new Node("10"), 1), list.get(1));
		assertEquals(new Edge(new Node("1"), new Node("2"), 1), list.get(0));
		assertEquals(new Edge(new Node("3"), new Node("4"), 1), list.get(2));
	}
}