package org.dfpl.graph.blueprints.impl.m10366.custom;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Element;


public interface TimeEdge extends Element{
	/**
	 * Return the tail/out or head/in vertex.
	 *
	 * @param direction whether to return the tail/out or head/in vertex
	 * @return the tail/out or head/in vertex
	 * @throws IllegalArgumentException is thrown if a direction of both is provided
	 */
	public TimeVertex getVertex(Direction direction) throws IllegalArgumentException;

	/**
	 * Return the label associated with the edge.
	 *
	 * @return the edge label
	 */
	public String getLabel();

	/**
	 * Remove the element from the graph.
	 */
	public void remove();
	
	//time
	
	
	public EdgeEvent addEvent(long time);
	
	public EdgeEvent pickEvent(long time);
	
	public EdgeEvent removeEvent(long time);
	
	public Iterable<EdgeEvent> pickEvents(long time);
	
	public Iterable<EdgeEvent> getEdgeEvents(long time);
	
	
	
}
