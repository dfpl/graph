package org.dfpl.graph.api.jincheol;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Set;

import org.bson.Document;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyPersistentVertex implements Vertex{
	
	private MyMongoDB md;
	
	private String id;
	
	
	
	public MyPersistentVertex(String id) {
		this.md=new MyMongoDB();
		this.id=id;
		
		
		
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public Object getProperty(String key) {
		// TODO Auto-generated method stub
		if(this.md.getCollection("vertex").find(Filters.eq("_id",this.id)).first()!=null) {
			
			Document propertyDoc=this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first();
			return propertyDoc;
		}
		else {
			throw new IllegalArgumentException("no exist vertex id");
		}
		
	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		
		if(this.md.getCollection("vertex").find(Filters.eq("_id",this.id)).first()!=null) {
			
			Document propertyDoc=this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first();
			
			return propertyDoc.keySet();
			
		}
		else {
			throw new IllegalArgumentException("no exist vertex id");
		}
		
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		if(this.md.getCollection("vertex").find(Filters.eq("_id",this.id)).first()!=null) {
			
			this.md.getCollection("vertex").findOneAndUpdate(Filters.eq("_id", id),Updates.set("property",((Document) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("property")).append(key,value) ));
		}
		else {
			throw new IllegalArgumentException("no exist vertex id");
		}
	}

	@Override
	public Object removeProperty(String key) {
		// TODO Auto-generated method stub
		if(this.md.getCollection("vertex").find(Filters.eq("_id",this.id)).first()!=null) {
			
			Document propertyDoc=(Document) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("property");
			
			this.md.getCollection("vertex").findOneAndUpdate(Filters.eq("_id", id),Updates.set("property",((Document) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("property")).remove(key) ));
			
			return propertyDoc;
		}
		else {
			throw new IllegalArgumentException("no exist vertex id");
		}
	}

	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		
		if(this.md.getCollection("vertex").find(Filters.eq("_id",this.id)).first()!=null) {
			
			ArrayList<Edge> edges=new ArrayList<>();
			
			
			
			if(labels.length!=0) {
				
				if(direction==Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					
					
					
					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								edges.add(edge);
							}
							
							
						}
					}
					
					
					
					
				}
				else if(direction==Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								edges.add(edge);
							}
							
							
						}
					}
				}
				else { //both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					inEdgeIDs.addAll(outEdgeIDs);
					ArrayList<String> bothEdgeIDs=inEdgeIDs;
					
					
					
					for(String edgeID:bothEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								edges.add(edge);
							}
							
							
						}
					}
				}
				
				
				
				
				
			}
			else {
				//label이 없을 경우
				
				if(direction==Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						

						MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
						edges.add(edge);

					}
					
					
					
					
				}
				else if(direction==Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						

						MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
						edges.add(edge);

					}
				}
				else { //both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					inEdgeIDs.addAll(outEdgeIDs);
					ArrayList<String> bothEdgeIDs=inEdgeIDs;
					
					
					
					for(String edgeID:bothEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						

						MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
						edges.add(edge);

					}
				}

				
			}
			
			
			return edges;
			
		}
		else {
			throw new IllegalArgumentException("no exist vertex id");
		}
		
		
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(this.md.getCollection("vertex").find(Filters.eq("_id",this.id)).first()!=null) {
			
			ArrayList<Edge> edges=new ArrayList<>();
			ArrayList<Vertex> vertecies=new ArrayList<>();
			
			
			
			if(labels.length!=0) {
				
				if(direction==Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					
					
					
					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								edges.add(edge);
							}
							
							
						}
					}
					
					for(Edge edge:edges){
						
						vertecies.add(edge.getVertex(Direction.IN));
						
					}
					
					
				}
				else if(direction==Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								edges.add(edge);
							}
							
							
						}
					}
					
					for(Edge edge:edges){
						
						vertecies.add(edge.getVertex(Direction.OUT));
						
					}
				}
				else { //both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					
					
					
					
					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								vertecies.add(edge.getVertex(Direction.IN));
							}
							
							
						}
						
						
					}
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								vertecies.add(edge.getVertex(Direction.OUT));
							}
							
							
						}
						
						
					}
					
					
				}
				
				
				
				
				
			}
			else {
				//label이 없을 경우
				
				if(direction==Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");

					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						

						MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
						edges.add(edge);

					}
					
					for(Edge edge:edges){
						
						vertecies.add(edge.getVertex(Direction.IN));
						
					}
					
					
				}
				else if(direction==Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						

						MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
						edges.add(edge);

					}
					
					
					for(Edge edge:edges){
						
						vertecies.add(edge.getVertex(Direction.OUT));
						
					}
				}
				else { //both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						
						MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
						vertecies.add(edge.getVertex(Direction.IN));
							

					}
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						
						MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
						vertecies.add(edge.getVertex(Direction.OUT));
							

						
						
					}
				}

				
			}
			

			return vertecies;
			
		}
		else {
			throw new IllegalArgumentException("no exist vertex id");
		}
	}

	@Override
	public Collection<Vertex> getTwoHopVertices(Direction direction, String... labels) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String key, Object value, String... labels)
			throws IllegalArgumentException {
		
		
		if(this.md.getCollection("vertex").find(Filters.eq("_id",this.id)).first()!=null) {
			
			ArrayList<Edge> edges=new ArrayList<>();
			ArrayList<Vertex> vertecies=new ArrayList<>();
			
			
			
			if(labels.length!=0) {
				
				if(direction==Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					
					
					
					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						
						Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first().get("property");
						
						
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)&&propertyDoc.get(key).equals(value)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								edges.add(edge);
							}
							
							
						}
					}
					
					for(Edge edge:edges){
						
						vertecies.add(edge.getVertex(Direction.IN));
						
					}
					
					
				}
				else if(direction==Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first().get("property");
						
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)&&propertyDoc.get(key).equals(value)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								edges.add(edge);
							}
							
							
						}
					}
					
					for(Edge edge:edges){
						
						vertecies.add(edge.getVertex(Direction.OUT));
						
					}
				}
				else { //both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					
					
					
					
					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first().get("property");
						
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)&&propertyDoc.get(key).equals(value)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								vertecies.add(edge.getVertex(Direction.IN));
							}
							
							
						}
						
						
					}
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first().get("property");
						
						for(String label:labels) {
							
							if(edgeDoc.get("label").equals(label)&&propertyDoc.get(key).equals(value)) {
								

								MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
								vertecies.add(edge.getVertex(Direction.OUT));
							}
							
							
						}
						
						
					}
					
					
				}
				
				
				
				
				
			}
			else {
				//label이 없을 경우
				
				if(direction==Direction.IN) {
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first().get("property");
						
						if(propertyDoc.get(key).equals(value)) {
							MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
							edges.add(edge);
						}
						

					}
					
					for(Edge edge:edges){
						
						vertecies.add(edge.getVertex(Direction.IN));
						
					}
					
					
				}
				else if(direction==Direction.OUT) {
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						

						Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first().get("property");
						
						if(propertyDoc.get(key).equals(value)) {
							MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
							edges.add(edge);
						}

					}
					
					
					for(Edge edge:edges){
						
						vertecies.add(edge.getVertex(Direction.OUT));
						
					}
				}
				else { //both
					@SuppressWarnings("unchecked")
					ArrayList<String> inEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("inEdgeIDs");
					
					@SuppressWarnings("unchecked")
					ArrayList<String> outEdgeIDs=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", id)).first().get("outEdgeIDs");
					
					for(String edgeID:inEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						
						Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first().get("property");
						
						if(propertyDoc.get(key).equals(value)) {
							MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
							edges.add(edge);
						}
							

					}
					
					for(String edgeID:outEdgeIDs) {
						
						Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first();
						
						Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", edgeID)).first().get("property");
						
						if(propertyDoc.get(key).equals(value)) {
							MyPersistentEdge edge=new MyPersistentEdge((String)edgeDoc.get("_id"),(String)edgeDoc.get("label"),new MyPersistentVertex((String)edgeDoc.get("source")),new MyPersistentVertex((String)edgeDoc.get("target")));
							edges.add(edge);
						}
							

						
						
					}
				}

				
			}
			

			return vertecies;
			
		}
		else {
			throw new IllegalArgumentException("no exist vertex id");
		}
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
