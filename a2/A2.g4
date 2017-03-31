grammar A2;

@header {

}

@members {
	String firstName = "Melvin";
	String lastName = "Cheng";
	String studentNum = "100526486";
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
statement returns [String s]
    : num_decl
        {
            $s = $num_decl.s;
        }
    | str_decl
        {
            $s = $str_decl.s;
        }
    | var_assign
        {
            $s = $var_assign.s;
        }
    | show
        {
            $s = $show.s;
        }
    | loop
        {
            $s = $loop.s;
        }
    ;


/*
		loop --> 'loop' var '{' statement+ '}'
*/
loop returns [String s]
    :'loop' var '{' statement+ '}'
        {
            $s = ;
        }
    ;

/*
		check --> check condition '{' statement+ '}'
*/

/*
		condition --> var OP var
*/

/*
		num_decl --> 'num' ID '/'
*/
num_decl returns [String s]
    :'num' ID '/'
        {
            $s = "int " + $ID.getText() + ";";
        }
    ;

/*
		str_decl --> 'str' ID '/'
*/
str_decl returns [String s]
    :'str' ID '/'
        {
            $s = "String " + $ID.getText() + ";";
        }
    ;

/*
		var_assign --> 'assign' ID var '/'
*/
var_assign returns [String s]
    :'assign' ID var '/'
        {
            $s = $ID.getText() + " = " + $var.s + ";";
        }
    ;

/*
		show --> 'show' var '/'
*/
show returns [String s]
    :'show' var '/'
        {
            $s = "System.out.println(" + $var.s + ")" + ";";
        }
    ;


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

