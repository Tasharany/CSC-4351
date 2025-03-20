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
  errorMsg.error(pos,s);
}

private void err(String s) {
  err(yychar,s);
}

private java_cup.runtime.Symbol tok(int kind) {
    return tok(kind, null);
}

private java_cup.runtime.Symbol tok(int kind, Object value) {
    return new java_cup.runtime.Symbol(kind, yychar, yychar+yylength(), value);
}

private ErrorMsg errorMsg;

Yylex(java.io.InputStream s, ErrorMsg e) {
  this(s);
  errorMsg=e;
}

private StringBuffer string = new StringBuffer();
%}

%states STRING COMMENT

%%

<YYINITIAL> {
  "var"       { return tok(sym.VAR, null); }
  "fun"       { return tok(sym.FUN, null); }

  "+"         { return tok(sym.PLUS, null); }
  "-"         { return tok(sym.MINUS, null); }
  "*"         { return tok(sym.TIMES, null); }
  "/"         { return tok(sym.DIVIDE, null); }
  "="         { return tok(sym.EQ, null); }
  "<"         { return tok(sym.LT, null); }
  "<="        { return tok(sym.LE, null); }
  ">"         { return tok(sym.GT, null); }
  ">="        { return tok(sym.GE, null); }

  "("         { return tok(sym.LPAREN, null); }
  ")"         { return tok(sym.RPAREN, null); }
  "{"         { return tok(sym.LBRACE, null); }
  "}"         { return tok(sym.RBRACE, null); }
  "["         { return tok(sym.LBRACK, null); }
  "]"         { return tok(sym.RBRACK, null); }
  ","         { return tok(sym.COMMA, null); }
  ";"         { return tok(sym.SEMICOLON, null); }

  [0-9]+      { return tok(sym.INT, Integer.parseInt(yytext())); }
  0[xX][0-9a-fA-F]+ { return tok(sym.INT, Integer.decode(yytext())); }
  0[0-7]+     { return tok(sym.INT, Integer.decode(yytext())); }

  "/*"        { yybegin(COMMENT); }

  \"          { string.setLength(0); yybegin(STRING); }

  [ \t\r\f]   { }
  \n          { newline(); }

  [a-zA-Z][a-zA-Z0-9_]* { return tok(sym.ID, yytext()); }
}

<STRING> {
  \"          { yybegin(YYINITIAL);
                return tok(sym.STRING, string.toString()); }

  \\n         { string.append('\n'); }
  \\t         { string.append('\t'); }
  \\r         { string.append('\r'); }
  \\\"        { string.append('\"'); }
  \\\\        { string.append('\\'); }
  \\[0-7]{1,3} {
                int val = Integer.parseInt(yytext().substring(1), 8);
                string.append((char)val);
              }

  [^\n\"\\]+  { string.append(yytext()); }

  \n          { err("Unterminated string constant");
                yybegin(YYINITIAL); }
}

<COMMENT> {
  "*/"        { yybegin(YYINITIAL); }
  [^*\n]+     { }
  "*"         { }
  \n          { newline(); }
}

.             { err("Illegal character: " + yytext()); }
