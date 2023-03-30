package org.dfpl.graph.api.jincheol;

import java.util.Collection;
import java.util.HashSet;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyGraph implements Graph{

	private MongoClient client;
	private String url="mongodb://localhost:27017";
	private String DBName;
	private MongoDatabase db;
	private MongoCollection<Document> vertices;
	private MongoCollection<Document> edges;
	
	
	/**
	 * set graph
	 * @param DBName
	 */
	public MyGraph(String DBName) {
		client=MongoClients.create(url);
		this.DBName=DBName;
		db=client.getDatabase(DBName);
		vertices = db.getCollection("vertex", Document.class);
		edges = db.getCollection("edge", Document.class);
		
		
	}
	
	
	
	
	@Override
	public Vertex addVertex(String id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		HashSet<String> inEdgeSet=new HashSet<>();
		HashSet<String> outEdgeSet=new HashSet<>();
		
		Document doc=new Document("_id",id).append("inE",inEdgeSet).append("outE",outEdgeSet);
		
		vertices.insertOne(doc);
		
		return null;
	}

	@Override
	public Vertex getVertex(String id) {
		// TODO Auto-generated method stub
		Bson filter=Filters.eq("_id", id);
		Document doc=vertices.find(filter).first();
		
		Vertex v=new MyVertex(doc);
		
		return v;
	}

	@Override
	public void removeVertex(Vertex vertex) {
		// TODO Auto-generated method stub
		String id=vertex.getId();
		
		Bson filter=Filters.eq("_id", id);
		Document doc=vertices.find(filter).first();
		
		if(doc!=null) {
			vertices.deleteOne(filter);
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
