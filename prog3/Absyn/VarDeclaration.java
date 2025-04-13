package Absyn;

public class VarDeclaration extends Dec {
    public Bitfield bitfield;
    public Type type;
    public String name;
    public Exp init;

    public VarDeclaration(int p, Bitfield bf, Type t, String n, Exp i) {
        pos=p;
        bitfield = bf;
        type = t;
        name = n;
        init = i;
    }

}
