package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.Comparator;

public class MyEdgeEventComparator implements Comparator<MyEdgeEvent>{

	@Override
	public int compare(MyEdgeEvent event1, MyEdgeEvent event2) {
		
		
		if(event1.getTime()>event2.getTime())
			return 1;
		else if(event1.getTime()<event2.getTime())
			return -1;
		else
			return 0;
	}

}
