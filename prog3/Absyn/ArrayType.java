package Absyn;

public class ArrayType extends Dec {
    // Can either be empty
    public Exp size;
    public ArrayType(int p, Exp s) {
        pos=p;
        size=s;
    }

}
