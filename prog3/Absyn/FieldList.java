package Absyn;

public class FieldList extends Absyn {
   public Symbol name;
   public Symbol typ;
   public FieldList tail;
   public boolean escape;  // For escape analysis

   public FieldList(int p, Symbol n, Symbol t, FieldList x) {
      pos = p;
      name = n;
      typ = t;
      tail = x;
      escape = false;  // Initialize to false (was true)
   }
}
