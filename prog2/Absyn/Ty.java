package Absyn;
import java.util.ArrayList;
import Symbol.Symbol;
abstract public class Ty extends Absyn {
    public static final int VOID = 0;
    public static final int CHAR = 1;
    public static final int SHORT = 2;
    public static final int INT = 3;
    public static final int LONG = 4;
    public static final int FLOAT = 5;
    public static final int DOUBLE = 6;
    public NameTy name;
    public int type;
    public ArrayList<Boolean> pointers; // true for each *
    public ArrayList<Exp> arrayDimensions; // null for [], Exp for [exp]

    public Ty(int p, NameTy n) {
        super(p);
        name = n;
        pointers = new ArrayList<>();
        arrayDimensions = new ArrayList<>();
    }
}

