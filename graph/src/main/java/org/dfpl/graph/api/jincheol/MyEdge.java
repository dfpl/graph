package org.dfpl.graph.api.jincheol;

import java.util.Set;

import org.bson.Document;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyEdge implements Edge{
	
	
	
	private String label;
	private Vertex outV;
	private Vertex inV;
	
	public MyEdge(Document doc) {
		
		label=doc.getString("label");
		outV=doc.get("outV",Vertex.class);
		inV=doc.get("inV",Vertex.class);
	}
	
	
	
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub

		return label;
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
		
		
		if(direction==Direction.IN) return inV;
		else if(direction==Direction.OUT) return outV;
		else {
			
			return null;
		}
		
		
		
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return label;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
