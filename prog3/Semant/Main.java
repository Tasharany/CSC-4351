package Semant;
import java.io.PrintWriter;

import Absyn.Exp;
import Parse.Parse;

public class Main {
    static Frame.Frame frame = new Mips.MipsFrame();

    public static void main(String[] argv) {
        for (String filename : argv) {
            if (argv.length > 1)
                System.out.println("***Processing: " + filename);
            Parse parse = new Parse(filename);
            Semant semant = new Semant(frame, parse.errorMsg);
            semant.transProg((Exp) parse.absyn);
            PrintWriter writer = new PrintWriter(System.out);
            Absyn.Print printer = new Absyn.Print(writer);

            // Don't cast to OpExp, just use the Exp interface
            printer.prExp((Exp) parse.absyn, 0);

            writer.println();
            writer.flush();
        }
    }
}
