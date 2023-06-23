package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.HashMap;
import java.util.Map;

import org.dfpl.graph.blueprints.impl.m10366.custom.EdgeEvent;

import org.dfpl.graph.blueprints.impl.m10366.custom.VertexEvent;

import com.tinkerpop.blueprints.revised.Direction;

public class MyEdgeEvent implements EdgeEvent{
	
	private MyTimeGraph graph;
	
	private String id; //식별 id
	 
	private long time; 
	
	private String sourceID;
	
	private String targetID;
	
	private String label;
	
	private HashMap<String,Object> property;
	
	public MyEdgeEvent(MyTimeGraph graph,String sourceID,String targetID,String label,long time) {
		
		
		this.graph=graph;
		
		
		this.id=sourceID+"|"+label+"|"+targetID+"|"+String.valueOf(time);
		
		this.sourceID=sourceID;
		this.targetID=targetID;
		
		this.label=label;
		
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
	
	
	public String getSourceID() {
		return this.sourceID;
	}
	
	public String getTargetID() {
		return this.targetID;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public long getTime() {
		return this.time;
	}
	
	
	@Override
	public Map<String, Object> getProperties() {
		// TODO Auto-generated method stub
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
	public VertexEvent getVertexEvent(Direction d) {
		
		if(d==Direction.IN) //return ingoing vertex event
			return this.graph.getVertexEvents().get(this.sourceID+"|"+String.valueOf(this.time));
		else if(d==Direction.OUT) //return outgoing vertex event
			return this.graph.getVertexEvents().get(this.targetID+"|"+String.valueOf(this.time));
		else 
			throw new IllegalArgumentException("not use both");
			
		
	}




	
}
