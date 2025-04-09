package Frame;
import Symbol.Symbol;
import Temp.Label;

abstract public class Frame {
  public Label name;
  public AccessList formals;

  abstract public Access allocLocal(boolean escape);
  abstract public Frame newFrame(Symbol name, BoolList formals);
  abstract public int wordSize();
}
