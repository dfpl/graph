package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.ArrayList;
import java.util.Map;

import org.dfpl.graph.blueprints.impl.m10366.custom.EdgeEvent;
import org.dfpl.graph.blueprints.impl.m10366.custom.TemporalRelation;
import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;

import org.dfpl.graph.blueprints.impl.m10366.custom.VertexEvent;

import com.tinkerpop.blueprints.revised.Direction;

public class MyVertexEvent implements VertexEvent{

	
	
	//vertexevent는 edgeevent들을 포함하고 있어야 한다 
	public MyVertexEvent(long time){
		
		ArrayList<Integer> a =new ArrayList<>();
		
		a.iterator();
	}
	
	
	@Override
	public Iterable<VertexEvent> getVertexEvents(TemporalRelation r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object removeProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<EdgeEvent> getEdgeEvents(Direction d, String[] labels, TemporalRelation r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<VertexEvent> getVertexEvents(Direction d, String[] labels, TemporalRelation r) {
		// TODO Auto-generated method stub
		return null;
	}

}
