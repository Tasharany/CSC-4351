package FindEscape;
import Absyn.VarDec;

public class VarEscape extends Escape {
  public VarDec dec;

  public VarEscape(int d, VarDec v) {
    super(d);
    dec = v;
  }
}

