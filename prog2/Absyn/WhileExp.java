package Absyn;
import Symbol.Symbol;

public class WhileExp extends Exp {
   public Exp test, body;

   public WhileExp(int p, Exp t, Exp b) {
      super(p);
      test = t;
      body = b;
   }
}

