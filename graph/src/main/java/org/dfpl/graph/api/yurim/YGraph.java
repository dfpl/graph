package org.dfpl.graph.api.yurim;
import com.mongodb.MongoClientException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;
import org.bson.Document;
import org.dfpl.graph.api.memory.InMemoryVertex;

import java.util.Collection;
import java.util.HashSet;

public class YGraph implements Graph {
    private MongoClient mongo;
    private MongoDatabase database;
    private Document doc;
    private MongoCollection<Document> verticies;
    private MongoCollection<Document> edges;


    // 데이터베이스 이름이 그래프 이름
    // 컬렉션은 Edges, Vertex
    // Key : Node Edges Properties Label
    // dbID, dbPW, dbName = graphName
    public YGraph(String dbID, String dbPW, String dbName) throws MongoClientException {
        mongo = new MongoClient("localhost", 27017);
        database = mongo.getDatabase(dbName);
        verticies = database.getCollection("v", Document.class);
        edges = database.getCollection("e", Document.class);

        System.out.println("myCollection selected successfully");
    }

    @Override
    public Vertex addVertex(String id) throws IllegalArgumentException {
        try {
            verticies.insertOne(new Document().append("_id", id));
        }catch (Exception e){}
        return new YVertex(this, id);
    }

    @Override
    public Vertex getVertex(String id) {
        Document doc = verticies.find(new Document().append("_id", id)).first();
        if (doc != null){
            return new YVertex(this, id);
        }
        else{
            return null;
        }
    }

    @Override
    public void removeVertex(Vertex vertex) {

    }

    @Override
    public Collection<Vertex> getVertices() {
        HashSet<Vertex> result = new HashSet<Vertex>();
        MongoCursor<Document> iter = verticies.find().iterator();
        while(iter.hasNext()){
            Document doc = iter.next();
            result.add(new YVertex(this, this.doc.getString("_id")));
        }
        return result;
    }

    @Override
    public Collection<Vertex> getVertices(String key, Object value) {
        return null;
    }

    @Override
    public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) throws IllegalArgumentException, NullPointerException {
        return null;
    }

    @Override
    public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
        return null;
    }

    @Override
    public Edge getEdge(String id) {
        return null;
    }

    @Override
    public void removeEdge(Edge edge) {

    }

    @Override
    public Collection<Edge> getEdges() {
        return null;
    }

    @Override
    public Collection<Edge> getEdges(String key, Object value) {
        return null;
    }

    @Override
    public void shutdown() {

    }
}

