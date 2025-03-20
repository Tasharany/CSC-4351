Team Members: Tiffany Scroggins, Troy Williams, Jaislen Nolasco, Jack M Frost
Group S:cs435133, 57, 66, 80

Directory Tree:

     prog1/
     ├── Makefile
     ├── lib (libray files)
     ├── Parse/
     │   ├── Tiger.lex 
     |   ├── Lexer.java
     |   ├── sym.java
     |   ├── Main.java
     │   ├── Yylex.java (auto-generated) 
     │   ├── *.java (Java source files)
     │   └── *.class (Compiled classes)

If on linux, WSL or classes server:

cd prog1

chmod +x run.sh
./run.sh

or 

make clean
make
java Parse.Main test.c

Expected output:
ID 0    $int
ID 4    $main
LPAREN 8
ID 9    $void
RPAREN 13
LBRACE 15
ID 22   $char
TIMES 27
ID 28   $msg
ASSIGN 32
ID 35   $Test
RBRACE 184
EOF 185

What Works Successfully:
* Basic arithmetic and comparison operators
* String literals with escape sequences (\n, \t, \", \\)
* Hexadecimal (0xFF) and octal (0777) number recognition
* Compound assignments (+=, -=, *=, >>=, etc.)
* Multi-line comments /* ... */
* Control structures (if, while, for)
* Identifiers with varied patterns (_test123, var_name)
* Compound operators (++, --, ->)
* Multiple statements and complex combinations
- Proper error handling for:
    * Unterminated strings and comments
    * Invalid escape sequences
    * Illegal characters

Implementation Decisions:
The lexer uses two states (STRING and COMMENT) for complex token handling. String literals accumulate in a StringBuffer, supporting all escape sequences. Comments are handled as a separate state, properly managing multi-line comments and EOF detection within comments. Numbers are processed using Integer.parseInt() for decimal and Integer.decode() for hex/octal. Error reporting is implemented through the ErrorMsg class, providing precise position information for all lexical errors. The implementation successfully balances comprehensive token recognition with robust error handling, making it suitable for real C code analysis.

Resources Used for Implementation:

1. JLex Documentation:
- Used for understanding state declarations (%state STRING COMMENT)
- Regular expression syntax for patterns
- Understanding the three-section format (declarations, rules, user code)

2. Tiger Book (Modern Compiler Implementation):
- Basic lexer structure
- Error handling approach using ErrorMsg class
- Understanding token generation with sym.java

3. C Language Specification:
- List of keywords and operators
- Rules for identifiers
- Number formats (decimal, hex, octal)
- String literal escape sequences

4. Example Code Provided:
- Basic structure:
  " "   {}
  \n    {newline();}
  ","   {return tok(sym.COMMA, null);}

- Error handling pattern:
  err("EOF read inside string")

- Token format:
  <STATE> {illegal_regex} {err("Error")}

