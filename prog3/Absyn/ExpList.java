package Absyn;
import java.util.ArrayList;
import Symbol.Symbol;

public class ExpList extends Exp{
    public Exp head;
    public ExpList tail;

    public ExpList(Exp h, ExpList t) {
        head = h;
        tail = t;
    }
}
