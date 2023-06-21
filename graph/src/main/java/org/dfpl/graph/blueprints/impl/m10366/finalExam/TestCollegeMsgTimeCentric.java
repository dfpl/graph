package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;


public class TestCollegeMsgTimeCentric {

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
		
		
		
		
		TemporalReachabilityTimeCentricApproach ta=new TemporalReachabilityTimeCentricApproach(graph,Long.valueOf(0));
        
		ta.compute();
		
		String vertexID="1";
        
		System.out.println("vertex "+vertexID+" temporal reachability size : "+ta.getTemporalReachabilitySize(vertexID)+" in CollegeMsg-1000.txt");
        
		long afterTime = System.currentTimeMillis();
		
		long secDiffTime = (afterTime - beforeTime);
		System.out.println("time(Millis) : "+secDiffTime);
        
		
	}

}
