%{
#include <stdio.h>
#include <stdlib.h>

#define YYDEBUG 1
%}



%token PROGRAM
%token DECLARE
%token VAR
%token INTEGER
%token FLOAT
%token BOOL
%token IF
%token WHILE
%token FOR
%token THEN
%token ELSE
%token ENDPROGRAM
%token ENDDECLARE
%token ENDIF
%token ENDWHILE
%token ENDFOR

%token AND
%token OR
%token STRING


%token IDENTIFIER
%token CONST

%token ATRIB
%token EQ
%token NE
%token LE
%token GE
%token LT
%token GT
%token NOT

%token DOT

%left '+' '-' '*' '/'

%token PLUS
%token MINUS
%token DIV
%token MOD
%token MUL

%token OPEN_CURLY_BRACKET
%token CLOSED_CURLY_BRACKET
%token OPEN_ROUND_BRACKET
%token CLOSED_ROUND_BRACKET
%token OPEN_RIGHT_BRACKET
%token CLOSED_RIGHT_BRACKET

%token READ_OP
%token WRITE_OP

%token COMMA
%token SEMI_COLON
%token COLON
%token SPACE

%start program

%%
program : DECLARE decllist ENDDECLARE PROGRAM stmtlist ENDPROGRAM
                ;
decllist : declaration | declaration decllist
                ;
declaration :  VAR IDENTIFIER COLON type
                    ;
type :  INTEGER | STRING |BOOL |FLOAT | typeTemp
           ;
typeTemp : type OPEN_RIGHT_BRACKET CLOSED_RIGHT_BRACKET
                 ;
stmtlist :  stmt | stmt stmtlist
                 ;
stmt :  simplstmt | structstmt
          ;
simplstmt :  assignstmt | iostmt | declaration
                 ;
assignstmt : IDENTIFIER ATRIB expression | IDENTIFIER ATRIB array_assignstmt
                 ;
array_assignstmt: OPEN_RIGHT_BRACKET list_array CLOSED_RIGHT_BRACKET
                ;
list_element: CONST|IDENTIFIER
            ;
list_array: list_element | list_element COMMA list_array
          ;
expression : arithmetic2 arithmetic1
                              ;
arithmetic1 : PLUS arithmetic2 arithmetic1 | MINUS arithmetic2 arithmetic1 | /*Empty*/
                                ;
arithmetic2 : multiply2 multiply1
                                ;
multiply1 : MUL multiply2 multiply1 | DIV multiply2 multiply1 | /*Empty*/
                            ;
multiply2 : OPEN_ROUND_BRACKET expression CLOSED_ROUND_BRACKET | CONST | IDENTIFIER | IndexedIdentifier
                            ;
IndexedIdentifier :  IDENTIFIER OPEN_ROUND_BRACKET CONST CLOSED_ROUND_BRACKET |
                 IDENTIFIER OPEN_ROUND_BRACKET IDENTIFIER CLOSED_ROUND_BRACKET
        ;
iostmt : readstmt | writestmt
       ;
readstmt: READ_OP OPEN_ROUND_BRACKET IDENTIFIER CLOSED_ROUND_BRACKET | READ_OP OPEN_ROUND_BRACKET CONST CLOSED_ROUND_BRACKET
        ;
writestmt: WRITE_OP OPEN_ROUND_BRACKET IDENTIFIER CLOSED_ROUND_BRACKET | WRITE_OP OPEN_ROUND_BRACKET CONST CLOSED_ROUND_BRACKET
                ;
structstmt : ifstmt | whilestmt | forstmt
                   ;
ifstmt :  IF OPEN_ROUND_BRACKET boolean_condition CLOSED_ROUND_BRACKET THEN stmtlist tempIf ENDIF
              ;
tempIf :/*Empty*/ | ELSE stmtlist
              ;
forstmt :  FOR forheader stmtlist ENDFOR
               ;
forheader :  OPEN_ROUND_BRACKET assignstmt COMMA boolean_condition COMMA assignstmt CLOSED_ROUND_BRACKET
                  ;
whilestmt :  WHILE OPEN_ROUND_BRACKET boolean_condition CLOSED_ROUND_BRACKET stmtlist ENDWHILE
             ;
condition : expression GT expression |
                 expression GE expression |
         expression LT expression |
         expression LE expression |
         expression EQ expression |
         expression NE expression
          ;
boolean_condition : condition boolean_cond_temp
                                  ;
boolean_cond_temp : /*Empty*/ | AND boolean_condition | OR boolean_condition
                                 ;
%%
yyerror(char *s)
{
        printf("%s\n",s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
        if(argc>1) yyin :  fopen(argv[1],"r");
        //if(argc>2 && !strcmp(argv[2],"-d")) yydebug: 1;
        if(!yyparse()) fprintf(stderr, "\tO.K.\n");
}