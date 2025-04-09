package Semant;
import Types.Type;
import Frame.Label;
import Translate.Level;

public class FunEntry extends Entry {
  public Level level;
  public Label label;
  public Types.RECORD formals;
  public Type result;

  public FunEntry(Level l, Label lab, Types.RECORD f, Type r) {
    level = l;
    label = lab;
    formals = f;
    result = r;
  }
}



