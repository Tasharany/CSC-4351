package Absyn;

public class Parameter extends Dec {
    public Type type;
    public String name;
    public Parameter(int p, Type t, String n) {
        pos=p;
        name = n;
        type = t;
    }
}
