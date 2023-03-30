package org.dfpl.graph.api.jincheol;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public class App {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		MongoDBController con=new MongoDBController("emaildata");
		
		con.setMongoDBClient();
		
		//con.insertEdge("D:\\mydata\\email-EuAll.txt");
		
		//con.deleteCollection("edge");
		
		//con.insertVertex("D:\\mydata\\email-EuAll.txt");
		
		//con.deleteCollection("vertex");
		
		//con.updateVertex();
		
		System.out.println(con.getVertexSize());
		System.out.println(con.getEdgeSize());
		
	}

}
