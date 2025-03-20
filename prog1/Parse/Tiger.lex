package Parse;
import ErrorMsg.ErrorMsg;

%%

%implements Lexer
%function nextToken
%type java_cup.runtime.Symbol
%char

%{
private void newline() {
  errorMsg.newline(yychar);
}

private void err(int pos, String s) {
  errorMsg.error(pos, s);
}

private void err(String s) {
  err(yychar, s);
}

private java_cup.runtime.Symbol tok(int kind) {
    return tok(kind, null);
}

private java_cup.runtime.Symbol tok(int kind, Object value) {
    return new java_cup.runtime.Symbol(kind, yychar, yychar + yylength(), value);
}

private ErrorMsg errorMsg;

private StringBuffer string = new StringBuffer();

Yylex(java.io.InputStream s, ErrorMsg e) {
  this(s);
  errorMsg = e;
}

%}

%state STRING COMMENT

%eofval{
    return tok(sym.EOF, null);
%eofval}

%%
" "    {}
[ \t\r\f]  {}
\n     {newline();}
[\n\r]     {newline();}
[a-zA-Z_][a-zA-Z0-9_]* {return tok(sym.ID, yytext());}
"auto"     {return tok(sym.AUTO, null);}
"break"    {return tok(sym.BREAK, null);}
"char"     {return tok(sym.CHAR, null);}
"const"    {return tok(sym.CONST, null);}
"continue" {return tok(sym.CONTINUE, null);}
"do"       {return tok(sym.DO, null);}
"double"   {return tok(sym.DOUBLE, null);}
"else"     {return tok(sym.ELSE, null);}
"enum"     {return tok(sym.ENUM, null);}
"extern"   {return tok(sym.EXTERN, null);}
"float"    {return tok(sym.FLOAT, null);}
"for"      {return tok(sym.FOR, null);}
"fun"      {return tok(sym.FUN, null);}
"goto"     {return tok(sym.GOTO, null);}
"if"       {return tok(sym.IF, null);}
"int"      {return tok(sym.INT, null);}
"long"     {return tok(sym.LONG, null);}
"register" {return tok(sym.REGISTER, null);}
"return"   {return tok(sym.RETURN, null);}
"short"    {return tok(sym.SHORT, null);}
"signed"   {return tok(sym.SIGNED, null);}
"sizeof"   {return tok(sym.SIZEOF, null);}
"static"   {return tok(sym.STATIC, null);}
"struct"   {return tok(sym.STRUCT, null);}
"typedef"  {return tok(sym.TYPEDEF, null);}
"union"    {return tok(sym.UNION, null);}
"unsigned" {return tok(sym.UNSIGNED, null);}
"var"      {return tok(sym.VAR, null);}
"void"     {return tok(sym.VOID, null);}
"volatile" {return tok(sym.VOLATILE, null);}
"while"    {return tok(sym.WHILE, null);}

"->"      {return tok(sym.ARROW, null);}
"++"      {return tok(sym.INCREMENT, null);}
"--"      {return tok(sym.DECREMENT, null);}
"<<"      {return tok(sym.LSHIFT, null);}
">>"      {return tok(sym.RSHIFT, null);}
"<="      {return tok(sym.LE, null);}
">="      {return tok(sym.GE, null);}
"=="      {return tok(sym.EQ, null);}
"!="      {return tok(sym.NEQ, null);}
"&&"      {return tok(sym.AND, null);}
"||"      {return tok(sym.OR, null);}
"+="      {return tok(sym.ADDASSIGN, null);}
"-="      {return tok(sym.SUBASSIGN, null);}
"*="      {return tok(sym.MULASSIGN, null);}
"/="      {return tok(sym.DIVASSIGN, null);}
"%="      {return tok(sym.MODASSIGN, null);}
"<<="     {return tok(sym.LSHIFTASSIGN, null);}
">>="     {return tok(sym.RSHIFTASSIGN, null);}
"&="      {return tok(sym.BWISEANDASSIGN, null);}
"|="      {return tok(sym.BWISEORASSIGN, null);}
"^="      {return tok(sym.BWISEXORASSIGN, null);}

"+"       {return tok(sym.PLUS, null);}
"-"       {return tok(sym.MINUS, null);}
"*"       {return tok(sym.TIMES, null);}
"/"       {return tok(sym.DIVIDE, null);}
"%"       {return tok(sym.MODULUS, null);}
"<"       {return tok(sym.LT, null);}
">"       {return tok(sym.GT, null);}
"="       {return tok(sym.ASSIGN, null);}
"&"       {return tok(sym.BITWISEAND, null);}
"|"       {return tok(sym.BWISEOR, null);}
"^"       {return tok(sym.BWISEXOR, null);}
"~"       {return tok(sym.TILDE, null);}
"."       {return tok(sym.PERIOD, null);}
","    {return tok(sym.COMMA, null);}
";"       {return tok(sym.SEMICOLON, null);}
":"       {return tok(sym.COLON, null);}
"("       {return tok(sym.LPAREN, null);}
")"       {return tok(sym.RPAREN, null);}
"["       {return tok(sym.LBRACK, null);}
"]"       {return tok(sym.RBRACK, null);}
"{"       {return tok(sym.LBRACE, null);}
"}"       {return tok(sym.RBRACE, null);}
"..."     {return tok(sym.ELIPSES, null);}

[0-9]+    {return tok(sym.DECIMAL_LITERAL, Integer.parseInt(yytext()));}
0[xX][0-9a-fA-F]+ {return tok(sym.DECIMAL_LITERAL, Integer.decode(yytext()));}
0[0-7]+   {return tok(sym.DECIMAL_LITERAL, Integer.decode(yytext()));}

\"    {string.setLength(0); yybegin(STRING);}
<STRING> \"    {yybegin(YYINITIAL); return tok(sym.STRING_LITERAL, string.toString());}
<STRING> \n    {err("Unterminated string constant"); yybegin(YYINITIAL);}
<STRING> \\n   {string.append('\n');}
<STRING> \\t   {string.append('\t');}
<STRING> \\\"  {string.append('\"');}
<STRING> \\\\  {string.append('\\');}
<STRING> \\[0-7] {
    try {
        int value = Integer.parseInt(yytext().substring(1), 8);
        string.append((char)value);
    } catch (NumberFormatException e) {
        err("Invalid octal escape sequence");
    }
}
<STRING> \\[^ntf\"\\0-7] {err("Illegal escape sequence: " + yytext());}
<STRING> [^\"\n\\]+ {string.append(yytext());}

"/*"   {yybegin(COMMENT);}
<COMMENT> "*/"   {yybegin(YYINITIAL);}
<COMMENT> <<EOF>> {err("EOF read inside comment"); return tok(sym.EOF, null);}
<COMMENT> [^*\n]+ {}
<COMMENT> "*"    {}
<COMMENT> \n     {newline();}

.      {err("Illegal character: " + yytext());}
