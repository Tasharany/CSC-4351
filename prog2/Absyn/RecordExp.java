package Absyn;
import Symbol.Symbol;

public class RecordExp extends Exp {
   public Symbol.Symbol typ;
   public FieldExpList fields;

   public RecordExp(int p, Symbol.Symbol t, FieldExpList f) {
      super(p);
      typ = t;
      fields = f;
   }
}

