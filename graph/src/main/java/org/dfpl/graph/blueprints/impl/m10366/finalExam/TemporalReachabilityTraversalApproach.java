package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;

import com.tinkerpop.blueprints.revised.Direction;



public class TemporalReachabilityTraversalApproach {

	private MyTimeGraph graph;
	
	private HashMap<MyTimeVertex,Long> gamma;
	
	private HashMap<MyTimeVertex,Long> distance;
	
	private HashMap<MyTimeVertex,MyTimeVertex> parent;
	
	private HashSet<MyTimeVertex> visited;
	
	private ArrayList<MyTimeVertex> queue;
	
	
	public TemporalReachabilityTraversalApproach(MyTimeGraph graph) {
		this.graph=graph;
		
		this.gamma=new HashMap<>();
		this.distance=new HashMap<>();
		this.parent=new HashMap<>();
		
		this.visited=new HashSet<>();
		this.queue=new ArrayList<>();
	}
	
	
	public HashSet<MyTimeVertex> compute(MyTimeVertex source,Long startTime){
		
		
		//초기화
		this.graph.getVertex(source.getId()).addEvent(startTime);
		this.graph.getVertexEvents().get(source.getId()+"|"+startTime.toString()).setProperty("", "");
		
		
		//최소 방문 시간 관리
		this.gamma.put(source, startTime);
		
		//소스로부터의 hop 관리
		this.distance.put(source, Long.valueOf(0));
		
		//각 vertex의 부모 vertex 관리 
		this.parent.put(source, null);
		
		//방문한 vertex 관리 
		this.visited.add(source);
		
		//큐가 비면 종료
		this.queue.add(source);
		
		
		
		
		//queue가 빌 때 까지
		while(!this.queue.isEmpty()) {
			
			MyTimeVertex outV=this.queue.remove(0);
			ArrayList<TimeEdge> outEList=(ArrayList<TimeEdge>) outV.getEdges(Direction.OUT);
			
			for(TimeEdge outE:outEList) {
				
				MyTimeVertex inV=(MyTimeVertex) outE.getVertex(Direction.IN);
				
				if(!this.visited.contains(inV)) {
					//아직 방문하지 않았다면 
					
					MyEdgeEvent outEe=(MyEdgeEvent) outE.pickEvent(startTime);
					
					
					
					if(this.gamma.get(inV)==null||outEe.getTime()<this.gamma.get(inV)) {
						
						
						this.distance.put(inV, this.distance.get(outV)+1);
						this.gamma.put(inV, outEe.getTime());
						this.parent.put(inV, outV);
						
						if(!this.queue.contains(inV)) {
							this.visited.add(inV);
							this.queue.add(inV);
						}
						
						
					}
					
					
					
				}
				

			}
			
			
		}
		
		
		
		
		
		return this.visited;
	}
	
}
