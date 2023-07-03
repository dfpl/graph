package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.dfpl.graph.blueprints.impl.m10366.custom.EdgeEvent;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;
import org.dfpl.graph.blueprints.impl.m10366.custom.VertexEvent;

public class TestApp {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MyTimeGraph g = new MyTimeGraph();

		TimeVertex a = g.addVertex("a");
		TimeVertex b = g.addVertex("b");
		TimeVertex c = g.addVertex("c");
		TimeVertex d = g.addVertex("d");
		TimeVertex e = g.addVertex("e");

		TimeEdge ad = g.addEdge(a, d, "contact");
		TimeEdge ab = g.addEdge(a, b, "contact");

		TimeEdge bc = g.addEdge(b, c, "contact");
		TimeEdge bd = g.addEdge(b, d, "contact");

		TimeEdge cd = g.addEdge(c, d, "contact");
		TimeEdge ce = g.addEdge(c, e, "contact");

		// add events
		EdgeEvent adt5 = ad.addEvent(5);
		EdgeEvent abt10 = ab.addEvent(10);

		EdgeEvent bct8 = bc.addEvent(8);
		EdgeEvent bdt16 = bd.addEvent(16);

		EdgeEvent cet14 = ce.addEvent(14);
		
		TimeEdge da=g.getEdge(d,a,"contact");
		EdgeEvent dat12=da.addEvent(12);
		
		TimeEdge dc=g.getEdge(d,c,"contact");
		EdgeEvent dct13=dc.addEvent(13);

		VertexEvent at3 = a.addEvent(3);

		
		
		
		TemporalReachabilityTimeCentricApproach ta = new TemporalReachabilityTimeCentricApproach(g, Long.valueOf(3));

		ta.compute();

		
		
		
		

		System.out.println(ta.getTemporalPath("a", "d"));

	}

}
