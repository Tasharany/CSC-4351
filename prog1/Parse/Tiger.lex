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
/* Keywords */
<YYINITIAL> "var"      { return tok(sym.VAR); }
<YYINITIAL> "function" { return tok(sym.FUNCTION); }
<YYINITIAL> "break"    { return tok(sym.BREAK); }
<YYINITIAL> "of"       { return tok(sym.OF); }
<YYINITIAL> "end"      { return tok(sym.END); }
<YYINITIAL> "in"       { return tok(sym.IN); }
<YYINITIAL> "nil"      { return tok(sym.NIL); }
<YYINITIAL> "let"      { return tok(sym.LET); }
<YYINITIAL> "do"       { return tok(sym.DO); }
<YYINITIAL> "to"       { return tok(sym.TO); }
<YYINITIAL> "for"      { return tok(sym.FOR); }
<YYINITIAL> "while"    { return tok(sym.WHILE); }
<YYINITIAL> "else"     { return tok(sym.ELSE); }
<YYINITIAL> "then"     { return tok(sym.THEN); }
<YYINITIAL> "if"       { return tok(sym.IF); }
<YYINITIAL> "array"    { return tok(sym.ARRAY); }
<YYINITIAL> "type"     { return tok(sym.TYPE); }

/* Operators */
<YYINITIAL> "+"        { return tok(sym.PLUS); }
<YYINITIAL> "-"        { return tok(sym.MINUS); }
<YYINITIAL> "*"        { return tok(sym.TIMES); }
<YYINITIAL> "/"        { return tok(sym.DIVIDE); }
<YYINITIAL> "="        { return tok(sym.EQ); }
<YYINITIAL> "<>"       { return tok(sym.NEQ); }
<YYINITIAL> "<"        { return tok(sym.LT); }
<YYINITIAL> "<="       { return tok(sym.LE); }
<YYINITIAL> ">"        { return tok(sym.GT); }
<YYINITIAL> ">="       { return tok(sym.GE); }
<YYINITIAL> "&"        { return tok(sym.AND); }
<YYINITIAL> "|"        { return tok(sym.OR); }
<YYINITIAL> ":="       { return tok(sym.ASSIGN); }

/* Punctuation */
<YYINITIAL> "("        { return tok(sym.LPAREN); }
<YYINITIAL> ")"        { return tok(sym.RPAREN); }
<YYINITIAL> "["        { return tok(sym.LBRACK); }
<YYINITIAL> "]"        { return tok(sym.RBRACK); }
<YYINITIAL> "{"        { return tok(sym.LBRACE); }
<YYINITIAL> "}"        { return tok(sym.RBRACE); }
<YYINITIAL> "."        { return tok(sym.DOT); }
<YYINITIAL> ","        { return tok(sym.COMMA); }
<YYINITIAL> ";"        { return tok(sym.SEMICOLON); }

/* Numbers */
<YYINITIAL> [0-9]+     { return tok(sym.INT_LITERAL, Integer.parseInt(yytext())); }
<YYINITIAL> 0x[0-9a-fA-F]+ { return tok(sym.INT_LITERAL, Integer.decode(yytext())); }
<YYINITIAL> 0[0-7]+    { return tok(sym.INT_LITERAL, Integer.parseInt(yytext(), 8)); }

/* Strings */
<YYINITIAL> \"         { yybegin(STRING); }
<STRING> \"            { yybegin(YYINITIAL); return tok(sym.STRING_LITERAL, yytext()); }
<STRING> [^\"\n\\]+    { }
<STRING> \\n           { }
<STRING> \\t           { }
<STRING> \\[0-7]{3}    { }
<STRING> \\\\          { }
<STRING> \\\"|\\       { }
<STRING> \n            { err("Unterminated string constant"); }

/* Comments */
<YYINITIAL> "/*"       { yybegin(COMMENT); }
<COMMENT> "*/"         { yybegin(YYINITIAL); }
<COMMENT> [^*\n]+      { }
<COMMENT> "*"          { }
<COMMENT> \n           { newline(); }

/* Error catching */
. { err("Illegal character: " + yytext()); }

