Script and Jlex library are within the files.

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

Next steps:
Creating test.c



