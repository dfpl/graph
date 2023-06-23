package org.dfpl.graph.blueprints.impl.m10366.finalExam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class Text {

	public static void main(String[] args) throws IOException {
		
		
		BufferedReader br = new BufferedReader(new FileReader("D://mydata/CollegeMsg.txt"));
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("D://mydata/CollegeMsg-1000.txt"));
		
		
		
		int size=0;
		
		while(size<1000) {
			
			String line = br.readLine();
			
			
			if (line == null)
				break;
			if (line.startsWith("#"))
				continue;
			
			size+=1;
			
			bw.write(line);
			bw.newLine();
			
		}
			
		
		br.close();
		bw.close();

	}

}
