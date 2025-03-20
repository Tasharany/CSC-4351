package Absyn;
import Symbol.Symbol;

public class Name extends Absyn {
   public Symbol.Symbol name;

   public Name(int p, Symbol.Symbol n) {
      super(p);
      name = n;
   }
}


