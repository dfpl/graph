package org.dfpl.graph.api.jincheol;

import java.util.Collection;

import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyGraph implements Graph{
	
	private MyMongoDB md;
	
	public MyGraph() {
		
		this.md=new MyMongoDB();
		
	}
	
	
	
	
	@Override
	public Vertex addVertex(String id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		Vertex newV=new MyVertex(id);
		
		return null;
	}

	@Override
	public Vertex getVertex(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Vertex> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label)
			throws IllegalArgumentException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge getEdge(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEdge(Edge edge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Edge> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	

}
