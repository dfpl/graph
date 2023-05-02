package org.dfpl.graph.blueprints.impl.m50119;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

import java.io.BufferedReader;

public class ShortestPath {
	private static int nSize; // 노드개수

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(
				"f:\\com-dblp.ungraph.txt"));
		TinkerGraph g = new TinkerGraph();

		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			if (line.startsWith("#")) {
				continue;
			}
			String[] arr = line.split("\t");
			String fromID = arr[0];
			String toID = arr[1];

			Vertex from = g.getVertex(fromID);
			if (from == null) {
				from = g.addVertex(fromID);
			}

			Vertex to = g.getVertex(toID);
			if (to == null) {
				to = g.addVertex(toID);
			}
			g.addEdge(from + "|label|" + to, from, to, "send");
			g.addEdge(to + "|label|" + from, to, from, "send");
		}
		reader.close();

		// test용 그래프
		/*
		 * Graph g = new TinkerGraph(); g.addVertex("1"); g.addVertex("2");
		 * g.addVertex("3"); Vertex a = g.getVertex("1"); Vertex b = g.getVertex("2");
		 * Vertex c = g.getVertex("3");
		 * 
		 * // outV | label | inV -> unique . g.addEdge("A|likes|A", a, a, "likes" );
		 * g.addEdge("A|likes|B", a, b, "likes" ); g.addEdge("A|likes|C", a, c, "likes"
		 * ); g.addEdge("A|loves|B", a, b, "loves" ); g.addEdge("C|likes|C", c, c,
		 * "likes" );
		 */

		// 모든 노드

		Iterable<Vertex> vIter = g.getVertices();
		// (1) 노드 개수 출력
		int cnt = 0;
		for (Vertex v : vIter) {
			cnt++;
		}
		System.out.println(cnt);

		// 모든 간선
		cnt = 0;
		Iterable<Edge> eIter = g.getEdges();
		// (2) 모든 간선 개수 출력
		for (Edge e : eIter) {
			cnt++;
		}
		System.out.println(cnt);

		// id max number 파악 (vertex size != max number)
		nSize = -1000;
		for (Vertex val : vIter) {
			int val_id = Integer.parseInt((String) val.getId());
			if (nSize <= val_id) {
				nSize = val_id;
			}
		}
		// System.out.println("array size : "+ nSize);
		// System.out.println();

		System.out.println("n m k");
		// (3) BFS : v(n) 에서 v(m) 까지 최단경로 길이 출력
		// 모든 노드 pair 검토
		for (Vertex first : vIter) { // 출발지
			for (Vertex second : vIter) { // 목적지
				int sid = Integer.parseInt((String) first.getId());
				int fid = Integer.parseInt((String) second.getId());
				Collection route = BFS(g, sid, fid);

				// 경로 출력
				// System.out.println(route.getArray());

				// 길이 출력
				if (route.getFlag()) {
					int route_size = route.getArray().size() - 1;
					System.out.println(sid + " " + fid + " " + route_size);
				} else {
					System.out.println(sid + " " + fid + " " + "-1");
				}
			}
		}

	}

	public static Collection BFS(Graph g, int sid, int fid) {
		// 노드의 방문 여부 판단 (초깃값: false)
		boolean visited[] = new boolean[nSize + 1];
		// BFS 구현을 위한 큐(Queue) 생성
		LinkedList<Vertex> queue = new LinkedList<Vertex>();

		// 시작 노드를 방문한 것으로 표시하고 큐에 삽입(enqueue)
		visited[sid] = true;
		Vertex s = g.getVertex(sid);
		queue.add(s);

		Collection route = new Collection();

		// 큐(Queue)가 빌 때까지 반복
		while (queue.size() != 0) {
			// 방문한 노드를 큐에서 추출(dequeue)하고 값을 출력
			Vertex vV = queue.poll();
			route.setArray(vV);
			// System.out.print(vV + " ");

			// 인접 노드 순회
			Iterator<Vertex> out = vV.getVertices(Direction.OUT).iterator();
			while (out.hasNext()) {
				Vertex n = out.next();
				int nid = Integer.parseInt((String) n.getId());
				if (!visited[nid]) {
					visited[nid] = true;
					queue.add(n);
				}

				// 목적지 찾은 경우 or 자기 자신에게 보낸 경우
				if (nid == fid) {
					Vertex f = g.getVertex(nid);
					route.setArray(f); // 목적지 노드 삽입
					route.setFlag(true); // 탐색 성공 여부 flag 변경
					queue.clear(); // 탐색 종료를 위해 queue 비움
					break;
				}
			}
		}
		return route;
	}
}

class Collection {
	private ArrayList<Vertex> route;
	private boolean flag;

	// 객체 어레이을 생성하고 주어진 값으로 초기화
	public Collection() {
		this.route = new ArrayList<Vertex>();
		this.flag = false;
	}

	public void setArray(Vertex v) {
		this.route.add(v);
	}

	public void setFlag(boolean b) {
		this.flag = b;
	}

	public ArrayList<Vertex> getArray() {
		return this.route;
	}

	public Boolean getFlag() {
		return this.flag;
	}

}