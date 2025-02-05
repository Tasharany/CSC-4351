# Makefile for compiling the lexer and Java files

# Compiler flags
JFLAGS = -g

# Source and test files
TEST_FILE = test.c

# Setting up CLASSPATH
CLASSPATH = $(shell pwd)/lib/classes:$(shell pwd)

# Targets
all: Parse/*.class

# Rule to compile Java classes
Parse/*.class: Parse/*.java Parse/Yylex.java
	javac -cp $(CLASSPATH) $(JFLAGS) Parse/*.java

# Rule to generate Yylex.java from Tiger.lex
Parse/Yylex.java: Parse/Tiger.lex
	cd Parse; java -cp $(CLASSPATH) JLex.Main Tiger.lex; mv Tiger.lex.java Yylex.java

# Clean target to remove compiled classes and generated files
clean:
	rm -f Parse/*.class Parse/Yylex.java

# Run target to execute the lexer with test.c
run: all
	java -cp $(CLASSPATH) Parse.Main $(TEST_FILE)
