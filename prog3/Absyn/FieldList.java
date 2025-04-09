package Absyn;
import Symbol.Symbol;

public class FieldList extends Absyn {
   public Symbol name;
   public Symbol typ;
   public boolean escape;  // Changed initialization
   public FieldList tail;

   public FieldList(Symbol n, Symbol t, FieldList x) {
      name = n;
      typ = t;
      tail = x;
      escape = false;  // Initialize to false instead of true
   }
}
