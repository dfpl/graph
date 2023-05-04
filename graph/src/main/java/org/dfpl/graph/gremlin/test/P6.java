package org.dfpl.graph.gremlin.test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.branch.LoopPipe;
import com.tinkerpop.pipes.branch.LoopPipe.LoopBundle;

public class P6 {

	public static void main(String[] args) {
		Graph g = new TinkerGraph();
		Vertex v1 = g.addVertex("1");
		Vertex v2 = g.addVertex("2");
		Vertex v3 = g.addVertex("3");

		g.addEdge("1|2", v1, v2, "c");
		g.addEdge("1|3", v1, v3, "c");
		g.addEdge("2|1", v2, v1, "c");
		g.addEdge("3|1", v3, v1, "c");

		new GremlinPipeline<Vertex, Vertex>(v1).as("S").out()
				.loop("S", new PipeFunction<LoopPipe.LoopBundle<Vertex>, Boolean>() {
					@Override
					public Boolean compute(LoopBundle<Vertex> bundle) {
						System.out.println(bundle.getLoops() + " : " + bundle.getObject());
						if (bundle.getLoops() == 4)
							return false;
						return true;
					}
				}).toList().forEach(System.out::println);
	}

}
