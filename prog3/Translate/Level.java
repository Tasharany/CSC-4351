package Translate;
import Frame.*;
import Symbol.Symbol;

public class Level {
  public Level parent;
  public Frame frame;
  public AccessList formals;

  public Level(Level p, Label name, BoolList formals) {
    parent = p;
    frame = (p == null) ? null :
            p.frame.newFrame(name.toString(), formals);
    if (frame != null) {
      AccessList head = null, tail = null;
      for (Frame.AccessList a = frame.formals; a != null; a = a.tail) {
        Access access = new Access(this, a.head);
        if (head == null) {
          head = tail = new AccessList(access, null);
        } else {
          tail.tail = new AccessList(access, null);
          tail = tail.tail;
        }
      }
      this.formals = head;
    }
  }

  public Access allocLocal(boolean escape) {
    return new Access(this, frame.allocLocal(escape));
  }
}


