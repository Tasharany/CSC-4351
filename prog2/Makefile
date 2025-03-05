# Compiler and tool flags
JFLAGS = -g

# Source and test files
TEST_FILE = test.c

# Setting up CLASSPATH - Fix path issues
CLASSPATH = $(shell pwd)/lib/classes:$(shell pwd)
# Targets
all: Parse/*.class

# Rule to compile all Java files
Parse/*.class: Parse/*.java Parse/Yylex.java Parse/Grm.java
	javac -cp "$(CLASSPATH)" $(JFLAGS) Parse/*.java

# Rule to generate parser from Grm.cup
Parse/Grm.java: Parse/Grm.cup
	cd Parse; java -cp $(CLASSPATH) java_cup.Main -parser Grm -expect 3 -nonterms -dump_grammar -dump_states <Grm.cup >Grm.out 2>Grm.err

# Rule to generate Yylex.java from Tiger.lex
Parse/Yylex.java: Parse/Tiger.lex
	cd Parse; java -cp $(CLASSPATH) JLex.Main Tiger.lex; mv Tiger.lex.java Yylex.java
# Clean target to remove all generated files
clean:
	rm -f */*.class Parse/Grm.java Parse/Grm.err Parse/Grm.out Parse/Yylex.java
# Run target to execute the program with test.c
run: all
	java -cp $(CLASSPATH) Parse.Main $(TEST_FILE)
