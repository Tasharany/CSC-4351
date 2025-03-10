package Parse;

import java.io.PrintWriter;

public class Main {
  public static void main(String args[]) {
    if (args.length != 1) {
      System.out.println("Usage: java Parse.Main <filename>");
      return;
    }

    try {
      String filename = args[0];
      Parse parser = new Parse(filename);
      Absyn.Exp exp = parser.parse();

      if (exp != null) {
        System.out.println("Parsing completed successfully");
        Absyn.Print printer = new Absyn.Print(System.out);
        printer.prExp(exp, 0);
      } else {
        System.out.println("Parsing produced null expression");
      }
    } catch (Exception e) {
      System.err.println("Parsing failed:");
      e.printStackTrace();
    }
  }
}

