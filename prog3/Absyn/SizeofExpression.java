package Absyn;

public class SizeofExpression extends Exp {
    Exp exp;
    Dec name;

    public SizeofExpression(int p, Exp e) {
        pos=p;
        exp=e;
        name=null;
    }
    public SizeofExpression(int p, Dec n) {
        name=n;
        pos=p;
        exp=null;
    }

}
