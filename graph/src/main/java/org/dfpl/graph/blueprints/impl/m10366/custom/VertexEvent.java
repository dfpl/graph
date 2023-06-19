package org.dfpl.graph.blueprints.impl.m10366.custom;

import java.util.Map;

import com.tinkerpop.blueprints.revised.Direction;

public interface VertexEvent {
	
	public Iterable<VertexEvent> getVertexEvents(TemporalRelation r);
	
	public Map<String,Object> getProperties();
	
	public void setProperty(String key,Object value);
	
	public Object removeProperty(String key);
	
	public Iterable<EdgeEvent> getEdgeEvents(Direction d,String[] labels,TemporalRelation r);
	
	public Iterable<VertexEvent> getVertexEvents(Direction d,String[] labels,TemporalRelation r);
}
