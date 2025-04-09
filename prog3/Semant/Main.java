package Semant;
import Parse.Parse;
import Frame.Frame;
import ErrorMsg.ErrorMsg;
import Absyn.Exp;

public class Main {
  static Frame frame = new Mips.MipsFrame();

  public static void main(String argv[]) {
    for (int i = 0; i < argv.length; ++i) {
      String filename = argv[i];
      ErrorMsg errorMsg = new ErrorMsg(filename);
      Parse parse = new Parse(filename);

      if (parse.absyn instanceof Exp) {
        Semant semant = new Semant(frame, errorMsg);
        semant.transProg((Exp)parse.absyn);
      }
    }
  }
}

