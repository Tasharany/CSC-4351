package Absyn;
import Symbol.Symbol;

public class FieldExpList extends Absyn {
   public Symbol.Symbol name;
   public Exp init;
   public FieldExpList tail;

   public FieldExpList(int p, Symbol.Symbol n, Exp i, FieldExpList t) {
      super(p);
      name = n;
      init = i;
      tail = t;
   }
}

