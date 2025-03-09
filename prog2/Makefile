# Compiler and tool flags
JFLAGS = -g

# Source and test files
TEST_FILE = test.c

# Setting up CLASSPATH - Fix path issues
CLASSPATH = $(shell pwd)/lib/classes:$(shell pwd)

# Rule to compile all Java files
Parse/*.class: Parse/*.java  Parse/Grm.java
	javac -cp "$(CLASSPATH)" $(JFLAGS) Parse/*.java

# Rule to generate parser from Grm.cup
Parse/Grm.java: Parse/Grm.cup
	cd Parse; java -cp $(CLASSPATH) java_cup.Main -parser Grm -expect 3 -nonterms -dump_grammar -dump_states <Grm.cup >Grm.out 2>Grm.err

# Clean target to remove all generated files
clean:
	cp Parse/Yylex* ./.
	rm -f */*.class Parse/Grm.java Parse/Grm.err Parse/Grm.out
	mv Yylex* ./Parse/

# Run target to execute the program with test.c
test:
	java -cp $(CLASSPATH) Parse.Main $(TEST_FILE)
