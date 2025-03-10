package Absyn;
import Symbol.Symbol;

public class IfExp extends Exp {
   public Exp test;
   public Exp thenclause;
   public Exp elseclause;

   public IfExp(int p, Exp x, Exp y) {
      super(p);
      test = x;
      thenclause = y;
      elseclause = null;
   }

   public IfExp(int p, Exp x, Exp y, Exp z) {
      super(p);
      test = x;
      thenclause = y;
      elseclause = z;
   }
}

