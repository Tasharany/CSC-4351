package Absyn;
import Symbol.Symbol;

public class CallExp extends Exp {
   public Symbol.Symbol func;
   public ExpList args;

   public CallExp(int p, Symbol.Symbol f, ExpList a) {
      super(p);
      func = f;
      args = a;
   }
}

