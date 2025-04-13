Team Members: Tiffany Scroggins, Jaislen Nolasco, Jack M Frost

If on linux, WSL or classes server:

cd prog3

chmod +x test.sh

./test.sh


What's Implemented:

1. Basic Grammar Structure: You have a grammar file for the Tiger language with rules for expressions, declarations, and other language constructs.

2. Let-In-End Expressions: Your grammar includes support for let-in-end blocks.

3. Type Declarations: Based on our conversation, it seems you have implemented type declarations.

4. Function Declarations: Your grammar includes rules for function declarations.

5. Variable Declarations with Type Annotations

What's Missing or Problematic

1. Variable Declarations without Type Annotations: Your grammar doesn't support variable declarations without type annotations (e.g., `var x := 5`), which is causing the syntax error in your test file.

2. Possible Issues with Declaration Lists: There might be issues with how declaration lists are handled in let expressions.

3. Expression Sequences: The error output shows an empty `SeqExp(ExpList())`, suggesting issues with how expression sequences are parsed.
