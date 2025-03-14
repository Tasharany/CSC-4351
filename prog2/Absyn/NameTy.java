package Absyn;
import Symbol.Symbol;

public class NameTy extends Ty {
   public Symbol.Symbol name;
   public static final int VOID = 0;
   public static final int CHAR = 1;
   public static final int SHORT = 2;
   public static final int INT = 3;
   public static final int LONG = 4;
   public static final int FLOAT = 5;
   public static final int DOUBLE = 6;
   public static final int ENUM = 7;
   public static final int NAMED = 8;

   public NameTy(int p, Symbol.Symbol n) {
      super(p);
      name = n;
   }
}

