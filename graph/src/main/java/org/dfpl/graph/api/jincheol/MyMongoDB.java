package org.dfpl.graph.api.jincheol;


import org.bson.Document;


import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class MyMongoDB {

	
	private static final String url="mongodb://localhost:27017";
	private static final String database="CollegeMsg";
	private MongoDatabase mongoDB;
	
	
	
	public MyMongoDB() {
		
		this.mongoDB=MongoClients.create(url).getDatabase(database);
		
		
	}
	
	/**
	 * vertex의 id와 property 저장 
	 * @param vertex
	 */
	
	
	public MongoCollection<Document> getCollection(String collectionName){
		return mongoDB.getCollection(collectionName);
	}
	
	
	
	
	
	
	
	
	
}
