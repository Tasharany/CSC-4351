package Absyn;
import Symbol.Symbol;

public class FieldVar extends Var {
   public Var var;
   public Symbol.Symbol field;

   public FieldVar(int p, Var v, Symbol.Symbol f) {
      super(p);
      var = v;
      field = f;
   }
}

