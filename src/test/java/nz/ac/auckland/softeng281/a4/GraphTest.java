package nz.ac.auckland.softeng281.a4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class GraphTest {

	public static class GraphUnitTest {
		Graph graph;

		@Before
		public void setUp() throws Exception {
			List<String> edges = Arrays.asList("1,2", "2,3", "2,4", "4,2");
			List<String> weights = Arrays.asList("10", "20", "30", "20");
			graph = new Graph(edges, weights);
		}

		@Test
		public void testFindNode() {
			assertTrue(graph.isNodeInGraph(new Node("1")));
			assertTrue(graph.isNodeInGraph(new Node("2")));
			assertTrue(graph.isNodeInGraph(new Node("3")));
			assertTrue(graph.isNodeInGraph(new Node("4")));
			assertFalse(graph.isNodeInGraph(new Node("5")));
		}

		@Test
		public void testFindNodeEmpty() {
			assertFalse(graph.isNodeInGraph(new Node(null)));
		}

		@Test
		public void testShortestPath() {
			List<String> edges = Arrays.asList("1,2", "2,3", "3,4", "4,5");
			List<String> weights = Arrays.asList("10", "20", "30", "20");
			graph = new Graph(edges, weights);
			Path path = new Path(80, new Node("1"), new Node("2"), new Node("3"), new Node("4"), new Node("5"));
			assertEquals(path, graph.computeShortestPath(new Node("1"), new Node("5")));
		}
		
		@Test
		public void testShortestPath2() {
			List<String> edges = Arrays.asList("1,2", "2,3", "2,4", "4,2", "4,3", "4,5", "5,1", "1,5", "5,4");
			List<String> weights = Arrays.asList("10", "30", "30", "20", "10", "50", "60", "15", "5");
			graph = new Graph(edges, weights);
			Path path = new Path(30, new Node("1"), new Node("5"), new Node("4"), new Node("3"));
			assertEquals(path, graph.computeShortestPath(new Node("1"), new Node("3")));
		}

		@Test
		public void testSearchEdgeByWeight1() {
			Edge expected = new Edge(new Node("1"), new Node("2"), 10);
			assertEquals(expected, graph.searchEdgeByWeight(10));
		}

		@Test
		public void testSearchEdgeByWeight2() {
			Edge expected2 = new Edge(new Node("2"), new Node("4"), 30);
			assertEquals(expected2, graph.searchEdgeByWeight(30));
		}

		@Test
		public void testSearchEdgeByWeight3() {
			assertNull(graph.searchEdgeByWeight(15));
		}

		@Test
		public void testSearchEdgeByWeightAll() {
			Edge expected = new Edge(new Node("1"), new Node("2"), 10);
			assertEquals(expected, graph.searchEdgeByWeight(10));
			Edge expected2 = new Edge(new Node("2"), new Node("3"), 20);
			assertEquals(expected2, graph.searchEdgeByWeight(20));
			Edge expected3 = new Edge(new Node("2"), new Node("4"), 30);
			assertEquals(expected3, graph.searchEdgeByWeight(30));
			Edge expected4 = new Edge(new Node("2"), new Node("3"), 20);
			assertEquals(expected4, graph.searchEdgeByWeight(20));
			assertNull(graph.searchEdgeByWeight(15));
		}

		@Test
		public void testSearchWeightByEdge1() {
			assertEquals(10, graph.searchWeightByEdge(new Node("1"), new Node("2")));
		}

		@Test
		public void testSearchWeightByEdge2() {
			assertEquals(20, graph.searchWeightByEdge(new Node("2"), new Node("3")));
		}

		@Test
		public void testSearchWeightByEdge3() {
			assertEquals(-1, graph.searchWeightByEdge(new Node("5"), new Node("6")));
		}

		@Test
		public void testSearchWeightByEdgeAll() {
			assertEquals(10, graph.searchWeightByEdge(new Node("1"), new Node("2")));
			assertEquals(20, graph.searchWeightByEdge(new Node("2"), new Node("3")));
			assertEquals(30, graph.searchWeightByEdge(new Node("2"), new Node("4")));
			assertEquals(20, graph.searchWeightByEdge(new Node("4"), new Node("2")));
			assertEquals(-1, graph.searchWeightByEdge(new Node("5"), new Node("6")));
		}

		@Test
		public void testSearchWeightByEdgePrint() {
			List<String> edges = Arrays.asList("1,2", "2,3", "2,4", "4,2", "4,3", "4,5", "5,1");
			List<String> weights = Arrays.asList("10", "20", "30", "20", "40", "50", "60");
			graph = new Graph(edges, weights);
			assertEquals(-1, graph.searchWeightByEdge(new Node("5"), new Node("6")));
		}

		public static class GraphSystemTest {

			ByteArrayOutputStream myOut;
			PrintStream origOut;

			@Before
			public void setUp() {
				origOut = System.out;
				myOut = new ByteArrayOutputStream();
				System.setOut(new PrintStream(myOut));
			}

			@After
			public void tearDown() {
				System.setOut(origOut);
				if (myOut.toString().length() > 1) {
					System.out.println(System.lineSeparator() + "the System.out.print was :" + System.lineSeparator()
							+ myOut.toString());
				}
			}

			private void runTest(String fileName, String command) {
				GraphUI.scanner = new Scanner("open " + fileName + System.getProperty("line.separator") + command
						+ System.getProperty("line.separator") + "exit" + System.getProperty("line.separator"));
				GraphControl controller = new GraphControl();
				controller.execute();
			}

			@Test
			public void testSearchWeightA() {
				runTest("a.txt", "search 1 3");
				assertTrue(myOut.toString().contains("Given the edge from source 1 target 3 has weight: 5"));
			}

			@Test
			public void testSearchWeightB() {
				runTest("g7.txt", "search 5 6");
				assertTrue(myOut.toString().contains("Given the edge from source 5 target 6 has weight: 2"));
			}

			@Test
			public void testSearchEdgeByWeightA() {
				runTest("a.txt", "search 5");
				assertTrue(myOut.toString().contains("The edge searched having weight 5 is: 1-->3"));
			}

			@Test
			public void testSearchEdgeByWeightB() {
				runTest("a.txt", "search 2");
				assertTrue(myOut.toString().contains("The edge searched having weight 2 is: 0-->1"));
			}

			@Test
			public void testShortestPathA() {
				runTest("a.txt", "path 5 1");
				assertTrue(myOut.toString().contains("The shortest path is: 5 -> 4 -> 1 cost: 4"));
			}
			
			@Test
			public void testShortestPathB() {
				runTest("g7.txt", "path 1 4");
				assertTrue(myOut.toString().contains("The shortest path is: 1 -> 7 -> 4 cost: 5"));
			}
			
			@Test
			public void testShortestPathC() {
				runTest("a.txt", "path 5 3");
				assertTrue(myOut.toString().contains("The shortest path is: 5 -> 3 cost: 2"));
			}

		}
	}
}
