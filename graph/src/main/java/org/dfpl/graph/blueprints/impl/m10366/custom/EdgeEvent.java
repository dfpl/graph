package org.dfpl.graph.blueprints.impl.m10366.custom;

import java.util.Map;

import com.tinkerpop.blueprints.revised.Direction;

public interface EdgeEvent {

	public Iterable<EdgeEvent> getEdgeEvents(TemporalRelation r);
	
	public Map<String,Object> getProperties();
	
	public void setProperty(String key,Object value);
	
	public Object removeProperty(String key);
	
	public VertexEvent getVertexEvent(Direction d);
	
}
