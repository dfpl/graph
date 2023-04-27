package org.dfpl.graph.api.assignment;

import java.io.BufferedReader;
import java.io.FileReader;

import org.dfpl.graph.api.persistent.mariadb.bsjoe.MGraph;

import com.tinkerpop.blueprints.revised.Direction;
import com.tinkerpop.blueprints.revised.Edge;
import com.tinkerpop.blueprints.revised.Graph;
import com.tinkerpop.blueprints.revised.Vertex;

public class SmallDataTest {

    // isEvenTag, addTag, subTag, divTag 의 문자열은 바뀔 수 있음
    public static String isEvenTag = "isEven";
    public static String addTag = "add";
    public static String subTag = "sub";
    public static String divTag = "div";

	public static void main(String[] args) throws Exception {

        // 참고: 평가를 위한 데이터셋은 보다 작은 데이터셋을 활용 (예: CollegeMsg.txt, http://snap.stanford.edu/data/index.html)
        // 참고: 데이터셋 변경 가능
        String fileName = "C:\\CollegeMsg.txt";
        String delimiter = "\\s";
        String dbID = "root";
        String dbPW = "1234";
        String dbName = "team";

        BufferedReader r = new BufferedReader(new FileReader(fileName));

        // Graph g = new InMemoryGraph();
        // PersistentGraph의 생성자는 빈 생성자 (id는 root, pw는 1234, db name은 팀 이름으로) 혹은
        // (String dbID, String dbPW, String dbName) 의 생성자를 가질 수 있음
        Graph g = new MGraph(dbID, dbPW, dbName);

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
            int v1Int = Integer.parseInt(arr[0]);
            Vertex v2 = g.addVertex(v2str);
            int v2Int = Integer.parseInt(arr[1]);
            Edge e12 = g.addEdge(v1, v2, "l");
            e12.setProperty(isEvenTag, ((v1Int + v2Int) % 2 == 0));
            e12.setProperty(addTag, v1Int + v2Int);
            e12.setProperty(subTag, v1Int - v2Int);
            e12.setProperty(divTag, (v1Int / (double) v2Int));

        }

        r.close();
        System.out.println("Data loaded");

        // finding a vertex that has a maximum out-degree
        int maxOutDegree = Integer.MIN_VALUE;
        String maxOutDegreeID = null;
        int dummy = 0;
        for (Vertex v : g.getVertices()) {
            if (++dummy % 1000 == 0)
                System.out.print(".");
            int d = v.getVertices(Direction.OUT).size();
            if (d > maxOutDegree) {
                maxOutDegree = d;
                maxOutDegreeID = v.getId();
            }
        }
        System.out.println();
        // finding a vertex that has a maximum in-degree
        int maxInDegree = Integer.MIN_VALUE;
        String maxInDegreeID = null;
        dummy = 0;
        for (Vertex v : g.getVertices()) {
            if (++dummy % 1000 == 0)
                System.out.print(".");
            int d = v.getVertices(Direction.IN).size();
            if (d > maxInDegree) {
                maxInDegree = d;
                maxInDegreeID = v.getId();
            }
        }
        System.out.println("Start to evaluate");
        System.out.println("[1] " + maxOutDegreeID);
        System.out.println("[2] " + maxOutDegree);
        System.out.println("[3] " + maxInDegreeID);
        System.out.println("[4] " + maxInDegree);
        System.out.println("[5] " + g.getVertices().size());
        System.out.println("[6] " + g.getEdges().size());
        System.out.println("[7] " + g.getEdges(isEvenTag, true).size());
        System.out.println("[8] " + g.getEdges(isEvenTag, false).size());
        System.out.println("[9] " + g.getVertex(maxOutDegreeID).getVertices(Direction.OUT).size());
        System.out.println("[10] " + g.getVertex(maxInDegreeID).getVertices(Direction.IN).size());

        System.out.println("[11] " + g.getVertex(maxOutDegreeID).getVertices(Direction.OUT).stream()
                .flatMap(v -> v.getVertices(Direction.OUT).stream()).toList().size());
        System.out.println("[12] " + g.getVertex(maxInDegreeID).getVertices(Direction.IN).stream()
                .flatMap(v -> v.getVertices(Direction.IN).stream()).toList().size());
        System.out.println("[13] " + g.getVertex(maxOutDegreeID).getVertices(Direction.OUT, isEvenTag, true).stream()
                .flatMap(v -> v.getVertices(Direction.OUT, isEvenTag, false).stream()).toList().size());
        System.out.println("[14] " + g.getVertex(maxInDegreeID).getVertices(Direction.IN, isEvenTag, true).stream()
                .flatMap(v -> v.getVertices(Direction.IN, isEvenTag, false).stream()).toList().size());
        System.out.println("[15] " + g.getVertex(maxOutDegreeID).getVertices(Direction.OUT).stream()
                .flatMap(v -> v.getVertices(Direction.OUT).stream()).flatMap(v -> v.getVertices(Direction.OUT).stream())
                .toList().size());
        System.out.println("[16] " + g.getVertex(maxInDegreeID).getVertices(Direction.IN).stream()
                .flatMap(v -> v.getVertices(Direction.IN).stream()).flatMap(v -> v.getVertices(Direction.IN).stream())
                .toList().size());

        long min7 = Long.MAX_VALUE;
        int min7C = 0;
        for (int i = 0; i < 10; i++) {
            long pre = System.nanoTime();
            min7C = g.getVertex(maxOutDegreeID).getVertices(Direction.OUT).stream().flatMap(v -> v.getVertices(Direction.OUT).stream())
                    .toList().size();
            long elapsedTime = System.nanoTime() - pre;
            if (elapsedTime < min7) {
                System.out.println("\t" + min7 + " -> " + elapsedTime);
                min7 = elapsedTime;

            }
        }
        System.out.println("[17] " + min7C);
        System.out.println("[P1] " + min7);

        long min8 = Long.MAX_VALUE;
        int min8C = 0;
        for (int i = 0; i < 10; i++) {
            long pre = System.nanoTime();
            min8C = g.getVertex(maxInDegreeID).getVertices(Direction.IN).stream().flatMap(v -> v.getVertices(Direction.IN).stream())
                    .toList().size();
            long elapsedTime = System.nanoTime() - pre;
            if (elapsedTime < min8) {
                System.out.println("\t" + min8 + " -> " + elapsedTime);
                min8 = elapsedTime;
            }
        }
        System.out.println("[18] " + min8C);
        System.out.println("[P2] " + min8);

        long min9 = Long.MAX_VALUE;
        int min9C = 0;
        for (int i = 0; i < 10; i++) {
            long pre = System.nanoTime();
            min9C = g.getVertex(maxOutDegreeID).getVertices(Direction.OUT, isEvenTag, true).stream()
                    .flatMap(v -> v.getVertices(Direction.OUT, isEvenTag, false).stream()).toList().size();
            long elapsedTime = System.nanoTime() - pre;
            if (elapsedTime < min9) {
                System.out.println("\t" + min9 + " -> " + elapsedTime);
                min9 = elapsedTime;
            }
        }
        System.out.println("[19] " + min9C);
        System.out.println("[P3] " + min9);

        long min10 = Long.MAX_VALUE;
        int min10C = 0;
        for (int i = 0; i < 10; i++) {
            long pre = System.nanoTime();
            min10C = g.getVertex(maxInDegreeID).getVertices(Direction.IN, isEvenTag, true).stream()
                    .flatMap(v -> v.getVertices(Direction.IN, isEvenTag, false).stream()).toList().size();
            long elapsedTime = System.nanoTime() - pre;
            if (elapsedTime < min10) {
                System.out.println("\t" + min10 + " -> " + elapsedTime);
                min10 = elapsedTime;
            }
        }
        System.out.println("[20] " + min10C);
        System.out.println("[P4] " + min10);

        long min11 = Long.MAX_VALUE;
        int min11C = 0;
        for (int i = 0; i < 5; i++) {
            long pre = System.nanoTime();
            min11C = g.getVertex(maxOutDegreeID).getVertices(Direction.OUT).stream().flatMap(v -> v.getVertices(Direction.OUT).stream())
                    .flatMap(v -> v.getVertices(Direction.OUT).stream()).toList().size();
            long elapsedTime = System.nanoTime() - pre;
            if (elapsedTime < min11) {
                System.out.println("\t" + min11 + " -> " + elapsedTime);
                min11 = elapsedTime;
            }
        }
        System.out.println("[21] " + min11C);
        System.out.println("[P5] " + min11);

        long min12 = Long.MAX_VALUE;
        int min12C = 0;
        for (int i = 0; i < 5; i++) {
            long pre = System.nanoTime();
            min12C = g.getVertex(maxInDegreeID).getVertices(Direction.IN).stream().flatMap(v -> v.getVertices(Direction.IN).stream())
                    .flatMap(v -> v.getVertices(Direction.IN).stream()).toList().size();
            long elapsedTime = System.nanoTime() - pre;
            if (elapsedTime < min12) {
                System.out.println("\t" + min12 + " -> " + elapsedTime);
                min12 = elapsedTime;
            }
        }
        System.out.println("[22] " + min12C);
        System.out.println("[P6] " + min12);

        long min13 = Long.MAX_VALUE;
        int min13C = 0;
        for (int i = 0; i < 5; i++) {
            long pre = System.nanoTime();
            min13C = g.getVertex(maxOutDegreeID).getTwoHopVertices(Direction.OUT).size();
            long elapsedTime = System.nanoTime() - pre;
            if (elapsedTime < min13) {
                System.out.println("\t" + min13 + " -> " + elapsedTime);
                min13 = elapsedTime;
            }
        }
        //  [23] should be same with [21]
        System.out.println("[23] " + min13C);
        System.out.println("[P7] " + min13);

        long min14 = Long.MAX_VALUE;
        int min14C = 0;
        for (int i = 0; i < 5; i++) {
            long pre = System.nanoTime();
            min14C = g.getVertex(maxInDegreeID).getTwoHopVertices(Direction.IN).size();
            long elapsedTime = System.nanoTime() - pre;
            if (elapsedTime < min14) {
                System.out.println("\t" + min14 + " -> " + elapsedTime);
                min14 = elapsedTime;
            }
        }
        // [24] should be same with [22]
        System.out.println("[24] " + min14C);
        System.out.println("[P8] " + min14);
    }

    @SuppressWarnings("unused")
    private static void scanDegree(Graph g, Direction direction) {
        String maxID = null;
        Integer maxDegree = Integer.MIN_VALUE;
        for (Vertex v : g.getVertices()) {
            int size = v.getVertices(direction).size();
            if (size > maxDegree) {
                maxID = v.getId();
                maxDegree = size;
                System.out.println(maxID + " : " + maxDegree);
            }
        }
    }
}
