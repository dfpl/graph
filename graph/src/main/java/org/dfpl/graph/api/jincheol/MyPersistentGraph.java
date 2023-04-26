package org.dfpl.graph.api.jincheol;

import java.util.ArrayList;
import java.util.Collection;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyPersistentGraph implements Graph{
	
	private MyMongoDB md;
	
	public MyPersistentGraph() {
		
		this.md=new MyMongoDB();
		
	}
	
	
	
	
	@Override
	public Vertex addVertex(String id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		

		
		if(this.md.getCollection("vertex").find(Filters.eq("_id", id)).first()==null) {
			Document doc=new Document();
			Document propertyDoc=new Document();
			ArrayList<String> inEdgeIDs=new ArrayList<>();
			ArrayList<String> outEdgeIDs=new ArrayList<>();
			
			doc.append("_id", id);	
			doc.append("property", propertyDoc);
			doc.append("inEdgeIDs", inEdgeIDs);
			doc.append("outEdgeIDs", outEdgeIDs);
			
			
			this.md.getCollection("vertex").insertOne(doc);
			
			MyPersistentVertex vertex=new MyPersistentVertex(id,this.md);
			return vertex;
		}
		else {
			//System.out.println("already exist vertex");
			return new MyPersistentVertex(id,md);
		}
		
		
		
		
		
		
	}

	@Override
	public Vertex getVertex(String id) {
		// TODO Auto-generated method stub
		if(this.md.getCollection("vertex").find(Filters.eq("_id", id)).first()!=null) {	
			return new MyPersistentVertex(id,md);
		}
		else {
			throw new IllegalArgumentException("no exist vertex id");
		}
		
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// TODO Auto-generated method stub
		if (this.md.getCollection("vertex").find(Filters.eq("_id", vertex.getId())).first() != null) 
			this.md.getCollection("vertex").findOneAndDelete(Filters.eq("_id", vertex.getId()));
		else 
			throw new IllegalArgumentException("no exist vertex");
		
	}

	@Override
	public Collection<Vertex> getVertices() {
		// TODO Auto-generated method stub
		ArrayList<Vertex> vertexList=new ArrayList<>();
		
		FindIterable<Document> docs=this.md.getCollection("vertex").find();
		
		for(Document doc:docs) 
			vertexList.add(new MyPersistentVertex(doc.getString("_id"),md));
			

		return vertexList;
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		// TODO Auto-generated method stub
		ArrayList<Vertex> vertexList=new ArrayList<>();
		
		FindIterable<Document> docs=this.md.getCollection("vertex").find();
		
		for(Document doc:docs) {
			
			Document propertyDoc=(Document) doc.get("property");
			
			if(propertyDoc.get(key).equals(value))
				vertexList.add(new MyPersistentVertex(doc.getString("_id"),md));
			
		}
			

		return vertexList;
	}

	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label)
			throws IllegalArgumentException, NullPointerException {
		// TODO Auto-generated method stub
		
		if(this.md.getCollection("edge").find(Filters.eq("_id", inVertex.getId()+"|"+label+"|"+outVertex.getId())).first()==null) {
			Document doc=new Document();
			Document propertyDoc=new Document();
			
			doc.append("_id", inVertex.getId()+"|"+label+"|"+outVertex.getId());
			doc.append("label", label);
			doc.append("source", inVertex.getId());
			doc.append("target", outVertex.getId());
			doc.append("property", propertyDoc);
			
			this.md.getCollection("edge").insertOne(doc);
			
			@SuppressWarnings("unchecked")
			ArrayList<String> edgeIDsForInVertex=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", inVertex.getId())).first().get("outEdgeIDs");
			edgeIDsForInVertex.add(inVertex.getId()+"|"+label+"|"+outVertex.getId());			
			
			this.md.getCollection("vertex").findOneAndUpdate(Filters.eq("_id", inVertex.getId()), Updates.set("outEdgeIDs",edgeIDsForInVertex));
			
			@SuppressWarnings("unchecked")
			ArrayList<String> edgeIDsForOutVertex=(ArrayList<String>) this.md.getCollection("vertex").find(Filters.eq("_id", outVertex.getId())).first().get("inEdgeIDs");
			edgeIDsForOutVertex.add(inVertex.getId()+"|"+label+"|"+outVertex.getId());			
			
			this.md.getCollection("vertex").findOneAndUpdate(Filters.eq("_id", outVertex.getId()), Updates.set("inEdgeIDs",edgeIDsForOutVertex));
			
			MyPersistentEdge edge=new MyPersistentEdge(inVertex.getId()+"|"+label+"|"+outVertex.getId(),label,md);
			
			
			
			
			return edge;
		}
		else {
			//System.out.println("already exist edge");
			return new MyPersistentEdge(inVertex.getId()+"|"+label+"|"+outVertex.getId(),label,md);
		}
		
		
		
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		// TODO Auto-generated method stub
		
		if(this.md.getCollection("edge").find(Filters.eq("_id", inVertex.getId()+"|"+label+"|"+outVertex.getId())).first()!=null) 
			return new MyPersistentEdge(inVertex.getId()+"|"+label+"|"+outVertex.getId(),label,md);
		else 
			throw new IllegalArgumentException("no exist edge");
		
		
	}

	@Override
	public Edge getEdge(String id) {
		// TODO Auto-generated method stub
		
		if(this.md.getCollection("edge").find(Filters.eq("_id", id)).first()!=null) 
			return new MyPersistentEdge(id,this.md.getCollection("edge").find(Filters.eq("_id", id)).first().getString("label"),md);
		else 
			throw new IllegalArgumentException("no exist edge");
		
		
	}

	@Override
	public void removeEdge(Edge edge) {
		// TODO Auto-generated method stub
		if (this.md.getCollection("edge").find(Filters.eq("_id", edge.getId())).first() != null)
			this.md.getCollection("edge").findOneAndDelete(Filters.eq("_id", edge.getId()));
	    else 
	    	throw new IllegalArgumentException("no exist edge");
		
	}

	@Override
	public Collection<Edge> getEdges() {
		// TODO Auto-generated method stub
		ArrayList<Edge> edgeList=new ArrayList<>();
		
		FindIterable<Document> docs=this.md.getCollection("edge").find();
		
		for(Document doc:docs)
			edgeList.add(new MyPersistentEdge(doc.getString("_id"),doc.getString("label"),md));

		return edgeList;
	}

	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		// TODO Auto-generated method stub
		ArrayList<Edge> edgeList=new ArrayList<>();
		
		FindIterable<Document> docs=this.md.getCollection("edge").find();
		
		for(Document doc:docs) {
			
			Document propertyDoc=(Document) doc.get("property");
			
			if(propertyDoc.get(key).equals(value))
				edgeList.add(new MyPersistentEdge(doc.getString("_id"),doc.getString("label"),md));
			
		}
			

		return edgeList;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
		this.md.getCollection("vertex").drop();
		this.md.getCollection("edge").drop();
		
	}

	
	
	
	
	

}
