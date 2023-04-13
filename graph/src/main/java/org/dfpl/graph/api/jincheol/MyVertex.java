package org.dfpl.graph.api.jincheol;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Vertex;



public class MyVertex implements Vertex{

	private String id;
	private Map<String,Object> property;
	
	/**
	 * set id 
	 * reset property
	 * @param id
	 */
	public MyVertex(String id) {
		
		this.id=id;
		this.property=new HashMap<>();
	
		
	}
	
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
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
	public Collection<Edge> getEdges(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		//only out edges 
		
		ArrayList<Edge> edges=new ArrayList<>();
		
		
		
		return edges;
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Vertex> getTwoHopVertices(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String key, Object value, String... labels)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	

}
