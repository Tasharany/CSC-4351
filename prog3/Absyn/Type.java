package Absyn;
import java.util.ArrayList;

public class Type extends Dec {
    public String name;
    public int pointerCount;
    public ArrayList<ArrayType> brackets;

    public Type(int p, String n, int pc, ArrayList<ArrayType> b) {
        pos=p;
        name = n;
        pointerCount = pc;
        brackets = b;

    }

}
