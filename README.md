Make sure your directory looks like this:

     prog2/
     ├── Makefile
     ├── lib (libray files)
     ├── Parse/
     │   ├── Grm.cup
     |   ├── Lexer.java
     |   ├── sym.java
     |   ├── Main.java
     |   ├── sym.java
If on linux, WSL or classes server:

cd prog2

make

Expected output:
creation of Grm.err and Grm.out in /Parse


[ERROR]
make run

Expected Output: SeqExp(
                        ExpList())


make clean

________
Need Yylex.classes 


clean:

cp Prase/Yylex.class ./.

rm -f */*.class Parse/Grm.java Parse/Grm.err Parse/Grm.out

mv Yylex.class ./Parse/





