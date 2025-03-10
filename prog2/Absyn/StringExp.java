package Absyn;
import Symbol.Symbol;

public class StringExp extends Exp {
   public String value;

   public StringExp(int p, String v) {
      super(p);
      value = v;
   }
}

