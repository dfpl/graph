package org.dfpl.graph.api.jincheol;


import java.io.IOException;


import org.json.simple.parser.ParseException;


public class App {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub

		
		MyMongoDB md=new MyMongoDB();
		
		MyVertex v1=new MyVertex("1");
		
		md.insertVertex(v1);

		md.setVertexProperty("1", "name", "jincheol");
		
	}

}
