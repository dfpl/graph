package org.dfpl.graph.api.assignment;

import org.dfpl.graph.api.jincheol.MyGraphInMemory;
import org.dfpl.graph.api.memory.InMemoryGraph;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class UnitTestJincheol {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		String dbID = "root";
		String dbPW = "1234";
		String dbName = "team?";

		//Graph g = new InMemoryGraph();
		// Graph g = new PersistentGraph(dbID, dbPW, dbName);
		Graph g = new MyGraphInMemory();

		Vertex v1 = g.addVertex("1");
		System.out.println("[1] " + v1.getId());
		v1 = g.addVertex("1");
		System.out.println("[2] " + v1.getId());
		System.out.println("[3] " + g.getVertices().size());
		System.out.println("[4] " + g.getVertex("1").getId());
		try {
			g.addVertex("a|b");
		} catch (IllegalArgumentException e) {
			System.out.println("[5] " + e.getMessage());
		}
		System.out.println("[6] " + g.getVertex("2"));
		Vertex v2 = g.addVertex("2");
		System.out.println("[7] " + g.getVertices().size());
		v1.setProperty("k1", "v1");
		v1.setProperty("k2", true);
		v1.setProperty("k3", 3);
		v1.setProperty("k4", 4.5);
		System.out.println("[8] " + v1.getPropertyKeys().size());
		System.out.println("[9] " + (v1.getProperty("k0") == null));
		System.out.println("[10] " + v1.getProperty("k1").equals("v2"));
		System.out.println("[11] " + ((boolean) v1.getProperty("k2") == true));
		System.out.println("[12] " + ((int) v1.getProperty("k3") == 4));
		System.out.println("[13] " + ((double) v1.getProperty("k4") == 4.5));
		System.out.println("[14] " + g.getVertices("k1", "v1").size());
		Edge e1l2 = g.addEdge(v1, v2, "l");
		System.out.println("[15] " + e1l2.getId());
		System.out.println("[16] " + g.getEdges().size());
		try {
			g.addEdge(v1, null, "l");
		} catch (NullPointerException e) {
			System.out.println("[17] " + e.getMessage());
		}
		try {
			g.addEdge(v1, v2, "l|l");
		} catch (IllegalArgumentException e) {
			System.out.println("[18] " + e.getMessage());
		}
		e1l2 = g.addEdge(v1, v2, "l");
		System.out.println("[19] " + g.getEdges().size());
		g.addEdge(v1, v2, "k");
		g.addEdge(v1, v1, "l");
		System.out.println("[20] " + g.getEdges().size());
		e1l2.setProperty("k1", "v1");
		e1l2.setProperty("k2", true);
		e1l2.setProperty("k3", 3);
		e1l2.setProperty("k4", 4.5);
		System.out.println("[21] " + e1l2.getPropertyKeys().size());
		System.out.println("[22] " + (e1l2.getProperty("k0") != null));
		System.out.println("[23] " + e1l2.getProperty("k1").equals("v1"));
		System.out.println("[24] " + ((boolean) e1l2.getProperty("k2") == false));
		System.out.println("[25] " + ((int) e1l2.getProperty("k3") == 3));
		System.out.println("[26] " + ((double) e1l2.getProperty("k4") == 4));
		System.out.println("[27] " + g.getEdges("k1", "v1").size());

		Vertex a = g.addVertex("a");
		Vertex b = g.addVertex("b");
		Vertex c = g.addVertex("c");
		Vertex d = g.addVertex("d");
		Vertex e = g.addVertex("e");

		Edge ab = g.addEdge(a, b, "l");
		Edge ac = g.addEdge(a, c, "l");
		Edge da = g.addEdge(d, a, "l");
		Edge ea = g.addEdge(e, a, "l");

		System.out.println("[28] " + a.getEdges(Direction.OUT).contains(ab));
		System.out.println("[29] " + a.getEdges(Direction.OUT).contains(ac));
		System.out.println("[30] " + a.getEdges(Direction.OUT).contains(da));
		System.out.println("[31] " + a.getEdges(Direction.OUT, "l").contains(ac));
		System.out.println("[32] " + a.getEdges(Direction.OUT, "k").contains(ac));
		System.out.println("[33] " + a.getEdges(Direction.OUT, "l", "k").contains(ac));
		System.out.println("[34] " + a.getEdges(Direction.IN).contains(ab));
		System.out.println("[35] " + a.getEdges(Direction.IN).contains(ac));
		System.out.println("[36] " + a.getEdges(Direction.IN).contains(ea));
		System.out.println("[37] " + a.getVertices(Direction.OUT).contains(b));
		System.out.println("[38] " + a.getVertices(Direction.OUT).contains(c));
		System.out.println("[39] " + a.getVertices(Direction.OUT).contains(d));
		System.out.println("[40] " + a.getVertices(Direction.OUT, "l").contains(b));
		System.out.println("[41] " + a.getVertices(Direction.OUT, "k").contains(b));
		System.out.println("[42] " + a.getVertices(Direction.OUT, "l", "k").contains(b));
		System.out.println("[43] " + a.getVertices(Direction.IN).contains(b));
		System.out.println("[44] " + a.getVertices(Direction.IN).contains(c));
		System.out.println("[45] " + a.getVertices(Direction.IN).contains(e));

		Vertex s = g.addVertex("s");
		Edge ss = g.addEdge(s, s, "l");
		System.out.println("[46] " + s.getEdges(Direction.OUT).contains(ss));
		System.out.println("[47] " + s.getEdges(Direction.IN).contains(ss));
		System.out.println("[48] " + s.getEdges(Direction.IN).contains(ab));
		try {
			System.out.println("[49] " + s.getEdges(Direction.BOTH).size());
		} catch (IllegalArgumentException err) {
			System.out.println("[49] " + err.getMessage());
		}

		try {
			System.out.println("[50] " + ab.getVertex(Direction.BOTH));
		} catch (IllegalArgumentException err) {
			System.out.println("[50] " + err.getMessage());
		}

	}
}
