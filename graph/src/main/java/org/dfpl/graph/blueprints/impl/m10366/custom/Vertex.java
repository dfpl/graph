package org.dfpl.graph.blueprints.impl.m10366.custom;

import java.util.Collection;

import com.tinkerpop.blueprints.revised.Direction;


public interface Vertex {

	/**
	 * Return the edges incident to the vertex according to the provided direction
	 * and edge labels.
	 *
	 * @param direction the direction of the edges to retrieve
	 * @param labels    the labels of the edges to retrieve
	 * @return an iterable of incident edges
	 * @throws IllegalArgumentException is thrown if a direction of both is provided
	 */
	public Collection<Edge> getEdges(Direction direction, String... labels) throws IllegalArgumentException;

	/**
	 * Return the vertices adjacent to the vertex according to the provided
	 * direction and edge labels. This method does not remove duplicate vertices
	 * (i.e. those vertices that are connected by more than one edge).
	 *
	 * @param direction the direction of the edges of the adjacent vertices
	 * @param labels    the labels of the edges of the adjacent vertices
	 * @return an iterable of adjacent vertices
	 * @throws IllegalArgumentException is thrown if a direction of both is provided
	 */
	public Collection<Vertex> getVertices(Direction direction, String... labels) throws IllegalArgumentException;

	public Collection<Vertex> getTwoHopVertices(Direction direction, String... labels) throws IllegalArgumentException;
	
	/**
	 * Return the vertices adjacent to the vertex according to the provided
	 * direction and edge labels. This method does not remove duplicate vertices
	 * (i.e. those vertices that are connected by more than one edge).
	 *
	 * @param direction the direction of the edges of the adjacent vertices
	 * @param key-value pair matched to in or out edges' property
	 * @param labels    the labels of the edges of the adjacent vertices
	 * @return an iterable of adjacent vertices
	 * @throws IllegalArgumentException is thrown if a direction of both is provided
	 */
	public Collection<Vertex> getVertices(Direction direction, String key, Object value, String... labels) throws IllegalArgumentException;

	/**
	 * Remove the element from the graph.
	 */
	public void remove();
	
	public VertexEvent addEvent(long time);
}
