package Absyn;

public class StorageConstant extends Dec {

  public int constant;
  public StorageConstant(int p, int c) {
    pos = p;
    constant = c;
  }

   public final static int CONST=0, VOLATILE=1, EXTERN=2, STATIC=3, AUTO=4, REGISTER=5,
      TYPEDEF = 6;
}
