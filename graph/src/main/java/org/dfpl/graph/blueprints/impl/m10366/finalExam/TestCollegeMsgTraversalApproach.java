package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;

public class TestCollegeMsgTraversalApproach {

	public static void main(String[] args) throws IOException {
		long beforeTime=System.currentTimeMillis();
		
		BufferedReader br = new BufferedReader(new FileReader("D://mydata/CollegeMsg-1000.txt"));
		
		MyTimeGraph graph=new MyTimeGraph();
		
		
		
		while(true) {
			
			String line = br.readLine();
			
			
			if (line == null)
				break;
			if (line.startsWith("#"))
				continue;
			
			
			
			String[] arr = line.split("\\s");
			
			TimeVertex sv=graph.addVertex(arr[0]);
			TimeVertex tv=graph.addVertex(arr[1]);
			
			TimeEdge edge=graph.addEdge(sv, tv, "contact");
			
			edge.addEvent(Long.valueOf(arr[2]));
			
		}
			
		
		br.close();
		
		
		for(MyEdgeEvent event:graph.getEdgeEventsToList()) {
			event.setProperty("property", "property");
		}
		
		
		String vertexID="1";
		

		
		TemporalReachabilityTraversalApproach trta=new TemporalReachabilityTraversalApproach(graph);
        
		
		
		HashSet<MyTimeVertex> visitedSet=trta.compute((MyTimeVertex)graph.getVertex(vertexID), Long.valueOf(0));
		
		
        
		System.out.println("vertex "+vertexID+" temporal reachability size : "+visitedSet.size()+" in CollegeMsg-1000.txt");
        
		long afterTime = System.currentTimeMillis();
		
		long secDiffTime = (afterTime - beforeTime);
		System.out.println("time(Millis) : "+secDiffTime);

	}

}
