package Semant;
import java.io.PrintWriter;

import Absyn.Exp;
import Absyn.VarExp;
import Parse.Parse;
import Translate.Level;

public class Main {
  public static void main(String argv[]) {
    for (int i = 0; i < argv.length; ++i) {
      String filename = argv[i];
      if (argv.length > 1)
        System.out.println("***Processing: " + filename);

      Parse parse = new Parse(filename);

      FindEscape.FindEscape escapeAnalyzer = new FindEscape.FindEscape((VarExp) parse.absyn);


      // Create semantic analyzer and run it
      Semant semant = new Semant(new Env(), new Level()); // With proper Env and Level instances


      // Print results
      PrintWriter writer = new PrintWriter(System.out);
      print((Exp) parse.absyn, writer);
      writer.flush();
    }
  }

  private static void print(Absyn.Exp exp, PrintWriter writer) {
    if (exp == null) return;

    Absyn.Print printer = new Absyn.Print(writer);
    printer.prExp(exp, 0);
    writer.println();
  }
}
