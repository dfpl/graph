package org.dfpl.graph.api.jincheol;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;


import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


public class MongoDBController {

	private String url="mongodb://localhost:27017";
	private String DBName;
	private MongoDatabase db;
	
	
	/**
	 * 생성자
	 * @param DBName
	 */
	public MongoDBController(String DBName) {
		
		this.setDBName(DBName);
		
	}
	
	/**
	 * database name 반환
	 * @return database name
	 */
	public String getDBName() {
		return DBName;
	}
	
	/**
	 * database name setting
	 * @param dBName
	 */
	public void setDBName(String dBName) {
		DBName = dBName;
	}
	
	/**
	 * mongoDB database client 
	 */
	public void setMongoDBClient() {
		
		MongoClient mongoClient=MongoClients.create(url);
		db=mongoClient.getDatabase(getDBName());
		
	}
	
	/**
	 * 해당 이름의 collection 반환 
	 * @param collectionName
	 * @return collection
	 */
	public MongoCollection<Document> getCollection(String collectionName){
		MongoCollection<Document> collection=db.getCollection(collectionName);
		
		return collection;
	}
	
	/**
	 * 해당 이름의 collection drop 
	 * @param collectionName
	 */
	public void deleteCollection(String collectionName) {
		MongoCollection<Document> collection=db.getCollection(collectionName);
		collection.drop();
	}

	/**
	 * edge data 입력 
	 * @param dataPath
	 * @throws IOException
	 */
	public void insertEdge(String dataPath) throws IOException {
		
		MongoCollection<Document> collection=getCollection("edge");
		
		BufferedReader br = new BufferedReader(new FileReader(dataPath));
		
		
		String line=br.readLine();
		Document doc=null;
		
		while(line!=null) {
			
			if(line.startsWith("#")) {
				line=br.readLine();
				continue;
			}
			
			String nodes[]=line.split("\t");
			
			
			doc=new Document("outV",nodes[0]).append("inV", nodes[1]).append("label",nodes[0]+"|"+nodes[1]).append("_id", nodes[0]+"|"+nodes[1]);
			collection.insertOne(doc);
			
			
			
			line=br.readLine();
		}
		
		br.close();
		
		
	}
	/**
	 * vertex data 입력 
	 * inE,outE = HashSet
	 * @param dataPath
	 * @throws IOException
	 */
	public void insertVertex(String dataPath) throws IOException {
		
		MongoCollection<Document> collection=getCollection("vertex");
		
		BufferedReader br = new BufferedReader(new FileReader(dataPath));
		
	
		
		String line=br.readLine();
		Document doc=null;
		
		HashSet<String> nodeSet=new HashSet<>();
		HashSet<String> inEdgeSet=new HashSet<>();
		HashSet<String> outEdgeSet=new HashSet<>();
		
		
		while(line!=null) {
			
			if(line.startsWith("#")) {
				line=br.readLine();
				continue;
			}
			
			String nodes[]=line.split("\t");
			
			nodeSet.add(nodes[0]);
			nodeSet.add(nodes[1]);
			
			
			
			line=br.readLine();
		}
		
		
		
		for(String node:nodeSet) {
			
			doc=new Document("_id",node).append("name", node).append("inE",inEdgeSet).append("outE",outEdgeSet);
			collection.insertOne(doc);
			
		}
		
	}
	
	
	public void updateVertex() throws ParseException {
		MongoCollection<Document> edgeCol=getCollection("edge");
		MongoCollection<Document> vertexCol=getCollection("vertex");
		
		
		MongoCursor<Document> edgeCur=edgeCol.find().iterator();
		
		JSONParser jsonParser=new JSONParser();
		JSONObject jsonDoc;
		
		while(edgeCur.hasNext()) {
			String edgeDoc=edgeCur.next().toJson();
			jsonDoc=(JSONObject)jsonParser.parse(edgeDoc);
			
			String outVertexName=(String)jsonDoc.get("outV");
			String inVertexName=(String)jsonDoc.get("inV");
			String edgeID=(String)jsonDoc.get("_id");
			
			Bson filter1=Filters.eq("_id", outVertexName);
			Bson filter2=Filters.eq("_id", inVertexName);
			
			HashSet<String> outESet;
			HashSet<String> inESet;
			
			Document doc=vertexCol.find(filter1).first();
			
			outESet=new HashSet<>();//기존 값 어캐 가져 올 것인가 
			
			outESet.add(edgeID);
			
			
			vertexCol.updateOne(filter1, new Document("$set", new Document("outE", outESet)));
			
			
			//////////////////
			
			doc=vertexCol.find(filter2).first();
			inESet=new HashSet<>();
			inESet.add(edgeID);
			
			vertexCol.updateOne(filter2, new Document("$set", new Document("inE", inESet)));
		
		}
		
		
	}
	
	
}
