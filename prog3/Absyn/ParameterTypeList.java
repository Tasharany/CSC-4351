package Absyn;
import java.util.ArrayList;

public class ParameterTypeList extends Dec {
    public ArrayList<Type> params;
    public boolean elipses;
    public ParameterTypeList(int p, boolean e, ArrayList<Type> l) {
        pos=p;
        elipses = e;
        params = l;
    }
}
