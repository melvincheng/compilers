import java.util.*;
import java.io.*;

public class MyLexer{
	public static void main(String[] args){
		BufferedReader tokenReader, sourceReader = null;
		Hashtable<String, String> symbolTable = new Hashtable<String, String>();
		ArrayList<String> source = new ArrayList<String>();
		String next;
		String[] temp;
		try{
			tokenReader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));

			while((next = tokenReader.readLine()) != null){
				temp = next.split("\\s+");
				symbolTable.put(temp[0], temp[1]);
			}

			tokenReader.close();

			sourceReader = new BufferedReader(new InputStreamReader(new FileInputStream(args[1])));

			while((next = sourceReader.readLine()) != null){
				temp = next.split("\\s+");

				for(int i = 0; i < temp.length; i++){
					source.add(temp[i]);
				}
			}
			sourceReader.close();

		}catch (Exception e) {
			System.err.println(e);
		}


	}
}