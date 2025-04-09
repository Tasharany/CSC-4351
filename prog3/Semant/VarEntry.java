package Semant;
import Types.Type;
import Frame.Access;

public class VarEntry extends Entry {
    public Type ty;
    public Access access;

    public VarEntry(Type t, Access a) {
        ty = t;
        access = a;
    }
}

