package org.dfpl.graph.api.jincheol;


import org.bson.Document;


import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;


public class MyMongoDB {

	
	private static final String url="mongodb://localhost:27017";
	private static final String database="CollegeMsg";
	private MongoDatabase mongoDB;
	private MongoCollection<Document> collection;
	
	
	public MyMongoDB() {
		
		this.mongoDB=MongoClients.create(url).getDatabase(database);
		
		
	}
	
	/**
	 * vertex의 id와 property 저장 
	 * @param vertex
	 */
	
	
	public MongoCollection<Document> getCollection(String collectionName){
		return this.collection=mongoDB.getCollection(collectionName);
	}
	
	
	
	
	/**
	 * mongoDB에서 해당 vertex의 property update
	 * @param key
	 * @param value
	 */
	public void setVertexProperty(String id,String key,Object value) {
		
		//update property for vertex
		collection.findOneAndUpdate(Filters.eq("_id", id),Updates.set("property",((Document) collection.find(Filters.eq("_id", id)).first().get("property")).append(key,value) ));
		
	}
	
	
	public void insertEdge(MyEdge edge) {
		
		
		
		
	}
	
	
}
