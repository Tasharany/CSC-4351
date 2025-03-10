package Absyn;
import Symbol.Symbol;

public class ArrayExp extends Exp {
   public Symbol.Symbol typ;
   public Exp size, init;

   public ArrayExp(int p, Symbol.Symbol t, Exp s, Exp i) {
      super(p);
      typ = t;
      size = s;
      init = i;
   }
}