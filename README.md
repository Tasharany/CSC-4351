Make sure your directory looks like this:

     prog2/
     ├── Makefile
     ├── lib (libray files)
     ├── Parse/
     │   ├── Grm.cup
     |   ├── Lexer.java
     |   ├── sym.java
     |   ├── Main.java
     |   ├── Tiger.lex
     |   ├── Parse.java
     |   ├── Yylex.java (auto-generated)
     |   ├── Grm.java (auto-generated)
     |   ├── Grm.err (auto-generated)
     |   ├── Grm.out (auto-generated)
     |   ├── Grm.class (auto-generated)
If on linux, WSL or classes server:

cd prog2

make 

make test 
Expected Output: SeqExp(
                        ExpList())


make clean

________





