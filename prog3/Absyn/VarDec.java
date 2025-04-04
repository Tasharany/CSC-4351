package Absyn;

public class VarDec extends Dec {
   public Symbol name;
   public Symbol typ;  // null means to be inferred
   public Exp init;
   public boolean escape;  // For escape analysis

   public VarDec(int p, Symbol n, Symbol t, Exp i) {
      pos = p;
      name = n;
      typ = t;
      init = i;
      escape = false;  // Initialize to false (was true)
   }
}
