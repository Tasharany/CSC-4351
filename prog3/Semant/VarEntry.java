package Semant;

public class VarEntry extends Entry {
  public Translate.Access access;  // Add access field for activation records
  public Types.Type ty;

  // Update constructor to include access parameter
  public VarEntry(Translate.Access a, Types.Type t) {
    access = a;
    ty = t;
  }

  // Keep the original constructor for compatibility
  public VarEntry(Types.Type t) {
    this(null, t);  // Call new constructor with null access
  }
}
