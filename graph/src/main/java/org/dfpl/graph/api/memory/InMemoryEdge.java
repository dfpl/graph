package org.dfpl.graph.api.memory;

import java.util.HashMap;
import java.util.Set;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class InMemoryEdge implements Edge {

	private Graph g;
	private String id;
	private Vertex outVertex;
	private String label;
	private Vertex inVertex;
	private HashMap<String, Object> properties;

	public InMemoryEdge(Graph g, Vertex outVertex, String label, Vertex inVertex) {
		this.g = g;
		this.outVertex = outVertex;
		this.label = label;
		this.inVertex = inVertex;
		this.id = getID(outVertex, label, inVertex);
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
