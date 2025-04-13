package Absyn;

public class Enumerator extends Dec {
    public String name;
    public Exp val;

    public Enumerator(int p, String n, Exp v) {
        pos=p;
        name=n;
        val=v;
    }

}
