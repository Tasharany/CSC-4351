package FindEscape;
import Absyn.FieldList;

public class FormalEscape extends Escape {
  public FieldList field;

  public FormalEscape(int d, FieldList f) {
    super(d);
    field = f;
  }
}
