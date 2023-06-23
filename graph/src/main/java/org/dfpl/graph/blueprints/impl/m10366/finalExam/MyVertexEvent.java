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
		
		
		ArrayList<MyEdgeEvent> validEvents=new ArrayList<>();
		
		HashSet<String> idSet=new HashSet<>();
		
		ArrayList<VertexEvent> returnVertexEvents=new ArrayList<>();
		
		
		//1. 유효한 event들을 validEvents에 저장
		for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {	
			if(event.getSourceID()==this.vertexID&&event.getTime()>this.time)
				validEvents.add(event);
		}
		
		
		//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
		Collections.sort(validEvents,new MyEdgeEventComparator());
		
		
		//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
		for(MyEdgeEvent validEvent:validEvents) {
			
			if(!idSet.contains(validEvent.getTargetID())) {
				idSet.add(validEvent.getTargetID());
				returnVertexEvents.add(validEvent.getVertexEvent(Direction.OUT));
			}
		}
		
		return returnVertexEvents;
		
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
		
		ArrayList<EdgeEvent> returnEdgeEvents=new ArrayList<>();
		
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
						returnEdgeEvents.add(event);
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
						returnEdgeEvents.add(event);
					}
				}
				
				
			}
			else {
				
				
				
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {	
					if((event.getSourceID()==this.vertexID||event.getTargetID()==this.vertexID)&&event.getTime()>this.time)
						validEvents.add(event);
				}
				
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				
				HashSet<String> sourceIdSet=new HashSet<>();
				HashSet<String> targetIdSet=new HashSet<>();
				
				for(MyEdgeEvent validEvent:validEvents) {
					
					if(validEvent.getSourceID()==this.vertexID) {
						if(!targetIdSet.contains(validEvent.getTargetID())) {
							targetIdSet.add(validEvent.getTargetID());
							returnEdgeEvents.add(validEvent);
						}
					}
					else {
						if(!sourceIdSet.contains(validEvent.getSourceID())) {
							sourceIdSet.add(validEvent.getSourceID());
							returnEdgeEvents.add(validEvent);
						}
					}
					
					
					
				}
				
				
				
				
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
						returnEdgeEvents.add(event);
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
						returnEdgeEvents.add(event);
					}
				}
				
				
			}
			else {
				
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {
					for(String label:labels) 
						if((event.getSourceID()==this.vertexID||event.getTargetID()==this.vertexID)&&event.getTime()>this.time&&event.getLabel()==label)
							validEvents.add(event);
				}
				
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				
				HashSet<String> sourceIdSet=new HashSet<>();
				HashSet<String> targetIdSet=new HashSet<>();
				
				for(MyEdgeEvent validEvent:validEvents) {
					
					if(validEvent.getSourceID()==this.vertexID) {
						if(!targetIdSet.contains(validEvent.getTargetID())) {
							targetIdSet.add(validEvent.getTargetID());
							returnEdgeEvents.add(validEvent);
						}
					}
					else {
						if(!sourceIdSet.contains(validEvent.getSourceID())) {
							sourceIdSet.add(validEvent.getSourceID());
							returnEdgeEvents.add(validEvent);
						}
					}
					
					
					
				}
			}
			
			
			
					
			
			
		}
		
		
		return returnEdgeEvents;
		
	}

	@Override
	public Collection<VertexEvent> getVertexEvents(Direction d, TemporalRelation r,String...labels) {
		
		
		ArrayList<MyEdgeEvent> validEvents=new ArrayList<>();
		
		HashSet<String> idSet=new HashSet<>();
		
		ArrayList<VertexEvent> returnVertexEvents=new ArrayList<>();
		
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
				for(MyEdgeEvent validEvent:validEvents) {
					
					if(!idSet.contains(validEvent.getTargetID())) {
						idSet.add(validEvent.getTargetID());
						returnVertexEvents.add(validEvent.getVertexEvent(Direction.OUT));
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
				for(MyEdgeEvent validEvent:validEvents) {
					
					if(!idSet.contains(validEvent.getSourceID())) {
						idSet.add(validEvent.getSourceID());
						returnVertexEvents.add(validEvent.getVertexEvent(Direction.IN));
					}
				}
				
				
			}
			else {
				
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {	
					if((event.getSourceID()==this.vertexID||event.getTargetID()==this.vertexID)&&event.getTime()>this.time)
						validEvents.add(event);
				}
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				
				HashSet<String> sourceIdSet=new HashSet<>();
				HashSet<String> targetIdSet=new HashSet<>();
				
				for(MyEdgeEvent validEvent:validEvents) {
					
					if(validEvent.getSourceID()==this.vertexID) {
						if(!targetIdSet.contains(validEvent.getTargetID())) {
							targetIdSet.add(validEvent.getTargetID());
							returnVertexEvents.add(validEvent.getVertexEvent(Direction.OUT));
						}
					}
					else {
						if(!sourceIdSet.contains(validEvent.getSourceID())) {
							sourceIdSet.add(validEvent.getSourceID());
							returnVertexEvents.add(validEvent.getVertexEvent(Direction.IN));
						}
					}
					
					
					
				}
				
			}
			
			
		}
		else {
			
			if(d==Direction.OUT) {
				
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {
					for(String label:labels)
						if(event.getSourceID()==this.vertexID&&event.getTime()>this.time&&event.getLabel()==label)
							validEvents.add(event);
				}
				
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent validEvent:validEvents) {
					
					if(!idSet.contains(validEvent.getTargetID())) {
						idSet.add(validEvent.getTargetID());
						returnVertexEvents.add(validEvent.getVertexEvent(Direction.OUT));
					}
				}
				
				
				
			}
			else if(d==Direction.IN) {
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {
					for(String label:labels)
						if(event.getTargetID()==this.vertexID&&event.getTime()>this.time&&event.getLabel()==label)
							validEvents.add(event);
				}
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				for(MyEdgeEvent validEvent:validEvents) {
					
					if(!idSet.contains(validEvent.getSourceID())) {
						idSet.add(validEvent.getSourceID());
						returnVertexEvents.add(validEvent.getVertexEvent(Direction.IN));
					}
				}
				
				
			}
			else {
				
				//1. 유효한 event들을 validEvents에 저장
				for(MyEdgeEvent event:this.graph.getEdgeEventsToList()) {
					for(String label:labels)
						if((event.getSourceID()==this.vertexID||event.getTargetID()==this.vertexID)&&event.getTime()>this.time&&event.getLabel()==label)
							validEvents.add(event);
				}
				
				//2. 저장된 edge event들을 시간순으로 오름차순 정렬 
				Collections.sort(validEvents,new MyEdgeEventComparator());
				
				
				//3. hash set을 사용하여 가장 빨리 도달가능한 edge event 들을 저장 
				
				HashSet<String> sourceIdSet=new HashSet<>();
				HashSet<String> targetIdSet=new HashSet<>();
				
				for(MyEdgeEvent validEvent:validEvents) {
					
					if(validEvent.getSourceID()==this.vertexID) {
						if(!targetIdSet.contains(validEvent.getTargetID())) {
							targetIdSet.add(validEvent.getTargetID());
							returnVertexEvents.add(validEvent.getVertexEvent(Direction.OUT));
						}
					}
					else {
						if(!sourceIdSet.contains(validEvent.getSourceID())) {
							sourceIdSet.add(validEvent.getSourceID());
							returnVertexEvents.add(validEvent.getVertexEvent(Direction.IN));
						}
					}
					
					
					
				}
				
			}
			
			
		}
		
		return returnVertexEvents;
	}

}
