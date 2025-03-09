package Absyn;
import java.util.ArrayList;
import Symbol.Symbol;
abstract public class Ty extends Absyn {
    public NameTy name;
    public ArrayList<Boolean> pointers; // true for each *
    public ArrayList<Exp> arrayDimensions; // null for [], Exp for [exp]

    public Ty(int p, NameTy n) {
        super(p);
        name = n;
        pointers = new ArrayList<>();
        arrayDimensions = new ArrayList<>();
    }
}

