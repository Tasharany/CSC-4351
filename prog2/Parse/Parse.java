package Parse;

public class Parse {
    public ErrorMsg.ErrorMsg errorMsg;
    public Absyn.Exp absyn;

    public Parse(String filename) {
        errorMsg = new ErrorMsg.ErrorMsg(filename);
        java.io.InputStream inp;

        try {
            inp = new java.io.FileInputStream(filename);
        } catch (java.io.FileNotFoundException e) {
            throw new Error("File not found: " + filename);
        }

        // Create lexer
        Lexer lexer = new Yylex(inp, errorMsg);

        // Create parser with the lexer
        Grm parser = new Grm(lexer, errorMsg);

        try {
            // Parse the input and get the abstract syntax tree
            absyn = (Absyn.Exp)(parser./*debug_*/parse().value);
        } catch (Throwable e) {
            // Print the stack trace for debugging
            e.printStackTrace();
            throw new Error("Syntax error: " + e.toString());
        } finally {
            try {
                inp.close();
            } catch (java.io.IOException e) {
                // Ignore close errors
            }
        }
    }

    // Method to get the error message handler
    public ErrorMsg.ErrorMsg getErrorMsg() {
        return errorMsg;
    }

    // Method to get the abstract syntax tree
    public Absyn.Exp getAbsyn() {
        return absyn;
    }

    // Optional debug method to print parsing details
    private void debugPrint(String message) {
        System.err.println("DEBUG: " + message);
    }

    // Method to check if any errors occurred during parsing
    public boolean hasErrors() {
        return errorMsg.anyErrors;
    }
}