package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;

public class TemporalReachabilityTimeCentricApproach {

	
	private MyTimeGraph graph;
	
	private Long startTime;
	
	private HashMap<String,HashMap<String,HashMap<Long,Long>>> gamma;
	
	private ArrayList<Long> timeList;
	
	
	public TemporalReachabilityTimeCentricApproach(MyTimeGraph graph,Long startTime) {
		
		this.graph=graph;
		
		this.startTime=startTime;
		
		this.gamma=new HashMap<>();
		
		this.setGamma();
		
		
		
	}
	
	public HashMap<String,HashMap<String,HashMap<Long,Long>>> getGamma(){
		return this.gamma;
	}
	
	public ArrayList<Long> getTimeList(){
		return this.timeList;
	}
	
	public void setGamma() {
		
		HashSet<Long> timeSet=new HashSet<>();
		
		ArrayList<MyEdgeEvent> edgeEvents=(ArrayList<MyEdgeEvent>) this.graph.getEdgeEventsToList();
		
		
		//1. hash set 으로 중복 없이 time 저장 후 time list에 정렬 저장  
		
		timeSet.add(this.startTime);
		
		for(MyEdgeEvent edgeEvent:edgeEvents) 
			if(edgeEvent.getTime()>this.startTime)
				timeSet.add(edgeEvent.getTime());
		
			
		
		this.timeList=new ArrayList<>(timeSet);
		
		//오름차순 정렬 
		Collections.sort(this.timeList);
		
		
		//2. max value로 초기화한 후 각 vertex에 대한 gamma table 초기화 
		
		//long max value로 초기화 

		for(TimeVertex vertex:this.graph.getVertices()) {
			
			HashMap<String,HashMap<Long,Long>> gammaVertex=new HashMap<>();
			
			for(TimeVertex v:this.graph.getVertices()) {
				
				HashMap<Long,Long> gammaTime=new HashMap<>();
				
				for(Long time:this.timeList) {
					gammaTime.put(time, Long.MAX_VALUE);
				}
				
				gammaVertex.put(v.getId(), gammaTime);
			}
			
			this.gamma.put(vertex.getId(), gammaVertex);
		}
		
	
		
		
		
		//3. 각 vertex의 시작 event time 값을 gamma table에서 업데이트
		for(String vertexID:this.gamma.keySet()) {
			
			for(String vID:this.gamma.get(vertexID).keySet()) {
				
				if(vertexID==vID) {
					for(Long t:this.gamma.get(vertexID).get(vID).keySet()) {
						this.gamma.get(vertexID).get(vID).put(t, this.startTime);
					}
					
				}
				
			}

		}
		
		
		
		
	}
	
	
	public void compute() {
		
		ArrayList<MyEdgeEvent> edgeEvents=(ArrayList<MyEdgeEvent>) this.graph.getEdgeEventsToList();


		
		for(String vertexID:this.gamma.keySet()) {
			
			
			for(Long t:this.timeList) {
				
				for(MyEdgeEvent edgeEvent:edgeEvents) {
					
					if(edgeEvent.getTime()==t&&this.gamma.get(vertexID).get(edgeEvent.getSourceID()).get(t)!=Long.MAX_VALUE&&this.gamma.get(vertexID).get(edgeEvent.getTargetID()).get(t)==Long.MAX_VALUE) {
						//시간이 같고
						//sourceID의 time key의 value 값이 설정되어있고
						//targetID의 time key의 value 값이 설정되어 있지 않으면 
						
						for(Long time:this.gamma.get(vertexID).get(edgeEvent.getTargetID()).keySet()) {
							
							if(time>=edgeEvent.getTime())
								this.gamma.get(vertexID).get(edgeEvent.getTargetID()).put(time, edgeEvent.getTime());
							
						}
						
						
					}
					
					
				}
	
			}
			
			
			
		}
		
		
		
		
	}
	
	public int getTemporalReachabilitySize(String vertexID) {
		
		
		Long lastTime=this.timeList.get(this.timeList.size()-1);
		
		int size=0;
		
		for(String vID:this.gamma.get(vertexID).keySet()) {
			
			if(this.gamma.get(vertexID).get(vID).get(lastTime)!=Long.MAX_VALUE)
				size+=1;

		}
		
		
		return size;
	}
	
	public HashMap<String,Long> getTemporalReachabilityToMap(String vertexID) {
		
		HashMap<String,Long> tr=new HashMap<>();
		
		Long lastTime=this.timeList.get(this.timeList.size()-1);
		
		for(String vID:this.gamma.get(vertexID).keySet()) {
			
			if(this.gamma.get(vertexID).get(vID).get(lastTime)!=Long.MAX_VALUE)
				tr.put(vID, this.gamma.get(vertexID).get(vID).get(lastTime));

		}
		

		return tr;
	}
	
	
}
