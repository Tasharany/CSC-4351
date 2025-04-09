package Absyn;
import Symbol.Symbol;
import Semant.Entry;
public class VarDec extends Dec {
   public Symbol name;
   public NameTy typ;  // Optional type
   public Exp init;
   public boolean escape;  // Changed initialization
   public Entry entry;

   public VarDec(int p, Symbol n, NameTy t, Exp i) {
      pos = p;
      name = n;
      typ = t;
      init = i;
      escape = false;  // Initialize to false instead of true
   }
}
