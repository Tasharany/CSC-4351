package Absyn;
import Symbol.Symbol;

public class Type extends Absyn {
    public static final int VOID = 0;
    public static final int CHAR = 1;
    public static final int SHORT = 2;
    public static final int INT = 3;
    public static final int LONG = 4;
    public static final int FLOAT = 5;
    public static final int DOUBLE = 6;

    public int type;

    public Type(int p, int t) {
        super(p);
        type = t;
    }
}



