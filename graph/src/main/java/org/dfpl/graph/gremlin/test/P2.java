package org.dfpl.graph.gremlin.test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.gremlin.java.GremlinPipeline;

public class P2 {

	public static void main(String[] args) {
		Graph g = new TinkerGraph();
		Vertex v1 = g.addVertex("1");
		Vertex v2 = g.addVertex("2");
		Vertex v3 = g.addVertex("3");

		g.addEdge("1|2", v1, v2, "c");
		g.addEdge("1|3", v1, v3, "c");
		g.addEdge("2|1", v2, v1, "c");
		g.addEdge("3|1", v3, v1, "c");

		for (Vertex v : g.getVertices()) {
			System.out.println(v);
		}
		for (Edge e : g.getEdges()) {
			System.out.println(e);
		}
		new GremlinPipeline<Vertex, Vertex>(v1).out().out().out().out().dedup().toList().forEach(System.out::println);
	}

}
