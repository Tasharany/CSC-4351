package Absyn;
import Symbol.Symbol;

public class FieldList extends Absyn {
   public Symbol.Symbol name;
   public Symbol.Symbol typ;
   public FieldList tail;

   public FieldList(int p, Symbol.Symbol n, Symbol.Symbol t, FieldList x) {
      super(p);
      name = n;
      typ = t;
      tail = x;
   }
}

