package org.dfpl.graph.api.yurim;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

import java.util.HashMap;
import java.util.Set;

public class YEdge implements Edge {
    private Graph g;
    private String id;
    private Vertex outVertex;
    private String label;
    private Vertex inVertex;
    private HashMap<String, Object> properties;

    public YEdge(Graph g, Vertex outVertex, String label, Vertex inVertex) {
        this.g = g;
        this.outVertex = outVertex;
        this.label = label;
        this.inVertex = inVertex;
        this.id = getId();
        this.properties = new HashMap<String, Object>();
    }

    @Override
    public Vertex getVertex(Direction direction) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Object getProperty(String key) {
        return null;
    }

    @Override
    public Set<String> getPropertyKeys() {
        return null;
    }

    @Override
    public void setProperty(String key, Object value) {

    }

    @Override
    public Object removeProperty(String key) {
        return null;
    }
}
