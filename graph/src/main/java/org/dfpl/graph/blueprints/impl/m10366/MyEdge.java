package org.dfpl.graph.blueprints.impl.m10366;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyEdge implements Edge{
	
	private String id;
	private String label;
	private Map<String,Object> property;
	private Vertex source; //out vertex
	private Vertex target; //in vertex
	
	
	public MyEdge(Vertex source,Vertex target,String label) {
		
		this.source=source;
		this.target=target;
		this.label=label;
		this.id=source.getId()+"|"+label+target.getId();
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
	public Vertex getVertex(Direction direction) throws IllegalArgumentException {
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
	
	
	
	

}
