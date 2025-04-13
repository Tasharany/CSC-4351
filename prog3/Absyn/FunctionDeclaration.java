package Absyn;

public class FunctionDeclaration extends Dec {

  public Bitfield bitfield;
  public Type type;
  public String name;
  public ParameterTypeList paramtypes;

  public FunctionDeclaration(int p, Bitfield bf, Type t, String n, ParameterTypeList tl) {
    pos = p;
    bitfield = bf;
    type=t;
    name=n;
    paramtypes=tl;
  }
}
