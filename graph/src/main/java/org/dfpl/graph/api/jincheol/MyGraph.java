package org.dfpl.graph.api.jincheol;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.tinkerpop.blueprints.revised.Direction;
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
	
	
	public void insertData(String dataPath) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(dataPath));
		
		HashSet<String> vertexSet=new HashSet<>();
		
		String line=br.readLine();
		
		
		while(line!=null) {
			
			if(line.startsWith("#")) {
				line=br.readLine();
				continue;
			}
			
			String nodes[]=line.split("\t");
			
			vertexSet.add(nodes[0]);
			vertexSet.add(nodes[1]);
			
			
			
			
			line=br.readLine();
		}
		
		br.close();
		
		for(String v:vertexSet) {
			
			System.out.println(addVertex(v).getId());
		}
		
		
		//input edges
		br = new BufferedReader(new FileReader(dataPath));
		line=br.readLine();
		
		
		while(line!=null) {
			
			if(line.startsWith("#")) {
				line=br.readLine();
				continue;
			}
			
			String nodes[]=line.split("\t");
			
			Vertex outV=this.getVertex(nodes[0]);
			Vertex inV=this.getVertex(nodes[1]);
			
			this.addEdge(outV, inV, nodes[0]+"|"+nodes[1]);
			
			line=br.readLine();
		}
		
		br.close();
		
		
	}
	
	
	
	@Override
	public Vertex addVertex(String id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		ArrayList<String> inEdgeSet=new ArrayList<>();
		ArrayList<String> outEdgeSet=new ArrayList<>();
		
		Document doc=new Document("_id",id).append("inE",inEdgeSet).append("outE",outEdgeSet);
		
		vertices.insertOne(doc);
		
		return new MyVertex(doc);
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
		
		ArrayList<Vertex> vertexList=new ArrayList<>();
		
		MongoCursor<Document> vertexCur=vertices.find().iterator();
		
		while(vertexCur.hasNext()) {
			
			vertexList.add(new MyVertex(vertexCur.next()));
		}
		
		return vertexList;
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
		
		
		Document doc=new Document("_id",label).append("label",label).append("outV",outVertex).append("inV", inVertex);
		
		edges.insertOne(doc);
		
		
		//update vertex edge list
		Bson filter=Filters.eq("_id", outVertex.getId());
		
		Document outV=vertices.find(filter).first();
		
		ArrayList<String> outE=outV.get("outE", ArrayList.class);
		outE.add(label);
		
		vertices.updateOne(filter, new Document("$set", new Document("outE", outE)));
		
		
		filter=Filters.eq("_id", inVertex.getId());
		
		Document inV=vertices.find(filter).first();
		
		ArrayList<String> inE=outV.get("outE", ArrayList.class);
		inE.add(label);
		
		vertices.updateOne(filter, new Document("$set", new Document("inE", inE)));
		
		return new MyEdge(doc);
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		// TODO Auto-generated method stub
		
		Bson filter=Filters.eq("label", label);
		Document doc=edges.find(filter).first();
		
		
		return new MyEdge(doc);
		
	}

	@Override
	public Edge getEdge(String id) {
		// TODO Auto-generated method stub
		
		Bson filter=Filters.eq("_id", id);
		Document doc=edges.find(filter).first();
		return new MyEdge(doc);
	}

	@Override
	public void removeEdge(Edge edge) {
		// TODO Auto-generated method stub
		String id=edge.getId();
		
		Bson filter=Filters.eq("_id", id);
		Document doc=edges.find(filter).first();
		
		if(doc!=null) {
			edges.deleteOne(filter);
		}
		
		
		MyVertex outV=(MyVertex) edge.getVertex(Direction.OUT);
		MyVertex inV=(MyVertex) edge.getVertex(Direction.IN);
		
		outV.removeEoutE(edge.getLabel());
		inV.removeEinE(edge.getLabel());
		
		
	}

	@Override
	public Collection<Edge> getEdges() {
		// TODO Auto-generated method stub
		
		ArrayList<Edge> edgeList=new ArrayList<>();
		
		MongoCursor<Document> edgeCur=edges.find().iterator();
		
		while(edgeCur.hasNext()) {
			
			edgeList.add(new MyEdge(edgeCur.next()));
		}
		
		return edgeList;
		
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
