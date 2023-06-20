package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeGraph;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;


import com.tinkerpop.blueprints.revised.Direction;

public class MyTimeGraph implements TimeGraph{


	
	private Map<String,MyTimeVertex> vertecies;
	private Map<String,MyTimeEdge> edges;
	
	private Map<String,MyVertexEvent> vertexEvents;
	private Map<String,MyEdgeEvent> edgeEvents;
	
	public MyTimeGraph() {
		
		
		this.vertecies=new HashMap<>();
		this.edges=new HashMap<>();
		
		this.vertexEvents=new HashMap<>();
		this.edgeEvents=new HashMap<>();
		
	}
	
	public void addVertexEvent(MyVertexEvent event) {
		
		vertexEvents.put(event.getID(), event);
		
	}
	
	public void addEdgeEvent(MyEdgeEvent event) {

		edgeEvents.put(event.getID(), event);
		
	}
	
	public Map<String,MyVertexEvent> getVertexEvents(){
		return this.vertexEvents;
	}
	
	public Map<String,MyEdgeEvent> getEdgeEvents(){
		return this.edgeEvents;
	}
	
	public Collection<MyVertexEvent> getVertexEventsToList(){
		ArrayList<MyVertexEvent> vertexEventList=new ArrayList<>();
		
		for(String Key:this.vertexEvents.keySet()) 
			vertexEventList.add(this.vertexEvents.get(Key));	
		
		return vertexEventList;
	}
	
	
	
	public Collection<MyEdgeEvent> getEdgeEventsToList(){
		ArrayList<MyEdgeEvent> edgeEventList=new ArrayList<>();
		
		for(String Key:this.edgeEvents.keySet()) 
			edgeEventList.add(this.edgeEvents.get(Key));	
		
		return edgeEventList;
	}
	
	
	
	
	
	
	@Override
	public TimeVertex addVertex(String id) throws IllegalArgumentException {
		
		if (id.contains("|")) 
			throw new IllegalArgumentException("id cannot contain '|'");
		
		
		
		
		if(this.vertecies.containsKey(id)) {
			System.out.println("already exist vertex");
			return this.vertecies.get(id);
		}
		else {
			MyTimeVertex vertex=new MyTimeVertex(this,id);
			this.vertecies.put(id, vertex);
			return vertex;
		}
		
		
		
		
	}

	@Override
	public TimeVertex getVertex(String id) {
		
		if(this.vertecies.containsKey(id)) 
			return this.vertecies.get(id);
		else return null;
	}

	@Override
	public void removeVertex(TimeVertex vertex) {
		
	}




	@Override
	public TimeEdge addEdge(TimeVertex outVertex, TimeVertex inVertex, String label)
			throws IllegalArgumentException, NullPointerException {
		
		if (label.contains("|")) 
			throw new IllegalArgumentException("label cannot contain '|'");
		
		if (outVertex == null) 
			throw new NullPointerException("outVertex cannot be null");
		
		if (inVertex == null) 
			throw new NullPointerException("inVertex cannot be null");
		
		if(this.edges.containsKey(outVertex.getId()+"|"+label+"|"+inVertex.getId())) {
			System.out.println("already exist edge");
			return null;
		}
			
		
			
		//undirected graph
		MyTimeEdge edge_1=new MyTimeEdge(this,outVertex,inVertex,label);
		MyTimeEdge edge_2=new MyTimeEdge(this,inVertex,outVertex,label);
		
		this.edges.put(inVertex.getId()+"|"+label+"|"+outVertex.getId(), edge_1);
		this.edges.put(outVertex.getId()+"|"+label+"|"+inVertex.getId(), edge_2);
		
		//각 vertex update
		MyTimeVertex edge_1_outV=(MyTimeVertex)edge_1.getVertex(Direction.OUT);
		MyTimeVertex edge_1_inV=(MyTimeVertex)edge_1.getVertex(Direction.OUT);
		
		edge_1_outV.addOutEdges(edge_1);
		edge_1_outV.addInEdges(edge_2);
		
		edge_1_inV.addOutEdges(edge_2);
		edge_1_inV.addInEdges(edge_1);
		
		
		return edge_1;
	}

	@Override
	public TimeEdge getEdge(TimeVertex outVertex, TimeVertex inVertex, String label) {
		
		if (label.contains("|")) 
			throw new IllegalArgumentException("label cannot contain '|'");
		
		if (outVertex == null) 
			throw new NullPointerException("outVertex cannot be null");
		
		if (inVertex == null) 
			throw new NullPointerException("inVertex cannot be null");
		
		if(this.edges.containsKey(outVertex.getId()+"|"+label+"|"+inVertex.getId())) 
			return this.edges.get(outVertex.getId()+"|"+label+"|"+inVertex.getId());
		else
			return null;
		
		
		
	
	}

	@Override
	public TimeEdge getEdge(String id) {
		if(this.edges.containsKey(id)) 
			return this.edges.get(id);
		else
			return null;
	}

	@Override
	public void removeEdge(TimeEdge edge) {
		// TODO Auto-generated method stub
		
	}


	
	

}
