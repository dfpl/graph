package org.dfpl.graph.blueprints.impl.m10621;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONObject;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class MEdge implements Edge {

	private Graph g;
	private String id;
	private Vertex inVertex;
	private Vertex outVertex;
	private String label;
	private HashMap<String, Object> properties;

	public MEdge(Graph g, String id, Vertex inVertex, Vertex outVertex, String label) {
		this.g = g;
		this.id = id;
		this.outVertex = outVertex;
		this.inVertex = inVertex;
		this.label = label;
	}
	
	@Override
	public String getId() {
		return id;
	}
	

	@Override
	public Object getProperty(String key) {
		try {
			ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT properties FROM e WHERE id = \'" + id + "\'");
			rs.next();
			Object result = rs.getObject(1);
			JSONObject obj = new JSONObject(result.toString());
			Object prop = obj.get(key);
			if(prop instanceof java.math.BigDecimal) {
				prop = ((java.math.BigDecimal) prop).doubleValue();
			}
			return prop;
		}catch (Exception e) {
		}
		return null;
	}

	@Override
	public Set<String> getPropertyKeys() {
		try {
			ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT properties FROM e WHERE id = \'" + id + "\'");
			rs.next();
			Object result = rs.getObject(1);
			JSONObject obj = new JSONObject(result.toString());
			return obj.keySet();
		}catch (Exception e) {
		}
		return null;
	}

	@Override
	public void setProperty(String key, Object value) {
		try {
			ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT properties FROM e WHERE id = \'" + id + "\'");
			rs.next();
			Object result = rs.getObject(1);
			JSONObject obj = null;
			if(result == null) {
				obj = new JSONObject();
				obj.put(key, value);
			}else {
				obj = new JSONObject(result.toString());
				obj.put(key, value);
			}
			
			((MGraph) g).getStatement().executeUpdate("UPDATE e SET properties = '" + obj.toString() + "' WHERE id = \'" + id + "\'");
			
			return;
		} catch (Exception e) {
		}
	}

	@Override
	public Object removeProperty(String key) {
		return properties.get(key);
	}

	@Override
	public Vertex getVertex(Direction direction) throws IllegalArgumentException {
		if (direction.equals(Direction.OUT)) {
			return outVertex;
		} else if (direction.equals(Direction.IN)) {
			return inVertex;
		} else {
			throw new IllegalArgumentException("Direction.BOTH is not allowed");
		}
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void remove() {
		g.removeEdge(this);
	}

	public static String getID(Vertex outVertex, String label, Vertex inVertex) {
		return outVertex.getId() + "|" + label + "|" + inVertex.getId();
	}

	@Override
	public boolean equals(Object obj) {
		return id.equals(((Edge) obj).getId());
	}
}
