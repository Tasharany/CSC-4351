package Semant;
import Translate.Exp;
import Types.Type;

public class Semant {
  Env env;
  Translate.Level level;

  public Semant(ErrorMsg.ErrorMsg err) {
    this(new Env(err));
  }

  Semant(Env e) {
    env = e;
    level = null;
  }

  public void transProg(Absyn.Absyn tree_head) {
    // First perform escape analysis
    FindEscape.FindEscape escapeAnalyzer = new FindEscape.FindEscape();
    escapeAnalyzer.findEscape(tree_head);

    if (tree_head instanceof Absyn.DecList) {
      transDecList((Absyn.DecList)tree_head);
    }
  }

  private void transDecList(Absyn.DecList decList) {
    while (decList != null) {
      transDec(decList.head);
      decList = decList.tail;
    }
  }

  private void transDec(Absyn.Dec dec) {
    if (dec instanceof Absyn.StructDeclaration) {
      Absyn.StructDeclaration struct = (Absyn.StructDeclaration)dec;
      // Handle struct declaration
      Types.RECORD fields = transFieldList(struct.fields);

      // Add struct type to type environment
      env.tenv.put(struct.name,
              new Types.RECORD(struct.name.toString(), fields));

      // If extern, mark appropriately
      if (struct.isExtern) {
        // Handle extern specific processing if needed
      }
    }
    else if (dec instanceof Absyn.VarDec) {
      Absyn.VarDec var = (Absyn.VarDec)dec;
      Types.Type type = transTy(var.typ);

      // Handle array initializers
      if (var.init != null) {
        ExpTy init = transExp(var.init);
        if (!init.ty.coerceTo(type)) {
          error(var.pos, "type mismatch in variable declaration");
        }
      }

      // Allocate variable with escape information
      Translate.Access access = level.allocLocal(var.escape);
      env.venv.put(var.name, new VarEntry(access, type));
    }
  }

  private Types.RECORD transFieldList(Absyn.FieldList fields) {
    if (fields == null) return null;

    Types.Type fieldType = transTy(fields.typ);
    return new Types.RECORD(fields.name.toString(),
            fieldType,
            transFieldList(fields.tail));
  }

  private void error(int pos, String msg) {
    env.errorMsg.error(pos, msg);
  }

  static final Types.VOID VOID = new Types.VOID();
  static final Types.INT INT = new Types.INT();
  static final Types.STRING STRING = new Types.STRING();
  static final Types.NIL NIL = new Types.NIL();

  private Exp checkInt(ExpTy et, int pos) {
    if (!INT.coerceTo(et.ty))
      error(pos, "integer required");
    return et.exp;
  }
}
