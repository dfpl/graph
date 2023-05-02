package org.dfpl.graph.blueprints.impl.m10366;


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
	private ArrayList<Edge> inEdges; //income edge
	private ArrayList<Edge> outEdges; //outcome edge
	
	
	/**
	 * set id 
	 * reset property
	 * @param id
	 */
	public MyVertex(String id) {
		
		this.id=id;
		this.property=new HashMap<>();
		this.inEdges=new ArrayList<>();
		this.outEdges=new ArrayList<>();
		
	}
	
	public void addInEdges(Edge inEdge) {
		this.inEdges.add(inEdge);
	}
	
	public void addOutEdges(Edge outEdge) {
		this.outEdges.add(outEdge);
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
	public Collection<Edge> getEdges(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		
		ArrayList<Edge> list=new ArrayList<>();
		
		
		
		if(labels.length!=0) {
			for(String edgeLabel:labels) {
				if(direction==Direction.IN) {
					
					for(Edge edge:this.inEdges) {
						if(edge.getLabel()==edgeLabel) list.add(edge);
					}
					
				}
				else if(direction==Direction.OUT) {
					for(Edge edge:this.outEdges) {
						if(edge.getLabel()==edgeLabel) list.add(edge);
					}
				}
				else { //both
					ArrayList<Edge> edges=new ArrayList<>();
					edges.addAll(this.inEdges);
					edges.addAll(this.outEdges);
					
					for(Edge edge:edges) {
						if(edge.getLabel()==edgeLabel) list.add(edge);
					}
				}
			}
		}
		else {
			if(direction==Direction.IN) {
				
				for(Edge edge:this.inEdges) {
					list.add(edge);
				}
				
			}
			else if(direction==Direction.OUT) {
				for(Edge edge:this.outEdges) {
					list.add(edge);
				}
			}
			else { //both
				ArrayList<Edge> edges=new ArrayList<>();
				edges.addAll(this.inEdges);
				edges.addAll(this.outEdges);
				
				for(Edge edge:edges) {
					list.add(edge);
				}
			}
			
		}
		
		
		
		
		return list;
		
		
		
		
		
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		
		ArrayList<Vertex> vertexList=new ArrayList<>();
		
		if(labels.length!=0) {
			for(String edgeLabel:labels) {
				if(direction==Direction.IN) {
					for(Edge edge:this.inEdges) {
						if(edge.getLabel()==edgeLabel) vertexList.add(edge.getVertex(Direction.IN));
					}
				}
				else if(direction==Direction.OUT) {
					for(Edge edge:this.outEdges) {
						if(edge.getLabel()==edgeLabel) vertexList.add(edge.getVertex(Direction.OUT));
					}
					
				}
				else {
					ArrayList<Edge> bothList=new ArrayList<>();
					bothList.addAll(this.inEdges);
					bothList.addAll(outEdges);
					
					for(Edge edge:bothList) {
						if(edge.getLabel()==edgeLabel) vertexList.add(edge.getVertex(Direction.OUT));
					}
					
				}
				
			}
		}
		else {
			
				if(direction==Direction.IN) {
					for(Edge edge:this.inEdges) {
						vertexList.add(edge.getVertex(Direction.IN));
					}
				}
				else if(direction==Direction.OUT) {
					for(Edge edge:this.outEdges) {
						vertexList.add(edge.getVertex(Direction.OUT));
					}
					
				}
				else {
					ArrayList<Edge> bothList=new ArrayList<>();
					bothList.addAll(this.inEdges);
					bothList.addAll(outEdges);
					
					for(Edge edge:bothList) {
						vertexList.add(edge.getVertex(Direction.OUT));
					}
					
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
		ArrayList<Vertex> vertexList=new ArrayList<>();
		
		for(String edgeLabel:labels) {
			if(direction==Direction.IN) {
				for(Edge edge:this.inEdges) {
					if(edge.getLabel()==edgeLabel&&edge.getProperty(key)==value) {
						
						vertexList.add(edge.getVertex(Direction.IN));
						
					}
				}
			}
			else if(direction==Direction.OUT) {
				for(Edge edge:this.outEdges) {
					if(edge.getLabel()==edgeLabel&&edge.getProperty(key)==value) {
						vertexList.add(edge.getVertex(Direction.IN));
					}
						
				}
				
			}
			else {
				ArrayList<Edge> bothList=new ArrayList<>();
				bothList.addAll(this.inEdges);
				bothList.addAll(outEdges);
				
				for(Edge edge:bothList) {
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

	

}
