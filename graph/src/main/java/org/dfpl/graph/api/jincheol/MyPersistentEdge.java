package org.dfpl.graph.api.jincheol;

import java.util.Set;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyPersistentEdge implements Edge{
	
	private String id;
	private String label;
	private Vertex source;
	private Vertex target;
	
	public MyPersistentEdge(String id,String label,Vertex source,Vertex target) {
		
		this.id=id;
		this.label=label;
		this.source=source;
		this.target=target;
	}
	
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object removeProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex getVertex(Direction direction) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
