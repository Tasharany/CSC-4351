package Types;
public class CHAR extends Type {
  public boolean coerceTo(Type t) {
    return (t instanceof CHAR || t instanceof INT);
  }
}
