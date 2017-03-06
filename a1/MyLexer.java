import java.util.*;
import java.io.*;
import java.util.regex.*;

public class MyLexer{
	public static void main(String[] args){
		Hashtable<String, String> tokenTable = new Hashtable<String, String>();
		Hashtable<String, String> symbolTable = new Hashtable<String, String>();
		BufferedReader tokenReader = null;
		FileInputStream sourceReader = null;
		ArrayList<String> symbols = new ArrayList<String>();
		ArrayList<String> source = new ArrayList<String>();
		String next;
		String[] temp;
		int commentStart;
		int commentEnd;
		Pattern idPattern = Pattern.compile("[a-zA-Z_$]+[\\w_]*");
		Pattern singleCommentPattern = Pattern.compile(".*//.*");
		Pattern blockCommentBeginPattern = Pattern.compile(".*/\\*.*");
		Pattern blockCommentEndPattern = Pattern.compile(".*\\*/.*");
		Matcher matcher;
		Boolean commentMode = false;
		String check = "";
		int peekInt;
		char peekChar;
		String prevToken = "";
		String prevValue = "";


		try{
			tokenReader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));

			while((next = tokenReader.readLine()) != null){
				temp = next.split("\\s+");
				if(temp[0] != " " && temp[0] != "\t"){
					tokenTable.put(temp[0], "<"+temp[1]+">");
				}
			}

			tokenReader.close();

			sourceReader = new FileInputStream(args[1]);
			while((peekInt = sourceReader.read()) != -1){
				peekChar = (char)peekInt;
				// System.out.println(peekChar);
				if(peekChar != ' ' && tokenTable.containsKey(peekChar)){

				}else if(peekChar == ' ' || peekChar == '\t'){
					if(tokenTable.containsKey(check)){
						prevToken = tokenTable.get(check);
					}else{
						if(prevToken == "<Prim_type>"){
							if(symbolTable.containsKey(check)){
								System.out.println("error: variable " + check + " is already defined");
							}else{
								symbolTable.put(check, prevValue);
							}
						}
						System.out.println(check);
					}

					prevValue = check;
					check = "";
				}else if(peekChar == ";"){

				}else{
					check += peekChar;
					
				}
			}
			// 	if(!commentMode && blockCommentBeginPattern.matcher(next).matches()){
			// 		commentMode = true;
			// 		commentStart = (next.indexOf("/*"));
			// 		next = next.substring(0, commentStart);
			// 		temp = next.split("\\s+");

			// 		for(int i = 0; i < temp.length; i++){
			// 			if(!tokenTable.containsKey(current)){
			// 				System.out.print(current);
			// 			}else{
			// 				if(next.indexOf(";") != -1){

			// 				}
			// 			}
			// 		}
			// 	}else if(commentMode && blockCommentEndPattern.matcher(next).matches()){
			// 		commentMode = false;
			// 		commentEnd = (next.indexOf("*/"));
			// 		next = next.substring(commentEnd+2);
			// 		temp = next.split("\\s+");

			// 		for(int i = 0; i < temp.length; i++){
			// 			source.add(temp[i]);
			// 		}
			// 	}else if(!commentMode){
			// 		temp = next.split("\\s+");

			// 		for(int i = 0; i < temp.length; i++){
			// 			source.add(temp[i]);
			// 		}
			// 	}
			// }
			sourceReader.close();

			// for(String current: source){

			// 	if(tokenTable.containsKey(current)){
			// 		// System.out.print(tokenTable.get(current) + " ");
			// 	}else{
			// 		System.out.println(current);
			// 	}
			// }

		}catch (Exception e) {
			System.err.println(e);
		}
	}
	// public void check(String stmt){
	// 	String thing;
	// 	char start;
	// 	char peek;
	// 	start = stmt[0];
	// 	peek = stmt[1];
	// 	for(int i = 0 ; i < stmt.length; i++){
	// 		if(stmt[i] != " " || stmt[i] != "\t" || !tokenTable.containsKey(stmt[i])){
	// 			thing+=stmt[i];
	// 		}else{

	// 		}
	// 	}
	// }
}
