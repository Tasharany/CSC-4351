package Absyn;
import java.util.ArrayList;

public class EnumDeclaration extends Dec {
    public String enumName;
    public ArrayList<Enumerator> body;

    public EnumDeclaration(int p, String n, ArrayList<Enumerator> b) {
        pos=p;
        enumName=n;
        body=b;
    }

}
