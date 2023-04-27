package org.dfpl.graph.api.persistent.mariadb.bsjoe;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Set;

import org.json.JSONObject;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class MVertex implements Vertex {

	private Graph g;
	private String id;
	
	

	public MVertex(Graph g, String id) {
		this.g = g;
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public Object getProperty(String key) {
		try {
			ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT properties FROM v WHERE id = \'" + id + "\';");
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
			ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT properties FROM v WHERE id = \'" + id + "\';");
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
			ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT properties FROM v WHERE id = \'" + id + "\';");
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
			
			((MGraph) g).getStatement().executeUpdate("UPDATE v SET properties = \'" + obj.toString() + "\' WHERE id = \'" + id + "\';");
			
			return;
		} catch (Exception e) {
		}
	}

	

	@Override
	public Object removeProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) throws IllegalArgumentException {
		if(direction.equals(Direction.BOTH)) {
            throw new IllegalArgumentException("Direction.BOTH is not allowed");
        }
		
		String str = "";
		for(int i = 0; i < labels.length; i++) {
			if (i == 0) {
				str += "AND (label = \'" + labels[i] + "\'";
			}
			else
			{
				str += "OR label = \'" + labels[i] + "\'";
			}
			if(i == labels.length - 1) {
				str += ")";
			}
		}
		
		
		java.util.ArrayList<Edge> list = new java.util.ArrayList<Edge>();
		try {
			if (direction.equals(Direction.OUT)) {
				ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT * FROM e WHERE outV = \'" + id + "\'" + str + ";");
				while (rs.next()) {
					String tid = rs.getString(1);
					String _toutV = rs.getString(2);
					String _tinV = rs.getString(3);
					String tlabel = rs.getString(4);
					
					Vertex toutV = ((MGraph) g).getVertex(_toutV);
					Vertex tinV = ((MGraph) g).getVertex(_tinV);
					
					list.add(new MEdge(g, tid, toutV, tinV, tlabel));
				}
			} else if (direction.equals(Direction.IN)) {
				ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT * FROM e WHERE inV = \'" + id + "\'" + str + ";");
				while (rs.next()) {
					String tid = rs.getString(1);
					String _toutV = rs.getString(2);
					String _tinV = rs.getString(3);
					String tlabel = rs.getString(4);
					
					Vertex toutV = ((MGraph) g).getVertex(_toutV);
					Vertex tinV = ((MGraph) g).getVertex(_tinV);
					
					list.add(new MEdge(g, tid, toutV, tinV, tlabel));
				}
			}
		} catch (Exception e) {
		}
		return list;
	}

	
	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException {
		if(direction.equals(Direction.BOTH)) {
			throw new IllegalArgumentException("Direction.BOTH is not allowed");
	    }
		String str = "";
		for(int i = 0; i < labels.length; i++) {
			if (i == 0) {
				str += "AND (label = \'" + labels[i] + "\'";
			}
			else
			{
				str += "OR label = \'" + labels[i] + "\'";
			}
			if(i == labels.length - 1) {
				str += ")";
			}
		}
		
		
		java.util.ArrayList<Vertex> list = new java.util.ArrayList<Vertex>();
		try {
			if (direction.equals(Direction.OUT)) {
				ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT * FROM e WHERE outV = \'" + id + "\'" + str + ";");
				while (rs.next()) {
					String tid = rs.getString(3);
					list.add(new MVertex(g, tid));
				}
			} else if (direction.equals(Direction.IN)) {
				ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT * FROM e WHERE inV = \'" + id + "\'" + str + ";");
				while (rs.next()) {
					String tid = rs.getString(2);
					list.add(new MVertex(g, tid));
				}
			}
		} catch (Exception e) {
		}
		return list;
	}
	
	@Override
	public Collection<Vertex> getTwoHopVertices(Direction direction, String... labels) throws IllegalArgumentException {
		return this.getVertices(direction).stream().flatMap(v -> v.getVertices(direction).stream())
                .flatMap(v -> v.getVertices(direction).stream()).toList();
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String key, Object value, String... labels)
			throws IllegalArgumentException {
		if(direction.equals(Direction.BOTH)) {
			throw new IllegalArgumentException("Direction.BOTH is not allowed");
	    }
		String str = "";
		for(int i = 0; i < labels.length; i++) {
			if (i == 0) {
				str += "AND (label = \'" + labels[i] + "\'";
			}
			else
			{
				str += "OR label = \'" + labels[i] + "\'";
			}
			if(i == labels.length - 1) {
				str += ")";
			}
		}
		
		
		java.util.ArrayList<Vertex> list = new java.util.ArrayList<Vertex>();
		try {
			if (direction.equals(Direction.OUT)) {
				ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT * FROM e WHERE outV = \'" + id + "\'" + str + ";");
				while (rs.next()) {
					Object result = rs.getObject(5);
					JSONObject obj = new JSONObject(result.toString());
					Object prop = obj.get(key);
					if(prop.equals(value))
					{
						String tid = rs.getString(3);
						list.add(new MVertex(g, tid));
					}
				}
			} else if (direction.equals(Direction.IN)) {
				ResultSet rs = ((MGraph) g).getStatement().executeQuery("SELECT * FROM e WHERE inV = \'" + id + "\'" + str + ";");
				while (rs.next()) {
					Object result = rs.getObject(5);
					JSONObject obj = new JSONObject(result.toString());
					Object prop = obj.get(key);
					if(prop.equals(value))
					{
						String tid = rs.getString(2);
						list.add(new MVertex(g, tid));
					}
				}
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object obj) {
		return id.equals(((Vertex) obj).getId());
	}
}
