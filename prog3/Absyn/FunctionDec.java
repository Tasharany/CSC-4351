package Absyn;
import Symbol.Symbol;

public class FunctionDec extends Dec {
  public Symbol name;
  public FieldList params;
  public NameTy result;  // Optional return type
  public Exp body;
  public FunctionDec next;  // For mutually recursive functions
  public boolean leaf;  // Changed initialization
  public Entry entry;

  public FunctionDec(int p, Symbol n, FieldList a, NameTy r, Exp b, FunctionDec x) {
    pos = p;
    name = n;
    params = a;
    result = r;
    body = b;
    next = x;
    leaf = true;  // Initialize to true instead of false
  }
}
