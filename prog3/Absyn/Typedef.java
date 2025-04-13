package Absyn;

public class Typedef extends Dec {
  public Type type;
  public String name;
  public Typedef(int p, Type t, String n) {
      pos = p;
      type = t;
      name = n;
  }
}
