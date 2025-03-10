package Absyn;
import Symbol.Symbol;

public class SeqExp extends Exp {
   public ExpList list;

   public SeqExp(int p, ExpList l) {
      super(p);
      list = l;
   }
}

