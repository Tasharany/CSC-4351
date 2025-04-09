package Semant;
import java.io.*;
import Parse.Parse;
import ErrorMsg.ErrorMsg;
import Frame.Frame;
import Mips.MipsFrame;

public class Main {
  static Frame frame = new MipsFrame();

  public static void main(String argv[]) {
    for (int i = 0; i < argv.length; ++i) {
      String filename = argv[i];
      if (argv.length > 1)
        System.out.println("***Processing: " + filename);

      // Debug: Print file contents
      try {
        System.out.println("Reading file: " + filename);
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);
        }
        reader.close();
        System.out.println("---End of file---\n");
      } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
        continue;
      }

      Parse parse = new Parse(filename);

      // Run escape analysis
      if (parse.absyn instanceof Absyn.Exp) {
        FindEscape.FindEscape escapeAnalyzer =
                new FindEscape.FindEscape((Absyn.VarExp)parse.absyn);
      }

      // Create semantic analyzer with frame
      Semant semant = new Semant(frame, parse.errorMsg);

      // Run semantic analysis
      if (parse.absyn instanceof Absyn.Exp) {
        semant.transProg((Absyn.Exp)parse.absyn);
      }

      // Print results if no errors
      if (!parse.errorMsg.anyErrors) {
        PrintWriter writer = new PrintWriter(System.out);
        Absyn.Print printer = new Absyn.Print(writer);
        if (parse.absyn instanceof Absyn.Exp) {
          printer.prExp((Absyn.Exp)parse.absyn, 0);
        }
        writer.println();
        writer.flush();
      }
    }
  }
}
