package Absyn;
import Symbol.Symbol;
import java.util.ArrayList;

public class VarDec extends Dec {
   public Symbol name;
   public boolean escape = true;
   public NameTy typ; /* optional */
   public Exp init;
   public VarDec(int p, Symbol n, NameTy t, Exp i) {pos=p; name=n; typ=t; init=i;}
   super(p, b, t, n);
   init = i;
}
