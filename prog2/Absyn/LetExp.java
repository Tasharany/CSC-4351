package Absyn;
import Symbol.Symbol;

public class LetExp extends Exp {
   public DecList decs;
   public Exp body;

   public LetExp(int p, DecList d, Exp b) {
      super(p);
      decs = d;
      body = b;
   }
}