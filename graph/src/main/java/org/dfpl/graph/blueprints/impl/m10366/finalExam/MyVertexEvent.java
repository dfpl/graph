package org.dfpl.graph.blueprints.impl.m10366.finalExam;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.dfpl.graph.blueprints.impl.m10366.custom.EdgeEvent;
import org.dfpl.graph.blueprints.impl.m10366.custom.TemporalRelation;

import org.dfpl.graph.blueprints.impl.m10366.custom.VertexEvent;

import com.tinkerpop.blueprints.revised.Direction;

public class MyVertexEvent implements VertexEvent{

	
	private MyTimeGraph graph;
	
	private String id;
	
	private String vertexID;
	
	private long time;
	
	
	
	private HashMap<String,Object> property;
	
	
	public MyVertexEvent(MyTimeGraph graph,String vertexID,long time){
		
		this.graph=graph;
		this.id=vertexID+"|"+String.valueOf(time);
		this.vertexID=vertexID;
		this.time=time;
		this.property=new HashMap<>();
		
	}
	
	
	
	
	@Override
	public String toString() {
		return this.id;
	}




	public String getID() {
		return this.id;
	}
	
	public String getVertexID() {
		return this.vertexID;
	}
	
	public long getTime() {
		return this.time;
	}
	
	
	@Override
	public Collection<VertexEvent> getVertexEvents(TemporalRelation r) {
		
		
		//1. source vertex id가 같은 edge event들을 저장 
		ArrayList<MyEdgeEvent> validEvents=new ArrayList<>();
		
		for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {	
			if(event.getSourceID()==this.vertexID&&event.getTime()>this.time)
				validEvents.add(event);
		}
		
		
		//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
		Collections.sort(validEvents,new MyEdgeEventComparator());
		
		//3. hash set을 사용하여 가장 빨리 도달가능한 vertex event 들을 저장 
		HashSet<VertexEvent> validEventSet=new HashSet<>();
		
		for(MyEdgeEvent event:validEvents) {
			if(!validEventSet.contains(event.getVertexEvent(Direction.OUT))) {
				validEventSet.add(event.getVertexEvent(Direction.OUT));
			}
		}
		//4. array list 로 변환하여 반환 
		return new ArrayList<>(validEventSet);
	}

	@Override
	public Map<String, Object> getProperties() {
		
		return property;
	}

	@Override
	public void setProperty(String key, Object value) {
		
		if(this.property.containsKey(key))
			System.out.println("Already exist");
		else
			this.property.put(key, value);
	}

	@Override
	public Object removeProperty(String key) {

		return this.property.remove(key);

	}

	@Override
	public Collection<EdgeEvent> getEdgeEvents(Direction d, TemporalRelation r,String...labels) {
		
		
		
		ArrayList<MyEdgeEvent> validEvents=new ArrayList<>();
		
		HashSet<String> set=new HashSet<>();
		
		HashSet<EdgeEvent> returnSet=new HashSet<>();
		
		if(labels.length==0) {
			
			if(d==Direction.OUT) {
				
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {	
					if(event.getSourceID()==this.vertexID&&event.getTime()>this.time)
						validEvents.add(event);
				}
				
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent event:validEvents) {
					if(!set.contains(event.getTargetID())) {
						set.add(event.getTargetID());
						returnSet.add(event);
					}
				}
				
				
				
			}
			else if(d==Direction.IN) {
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {	
					if(event.getTargetID()==this.vertexID&&event.getTime()>this.time)
						validEvents.add(event);
				}
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent event:validEvents) {
					if(!set.contains(event.getSourceID())) {
						set.add(event.getSourceID());
						returnSet.add(event);
					}
				}
				
				
			}
			else {
				
				
			}
		
			
			
			
		}
		else {
			
			
			
			
			if(d==Direction.OUT) {
				
				//1. label이 일치하고유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {
					for(String label:labels) {
						if(event.getSourceID()==this.vertexID &&event.getLabel()==label&&event.getTime()>this.time)
							validEvents.add(event);
					}
					
				}
				
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent event:validEvents) {
					if(!set.contains(event.getTargetID())) {
						set.add(event.getTargetID());
						returnSet.add(event);
					}
				}
				
				
				
			}
			else if(d==Direction.IN) {
				
				
				//1. label이 일치하고유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {
					for(String label:labels) {
						if(event.getTargetID()==this.vertexID &&event.getLabel()==label&&event.getTime()>this.time)
							validEvents.add(event);
					}
					
				}
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent event:validEvents) {
					if(!set.contains(event.getSourceID())) {
						set.add(event.getSourceID());
						returnSet.add(event);
					}
				}
				
				
			}
			else {
				
				
			}
			
			
			
					
			
			
		}
		
		
		return new ArrayList<>(returnSet);
		
	}

	@Override
	public Collection<VertexEvent> getVertexEvents(Direction d, TemporalRelation r,String...labels) {
		
		
		ArrayList<MyEdgeEvent> validEvents=new ArrayList<>();
		
		HashSet<VertexEvent> set=new HashSet<>();
		
		
		
		if(labels.length==0) {
			
			if(d==Direction.OUT) {
				
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {	
					if(event.getSourceID()==this.vertexID&&event.getTime()>this.time)
						validEvents.add(event);
				}
				
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent event:validEvents) {
					if(!set.contains(event.getVertexEvent(Direction.OUT))) {
						set.add( event.getVertexEvent(Direction.OUT));
						
					}
				}
				
				
				
			}
			else if(d==Direction.IN) {
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {	
					if(event.getTargetID()==this.vertexID&&event.getTime()>this.time)
						validEvents.add(event);
				}
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent event:validEvents) {
					if(!set.contains(event.getVertexEvent(Direction.IN))) {
						set.add(event.getVertexEvent(Direction.IN));
						
					}
				}
				
				
			}
			else {
				
				throw new IllegalArgumentException("not use both");
			}
			
			
		}
		else {
			
			if(d==Direction.OUT) {
				
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {
					for(String label:labels) 
						if(event.getSourceID()==this.vertexID&&event.getLabel()==label&&event.getTime()>this.time)
							validEvents.add(event);
				}
				
				
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent event:validEvents) {
					if(!set.contains(event.getVertexEvent(Direction.OUT))) {
						set.add( event.getVertexEvent(Direction.OUT));
						
					}
				}
				
				
				
			}
			else if(d==Direction.IN) {
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {	
					for(String label:labels) 
						if(event.getTargetID()==this.vertexID&&event.getLabel()==label&&event.getTime()>this.time)
							validEvents.add(event);
				}
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent event:validEvents) {
					if(!set.contains(event.getVertexEvent(Direction.IN))) {
						set.add(event.getVertexEvent(Direction.IN));
						
					}
				}
				
				
			}
			else {
				
				throw new IllegalArgumentException("not use both");
			}
			
			
		}
		
		return new ArrayList<>(set);
	}

}
