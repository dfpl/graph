package org.dfpl.graph.blueprints.impl.m10366;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import java.util.Map;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyUndirectedGraph implements Graph{
	
	private Map<String,MyVertex> vertecies;
	private Map<String,MyEdge> edges;
	
	//생성자
	public MyUndirectedGraph() {
		
		this.vertecies=new HashMap<>();
		this.edges=new HashMap<>();
		
	}
	
	
	//sssp
	public void ShortestPath() {
		//shortest path from each source vertex
		
		for(String key:this.vertecies.keySet()) {
			
			
			for(String targetKey:this.vertecies.keySet()) {
				
				if(key==targetKey) continue;
				
				
				
				
			}
			
			
		}
		
		
		
	}
	
	
	
	
	
	
	@Override
	public Vertex addVertex(String id) throws IllegalArgumentException {
		// TODO Auto-generated method stub		
		
		if (id.contains("|")) {
			throw new IllegalArgumentException("id cannot contain '|'");
		}
		
		MyVertex vertex=new MyVertex(id);
		
		if(this.vertecies.get(id)!=null) {
			//System.out.println("already exist vertex");
			return this.vertecies.get(id);
		}
		else {
			//System.out.println("insert vertex "+id);
			this.vertecies.put(id, vertex);
			return vertex;
		}
		
		
	}

	@Override
	public Vertex getVertex(String id) {
		// TODO Auto-generated method stub
		return this.vertecies.get(id);
		
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// TODO Auto-generated method stub
		if(this.vertecies.get(vertex.getId())!=null) {
			this.vertecies.remove(vertex.getId());
		}
		else {
			System.out.println("no exists");
		}
	}

	@Override
	public Collection<Vertex> getVertices() {
		// TODO Auto-generated method stub
		
		ArrayList<Vertex> vertexList=new ArrayList<>();
		
		for(String vertexKey:this.vertecies.keySet()) {
			vertexList.add(this.vertecies.get(vertexKey));
		}
		
		return vertexList;
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		// TODO Auto-generated method stub
		ArrayList<Vertex> vertexList=new ArrayList<>();
		
		for(String vertexKey:this.vertecies.keySet()) {
			
			if(value==this.vertecies.get(vertexKey).getProperty(key)) {
				
				 vertexList.add(this.vertecies.get(vertexKey));	
			}
			
		}
		
		return vertexList;
	}

	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label)
			throws IllegalArgumentException, NullPointerException {
		// TODO Auto-generated method stub
		
		if (label.contains("|")) {
			throw new IllegalArgumentException("label cannot contain '|'");
		}
		if (outVertex == null) {
			throw new NullPointerException("outVertex cannot be null");
		}

		if (inVertex == null) {
			throw new NullPointerException("inVertex cannot be null");
		}
		
		MyEdge edge_1=new MyEdge(inVertex,outVertex,label);
		MyEdge edge_2=new MyEdge(outVertex,inVertex,label);
		
		if(this.edges.get(inVertex.getId()+"|"+label+"|"+outVertex.getId())!=null) {
			System.out.println("already exist edge");
			return this.edges.get(inVertex.getId()+"|"+label+"|"+outVertex.getId());
		}
		else if(this.edges.get(outVertex.getId()+"|"+label+"|"+inVertex.getId())!=null) {
			System.out.println("already exist edge");
			return this.edges.get(outVertex.getId()+"|"+label+"|"+inVertex.getId());
			
		}
		else {
			//System.out.println("inset edge id : "+inVertex.getId()+"|"+label+"|"+outVertex.getId());
			//System.out.println("inset edge id : "+outVertex.getId()+"|"+label+"|"+inVertex.getId());
			
			
			this.edges.put(inVertex.getId()+"|"+label+"|"+outVertex.getId(), edge_1);
			this.edges.put(outVertex.getId()+"|"+label+"|"+inVertex.getId(), edge_2);
			
			
			MyVertex sv=(MyVertex) edge_1.getVertex(Direction.IN);
			MyVertex tv=(MyVertex) edge_1.getVertex(Direction.OUT);
			
			sv.addOutEdges(edge_1);
			tv.addInEdges(edge_1);
			
			sv.addOutEdges(edge_2);
			tv.addInEdges(edge_2);
			
			return edge_1;
		}
		
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		// TODO Auto-generated method stub
		
		return this.edges.get(inVertex.getId()+"|"+label+"|"+outVertex.getId());
	}

	@Override
	public Edge getEdge(String id) {
		// TODO Auto-generated method stub
		return this.edges.get(id);
	}

	@Override
	public void removeEdge(Edge edge) {
		// TODO Auto-generated method stub
		if(this.edges.get(edge.getId())!=null) {
			this.vertecies.remove(edge.getId());
		}
		else {
			System.out.println("no exists");
		}
	}

	@Override
	public Collection<Edge> getEdges() {
		// TODO Auto-generated method stub
		ArrayList<Edge> edgeList=new ArrayList<>();
		
		for(String edgeKey:this.edges.keySet()) {
			edgeList.add(this.edges.get(edgeKey));
		}
		
		return edgeList;
		
	}

	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		// TODO Auto-generated method stub
		ArrayList<Edge> edgeList=new ArrayList<>();
		
		for(String edgeKey:this.edges.keySet()) {
			
			if(value==this.edges.get(edgeKey).getProperty(key)) {

				 edgeList.add(this.edges.get(edgeKey));

			}
			
		}
		
		return edgeList;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
