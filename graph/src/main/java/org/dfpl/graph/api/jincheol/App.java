package org.dfpl.graph.api.jincheol;


import java.io.IOException;


import org.json.simple.parser.ParseException;


public class App {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
//		MongoDBController con=new MongoDBController("emaildata");
//		con.setMongoDBClient();
//		con.deleteCollection("vertex");
		
		
		MyGraph g=new MyGraph("emaildata");
		
		g.insertData("C:\\mydata\\email.txt");
		
		
	}

}
