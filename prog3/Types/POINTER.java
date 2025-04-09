package Types;
public class POINTER extends Type {
  public Type base;
  public POINTER(Type t) { base = t; }
  public boolean coerceTo(Type t) {
    return (t instanceof POINTER);
  }
}
