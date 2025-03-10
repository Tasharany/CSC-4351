package Absyn;
import Symbol.Symbol;

public class OpExp extends Exp {
    public Exp left, right;
    public int oper;

    public final static int PLUS = 0;
    public final static int MINUS = 1;
    public final static int MUL = 2;
    public final static int DIV = 3;
    public final static int EQ = 4;
    public final static int NEQ = 5;
    public final static int LT = 6;
    public final static int LE = 7;
    public final static int GT = 8;
    public final static int GE = 9;
    public final static int AND = 10;
    public final static int OR = 11;
    public final static int NOT = 12;
    public final static int NE = 13;
    public OpExp(int p, Exp l, int o, Exp r) {
        super(p);
        left = l;
        oper = o;
        right = r;
    }
}
