package org.dfpl.graph.api.jincheol;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Set;

import org.bson.Document;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyVertex implements Vertex{

	
	private String id;
	private ArrayList<Edge> inEdge;
	private ArrayList<Edge> outEdge;
	
	
	public MyVertex(Document doc) {
		id=doc.getString("_id");
		inEdge=new ArrayList<>();
		outEdge=new ArrayList<>();
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
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
	public Collection<Edge> getEdges(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(direction==Direction.IN) return inEdge;
		else if(direction==Direction.OUT) return outEdge;
		else return null;
		
		
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Vertex> vertexList=new ArrayList<>();
		
		if(direction==Direction.IN) {
			
			
			for(Edge edge:inEdge) {
				vertexList.add(edge.getVertex(Direction.OUT));
			}
		}
		else if(direction==Direction.OUT) {
			for(Edge edge:outEdge) {
				vertexList.add(edge.getVertex(Direction.IN));
			}
		}
		else { //both
			for(Edge edge:inEdge) {
				vertexList.add(edge.getVertex(Direction.OUT));
			}
			for(Edge edge:outEdge) {
				vertexList.add(edge.getVertex(Direction.IN));
			}
			
		}
		return vertexList;
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
	
	public void removeEinE(String edgeLabel) {
		inEdge.remove(edgeLabel);
	}
	
	public void removeEoutE(String edgeLabel) {
		outEdge.remove(edgeLabel);
	}

}
