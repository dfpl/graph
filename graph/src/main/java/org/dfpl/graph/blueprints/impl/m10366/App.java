package org.dfpl.graph.blueprints.impl.m10366;


import java.io.IOException;
import java.util.HashMap;



import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;


public class App {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub

		//new MyPersistentGraph().shutdown();
		
		Graph g=new TinkerGraph();
        
        
        
        UndirectedGraph ug=new UndirectedGraph(g); //알고리즘 
		
        Vertex v1=g.addVertex("a");
        Vertex v2=g.addVertex("b");
        Vertex v3=g.addVertex("c");
        Vertex v4=g.addVertex("d");
        Vertex v5=g.addVertex("e");
        
        g.addEdge("a|edge|b", v1, v2, "edge");
        g.addEdge("a|edge|c", v1, v3, "edge");
        g.addEdge("a|edge|d", v1, v4, "edge");
        g.addEdge("b|edge|e", v2, v5, "edge");
        g.addEdge("c|edge|d", v3, v4, "edge");
        g.addEdge("d|edge|e", v4, v5, "edge");
        
        g.addEdge("b|edge|a", v2, v1, "edge");
        g.addEdge("c|edge|a", v3, v1, "edge");
        g.addEdge("d|edge|a", v4, v1, "edge");
        g.addEdge("e|edge|b", v5, v2, "edge");
        g.addEdge("d|edge|c", v4, v3, "edge");
        g.addEdge("e|edge|d", v5, v4, "edge");
        
//        Iterable<Vertex> vertices=g.getVertices();
//		for(Vertex v:vertices) {
//			
//			HashMap<String,Integer> distanceMap=ug.dijkstra(v);
//			
//			for(String key:distanceMap.keySet()) {
//				
//				System.out.println((String)v.getId()+" "+key+" "+distanceMap.get(key).toString());
//			}
//			
//			
//			
//		}
		
		
        HashMap<String,Double> cc=ug.closenessCentrality(ug.getVerticesNum());
		
		for(String id:cc.keySet())
			System.out.println(id+" "+cc.get(id).toString());
		
		
        
        
	}

}
