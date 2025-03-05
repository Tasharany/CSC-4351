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

        Lexer lexer = new Yylex(inp, errorMsg);
        Grm parser = new Grm(lexer, errorMsg);

        try {
            absyn = (Absyn.Exp)(parser.parse().value);
        } catch (Throwable e) {
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
}





