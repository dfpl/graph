package org.dfpl.graph.api.jincheol;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.Queue;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;


public class UndirectedGraph {

	private Graph g;
	
	public UndirectedGraph(Graph g){
		this.g=g;
	}
	
	
	
	public int getVerticesNum() {
		
		int count=0;
		
		Iterable<Vertex> vertices=g.getVertices();	
		
		if(vertices!=null) {
			for(Vertex v:vertices) 
				count+=1;
		}
		

		return count;
	}
	
	
	public int getEdgesNum() {
		
		int count=0;
		
		Iterable<Edge> edges=g.getEdges();	
		
		if(edges!=null) {
			for(Edge e:edges) 
				count+=1;
		}
		

		return count;
	}
	
	
	//최단거리 테이블 반환
	public HashMap<String,Integer> dijkstra(Vertex source) {
		
		ArrayList<Vertex> visited=new ArrayList<>();
		Queue<Vertex> q=new LinkedList<>();
		
		Iterable<Edge> edges=g.getEdges();
		Iterable<Vertex> vertices=g.getVertices();
		
		ArrayList<Vertex> near;
		
		//최단거리 table 초기화
		HashMap<String,Integer> spMap=new HashMap<String,Integer>();
		for(Vertex v:vertices) {
			spMap.put((String)v.getId(), Integer.MAX_VALUE);
		}
		
		
		
		visited.add(source);
		q.add(source);
		
		
		
		int distance=0; 
		
		//시작 node의 distance update
		spMap.replace((String)source.getId(), distance);
		
		while(!q.isEmpty()) {
			
			distance=spMap.get((String)q.peek().getId());
			Vertex node=q.remove();
			
			
			
			//인접 node들 near에 저장 
			near=new ArrayList<>();
			for(Edge e:edges) 
				if(e.getVertex(Direction.OUT).getId()==node.getId()) near.add(e.getVertex(Direction.IN));
			
			
			
			
			for(Vertex nv:near) {
				
				if(visited.contains(nv)) continue;
				
				
				
				if(spMap.get((String)nv.getId())>distance+1){
					spMap.replace((String)nv.getId(), distance+1);//기존의 최단거리 값보다 distance가 작으면 distance로 교체 
					
				}
				
				
				visited.add(nv);
				q.add(nv);
				
				
			}
			
			
			
			
			
		}
		
		
		return spMap; //최단거리 테이블을 반환 
		
	}
	
	
	public void closenessCentrality() {
		
		
	}
	

}
