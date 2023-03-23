package org.dfpl.graph.api.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class InMemoryVertex implements Vertex {

    private Graph g;
    private String id;
    private HashMap<String, Object> properties;

    public InMemoryVertex(Graph g, String id) {
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
        if (direction.equals(Direction.OUT)) {
            return g.getEdges().parallelStream().filter(e -> {
                if (labels.length != 0) {
                    boolean doFilter = true;
                    for (String label : labels) {
                        if (e.getLabel().equals(label)) {
                            doFilter = false;
                            break;
                        }
                    }
                    if (doFilter)
                        return false;
                }

                if (e.getVertex(Direction.OUT).getId().equals(this.id))
                    return true;
                else
                    return false;
            }).toList();
        } else if (direction.equals(Direction.IN)) {
            return g.getEdges().parallelStream().filter(e -> {
                if (labels.length != 0) {
                    boolean doFilter = true;
                    for (String label : labels) {
                        if (e.getLabel().equals(label)) {
                            doFilter = false;
                            break;
                        }
                    }
                    if (doFilter)
                        return false;
                }

                if (e.getVertex(Direction.IN).getId().equals(this.id))
                    return true;
                else
                    return false;
            }).toList();
        } else {
            throw new IllegalArgumentException("Direction.BOTH is not allowed");
        }
    }

    @Override
    public Collection<Vertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException {
        if (direction.equals(Direction.OUT)) {
            return g.getEdges().parallelStream().filter(e -> {
                if (labels.length != 0) {
                    boolean doFilter = true;
                    for (String label : labels) {
                        if (e.getLabel().equals(label)) {
                            doFilter = false;
                            break;
                        }
                    }
                    if (doFilter)
                        return false;
                }

                if (e.getVertex(Direction.OUT).getId().equals(this.id))
                    return true;
                else
                    return false;
            }).map(e -> e.getVertex(Direction.IN)).toList();
        } else if (direction.equals(Direction.IN)) {
            return g.getEdges().parallelStream().filter(e -> {
                if (labels.length != 0) {
                    boolean doFilter = true;
                    for (String label : labels) {
                        if (e.getLabel().equals(label)) {
                            doFilter = false;
                            break;
                        }
                    }
                    if (doFilter)
                        return false;
                }

                if (e.getVertex(Direction.IN).getId().equals(this.id))
                    return true;
                else
                    return false;
            }).map(e -> e.getVertex(Direction.OUT)).toList();
        } else {
            throw new IllegalArgumentException("Direction.BOTH is not allowed");
        }
    }

    @Override
    public Collection<Vertex> getTwoHopVertices(Direction direction, String... labels) throws IllegalArgumentException {
        return this.getVertices(direction).stream().flatMap(v -> v.getVertices(direction).stream())
                .flatMap(v -> v.getVertices(direction).stream()).toList();
    }

    @Override
    public Collection<Vertex> getVertices(Direction direction, String key, Object value, String... labels)
            throws IllegalArgumentException {
        if (direction.equals(Direction.OUT)) {
            return g.getEdges().parallelStream().filter(e -> {
                if (labels.length != 0) {
                    boolean doFilter = true;
                    for (String label : labels) {
                        if (e.getLabel().equals(label)) {
                            doFilter = false;
                            break;
                        }
                    }
                    if (doFilter)
                        return false;
                }

                if (!e.getVertex(Direction.OUT).getId().equals(this.id))
                    return false;

                if (e.getProperty(key) == null || e.getProperty(key) != value)
                    return false;

                return true;

            }).map(e -> e.getVertex(Direction.IN)).toList();
        } else if (direction.equals(Direction.IN)) {
            return g.getEdges().parallelStream().filter(e -> {
                if (labels.length != 0) {
                    boolean doFilter = true;
                    for (String label : labels) {
                        if (e.getLabel().equals(label)) {
                            doFilter = false;
                            break;
                        }
                    }
                    if (doFilter)
                        return false;
                }

                if (!e.getVertex(Direction.IN).getId().equals(this.id))
                    return false;

                if (e.getProperty(key) == null || e.getProperty(key) != value)
                    return false;

                return true;
            }).map(e -> e.getVertex(Direction.OUT)).toList();
        } else {
            throw new IllegalArgumentException("Direction.BOTH is not allowed");
        }
    }


    @Override
    public void remove() {
        g.removeVertex(this);
    }

    @Override
    public boolean equals(Object obj) {
        return id.equals(((Vertex) obj).getId());
    }
}
