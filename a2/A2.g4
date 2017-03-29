grammar A2;

@header {

}

@members {
	String firstName = "Adele";
	String lastName = "Hedrick";
	String studentNum = "100xxxxxx";
	int uniqueLoops = 0;
}

/* 
		start --> my_program statements+ 
*/
start 
	: {System.out.print("/*\n"+firstName+" "+lastName+"\n"+studentNum+"\n*/\npublic class ");} 
		my_program {System.out.print($my_program.s);}
		{System.out.println(" {\n\tpublic static void main(String[] args){");}
		( statement {System.out.println("\t\t" + $statement.s+"\n");})+ 
		{System.out.println("\n\t}\n}");}
	;	

/*
		my_program --> 'my program' ID '/'
*/
my_program returns [String s]
	: 'my program' ID '/' 
		{ 
			$s = $ID.getText();	
		}
	;

/*
		statement --> num_decl | str_decl | var_assign | loop | show | check
*/


/*
		loop --> 'loop' var '{' statement+ '}'
*/

/*
		check --> check condition '{' statement+ '}'
*/

/*
		condition --> var OP var
*/


/*
		num_decl --> 'num' ID '/'
*/



/*
		str_decl --> 'str' ID '/'
*/



/*
		var_assign --> 'assign' ID var '/'
*/



/*
		show --> 'show' var '/'
*/



/*
		var --> ID | NUM | STR
*/
var returns [String s]
	: ID 
		{ 
			$s = $ID.getText(); 
		}
	| NUM 
		{ 
			$s = ""+ $NUM.getText(); 
		}
	| STR
		{
			$s = $STR.getText();
		}
	;

/* Terminal Symbols */
NUM : ('0' .. '9')+ ;
OP : ('>' | '<' | '==' | '<=' | '>=' | '!=') ;
ID : ('a' .. 'z' | 'A' .. 'Z')+ ('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-')* ;
STR : '"'('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' | ' ')*'"' ;
WS : (' ' | '\t' | '\r' | '\n') {skip();} ;

