package Semant;

import Symbol.Symbol;
import Types.*;
import Translate.Level;
import Frame.Access;
import ErrorMsg.ErrorMsg;
import Absyn.*;

class ExpTy {
  Translate.Exp exp;
  Types.Type ty;
  ExpTy(Translate.Exp e, Types.Type t) {
    exp = e;
    ty = t;
  }
}

public class Semant {
  private Env env;
  private Level level;
  private Frame.Frame frame;

  // Basic types for C
  static final VOID VOID = new VOID();
  static final INT INT = new INT();
  static final CHAR CHAR = new CHAR();

  public Semant(Frame.Frame f, ErrorMsg.ErrorMsg err) {
    this(new Env(err), null, f);
  }

  Semant(Env e, Level l, Frame.Frame f) {
    env = e;
    level = l;
    frame = f;
  }

  void error(int pos, String msg) {
    env.errorMsg.error(pos, msg);
  }

  // Main entry point for type checking and frame allocation
  public void transProg(Exp exp) {
    // First run escape analysis to identify variables that need frame storage
    FindEscape.FindEscape esc = new FindEscape.FindEscape(exp);

    // Create top-level frame
    Level mainLevel = new Level(level, new Frame.Label("main"), null);
    level = mainLevel;

    transExp(exp);
  }

  ExpTy transExp(Exp e) {
    if (e == null)
      return new ExpTy(null, VOID);
    else if (e instanceof VarExp)
      return transExp((VarExp)e);
    else if (e instanceof IntExp)
      return transExp((IntExp)e);
    else if (e instanceof CharExp)
      return transExp((CharExp)e);
    else if (e instanceof CallExp)
      return transExp((CallExp)e);
    else if (e instanceof OpExp)
      return transExp((OpExp)e);
    else if (e instanceof AssignExp)
      return transExp((AssignExp)e);
    else if (e instanceof IfExp)
      return transExp((IfExp)e);
    else if (e instanceof WhileExp)
      return transExp((WhileExp)e);
    else if (e instanceof ForExp)
      return transExp((ForExp)e);
    else if (e instanceof CompoundExp)
      return transExp((CompoundExp)e);
    else
      throw new Error("transExp");
  }

  ExpTy transExp(VarExp e) {
    return transVar(e.var);
  }

  ExpTy transVar(Var v) {
    if (v instanceof SimpleVar)
      return transVar((SimpleVar)v);
    else if (v instanceof SubscriptVar)
      return transVar((SubscriptVar)v);
    else if (v instanceof PointerDerefVar)
      return transVar((PointerDerefVar)v);
    else
      throw new Error("transVar");
  }

  // Handle simple variable access
  ExpTy transVar(SimpleVar v) {
    Entry entry = (Entry)env.venv.get(v.name);
    if (entry instanceof VarEntry) {
      VarEntry ent = (VarEntry)entry;
      return new ExpTy(null, ent.ty);
    }
    error(v.pos, "undefined variable: " + v.name);
    return new ExpTy(null, VOID);
  }

  // Handle array subscripting
  ExpTy transVar(SubscriptVar v) {
    ExpTy var = transVar(v.var);
    ExpTy index = transExp(v.index);
    checkInt(index, v.index.pos);

    if (var.ty instanceof POINTER) {
      POINTER pt = (POINTER)var.ty;
      return new ExpTy(null, pt.base);
    }
    error(v.pos, "array type required");
    return new ExpTy(null, VOID);
  }

  // Handle pointer dereferencing
  ExpTy transVar(PointerDerefVar v) {
    ExpTy var = transVar(v.var);
    if (var.ty instanceof POINTER) {
      POINTER pt = (POINTER)var.ty;
      return new ExpTy(null, pt.base);
    }
    error(v.pos, "pointer type required");
    return new ExpTy(null, VOID);
  }

  ExpTy transExp(IntExp e) {
    return new ExpTy(null, INT);
  }

  ExpTy transExp(CharExp e) {
    return new ExpTy(null, CHAR);
  }

  ExpTy transExp(CallExp e) {
    Entry entry = (Entry)env.venv.get(e.func);
    if (!(entry instanceof FunEntry)) {
      error(e.pos, "undefined function: " + e.func);
      return new ExpTy(null, VOID);
    }
    FunEntry f = (FunEntry)entry;
    transArgs(e.pos, f.formals, e.args);
    return new ExpTy(null, f.result);
  }

  ExpTy transExp(OpExp e) {
    ExpTy left = transExp(e.left);
    ExpTy right = transExp(e.right);

    switch (e.oper) {
      case OpExp.PLUS:
      case OpExp.MINUS:
      case OpExp.MUL:
      case OpExp.DIV:
        if (left.ty instanceof INT && right.ty instanceof INT)
          return new ExpTy(null, INT);
        if (left.ty instanceof POINTER && right.ty instanceof INT)
          return new ExpTy(null, left.ty);
        error(e.pos, "integer or pointer arithmetic required");
        return new ExpTy(null, INT);

      case OpExp.EQ:
      case OpExp.NE:
      case OpExp.LT:
      case OpExp.LE:
      case OpExp.GT:
      case OpExp.GE:
        if (left.ty.coerceTo(right.ty) || right.ty.coerceTo(left.ty))
          return new ExpTy(null, INT);
        error(e.pos, "incompatible operands");
        return new ExpTy(null, INT);

      default:
        throw new Error("unknown operator");
    }
  }

  ExpTy transExp(AssignExp e) {
    ExpTy var = transVar(e.var);
    ExpTy exp = transExp(e.exp);

    if (!exp.ty.coerceTo(var.ty))
      error(e.pos, "type mismatch in assignment");
    return new ExpTy(null, VOID);
  }

  ExpTy transExp(IfExp e) {
    ExpTy test = transExp(e.test);
    checkInt(test, e.test.pos);

    ExpTy then = transExp(e.thenclause);
    if (e.elseclause != null) {
      ExpTy elseclause = transExp(e.elseclause);
      if (!then.ty.coerceTo(elseclause.ty) && !elseclause.ty.coerceTo(then.ty))
        error(e.pos, "then and else clauses have different types");
    }
    return new ExpTy(null, VOID);
  }

  ExpTy transExp(WhileExp e) {
    ExpTy test = transExp(e.test);
    checkInt(test, e.test.pos);
    ExpTy body = transExp(e.body);
    return new ExpTy(null, VOID);
  }

  ExpTy transExp(ForExp e) {
    ExpTy init = transExp(e.var.init);
    ExpTy limit = transExp(e.hi);
    checkInt(init, e.var.pos);
    checkInt(limit, e.hi.pos);

    env.venv.beginScope();
    Access access = level.allocLocal(e.var.escape);
    e.var.entry = new VarEntry(INT, access);
    env.venv.put(e.var.name, e.var.entry);

    ExpTy body = transExp(e.body);
    env.venv.endScope();
    return new ExpTy(null, VOID);
  }

  ExpTy transExp(CompoundExp e) {
    env.venv.beginScope();
    Type type = VOID;
    for (DecList d = e.decs; d != null; d = d.tail)
      transDec(d.head);
    for (ExpList el = e.exps; el != null; el = el.tail)
      type = transExp(el.head).ty;
    env.venv.endScope();
    return new ExpTy(null, type);
  }

  void transDec(Dec d) {
    if (d instanceof VarDec)
      transDec((VarDec)d);
    else if (d instanceof FunctionDec)
      transDec((FunctionDec)d);
  }

  // Handle variable declarations with frame allocation
  void transDec(VarDec d) {
    Type type = transTy(d.typ);

    if (d.init != null) {
      ExpTy init = transExp(d.init);
      if (!init.ty.coerceTo(type))
        error(d.pos, "type mismatch in variable declaration");
    }

    // Allocate space in frame if variable escapes
    Access access = level.allocLocal(d.escape);
    d.entry = new VarEntry(type, access);
    env.venv.put(d.name, d.entry);
  }

  // Handle function declarations with frame creation
  void transDec(FunctionDec d) {
    // First pass: process headers
    for (FunctionDec f = d; f != null; f = f.next) {
      Type resultTy = transTy(f.result);
      RECORD formals = transParams(f.params);

      // Create new level for function
      Level newLevel = new Level(level, f.name, escapes(f.params));
      f.entry = new FunEntry(newLevel, formals, resultTy);
      env.venv.put(f.name, f.entry);
    }

    // Second pass: process bodies
    for (FunctionDec f = d; f != null; f = f.next) {
      env.venv.beginScope();
      RECORD formals = ((FunEntry)f.entry).formals;
      Level savedLevel = level;
      level = ((FunEntry)f.entry).level;

      // Allocate parameters in function's frame
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

  Type transTy(Ty t) {
    if (t instanceof NameTy)
      return transTy((NameTy)t);
    else if (t instanceof PointerTy)
      return transTy((PointerTy)t);
    throw new Error("transTy");
  }

  Type transTy(NameTy t) {
    if (t.name.toString().equals("int")) return INT;
    if (t.name.toString().equals("char")) return CHAR;
    if (t.name.toString().equals("void")) return VOID;
    error(t.pos, "undefined type: " + t.name);
    return VOID;
  }

  Type transTy(PointerTy t) {
    return new POINTER(transTy(t.base));
  }

  private boolean[] escapes(FieldList params) {
    int count = 0;
    for (FieldList p = params; p != null; p = p.tail)
      count++;
    boolean[] escapes = new boolean[count];
    int i = 0;
    for (FieldList p = params; p != null; p = p.tail)
      escapes[i++] = p.escape;
    return escapes;
  }

  private void checkInt(ExpTy et, int pos) {
    if (!et.ty.coerceTo(INT))
      error(pos, "integer required");
  }

  private void transArgs(int epos, RECORD formal, ExpList args) {
    if (formal == null && args == null) return;
    if (formal == null || args == null) {
      error(epos, "argument count mismatch");
      return;
    }
    ExpTy e = transExp(args.head);
    if (!e.ty.coerceTo(formal.fieldType))
      error(args.head.pos, "argument type mismatch");
    transArgs(epos, formal.tail, args.tail);
  }
}
