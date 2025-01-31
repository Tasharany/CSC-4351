Make sure your directory looks like this:

     prog1/
     ├── Makefile
     ├── run.sh (script file)
     ├── lib (libray files)
     ├── Parse/
     │   ├── Tiger.lex --------- start here
     |   ├── Lexer.java
     |   ├── sym.java
     |   ├── Main.java
     │   ├── Yylex.java (auto-generated) 
     │   ├── *.java (Java source files)
     │   └── *.class (Compiled classes)
     

-Script and Jlex library are within the files.
-main file needed to be chnaged is tiger.lex

If on linux or classes server:
To run the script, first run "chmod +x run.sh"
Then run the command "./run.sh"

If on Windows or IDE
Install WSL or type "bash" in cmd
run the command "sh /root/prog1/run.sh"

Exspected Output:
rm -f */*.class Parse/Yylex.java
cd Parse; java JLex.Main Tiger.lex; mv Tiger.lex.java Yylex.java
Processing first section -- user code.
Processing second section -- JLex declarations.
Processing third section -- lexical rules.
Creating NFA machine representation.
NFA comprised of 285 states.
Creating DFA transition table.
Working on DFA states..............................................................................................................
Minimizing DFA transition table.
49 states after removal of redundant states.
Outputting lexical analyzer code.
javac -g Parse/*.java

Errors:
/usr/bin/env : The term '/usr/bin/env' is not recognized as the name of a cmdlet, function, script file, or operable program. Check the spelling of the name,    
or if a path was included, verify that the path is correct and try again.
then type "bash"

Next steps:
Creating test.c



