// Semant/Semant.java
package Semant;

import Symbol.Symbol;
import Types.*;
import Translate.Level;
import Frame.*;
import ErrorMsg.ErrorMsg;
import Absyn.*;

public class Semant {
  private Env env;
  private Level level;
  private Frame frame;

  static final VOID VOID = new VOID();
  static final INT INT = new INT();
  static final CHAR CHAR = new CHAR();

  public Semant(Frame f, ErrorMsg err) {
    this(new Env(err), null, f);
  }

  Semant(Env e, Level l, Frame f) {
    env = e;
    level = l;
    frame = f;
  }

  void error(int pos, String msg) {
    env.errorMsg.error(pos, msg);
  }

  public void transProg(Exp exp) {
    FindEscape.FindEscape esc = new FindEscape.FindEscape(exp);
    Level mainLevel = new Level(level, new Frame.Label("main"), null);
    level = mainLevel;
    transExp(exp);
  }

  private ExpTy transVar(Var v) {
    if (v instanceof SimpleVar)
      return transVar((SimpleVar)v);
    else if (v instanceof SubscriptVar)
      return transVar((SubscriptVar)v);
    else if (v instanceof FieldVar)
      return transVar((FieldVar)v);
    throw new Error("Unknown var type");
  }

  private ExpTy transVar(SimpleVar v) {
    Entry x = (Entry)env.venv.get(v.name);
    if (x instanceof VarEntry) {
      VarEntry ent = (VarEntry)x;
      return new ExpTy(null, ent.ty);
    }
    error(v.pos, "undefined variable: " + v.name);
    return new ExpTy(null, VOID);
  }

  private ExpTy transVar(SubscriptVar v) {
    ExpTy var = transVar(v.var);
    ExpTy index = transExp(v.index);

    if (!index.ty.actual().coerceTo(INT))
      error(v.index.pos, "array index must be integer");

    Type actual = var.ty.actual();
    if (actual instanceof POINTER) {
      return new ExpTy(null, ((POINTER)actual).base);
    }
    error(v.pos, "array type required");
    return new ExpTy(null, VOID);
  }

  private ExpTy transVar(FieldVar v) {
    ExpTy var = transVar(v.var);
    Type actual = var.ty.actual();
    if (actual instanceof RECORD) {
      for (RECORD field = (RECORD)actual; field != null; field = field.tail) {
        if (field.fieldName == v.field) {
          return new ExpTy(null, field.fieldType);
        }
      }
      error(v.pos, "field not found: " + v.field);
    } else {
      error(v.pos, "record type required");
    }
    return new ExpTy(null, VOID);
  }

  private ExpTy transExp(Exp e) {
    if (e == null)
      return new ExpTy(null, VOID);
    if (e instanceof VarExp)
      return transVar(((VarExp)e).var);
    if (e instanceof IntExp)
      return new ExpTy(null, INT);
    if (e instanceof CallExp)
      return transCallExp((CallExp)e);
    if (e instanceof OpExp)
      return transOpExp((OpExp)e);
    if (e instanceof AssignExp)
      return transAssignExp((AssignExp)e);
    if (e instanceof IfExp)
      return transIfExp((IfExp)e);
    if (e instanceof WhileExp)
      return transWhileExp((WhileExp)e);
    throw new Error("Unknown expression type");
  }

  private ExpTy transCallExp(CallExp e) {
    Entry x = (Entry)env.venv.get(e.func);
    if (!(x instanceof FunEntry)) {
      error(e.pos, "undefined function: " + e.func);
      return new ExpTy(null, VOID);
    }
    FunEntry f = (FunEntry)x;
    transArgs(e.pos, f.formals, e.args);
    return new ExpTy(null, f.result);
  }

  private ExpTy transOpExp(OpExp e) {
    ExpTy left = transExp(e.left);
    ExpTy right = transExp(e.right);

    switch (e.oper) {
      case OpExp.PLUS:
      case OpExp.MINUS:
      case OpExp.MUL:
      case OpExp.DIV:
        if (left.ty.isArithmetic() && right.ty.isArithmetic())
          return new ExpTy(null, INT);
        if (left.ty instanceof POINTER && right.ty.isArithmetic())
          return new ExpTy(null, left.ty);
        error(e.pos, "integer or pointer required");
        return new ExpTy(null, INT);

      case OpExp.EQ:
      case OpExp.NE:
        if (left.ty.coerceTo(right.ty) || right.ty.coerceTo(left.ty))
          return new ExpTy(null, INT);
        error(e.pos, "incompatible operands");
        return new ExpTy(null, INT);

      case OpExp.LT:
      case OpExp.LE:
      case OpExp.GT:
      case OpExp.GE:
        if (left.ty.isScalar() && right.ty.isScalar())
          return new ExpTy(null, INT);
        error(e.pos, "invalid comparison operands");
        return new ExpTy(null, INT);
    }
    throw new Error("unknown operator");
  }

  private ExpTy transAssignExp(AssignExp e) {
    ExpTy var = transVar(e.var);
    ExpTy exp = transExp(e.exp);

    if (!exp.ty.coerceTo(var.ty))
      error(e.pos, "type mismatch in assignment");
    return new ExpTy(null, VOID);
  }

  private ExpTy transIfExp(IfExp e) {
    ExpTy test = transExp(e.test);
    if (!test.ty.isScalar())
      error(e.test.pos, "integer or pointer required");

    ExpTy then = transExp(e.thenclause);
    if (e.elseclause != null) {
      ExpTy elseclause = transExp(e.elseclause);
      if (!then.ty.coerceTo(elseclause.ty) && !elseclause.ty.coerceTo(then.ty))
        error(e.pos, "branches have different types");
    }
    return new ExpTy(null, then.ty);
  }

  private ExpTy transWhileExp(WhileExp e) {
    ExpTy test = transExp(e.test);
    if (!test.ty.isScalar())
      error(e.test.pos, "integer or pointer required");
    ExpTy body = transExp(e.body);
    return new ExpTy(null, VOID);
  }

  private void transDec(Dec d) {
    if (d instanceof VarDec)
      transDec((VarDec)d);
    else if (d instanceof FunctionDec)
      transDec((FunctionDec)d);
  }

  private void transDec(VarDec d) {
    Types.Type type = transTy(d.typ);

    if (d.init != null) {
      ExpTy init = transExp(d.init);
      if (!init.ty.coerceTo(type))
        error(d.pos, "type mismatch in variable declaration");
    }

    Access access = level.allocLocal(d.escape);
    d.entry = new VarEntry(type, access);
    env.venv.put(d.name, d.entry);
  }

  private void transDec(FunctionDec d) {
    for (FunctionDec f = d; f != null; f = f.next) {
      Types.Type resultTy = transTy(f.result);
      RECORD formals = transParams(f.params);
      Level newLevel = new Level(level, f.name, boolList(f.params));
      f.entry = new FunEntry(newLevel, new Frame.Label(f.name.toString()), formals, resultTy);
      env.venv.put(f.name, f.entry);
    }

    for (FunctionDec f = d; f != null; f = f.next) {
      env.venv.beginScope();
      RECORD formals = ((FunEntry)f.entry).formals;
      Level savedLevel = level;
      level = ((FunEntry)f.entry).level;

      for (FieldList fl = f.params; fl != null; fl = fl.tail) {
        Access access = level.allocLocal(fl.escape);
        env.venv.put(fl.name, new VarEntry(formals.fieldType, access));
      }

      ExpTy body = transExp(f.body);
      if (!body.ty.coerceTo(((FunEntry)f.entry).result))
        error(f.body.pos, "function return type mismatch");

      level = savedLevel;
      env.venv.endScope();
    }
  }

  private Types.Type transTy(Ty t) {
    if (t == null) return VOID;
    if (t instanceof NameTy) {
      Symbol name = ((NameTy)t).name;
      if (name == Symbol.symbol("int")) return INT;
      if (name == Symbol.symbol("char")) return CHAR;
      if (name == Symbol.symbol("void")) return VOID;
      Type type = (Type)env.tenv.get(name);
      if (type == null) {
        error(t.pos, "undefined type: " + name);
        return VOID;
      }
      return type;
    }
    throw new Error("unknown type");
  }

  private RECORD transParams(FieldList params) {
    if (params == null) return null;
    return new RECORD(params.name,
            transTy(params.typ),
            transParams(params.tail));
  }

  private BoolList boolList(FieldList params) {
    if (params == null) return null;
    return new BoolList(params.escape,
            boolList(params.tail));
  }

  private void transArgs(int epos, RECORD formal, ExpList args) {
    if (formal == null && args == null) return;
    if (formal == null || args == null) {
      error(epos, "argument count mismatch");
      return;
    }
    ExpTy arg = transExp(args.head);
    if (!arg.ty.coerceTo(formal.fieldType))
      error(args.head.pos, "argument type mismatch");
    transArgs(epos, formal.tail, args.tail);
  }
}

