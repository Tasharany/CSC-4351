package Absyn;

public class StructMember extends Dec {
    public Type type;
    public String name;
    public StructMember(int p, Type t, String n) {
        pos=p;
        name = n;
        type = t;
    }
}
