package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.TreeMap;

import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;

public class TemporalReachabilityTimeCentricApproach {

	private MyTimeGraph graph;

	private Long startTime;

	private HashMap<String, HashMap<String, Long>> gamma;

	
	private TreeMap<Long, ArrayList<MyEdgeEvent>> sortedEdgeEvent;

	public TemporalReachabilityTimeCentricApproach(MyTimeGraph graph, Long startTime) {

		this.graph = graph;
		this.startTime = startTime;
		this.gamma = new HashMap<>();
		this.sortedEdgeEvent=new TreeMap<>();
		this.initializeGamma();

	}

	public HashMap<String, HashMap<String, Long>> getGamma() {
		return this.gamma;
	}

	
	public TreeMap<Long, ArrayList<MyEdgeEvent>> getSortedEdgeEvent(){
		return this.sortedEdgeEvent;
	}
	

	public void initializeGamma() {


		ArrayList<MyEdgeEvent> edgeEvents = (ArrayList<MyEdgeEvent>) this.graph.getEdgeEventsToList();
		
		

		// 1. tree map으로 time에 따라 정렬하여 edge event들 저장 
		for(MyEdgeEvent edgeEvent:edgeEvents) {
			
			if(this.sortedEdgeEvent.containsKey(edgeEvent.getTime())) {
				this.sortedEdgeEvent.get(edgeEvent.getTime()).add(edgeEvent);
				continue;
			}
				
			ArrayList<MyEdgeEvent> value=new ArrayList<>();
			
			value.add(edgeEvent);
			
			this.sortedEdgeEvent.put(edgeEvent.getTime(), value);
		}
		
		


		// 2. 각 vertex에 대한 gamma table 초기화
		for (TimeVertex vertex : this.graph.getVertices()) {

			HashMap<String, Long> gammaVertex = new HashMap<String, Long>();

			gammaVertex.put(vertex.getId(), startTime);

			this.gamma.put(vertex.getId(), gammaVertex);
		}

	}

	
	

	public void compute() {

	 
		//time으로 정렬된 edge event들을 순회하면서 gamma table update
		for(String vertexID:this.gamma.keySet()) {
			
			for(Long time:this.sortedEdgeEvent.keySet()) {
				
				for(MyEdgeEvent edgeEvent:this.sortedEdgeEvent.get(time)) {
					
					if(this.gamma.get(vertexID).containsKey(edgeEvent.getSourceID()) && !this.gamma.get(vertexID).containsKey(edgeEvent.getTargetID())) {
						
						this.gamma.get(vertexID).put(edgeEvent.getTargetID(), time);
					}
						
				}
				
			}
			
		}
		
		

	}


	public int getTemporalReachabilitySize(String vertexID) {

		return this.gamma.get(vertexID).keySet().size();
	}

	

}
