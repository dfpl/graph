package org.dfpl.graph.gremlin.test;

import java.util.ArrayList;
import java.util.List;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;

public class P5 {

	public static void main(String[] args) {
		Graph g = new TinkerGraph();
		Vertex v1 = g.addVertex("1");
		Vertex v2 = g.addVertex("2");
		Vertex v3 = g.addVertex("3");
		Vertex v4 = g.addVertex("4");
		Vertex v5 = g.addVertex("5");
		Vertex v6 = g.addVertex("6");
		Vertex v7 = g.addVertex("7");
		Vertex v8 = g.addVertex("8");
		Vertex v9 = g.addVertex("9");
		Vertex v10 = g.addVertex("10");
		Vertex v11 = g.addVertex("11");
		Vertex v12 = g.addVertex("12");
		Vertex v13 = g.addVertex("13");
		g.addEdge("1|2", v1, v2, "c");
		g.addEdge("1|3", v1, v3, "c");
		g.addEdge("2|4", v2, v4, "c");
		g.addEdge("2|5", v2, v5, "c");
		g.addEdge("3|6", v3, v6, "c");
		g.addEdge("3|7", v3, v7, "c");
		g.addEdge("10|8", v10, v8, "c");
		g.addEdge("11|8", v11, v8, "c");

		v1.setProperty("isOdd", true);
		v3.setProperty("isOdd", true);
		v5.setProperty("isOdd", true);
		v7.setProperty("isOdd", true);
		v9.setProperty("isOdd", true);
		v11.setProperty("isOdd", true);
		v13.setProperty("isOdd", true);

		v2.setProperty("isOdd", false);
		v4.setProperty("isOdd", false);
		v6.setProperty("isOdd", false);
		v8.setProperty("isOdd", false);
		v10.setProperty("isOdd", false);
		v12.setProperty("isOdd", false);

		g.addEdge("12|9", v12, v9, "c");
		g.addEdge("13|9", v13, v9, "c");
		g.addEdge("8|1", v8, v1, "c");
		g.addEdge("9|1", v9, v1, "c");

		for (Vertex v : g.getVertices()) {
			System.out.println(v);
		}
		for (Edge e : g.getEdges()) {
			System.out.println(e);
		}

		new GremlinPipeline<Graph, Vertex>(g).V().ifThenElse(new PipeFunction<Vertex, Boolean>() {

			@Override
			public Boolean compute(Vertex argument) {
				if ((boolean) argument.getProperty("isOdd") == true)
					return true;
				else
					return false;
			}
		}, new PipeFunction<Vertex, List<Vertex>>() {

			@Override
			public List<Vertex> compute(Vertex argument) {
				List<Vertex> result = new ArrayList<Vertex>();
				for (Vertex v : argument.getVertices(Direction.OUT)) {
					result.add(v);
				}
				return result;
			}
		}, new PipeFunction<Vertex, List<Vertex>>() {

			@Override
			public List<Vertex> compute(Vertex argument) {
				List<Vertex> result = new ArrayList<Vertex>();
				for (Vertex v : argument.getVertices(Direction.IN)) {
					result.add(v);
				}
				return result;
			}
		}).scatter().toList().forEach(System.out::println);
	}

}
