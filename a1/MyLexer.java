/* 
 * Melvin Cheng
 * 100526486
 */
import java.util.*;
import java.io.*;
import java.util.regex.*;

public class MyLexer{
	public static void main(String[] args){
		Hashtable<String, String> tokenTable = new Hashtable<String, String>();
		Hashtable<String, String> symbolTableType = new Hashtable<String, String>();
		Hashtable<String, Integer> symbolTableId = new Hashtable<String, Integer>();
		ArrayList<String> symbolArray = new ArrayList<String>();
		BufferedReader tokenReader, sourceReader = null;
		ArrayList<String> symbols = new ArrayList<String>();
		ArrayList<String> source = new ArrayList<String>();
		ArrayList<String> output = new ArrayList<String>();
		String next;
		String[] temp;
		int commentStart;
		int commentEnd;
		Pattern idPattern = Pattern.compile("[a-zA-Z_$]+[\\w_]*");
		Pattern floatPattern = Pattern.compile("[0-9]*\\.[0-9]+");
		Pattern integerPattern = Pattern.compile("[0-9]+");
		Pattern singleCommentPattern = Pattern.compile(".*//.*");
		Pattern blockCommentBeginPattern = Pattern.compile(".*/\\*.*");
		Pattern blockCommentEndPattern = Pattern.compile(".*\\*/.*");
		Matcher matcher;
		Boolean commentMode = false;
		String check = "";
		int peekInt;
		char peekChar;
		char prevChar;
		String peekString;
		String prevToken = "";
		String prevValue = "";
		int line = 0;
		String type = "";
		int idNum = 1;
		String varType = "";

		try{
			tokenReader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));

			while((next = tokenReader.readLine()) != null){
				temp = next.split("\\s+");
				if(!temp[0].equals("") && !temp[0].equals("\t")){
					// System.out.println(temp[0]+" |||" + temp[1]);
					tokenTable.put(temp[0].trim(), "<"+temp[1]);
				}
			}

			tokenReader.close();

			sourceReader = new BufferedReader(new InputStreamReader(new FileInputStream(args[1])));
			while((peekInt = sourceReader.read()) != -1){
				peekChar = (char)peekInt;
				// System.out.println(check);
				if(peekInt == '\n'){
					line++;
				}
				if(commentMode){
					if(peekChar == '*'){
						peekString = String.valueOf(peekChar);
						prevChar = peekChar;
						peekInt = sourceReader.read();
						peekChar = (char)peekInt;
						peekString += peekChar;
						if(peekString.equals("*/")){
							commentMode = false;
						}
					}
				}else{
					if(peekChar == '"'){
						while((peekInt = sourceReader.read()) != -1){
							peekChar = (char)peekInt;
							if(peekChar == '"'){
								break;
							}
						}
						check = ""; 
						output.add("<T_const> ");
					}else if(tokenTable.containsKey(String.valueOf(peekChar))){
						if(peekChar == '[' || peekChar == '.'){
							output.add("<T_ref,"+check+"> ");
							varType = check+"[]";
							prevValue = check;
							prevToken = "<T_ref";
							type = prevToken;
							check = "";
						}else if(tokenTable.containsKey(check)){
							output.add(tokenTable.get(check) + "," + check + "> ");
							prevToken = tokenTable.get(check);
							prevValue = check;
							check = "";
						}else if(peekChar == '(' && !check.trim().equals("")){
							output.add("<T_call,"+check+"> ");
							prevValue = check;
							prevToken = "<T_call";
							type = prevToken;
							check = "";
						}else if(idPattern.matcher(check).matches()){
							if(prevToken.equals("<Prim_type") || type.equals("<T_ref") || prevToken.equals("<T_class")){
								if(prevToken.equals("<T_class")){
									varType = "no type";
								}
								if(symbolTableType.containsKey(check)){
									output.add("<id,"+symbolTableId.get(check)+"> ");
								}else{
									prevToken = "<id";
									symbolTableType.put(check, varType);
									symbolTableId.put(check, idNum++);
									output.add("<id,"+symbolTableId.get(check)+"> ");
									symbolArray.add(check);
								}
							}
							prevValue = check;
							check = "";
						}else if(integerPattern.matcher(check).matches()){
							output.add("<Number,"+check+"> ");
							prevToken = "<Number";
							prevValue = check;
							check = "";
						}else if(prevToken.equals("<Prim_type")){
							if(varType.trim().equals("float")){
								if(!floatPattern.matcher(check).matches()){
									System.err.println("Lexical Error in line 3: No Such Lexeme can be matched.");
									System.exit(0);
								}
							}
						}
						peekString = String.valueOf(peekChar);
						prevChar = peekChar;
						sourceReader.mark(256);

						peekInt = sourceReader.read();
						peekChar = (char)peekInt;
						peekString += peekChar;
						if(peekString.equals("//")){
							while((peekInt = sourceReader.read()) != -1){
								peekChar = (char)peekInt;
								if(peekChar == '\n'){
									break;
								}
							}
						}else if(tokenTable.containsKey(peekString.trim())){
							if(tokenTable.get(peekString.trim()).equals("<Comp_op") || tokenTable.get(peekString.trim()).equals("<T_binary_op") || tokenTable.get(peekString.trim()).equals("<T_incr_decr") || tokenTable.get(String.valueOf(peekString.trim())).equals("<Prim_type")){
								output.add(tokenTable.get(peekString.trim())+",'"+peekString.trim()+"'> ");
							}else{
								output.add(tokenTable.get(peekString.trim())+"> ");
							}
							prevToken = tokenTable.get(peekString.trim());
							prevValue = peekString.trim();
							check = "";
						}else if(peekString.equals("/*")){
							commentMode = true;
							check = "";
						}else{
							if(tokenTable.get(String.valueOf(prevChar)).equals("<T_binary_op") || tokenTable.get(String.valueOf(prevChar)).equals("<Prim_type")){
								output.add(tokenTable.get(String.valueOf(prevChar))+",'"+prevChar+"'> ");
							}else{
								output.add(tokenTable.get(String.valueOf(prevChar))+"> ");	
							}

							prevToken = tokenTable.get(String.valueOf(prevChar));
							prevValue = String.valueOf(prevChar);
							check = "";
							sourceReader.reset();
						}
					}else if(peekChar == ' ' || peekChar == '\t' || peekChar == '\n'){
						if(!check.trim().equals("")){
							if(tokenTable.containsKey(check.trim())){
								if(tokenTable.get(check.trim()).equals("<Prim_type")){
									output.add(tokenTable.get(check.trim())+","+check+"> ");
									varType = check.trim();
								}else{
									output.add(tokenTable.get(check.trim())+"> ");
								}
								prevValue = check.trim();
								prevToken = tokenTable.get(check.trim());
							}else{
								if(prevToken.equals("<Prim_type") || type.equals("<T_ref") || prevToken.equals("<T_class")){
									if(prevToken.equals("<T_class")){
										varType = "no type";
									}
									if(symbolTableType.containsKey(check)){
										output.add("<id,"+symbolTableId.get(check)+"> ");
									}else{
										prevToken = "<id> ";
										symbolTableType.put(check, varType);
										symbolTableId.put(check, idNum++);
										output.add("<id,"+symbolTableId.get(check)+"> ");
										symbolArray.add(check);
									}
								}
							}
							prevValue = check;
							check = "";
						}
					}else if(tokenTable.containsKey(check)){
						output.add(tokenTable.get(check)+">");
						prevToken = tokenTable.get(check);
						prevValue = check;
						check = "";
					}else{
						check += peekChar;
						check.trim();
					}
				}
			}
			sourceReader.close();
		}catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		for(String current: output){
			System.out.print(current);
		}
		System.out.println("\nSymbol table");
		for(String current: symbolArray){
			varType = symbolTableType.get(current);
			if(varType.equals("")){
				varType = "no type";
			}
			System.out.println(String.format("%10s %10s no value", current, varType));
		}
	}
}
