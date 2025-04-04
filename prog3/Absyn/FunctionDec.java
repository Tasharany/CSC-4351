package Absyn;

public class FunctionDec extends Dec {
  public Symbol name;
  public FieldList params;
  public Symbol result;  // null means procedure
  public Exp body;
  public FunctionDec next;  // for mutual recursion
  public boolean leaf;   // For identifying leaf functions

  public FunctionDec(int p, Symbol n, FieldList a, Symbol r, Exp b, FunctionDec x) {
    pos = p;
    name = n;
    params = a;
    result = r;
    body = b;
    next = x;
    leaf = true;  // Initialize to true (was false)
  }
}
