package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.HashMap;

import org.dfpl.graph.blueprints.impl.m10366.custom.EdgeEvent;
import org.dfpl.graph.blueprints.impl.m10366.custom.TemporalRelation;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;
import org.dfpl.graph.blueprints.impl.m10366.custom.VertexEvent;


import com.tinkerpop.blueprints.revised.Direction;

public class TestApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MyTimeGraph g=new MyTimeGraph();
		
		TimeVertex a= g.addVertex("a");
        TimeVertex b=g.addVertex("b");
        TimeVertex c=g.addVertex("c");
        TimeVertex d= g.addVertex("d");
        TimeVertex e=g.addVertex("e");
        
        TimeEdge ad=g.addEdge(a, d, "contact");
        TimeEdge ab=g.addEdge(a, b, "contact");
        
        TimeEdge bc=g.addEdge(b, c, "contact");
        TimeEdge bd=g.addEdge(b, d, "contact");
        
        TimeEdge cd=g.addEdge(c, d, "contact");
        TimeEdge ce=g.addEdge(c, e, "contact");
       
        
        //add events
        EdgeEvent adt5=ad.addEvent(5);
        EdgeEvent abt10=ab.addEvent(10);
        
        EdgeEvent bct8=bc.addEvent(8);
        EdgeEvent bdt12=bd.addEvent(12);
        EdgeEvent bct16=bc.addEvent(16);
        
        EdgeEvent cdt13=cd.addEvent(13);
        EdgeEvent cet14=ce.addEvent(14);
        

        
        
        VertexEvent at3=a.addEvent(3);
        
        TemporalReachabilityTimeCentricApproach ta=new TemporalReachabilityTimeCentricApproach(g,Long.valueOf(3));
        
        ta.compute();
    
//        for(String key:ta.getGamma().keySet()) {
//        	
//        	for(String key2:ta.getGamma().get(key).keySet()) {
//        		
//        		
//        		System.out.println(key+" gamma table "+key2+" vertex table timelist = "+ta.getGamma().get(key).get(key2));
//        		
//        	}
//        	
//        	
//        }
        
        
        System.out.println(ta.getTemporalReachabilityToMap("a"));
        
	}

}
