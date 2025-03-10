package Parse;

import ErrorMsg.ErrorMsg;
import Absyn.Exp;

public class Parse {
    private ErrorMsg errorMsg;

    public Parse(String filename) {
        errorMsg = new ErrorMsg(filename);
    }

    public Exp parse() {
        try {
            java.io.InputStream inp = new java.io.FileInputStream(errorMsg.getFileName());
            Lexer lexer = new Yylex(inp, errorMsg);
            Grm parser = new Grm(lexer, errorMsg);
            return (Exp)(parser.parse().value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e.toString());
        }
    }
}

