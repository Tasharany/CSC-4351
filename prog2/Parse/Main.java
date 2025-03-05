package Parse;
import java.io.PrintWriter;

public class Main {
  public static void main(String argv[]) throws java.io.IOException {
    if (argv.length == 0) {
      System.out.println("Usage: java Parse.Main <filename> [-tokens]");
      return;
    }

    for (int i = 0; i < argv.length; ++i) {
      String filename = argv[i];
      if (argv.length > 1)
        System.out.println("***Processing: " + filename);

      boolean showTokens = (i + 1 < argv.length && argv[i + 1].equals("-tokens"));

      if (showTokens) {
        // Lexical analysis mode
        ErrorMsg.ErrorMsg errorMsg = new ErrorMsg.ErrorMsg(filename);
        java.io.InputStream inp = new java.io.FileInputStream(filename);
        Lexer lexer = new Yylex(inp, errorMsg);
        java_cup.runtime.Symbol tok;

        do {
          tok = lexer.nextToken();
          String extra = "";
          switch (tok.sym) {
            case sym.ID: extra = "\t$" + tok.value; break;
            case sym.DECIMAL_LITERAL: extra = "\t#" + tok.value; break;
            case sym.STRING_LITERAL: extra = " \"" + tok.value + "\""; break;
          }
          System.out.println(symnames[tok.sym] + " " + tok.left + extra);
        } while (tok.sym != sym.EOF);

        inp.close();
        i++; // Skip the -tokens argument
      } else {
        // Parsing mode
        try {
          Parse parse = new Parse(filename);
          PrintWriter writer = new PrintWriter(System.out);
          Absyn.Print printer = new Absyn.Print(writer);
          printer.prExp(parse.absyn, 0);
          writer.println();
          writer.flush();
        } catch (Exception e) {
          System.err.println("Error processing file " + filename + ":");
          e.printStackTrace();
        }
      }
    }
  }
}
