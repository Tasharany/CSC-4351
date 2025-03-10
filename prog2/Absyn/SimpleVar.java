package Absyn;
import Symbol.Symbol;

public class SimpleVar extends Var {
   public Symbol.Symbol name;
   public SimpleVar(int p, Symbol.Symbol n) {
      super(p);
      name = n;
   }
}

