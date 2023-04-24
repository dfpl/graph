package org.dfpl.graph.api.jincheol;

import java.util.Collection;

import org.bson.Document;

import com.mongodb.client.model.Filters;
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
		

		
		if(this.md.getCollection("vertex").find(Filters.eq("_id", id)).first()==null) {
			Document doc=new Document();
			Document propertyDoc=new Document();
			doc.append("_id", id);	
			doc.append("property", propertyDoc);
			this.md.getCollection("vertex").insertOne(doc);
			
			MyVertex vertex=new MyVertex(id);
			return vertex;
		}
		else {
			System.out.println("Already Exist Vertex");
			return null;
		}
		
		
		
		
		
		
	}

	@Override
	public Vertex getVertex(String id) {
		// TODO Auto-generated method stub
		if(this.md.getCollection("vertex").find(Filters.eq("_id", id)).first()!=null) {
			
			
			Document vertexDoc=this.md.getCollection("vertex").find(Filters.eq("_id", id)).first();
			Document propertyDoc=(Document) vertexDoc.get("property");
			
			
			
			
			MyVertex vertex=new MyVertex(id);
			
			
			//property 가져오기 
			for(String key:propertyDoc.keySet()) {
				vertex.setProperty(key, propertyDoc.get(key));
			}
			
			
			
			
			
			return vertex;
		}
		else {
			System.out.println("No Exist");
			return null;
		}
		
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// TODO Auto-generated method stub
		if (this.md.getCollection("vertex").find(Filters.eq("_id", vertex.getId())).first() != null) {

			this.md.getCollection("vertex").findOneAndDelete(Filters.eq("_id", vertex.getId()));
			
		} else {
			System.out.println("No Exist");
		}
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
		
		if(this.md.getCollection("edge").find(Filters.eq("_id", label)).first()==null) {
			Document doc=new Document();
			Document propertyDoc=new Document();
			
			doc.append("_id", label);
			doc.append("label", label);
			doc.append("source", inVertex.getId());
			doc.append("target", outVertex.getId());
			doc.append("property", propertyDoc);
			
			this.md.getCollection("vertex").insertOne(doc);
			
			MyEdge edge=new MyEdge(inVertex,outVertex,label);
			
			
			return edge;
		}
		else {
			System.out.println("Already Exist Edge");
			return null;
		}
		
		
		
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		// TODO Auto-generated method stub
		
		if(this.md.getCollection("edge").find(Filters.eq("_id", label)).first()!=null) {
			
			
			Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", label)).first();
			Document propertyDoc=(Document) edgeDoc.get("property");
			
			
			
			
			MyEdge edge=new MyEdge(inVertex,outVertex,label);
			
			
			//property 가져오기 
			for(String key:propertyDoc.keySet()) {
				edge.setProperty(key, propertyDoc.get(key));
			}
			
			
			
			
			
			return edge;
		}
		else {
			System.out.println("No Exist");
			return null;
		}
		
	}

	@Override
	public Edge getEdge(String id) {
		// TODO Auto-generated method stub
		
		if(this.md.getCollection("edge").find(Filters.eq("_id", id)).first()==null) {
			
			
			Document edgeDoc=this.md.getCollection("edge").find(Filters.eq("_id", id)).first();
			Document propertyDoc=(Document) edgeDoc.get("property");
			
			
			MyVertex sV=new MyVertex(edgeDoc.getString("source"));
			MyVertex tV=new MyVertex(edgeDoc.getString("target"));
			String label=edgeDoc.getString("label");
			
			MyEdge edge=new MyEdge(sV,tV,label);
			
			
			//property 가져오기 
			for(String key:propertyDoc.keySet()) {
				edge.setProperty(key, propertyDoc.get(key));
			}
			
			
			
			
			
			return edge;
		}
		else {
			System.out.println("No Exist");
			return null;
		}
		
	}

	@Override
	public void removeEdge(Edge edge) {
		// TODO Auto-generated method stub
		if (this.md.getCollection("edge").find(Filters.eq("_id", edge.getId())).first() != null) {

			this.md.getCollection("edge").findOneAndDelete(Filters.eq("_id", edge.getId()));
			
		} else {
			System.out.println("No Exist");
		}
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
