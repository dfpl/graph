package org.dfpl.graph.api.jincheol;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBController {

	private String url="mongodb://localhost:27017";
	private String DBName="emaildata";
	private MongoDatabase db;
	public void setMongoDBClient() {
		
		MongoClient mongoClient=MongoClients.create(url);
		db=mongoClient.getDatabase(DBName);
		
	}
	
	public MongoCollection<Document> getCollection(String collectionName){
		MongoCollection<Document> collection=db.getCollection(collectionName);
		
		return collection;
	}
}
