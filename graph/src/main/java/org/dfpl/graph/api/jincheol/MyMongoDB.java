package org.dfpl.graph.api.jincheol;


import org.bson.Document;


import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


public class MyMongoDB {

	
	private static final String url="mongodb://localhost:27017";
	private static final String database="dblp";
	private MongoDatabase mongoDB;
	private MongoCollection<Document> vertexCollection;
	private MongoCollection<Document> edgeCollection;
	
	public MyMongoDB() {
		
		this.mongoDB=MongoClients.create(url).getDatabase(database);
		this.vertexCollection=mongoDB.getCollection("vertex");
		this.edgeCollection=mongoDB.getCollection("edge");
	}
	
	/**
	 * vertex의 id와 property 저장 
	 * @param vertex
	 */
	public void insertVertex(MyVertex vertex) {
		
		
		
		Document doc=new Document();
		Document propertyDoc=new Document();
		
		doc.append("_id", vertex.getId());
		
		if(vertex.getPropertyKeys()==null) {
			//property empty
		}
		else {
			for(String key:vertex.getPropertyKeys()) {
				propertyDoc.put(key, vertex.getProperty(key));
			}
		}
				
		doc.append("property", propertyDoc);
		
		vertexCollection.insertOne(doc);
		
	}
	
	
	/**
	 * mongoDB에서 해당 vertex의 property update
	 * @param key
	 * @param value
	 */
	public void setVertexProperty(String id,String key,Object value) {
		//vertexCollection.findOneAndUpdate(null, null);
		Document vertexDoc=vertexCollection.find(Filters.eq("_id", id)).first();
		
		
		
	}
	
	
	public void insertEdge(MyEdge edge) {
		
		
		
		
	}
	
	
}
