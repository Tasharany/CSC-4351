package Absyn;
import java.util.ArrayList;

public class DeclarationList extends Dec {
    public ArrayList<Dec> list;
  public DeclarationList(int p, ArrayList<Dec> l) {
    pos = p;
    list = l;
  }

}
