package org.dfpl.graph.blueprints.impl.m10366;

import java.util.Set;

import org.bson.Document;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Vertex;

public class MyPersistentEdge implements Edge{
	
	private MyMongoDB md;
	
	private String id;
	private String label;
	
	
	public MyPersistentEdge(String id,String label,MyMongoDB md) {
		
		this.md=md;
		
		this.id=id;
		this.label=label;
		
	}
	
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public Object getProperty(String key) {
		// TODO Auto-generated method stub
		
		
		if(this.md.getCollection("edge").find(Filters.eq("_id",this.id)).first()!=null) {
			
			Document propertyDoc=(Document) this.md.getCollection("vertex").find(Filters.eq("_id", this.id)).first().get("property");
			return propertyDoc.get(key);
		}
		else {
			throw new IllegalArgumentException("no exist edge id");
		}
	}

	@Override
	public Set<String> getPropertyKeys() {
		// TODO Auto-generated method stub
		if(this.md.getCollection("edge").find(Filters.eq("_id",this.id)).first()!=null) {
			
			Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", this.id)).first().get("property");
			
			return propertyDoc.keySet();
			
		}
		else {
			throw new IllegalArgumentException("no exist edge id");
		}
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		if(this.md.getCollection("edge").find(Filters.eq("_id",this.id)).first()!=null) {
			
			this.md.getCollection("edge").findOneAndUpdate(Filters.eq("_id", id),Updates.set("property",((Document) this.md.getCollection("edge").find(Filters.eq("_id", id)).first().get("property")).append(key,value) ));
		}
		else {
			throw new IllegalArgumentException("no exist edge id");
		}
	}

	@Override
	public Object removeProperty(String key) {
		// TODO Auto-generated method stub
		if(this.md.getCollection("edge").find(Filters.eq("_id",this.id)).first()!=null) {
			
			Document propertyDoc=(Document) this.md.getCollection("edge").find(Filters.eq("_id", id)).first().get("property");
			
			this.md.getCollection("edge").findOneAndUpdate(Filters.eq("_id", id),Updates.set("property",((Document) this.md.getCollection("edge").find(Filters.eq("_id", id)).first().get("property")).remove(key) ));
			
			return propertyDoc;
		}
		else {
			throw new IllegalArgumentException("no exist edge id");
		}
	}

	@Override
	public Vertex getVertex(Direction direction) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(this.md.getCollection("edge").find(Filters.eq("_id", this.id)).first()!=null) {
			if(direction==Direction.IN) return new MyPersistentVertex((String) this.md.getCollection("edge").find(Filters.eq("_id", this.id)).first().get("source"),md);
			else if(direction==Direction.OUT) return new MyPersistentVertex((String) this.md.getCollection("edge").find(Filters.eq("_id", this.id)).first().get("target"),md);
			else {
				
				throw new IllegalArgumentException("not use both");
			}
		}
		else {
			throw new IllegalArgumentException("no exist edge id");
		}
		
		
		
		
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return this.label;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		if(this.md.getCollection("edge").find(Filters.eq("_id", this.id)).first()!=null) {
			this.md.getCollection("edge").findOneAndDelete(Filters.eq("_id", this.id));
		}
		else {
			throw new IllegalArgumentException("no exist edge id");
		}
	}

}
