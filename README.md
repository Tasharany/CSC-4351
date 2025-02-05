Make sure your directory looks like this:

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
make

Expected output:
cd Parse; java -cp /root/prog1/lib/classes:/root/prog1 JLex.Main Tiger.lex; mv Tiger.lex.java Yylex.java
Processing first section -- user code.
Processing second section -- JLex declarations.
Processing third section -- lexical rules.
Creating NFA machine representation.
NFA comprised of 252 states.
Creating DFA transition table.
Working on DFA states........................................................................................................
Minimizing DFA transition table.
101 states after removal of redundant states.
Outputting lexical analyzer code.
javac -cp /root/prog1/lib/classes:/root/prog1 -g Parse/*.java


make run
Expected Output:
INT_LITERAL 0   #1
TIMES 1
MINUS 2
INT_LITERAL 3   #2
PLUS 4
INT_LITERAL 5   #2
SEMICOLON 6
EOF 7

make clean

Errors:





