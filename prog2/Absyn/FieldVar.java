package Absyn;
import Symbol.Symbol;
public class FieldVar extends Var {
   public Var var;
   public Symbol field;
   public static final int CONST = 0;
   public static final int VOLATILE = 1;
   public static final int EXTERN = 2;
   public static final int STATIC = 3;
   public static final int AUTO = 4;
   public static final int REGISTER = 5;
   public FieldVar(int p, Var v, Symbol f)
   {  super(p);
      value = v;
      field=f;
   }
}
