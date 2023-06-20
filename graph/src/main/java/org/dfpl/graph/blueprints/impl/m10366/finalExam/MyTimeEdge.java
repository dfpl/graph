package org.dfpl.graph.blueprints.impl.m10366.finalExam;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.dfpl.graph.blueprints.impl.m10366.custom.EdgeEvent;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;

import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;

import com.tinkerpop.blueprints.revised.Direction;

public class MyTimeEdge implements TimeEdge{
	
	private MyTimeGraph graph;
	
	private String id;
	private String label;
	private Map<String,Object> property;
	private TimeVertex source; //out vertex
	private TimeVertex target; //in vertex
	
	
	
	
	public MyTimeEdge(MyTimeGraph graph,TimeVertex source,TimeVertex target,String label) {
		
		this.graph=graph;
		
		this.source=source;
		this.target=target;
		this.label=label;
		this.id=source.getId()+"|"+label+"|"+target.getId();
		this.property=new HashMap<>();
		
		
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public Object getProperty(String key) {
		// TODO Auto-generated method stub
		return property.get(key);
	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return property.keySet();
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		property.put(key, value);
	}

	@Override
	public Object removeProperty(String key) {
		// TODO Auto-generated method stub
		return property.remove(key);
	}

	@Override
	public TimeVertex getVertex(Direction direction) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		if(direction==Direction.IN) return this.target;
		else if(direction==Direction.OUT) return this.source;
		else throw new IllegalArgumentException("not use both");
		
		
		
		
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return this.label;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public EdgeEvent addEvent(long time) {
		
		
		
		if(this.graph.getEdgeEvents().containsKey(this.id+"|"+String.valueOf(time))) {
			System.out.println("Already Exist Event");
			return null;
		}
		else {
			
			//양쪽 vertex event 없으면 생성
			if(!this.graph.getVertexEvents().containsKey(this.source.getId()+"|"+String.valueOf(time))) 
				this.graph.getVertexEvents().put(this.source.getId()+"|"+String.valueOf(time), new MyVertexEvent(this.graph,this.source.getId(),time));

			if(!this.graph.getVertexEvents().containsKey(this.target.getId()+"|"+String.valueOf(time))) 
				this.graph.getVertexEvents().put(this.target.getId()+"|"+String.valueOf(time), new MyVertexEvent(this.graph,this.target.getId(),time));
			
			
			this.graph.addEdgeEvent(new MyEdgeEvent(this.graph,this.source.getId(),this.target.getId(),this.label,time));
			this.graph.addEdgeEvent(new MyEdgeEvent(this.graph,this.target.getId(),this.source.getId(),this.label,time));
			
		}
		
		
		
		
		return null;
		
	}

	@Override
	public EdgeEvent pickEvent(long time) {
		
		if(this.graph.getEdgeEvents().get(this.id+"|"+String.valueOf(time)).getProperties().isEmpty()) {
			System.out.println("No Properties");
			return null;
		}
		else
			return this.graph.getEdgeEvents().get(this.id+"|"+String.valueOf(time));
		
	}

	@Override
	public EdgeEvent removeEvent(long time) {
		
		return this.graph.getEdgeEvents().remove(this.id+"|"+String.valueOf(time));
	}

	@Override
	public Collection<EdgeEvent> pickEvents(long time) {
		
		ArrayList<EdgeEvent> pickEdgeEvents=new ArrayList<>();
		
		for(String key:this.graph.getEdgeEvents().keySet()) {
			
			
			String[] eventTime=key.split("|");
			
			if(Long.valueOf(eventTime[3])==time){
				
				if(!this.graph.getEdgeEvents().get(this.id+"|"+String.valueOf(time)).getProperties().isEmpty()) {
					pickEdgeEvents.add(this.graph.getEdgeEvents().get(key));
				}
				
			}
				
		}
			
			
		return pickEdgeEvents;
		
		
	}

	@Override
	public Collection<EdgeEvent> getEdgeEvents(long time) {
		ArrayList<EdgeEvent> edgeEvents=new ArrayList<>();
		
		for(String key:this.graph.getEdgeEvents().keySet()) {
			
			
			String[] eventTime=key.split("|");
			
			if(Long.valueOf(eventTime[3])==time)
				edgeEvents.add(this.graph.getEdgeEvents().get(key));
		}
		return edgeEvents;
	}

	

}
