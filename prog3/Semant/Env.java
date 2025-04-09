package Semant;
import Symbol.*;
import Types.*;
import ErrorMsg.ErrorMsg;

public class Env {
  public Table venv;
  public Table tenv;
  public ErrorMsg errorMsg;

  public Env(ErrorMsg err) {
    venv = new Table();
    tenv = new Table();
    errorMsg = err;

    // Initialize basic types
    tenv.put(Symbol.symbol("int"), new INT());
    tenv.put(Symbol.symbol("void"), new VOID());
    tenv.put(Symbol.symbol("char"), new CHAR());

    // Initialize standard functions
    Level level = null;
    RECORD params = new RECORD(Symbol.symbol("s"), new INT(), null);
    venv.put(Symbol.symbol("print"),
            new FunEntry(level, new Frame.Label("print"),
                    params, new VOID()));
  }
}
