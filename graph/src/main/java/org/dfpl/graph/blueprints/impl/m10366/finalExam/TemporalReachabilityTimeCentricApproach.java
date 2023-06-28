package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.dfpl.graph.blueprints.impl.m10366.custom.TimeVertex;

public class TemporalReachabilityTimeCentricApproach {

	private MyTimeGraph graph;

	private Long startTime;

	private HashMap<String, HashMap<String, Long>> gamma;

	private ArrayList<Long> timeList;
	// private TreeMap<Long, List<EdgeEvent>>

	public TemporalReachabilityTimeCentricApproach(MyTimeGraph graph, Long startTime) {

		this.graph = graph;
		this.startTime = startTime;
		this.gamma = new HashMap<>();

		this.initializeGamma();

	}

	public HashMap<String, HashMap<String, Long>> getGamma() {
		return this.gamma;
	}

	public ArrayList<Long> getTimeList() {
		return this.timeList;
	}

	public void initializeGamma() {

		HashSet<Long> timeSet = new HashSet<>();

		ArrayList<MyEdgeEvent> edgeEvents = (ArrayList<MyEdgeEvent>) this.graph.getEdgeEventsToList();

		// 1. hash set 으로 중복 없이 time 저장 후 time list에 정렬 저장

		timeSet.add(this.startTime);

		for (MyEdgeEvent edgeEvent : edgeEvents)
			if (edgeEvent.getTime() > this.startTime)
				timeSet.add(edgeEvent.getTime());

		this.timeList = new ArrayList<>(timeSet);

		// 오름차순 정렬
		Collections.sort(this.timeList);

		// 2. max value로 초기화한 후 각 vertex에 대한 gamma table 초기화

		// long max value로 초기화

		for (TimeVertex vertex : this.graph.getVertices()) {

			HashMap<String, Long> gammaVertex = new HashMap<String, Long>();

			gammaVertex.put(vertex.getId(), startTime);

			this.gamma.put(vertex.getId(), gammaVertex);
		}

	}

	@SuppressWarnings("unused")
	public void compute() {

		ArrayList<MyEdgeEvent> edgeEvents = (ArrayList<MyEdgeEvent>) this.graph.getEdgeEventsToList();

		// time list (순서화된) 
		
		// 임시 주석처리 
//		for (String vertexID : this.gamma.keySet()) {
//
//			for (Long t : this.timeList) {
//
//				for (MyEdgeEvent edgeEvent : edgeEvents) {
//
//					if (edgeEvent.getTime() == t
//							&& this.gamma.get(vertexID).get(edgeEvent.getSourceID()).get(t) != Long.MAX_VALUE
//							&& this.gamma.get(vertexID).get(edgeEvent.getTargetID()).get(t) == Long.MAX_VALUE) {
//						// 시간이 같고
//						// sourceID의 time key의 value 값이 설정되어있고
//						// targetID의 time key의 value 값이 설정되어 있지 않으면
//
//						for (Long time : this.gamma.get(vertexID).get(edgeEvent.getTargetID()).keySet()) {
//
//							if (time >= edgeEvent.getTime())
//								this.gamma.get(vertexID).get(edgeEvent.getTargetID()).put(time, edgeEvent.getTime());
//						}
//					}
//				}
//			}
//		}

	}

	@SuppressWarnings("unused")
	public int getTemporalReachabilitySize(String vertexID) {

		Long lastTime = this.timeList.get(this.timeList.size() - 1);

		int size = 0;

		// 임시 주석처리 
//		for (String vID : this.gamma.get(vertexID).keySet()) {
//
//			if (this.gamma.get(vertexID).get(vID).get(lastTime) != Long.MAX_VALUE)
//				size += 1;
//
//		}

		return size;
	}

	public HashMap<String, Long> getTemporalReachabilityToMap(String vertexID) {

		HashMap<String, Long> tr = new HashMap<>();

		// 임시 주석 처리
//		Long lastTime = this.timeList.get(this.timeList.size() - 1);
//
//		for (String vID : this.gamma.get(vertexID).keySet()) {
//
//			if (this.gamma.get(vertexID).get(vID).get(lastTime) != Long.MAX_VALUE)
//				tr.put(vID, this.gamma.get(vertexID).get(vID).get(lastTime));
//
//		}

		return tr;
	}

}
