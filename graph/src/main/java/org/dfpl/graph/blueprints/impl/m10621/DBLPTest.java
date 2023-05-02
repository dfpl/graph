package org.dfpl.graph.blueprints.impl.m10621;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;

import org.dfpl.graph.api.memory.InMemoryGraph;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

class Closeness_Centrality{
	public int index;
	public int id;
	public double Cc;
}

@SuppressWarnings("resource")
public class DBLPTest {
	
	/*
	public static void DijkstraShortestPaths(Graph g, String _s) {
		
		Collection<Vertex> v_col = g.getVertices();
		Collection<Edge> e_col = g.getEdges();
		
		ArrayList<Vertex> v_List = new ArrayList<Vertex>();
		v_List.addAll(v_col);
		
		ArrayList<Edge> e_List = new ArrayList<Edge>();
		e_List.addAll(e_col);
		

		for(int i = 0; i < v_List.size(); i++)
		{
			if(v_List.get(i).getId().equals(_s))	
				v_List.get(i).setProperty("distance", 0);
			else
				v_List.get(i).setProperty("distance", 30000);
		}
		
		ArrayList<Vertex> H_List = new ArrayList<Vertex>();
		for(int i = 0; i < v_List.size(); i++) {
				H_List.add(v_List.get(i));
		}
		
		Vertex u = null, z = null;
		while(!H_List.isEmpty()) {
			int dmin = 30000;
			for(int v = 0; v < H_List.size(); v++) {
				String str = H_List.get(v).getProperty("distance").toString();
				int dis = Integer.parseInt(str);
				if(dis < dmin) {
					u = H_List.get(v);
					dmin = dis;
				}
			}
			H_List.remove(u);
			
			for(int j = 0; j < v_List.size(); j++)
			{
				if(u.getVertices(Direction.OUT).contains(v_List.get(j)));
				{
					z = v_List.get(j);
					if(H_List.contains(z)) {
						String utr = u.getProperty("distance").toString();
						int ud = Integer.parseInt(utr);
						String ztr = z.getProperty("distance").toString();
						int zd = Integer.parseInt(ztr);
						if(ud + 1 < zd)	{
							z.setProperty("distance", ud + 1);
						}
					}
				}
			}
			
		}
		
		for(int i = 0; i < v_List.size(); i++) {
			String str = v_List.get(i).getProperty("distance").toString();
			int dis = Integer.parseInt(str);
			//if (dis != 0 && dis != 30000) {
				System.out.println(v_List.get(i).getId() + " " + dis);
			//}
		}
			
	}
	
	public static void BellmanFordShortestPaths(Graph g, String _s) {
		Collection<Vertex> v_col = g.getVertices();
		Collection<Edge> e_col = g.getEdges();
		
		ArrayList<Vertex> v_List = new ArrayList<Vertex>();
		v_List.addAll(v_col);
		
		ArrayList<Edge> e_List = new ArrayList<Edge>();
		e_List.addAll(e_col);
		
		for(int i = 0; i < v_List.size(); i++)
		{
			if(v_List.get(i).getId().equals(_s))	
				v_List.get(i).setProperty("distance", 0);
			else
				v_List.get(i).setProperty("distance", 30000);
		}
		
		Vertex u = null, z = null;
		for(int i = 0; i < v_List.size() - 1; i++) {
			for(int j = 0; j < e_List.size(); j++) {
				u = e_List.get(i).getVertex(Direction.IN);
				z = e_List.get(i).getVertex(Direction.OUT);
				
				String utr = u.getProperty("distance").toString();
				int ud = Integer.parseInt(utr);
				String ztr = z.getProperty("distance").toString();
				int zd = Integer.parseInt(ztr);
				
				if(zd > ud + 1) {
					z.setProperty("distance", ud + 1);
				}
			}
		}
		
		for(int i = 0; i < v_List.size(); i++) {
			String str = v_List.get(i).getProperty("distance").toString();
			int dis = Integer.parseInt(str);
			
			System.out.println(v_List.get(i).getId() + " " + dis);
		}
	}
	*/
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		
			Graph g = new InMemoryGraph();
		
	        String fileName = "f:\\com-dblp.ungraph.txt";
	        String delimiter = "\\t";

	        BufferedReader r = new BufferedReader(new FileReader(fileName));	        
	        int cnt = 0;
	        while (true) {
	            String line = r.readLine();
	            if (line == null)
	                break;
	            if (line.startsWith("#"))
	                continue;
	            if (++cnt % 1000 == 0) {
	                System.out.println(cnt + " lines read...");
	            }
	            String[] arr = line.split(delimiter);
	            String v1str = "s"+arr[0];
	            String v2str = "s"+arr[1];
	            Vertex v1 = g.addVertex(v1str);
	            Vertex v2 = g.addVertex(v2str);
	            Edge e12 = g.addEdge(v1, v2, "l");
	            Edge e21 = g.addEdge(v2, v1, "l");
	        }

	        System.out.println("(1) The number of vertices " + g.getVertices().size());
	        System.out.println("(2) The number of edges " + g.getEdges().size());
	        
	        System.out.println("Data loaded");
	        
	        //DijkstraShortestPaths(g, "s1");
	        //BellmanFordShortestPaths(g, "s1");
	        
	        
	        //Need to know all vertex pair of distance about graph...
	        //Dijkstra and BellmanFord returns distance information about just one node.
	        //let just use Floyd-Warshall Algorithm O(V^3)
	        //Applying dijkstra, bellmanford for ALL NODEs have no dramatically better points at time complexity.
	        
	        
	        Collection<Vertex> v_col = g.getVertices();
			Collection<Edge> e_col = g.getEdges();
			
			ArrayList<Vertex> v_List = new ArrayList<Vertex>();
			v_List.add(null);
			v_List.addAll(1, v_col);
			
			ArrayList<Edge> e_List = new ArrayList<Edge>();
			e_List.addAll(e_col);
			
			int N = v_List.size() - 1;
			
			int[][] d = new int [N + 1][N + 1];
			
			for(int i = 1; i <= N; i++)
				for(int j = 1; j <= N; j++)
					if(i != j)	{
						d[i][j] = 30000;
					}
			
			int row = 0, col = 0;
			for(int index = 0; index < e_List.size(); index++)
			{
				for(int jndex = 1; jndex <= N; jndex++)
					if (v_List.get(jndex).getId().equals(e_List.get(index).getVertex(Direction.OUT).getId()))
					{
						row = jndex;
						break;
					}
						
				for(int jndex = 1; jndex <= N; jndex++)
					if (v_List.get(jndex).getId().equals(e_List.get(index).getVertex(Direction.IN).getId()))
					{
						col = jndex;
						break;
					}
				d[row][col] = 1;
			}
			
			for(int m = 1; m <= N; m++)
				for(int s = 1; s <= N; s++)
					for(int e = 1; e <= N; e++)
						if(d[s][e] > d[s][m] + d[m][e])
							d[s][e] = d[s][m] + d[m][e];
			
			/*
			for(int i = 1; i <= n; i++)
			{
				System.out.println();
				for(int j = 1; j <= n; j++)
					System.out.print(d[i][j] + " ");
			}
			*/

			
			String outfileName = "C:\\output.txt";
			File file = new File(outfileName);
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);
			
			
			for (int i = 1; i <= N; i++)
			{
				String str = v_List.get(i).getId();
				for(int j = 1; j <= N; j++)
				{
					int v = d[i][j];
					if (d[i][j] == 30000)
						v = -1;
					System.out.println(str + "   " + v_List.get(j).getId() + "   " + v);
				}
			}
			
			Closeness_Centrality[] C = new Closeness_Centrality[N + 1];
			for(int i = 0; i < N + 1; i++) {
				C[i] = new Closeness_Centrality();
			}
			
			int id = 0, sum = 0, cczf = 0;
			double Cc = 0;
			for (int i = 1; i <= N; i++)
			{
				String str = v_List.get(i).getId().substring(1);
				id = Integer.valueOf(str);
				sum = 0;
				cczf = 0;
				for(int j = 1; j <= N; j++) {
					sum += d[i][j];
					if (d[i][j] == 30000) {
						cczf = 1;
						break;
					}
				}
				C[i].index = i;
				C[i].id = id;
				if(cczf == 0)
					C[i].Cc = (N - 1)/(double)sum;
				else
					C[i].Cc = 0;
			}
			
			Closeness_Centrality temp = new Closeness_Centrality();
			for(int i = 1; i <= N; i++)
				for(int j = 1; j <= N - 1; j++)
					if(C[j].id > C[j + 1].id)	{
						temp = C[j];
						C[j] = C[j + 1];
						C[j + 1] = temp;
					}
			
			for(int i = 1; i <= N; i++)
				System.out.printf("%d   %f\n", C[i].id, C[i].Cc);
	}
}