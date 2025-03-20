package Absyn;
import Symbol.Symbol;

public class BitfieldValue extends Absyn {
   public static final int CONST = 0;
   public static final int VOLATILE = 1;
   public static final int EXTERN = 2;
   public static final int STATIC = 3;
   public static final int AUTO = 4;
   public static final int REGISTER = 5;

   public int value;

   public BitfieldValue(int p, int v) {
      super(p);
      value = v;
   }
}
