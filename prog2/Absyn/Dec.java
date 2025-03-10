package Absyn;
import Symbol.Symbol;
import java.util.ArrayList;

abstract public class Dec extends Absyn {
    public static final int VAR = 0;
    public static final int FUN = 1;
    public static final int TYPE = 2;

    public int kind;
    public ArrayList<BitfieldValue> bitfields;
    public Type type;
    public Name name;

    public Dec(int p, int k, ArrayList<BitfieldValue> b, Type t, Name n, Object extra) {
        super(p);
        kind = k;
        bitfields = b;
        type = t;
        name = n;
    }
}


