package org.dfpl.graph.blueprints.impl.m50119;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@SuppressWarnings("unused")
public class YVertex implements Vertex {

	private Graph g;
	private String id;
	private HashMap<String, Object> properties;

	public YVertex(Graph g, String id) {
		this.g = g;
		this.id = id;
		this.properties = new HashMap<String, Object>();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Object getProperty(String key) {
		return properties.get(key);
	}

	@Override
	public Set<String> getPropertyKeys() {
		return properties.keySet();
	}

	@Override
	public void setProperty(String key, Object value) {
		properties.put(key, value);
	}

	@Override
	public Object removeProperty(String key) {
		return properties.get(key);
	}

	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) throws IllegalArgumentException {
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException {
		return null;
	}

	@Override
	public Collection<Vertex> getTwoHopVertices(Direction direction, String... labels) throws IllegalArgumentException {
		return null;
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String key, Object value, String... labels)
			throws IllegalArgumentException {
		return null;
	}

	@Override
	public void remove() {

	}
}
