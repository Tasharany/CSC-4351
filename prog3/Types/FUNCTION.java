package Types;
public class FUNCTION extends Type {
  public RECORD params;
  public Type result;
  public FUNCTION(RECORD p, Type r) {
    params = p;
    result = r;
  }
}

