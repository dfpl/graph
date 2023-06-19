package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import org.dfpl.graph.blueprints.impl.m10366.custom.TimeEdge;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;

import org.dfpl.graph.blueprints.impl.m10366.custom.VertexEvent;

import com.tinkerpop.blueprints.revised.Direction;

public class MyTimeVertex implements TimeVertex{
	
	
	private String id;
	private Map<String,Object> property;
	private ArrayList<TimeEdge> inEdges; //income edge
	private ArrayList<TimeEdge> outEdges; //outcome edge
	
	
	/**
	 * set id 
	 * reset property
	 * @param id
	 */
	public MyTimeVertex(String id) {
		
		this.id=id;
		this.property=new HashMap<>();
		this.inEdges=new ArrayList<>();
		this.outEdges=new ArrayList<>();
		
	}


	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}


	@Override
	public Object getProperty(String key) {
		// TODO Auto-generated method stub
		return this.property.get(key);
	}


	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		return this.property.keySet();
	}


	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		this.property.put(key, value);
	}


	@Override
	public Object removeProperty(String key) {
		// TODO Auto-generated method stub
		return this.property.remove(key);
	}


	@Override
	public Collection<TimeEdge> getEdges(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<TimeEdge> list=new ArrayList<>();
		
		
		
		if(labels.length!=0) {
			for(String edgeLabel:labels) {
				if(direction==Direction.IN) {
					
					for(TimeEdge edge:this.inEdges) {
						if(edge.getLabel()==edgeLabel) list.add(edge);
					}
					
				}
				else if(direction==Direction.OUT) {
					for(TimeEdge edge:this.outEdges) {
						if(edge.getLabel()==edgeLabel) list.add(edge);
					}
				}
				else { //both
					ArrayList<TimeEdge> edges=new ArrayList<>();
					edges.addAll(this.inEdges);
					edges.addAll(this.outEdges);
					
					for(TimeEdge edge:edges) {
						if(edge.getLabel()==edgeLabel) list.add(edge);
					}
				}
			}
		}
		else {
			if(direction==Direction.IN) {
				
				for(TimeEdge edge:this.inEdges) {
					list.add(edge);
				}
				
			}
			else if(direction==Direction.OUT) {
				for(TimeEdge edge:this.outEdges) {
					list.add(edge);
				}
			}
			else { //both
				ArrayList<TimeEdge> edges=new ArrayList<>();
				edges.addAll(this.inEdges);
				edges.addAll(this.outEdges);
				
				for(TimeEdge edge:edges) {
					list.add(edge);
				}
			}
			
		}
		
		
		
		
		return list;
	}


	@Override
	public Collection<TimeVertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<TimeVertex> vertexList=new ArrayList<>();
		
		if(labels.length!=0) {
			for(String edgeLabel:labels) {
				if(direction==Direction.IN) {
					for(TimeEdge edge:this.inEdges) {
						if(edge.getLabel()==edgeLabel) vertexList.add(edge.getVertex(Direction.IN));
					}
				}
				else if(direction==Direction.OUT) {
					for(TimeEdge edge:this.outEdges) {
						if(edge.getLabel()==edgeLabel) vertexList.add(edge.getVertex(Direction.OUT));
					}
					
				}
				else {
					ArrayList<TimeEdge> bothList=new ArrayList<>();
					bothList.addAll(this.inEdges);
					bothList.addAll(outEdges);
					
					for(TimeEdge edge:bothList) {
						if(edge.getLabel()==edgeLabel) vertexList.add(edge.getVertex(Direction.OUT));
					}
					
				}
				
			}
		}
		else {
			
				if(direction==Direction.IN) {
					for(TimeEdge edge:this.inEdges) {
						vertexList.add(edge.getVertex(Direction.IN));
					}
				}
				else if(direction==Direction.OUT) {
					for(TimeEdge edge:this.outEdges) {
						vertexList.add(edge.getVertex(Direction.OUT));
					}
					
				}
				else {
					ArrayList<TimeEdge> bothList=new ArrayList<>();
					bothList.addAll(this.inEdges);
					bothList.addAll(outEdges);
					
					for(TimeEdge edge:bothList) {
						vertexList.add(edge.getVertex(Direction.OUT));
					}
					
				}
				
			
		}
		
		
		
		
		return vertexList;
	}


	@Override
	public Collection<TimeVertex> getVertices(Direction direction, String key, Object value, String... labels)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<TimeVertex> vertexList=new ArrayList<>();
		
		for(String edgeLabel:labels) {
			if(direction==Direction.IN) {
				for(TimeEdge edge:this.inEdges) {
					if(edge.getLabel()==edgeLabel&&edge.getProperty(key)==value) {
						
						vertexList.add(edge.getVertex(Direction.IN));
						
					}
				}
			}
			else if(direction==Direction.OUT) {
				for(TimeEdge edge:this.outEdges) {
					if(edge.getLabel()==edgeLabel&&edge.getProperty(key)==value) {
						vertexList.add(edge.getVertex(Direction.IN));
					}
						
				}
				
			}
			else {
				ArrayList<TimeEdge> bothList=new ArrayList<>();
				bothList.addAll(this.inEdges);
				bothList.addAll(outEdges);
				
				for(TimeEdge edge:bothList) {
					if(edge.getLabel()==edgeLabel&&edge.getProperty(key)==value) {
						vertexList.add(edge.getVertex(Direction.IN));
					}
				}
				
			}
			
		}
		
		
		return vertexList;
	}


	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public VertexEvent addEvent(long time) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
