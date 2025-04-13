package Absyn;
import java.util.ArrayList;

public class StructDeclaration extends Dec {

    public Bitfield bitfield;
    public int kind;
    public String name;
    public ArrayList<StructMember> body;

    public StructDeclaration(int p, Bitfield bf, int k, String n, ArrayList<StructMember> b) {
      pos = p;
      bitfield = bf;
      name=n;
      body=b;
      kind=k;
  }

   public final static int STRUCT=0, UNION=1;
}
