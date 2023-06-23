package org.dfpl.graph.blueprints.impl.m10366.custom;

import java.util.Collection;
import java.util.Map;

import com.tinkerpop.blueprints.revised.Direction;

public interface VertexEvent {
	
	public Collection<VertexEvent> getVertexEvents(TemporalRelation r);
	
	public Map<String,Object> getProperties();
	
	public void setProperty(String key,Object value);
	
	public Object removeProperty(String key);
	
	public Collection<EdgeEvent> getEdgeEvents(Direction d,TemporalRelation r,String... labels);
	
	public Collection<VertexEvent> getVertexEvents(Direction d,TemporalRelation r,String... labels);
}
