package Absyn;
import java.util.ArrayList;

public class ParameterList extends Dec {
    public ArrayList<Parameter> params;
    public boolean elipses;
    public ParameterList(int p, boolean e, ArrayList<Parameter> l) {
        pos=p;
        elipses = e;
        params = l;
    }
}
