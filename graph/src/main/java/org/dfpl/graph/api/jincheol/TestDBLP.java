package org.dfpl.graph.api.jincheol;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;


public class TestDBLP {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		
		
		
		String fileName = "D:\\mydata\\com-dblp.ungraph.txt";
        String delimiter = "\\s";
       
        
        BufferedReader r = new BufferedReader(new FileReader(fileName));

        
        Graph g=new TinkerGraph();
        
        
        
        UndirectedGraph ug=new UndirectedGraph(g); //알고리즘 
		
        int cnt = 0;
        while (true) {
            String line = r.readLine();
            if (line == null)
                break;
            if (line.startsWith("#"))
                continue;
            if (++cnt % 1000 == 0) {
                //System.out.println(cnt + " lines read...");
            }
            String[] arr = line.split(delimiter);
            String v1str = "s"+arr[0];
            String v2str = "s"+arr[1];
            
            
            Vertex v1=null;
            Vertex v2=null;
            
            

            
            if(g.getVertex(v1str)==null)
            	v1=g.addVertex(v1str);
            else 
            	v1=g.getVertex(v1str);
            
            	

            
            if(g.getVertex(v2str)==null) 
            	v2=g.addVertex(v2str);
            else 
            	v2=g.getVertex(v2str);
            
            
            
            
            
            
        	if(g.getEdge(v1str+"|"+"edge"+"|"+v2str)==null) 
            	g.addEdge(v1str+"|"+"edge"+"|"+v2str,v1, v2,v1str+"|"+"edge"+"|"+v2str);
            	
            
            
            if(g.getEdge(v2str+"|"+"edge"+"|"+v1str)==null) 
            	g.addEdge(v2str+"|"+"edge"+"|"+v1str,v2, v1,v2str+"|"+"edge"+"|"+v1str);
            	  
            
            
          
        }

        r.close();
        System.out.println("Data loaded");
		
		
		System.out.println("vertices Num : "+ug.getVerticesNum());
	
		System.out.println("edges Num : "+ug.getEdgesNum());
		
//		Iterable<Vertex> vertices=g.getVertices();
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
