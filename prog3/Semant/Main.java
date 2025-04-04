package Semant;
import java.io.PrintWriter;
import Parse.Parse;

public class Main {
  public static void main(String argv[]) {
    for (int i = 0; i < argv.length; ++i) {
      String filename = argv[i];
      if (argv.length > 1)
        System.out.println("***Processing: " + filename);

      // Parse the input file
      Parse parse = new Parse(filename);

      // Perform escape analysis before semantic analysis
      FindEscape.FindEscape escapeAnalyzer = new FindEscape.FindEscape();
      escapeAnalyzer.findEscape(parse.absyn);

      // Perform semantic analysis
      Semant semant = new Semant(parse.errorMsg);
      semant.transProg(parse.absyn);

      // Print the results
      PrintWriter writer = new PrintWriter(System.out);

      // Print the abstract syntax tree with escape information
      printResults(writer, parse.absyn);

      writer.println("\nEscape Analysis Results:");
      printEscapeAnalysis(writer, parse.absyn);

      writer.println("\nType Checking Results:");
      printTypeInfo(writer, parse.absyn);

      writer.flush();
    }
  }

  private static void printResults(PrintWriter writer, Absyn.Absyn tree) {
    writer.println("Semantic Analysis Results:");

    if (tree instanceof Absyn.DecList) {
      Absyn.DecList decList = (Absyn.DecList)tree;
      while (decList != null) {
        if (decList.head instanceof Absyn.StructDeclaration) {
          Absyn.StructDeclaration struct =
                  (Absyn.StructDeclaration)decList.head;
          writer.printf("%sstruct %s {\n",
                  struct.isExtern ? "extern " : "",
                  struct.name);
          printFields(writer, struct.fields);
          writer.println("}");
        }
        else if (decList.head instanceof Absyn.VarDec) {
          Absyn.VarDec var = (Absyn.VarDec)decList.head;
          writer.printf("var %s %s", getTypeName(var.typ), var.name);
          if (var.init != null) {
            writer.print(" = ");
            printInit(writer, var.init);
          }
          writer.println(";");
        }
        decList = decList.tail;
      }
    }
  }


  private static void printEscapeAnalysis(PrintWriter writer, Absyn.Absyn tree) {
    if (tree instanceof Absyn.LetExp) {
      Absyn.LetExp let = (Absyn.LetExp)tree;
      for (Absyn.DecList decs = let.decs; decs != null; decs = decs.tail) {
        if (decs.head instanceof Absyn.VarDec) {
          Absyn.VarDec var = (Absyn.VarDec)decs.head;
          writer.printf("Variable %s: %s\n",
                  var.name.toString(),
                  var.escape ? "escapes" : "does not escape");
        }
        else if (decs.head instanceof Absyn.FunctionDec) {
          Absyn.FunctionDec func = (Absyn.FunctionDec)decs.head;
          writer.printf("Function %s: %s\n",
                  func.name.toString(),
                  func.leaf ? "leaf function" : "non-leaf function");

          // Print parameter escape information
          for (Absyn.FieldList params = func.params; params != null; params = params.tail) {
            writer.printf("  Parameter %s: %s\n",
                    params.name.toString(),
                    params.escape ? "escapes" : "does not escape");
          }
        }
      }
    }
  }

  private static void printTypeInfo(PrintWriter writer, Absyn.Absyn tree) {
    if (tree instanceof Absyn.LetExp) {
      Absyn.LetExp let = (Absyn.LetExp)tree;
      for (Absyn.DecList decs = let.decs; decs != null; decs = decs.tail) {
        if (decs.head instanceof Absyn.VarDec) {
          Absyn.VarDec var = (Absyn.VarDec)decs.head;
          writer.printf("Variable %s: type %s\n",
                  var.name.toString(),
                  var.typ != null ? var.typ.toString() : "inferred");
        }
        else if (decs.head instanceof Absyn.FunctionDec) {
          Absyn.FunctionDec func = (Absyn.FunctionDec)decs.head;
          writer.printf("Function %s: \n", func.name.toString());
          writer.printf("  Return type: %s\n",
                  func.result != null ? func.result.toString() : "void");// Print parameter types
          for (Absyn.FieldList params = func.params; params != null; params = params.tail) {
            writer.printf("  Parameter %s: type %s\n",
                    params.name.toString(),
                    params.typ.toString());
          }
        }
      }
    }
  }
}
