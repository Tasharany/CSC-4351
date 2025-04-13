package Absyn;

public class FunctionDefinition extends Dec {
  public Bitfield bitfield;
  public Type type;
  public String name;
  public ParameterList params;
  public Statement body;

  public FunctionDefinition(int p, Bitfield bf, Type t, String n, ParameterList pl, Statement cs) {
    pos = p;
    bitfield = bf;
    type=t;
    name=n;
    params=pl;
    body=cs;
  }
}
