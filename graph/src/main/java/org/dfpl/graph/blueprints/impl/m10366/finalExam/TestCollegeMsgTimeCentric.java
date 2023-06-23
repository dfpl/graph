package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;


public class TestCollegeMsgTimeCentric {

	public static void main(String[] args) throws IOException {
		
		
		long beforeTime=System.currentTimeMillis();
		
		BufferedReader br = new BufferedReader(new FileReader("D://mydata/CollegeMsg-1000.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("D://mydata/reachability_timecentric_approach.txt"));
		
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
		
		
		
		
		for(TimeVertex vertex:graph.getVertices()) {
			
			bw.write(vertex.getId()+"	"+String.valueOf(ta.getTemporalReachabilitySize(vertex.getId()))+"	"+String.valueOf( (System.currentTimeMillis() - beforeTime)));
			bw.newLine();
			
			
			//System.out.println("vertex "+vertex.getId()+" temporal reachability size : "+ta.getTemporalReachabilitySize(vertex.getId())+" in CollegeMsg-1000.txt");
		}
		
		
		bw.close();
		
        
		
        
		
		
		
		
		
		
        
		
	}

}
