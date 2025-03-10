package Absyn;
import Symbol.Symbol;

public class ExpList extends Absyn {
   public Exp head;
   public ExpList tail;

   public ExpList(Exp h, ExpList t) {
      super(h.pos);
      head = h;
      tail = t;
   }
}