package Absyn;
import Symbol.Symbol;
public class OpExp extends Exp {
    public static final int PLUS = 0;
    public static final int MINUS = 1;
    public static final int TIMES = 2;
    public static final int DIVIDE = 3;
    public static final int AND = 4;
    public static final int OR = 5;
    public static final int EQ = 6;
    public static final int NEQ = 7;
    public static final int LT = 8;
    public static final int LE = 9;
    public static final int GT = 10;
    public static final int GE = 11;
    public static final int UMINUS = 12;

    public Exp left, right;
   public int oper;
   public OpExp(int p, Exp l, int o, Exp r) {super(p); pos=p; left=l; oper=o; right=r;}
   public final static int PLUS=0, MINUS=1, MUL=2, DIV=3,
		    EQ=4, NE=5, LT=6, LE=7, GT=8, GE=9;
}
