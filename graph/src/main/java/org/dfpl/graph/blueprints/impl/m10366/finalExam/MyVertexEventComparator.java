package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.Comparator;

public class MyVertexEventComparator implements Comparator<MyVertexEvent>{
	@Override
	public int compare(MyVertexEvent event1, MyVertexEvent event2) {
		
		
		if(event1.getTime()>event2.getTime())
			return 1;
		else if(event1.getTime()<event2.getTime())
			return -1;
		else
			return 0;
	}
}
