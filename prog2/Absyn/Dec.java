package Absyn;
import Symbol.Symbol;
import java.util.ArrayList;

public class Dec extends Absyn {
    public static final int VAR_DEC = 0;
    public static final int FUN_DEC = 1;
    public static final int FUN_PROTO = 2;
    public static final int TYPEDEF_DEC = 3;
    public static final int STRUCT_UNION_DEC = 4;
    public static final int ENUM_DEC = 5;

    public int kind;
    public ArrayList<BitfieldValue> bitfields;
    public Type type;
    public Name name;
    public Object extra; // initialization, parameters, etc.
    public boolean hasVarArgs; // for function declarations

    public Dec(int p, int k, ArrayList<BitfieldValue> b, Type t, Name n, Object e) {
        super(p);
        kind = k;
        bitfields = b;
        type = t;
        name = n;
        extra = e;
    }
}

