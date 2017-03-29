import org.antlr.v4.runtime.*;
import java.io.*;

public class ParseFile {
    public static void main(String[] args) throws Exception {
		
		
		if (args.length != 1) {
			System.err.println("Error: Invalid use of command line argument, expected file name");
			System.exit(0); 			
		}

		FileInputStream in
			= new FileInputStream(args[0]);
				
			
        ANTLRInputStream input = new ANTLRInputStream(in);
        A2Lexer lex = new A2Lexer(input);
        CommonTokenStream tok = new CommonTokenStream(lex);
        A2Parser parser = new A2Parser(tok);

        parser.start();
    
    }
}
