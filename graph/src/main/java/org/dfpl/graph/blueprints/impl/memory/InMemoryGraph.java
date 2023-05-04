package org.dfpl.graph.blueprints.impl.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class InMemoryGraph implements Graph {

	private HashMap<String, Vertex> vertices;
	private HashMap<String, Edge> edges;

	public InMemoryGraph() {
		vertices = new HashMap<String, Vertex>();
		edges = new HashMap<String, Edge>();
	}

	@Override
	public Vertex addVertex(String id) throws IllegalArgumentException {
		if (id.contains("|")) {
			throw new IllegalArgumentException("id cannot contain '|'");
		}

		if (vertices.containsKey(id)) {
			return vertices.get(id);
		} else {
			Vertex newV = new InMemoryVertex(this, id);
			vertices.put(id, newV);
			return newV;
		}
	}

	@Override
	public Vertex getVertex(String id) {
		return vertices.get(id);
	}

	@Override
	public void removeVertex(Vertex vertex) {
		Vertex removedV = vertices.remove(vertex.getId());
		if (removedV == null)
			return;

		String removedVID = removedV.getId();
		Iterator<Entry<String, Edge>> iterator = edges.entrySet().iterator();
		while (iterator.hasNext()) {
			Edge e = iterator.next().getValue();
			if (e.getVertex(Direction.OUT).getId().equals(removedVID)
					|| e.getVertex(Direction.IN).getId().equals(removedVID)) {
				iterator.remove();
			}
		}
	}

	@Override
	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		return vertices.values().parallelStream().filter(v -> {
			Object tValue = v.getProperty(key);
			if (tValue != null && tValue == value)
				return true;
			else
				return false;
		}).toList();
	}

	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) throws IllegalArgumentException, NullPointerException {
		if (label.contains("|")) {
			throw new IllegalArgumentException("label cannot contain '|'");
		}

		if (outVertex == null) {
			throw new NullPointerException("outVertex cannot be null");
		}

		if (inVertex == null) {
			throw new NullPointerException("inVertex cannot be null");
		}

		String edgeID = InMemoryEdge.getID(outVertex, label, inVertex);
		if (edges.containsKey(edgeID)) {
			return edges.get(edgeID);
		} else {
			Edge newEdge = new InMemoryEdge(this, outVertex, label, inVertex);
			edges.put(edgeID, newEdge);
			return newEdge;
		}
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		return edges.get(InMemoryEdge.getID(outVertex, label, inVertex));
	}

	@Override
	public Edge getEdge(String id) {
		return edges.get(id);
	}

	@Override
	public void removeEdge(Edge edge) {
		edges.remove(edge.getId());
	}

	@Override
	public Collection<Edge> getEdges() {
		return edges.values();
	}

	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		return edges.values().parallelStream().filter(e -> {
			Object tValue = e.getProperty(key);
			if (tValue != null && tValue == value)
				return true;
			else
				return false;
		}).toList();
	}

	@Override
	public void shutdown() {
		// Not necessary for in-memory implementation
	}
}
