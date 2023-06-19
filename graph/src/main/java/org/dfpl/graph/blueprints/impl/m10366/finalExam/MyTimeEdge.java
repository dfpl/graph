package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.dfpl.graph.blueprints.impl.m10366.custom.EdgeEvent;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;


import com.tinkerpop.blueprints.revised.Direction;

public class MyTimeEdge implements TimeEdge{
	
	private String id;
	private String label;
	private Map<String,Object> property;
	private TimeVertex source; //out vertex
	private TimeVertex target; //in vertex
	
	
	public MyTimeEdge(TimeVertex source,TimeVertex target,String label) {
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EdgeEvent pickEvent(long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EdgeEvent removeEvent(long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<EdgeEvent> pickEvents(long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<EdgeEvent> getEdgeEvents(long time) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
