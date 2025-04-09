package Absyn;
import java.io.PrintWriter;
import Symbol.Symbol;

public class Print {
  PrintWriter out;
  int indent = 0;

  public Print(PrintWriter o) {
    out = o;
  }

  void indent() {
    for (int i = 0; i < indent; i++)
      out.print(" ");
  }

  void say(String s) {
    out.print(s);
  }

  void sayln(String s) {
    say(s);
    say("\n");
  }

  void prVar(SimpleVar v, int d) {
    say("SimpleVar(");
    say(v.name.toString());
    say(")");
  }

  void prVar(FieldVar v, int d) {
    sayln("FieldVar(");
    indent += d;
    prVar(v.var, d);
    sayln(",");
    indent();
    say(v.field.toString());
    indent -= d;
    say(")");
  }

  void prVar(SubscriptVar v, int d) {
    sayln("SubscriptVar(");
    indent += d;
    prVar(v.var, d);
    sayln(",");
    indent();
    prExp(v.index, d);
    indent -= d;
    say(")");
  }

  void prExp(OpExp e, int d) {
    sayln("OpExp(");
    indent += d;
    indent();
    switch(e.oper) {
      case OpExp.PLUS: say("PLUS"); break;
      case OpExp.MINUS: say("MINUS"); break;
      case OpExp.MUL: say("MUL"); break;
      case OpExp.DIV: say("DIV"); break;
      case OpExp.EQ: say("EQ"); break;
      case OpExp.NE: say("NE"); break;
      case OpExp.LT: say("LT"); break;
      case OpExp.LE: say("LE"); break;
      case OpExp.GT: say("GT"); break;
      case OpExp.GE: say("GE"); break;
      default: throw new Error("Print.prExp.OpExp");
    }
    sayln(",");
    indent();
    prExp(e.left, d);
    sayln(",");
    indent();
    prExp(e.right, d);
    indent -= d;
    say(")");
  }

  void prExp(VarExp e, int d) {
    sayln("VarExp(");
    indent += d;
    indent();
    prVar(e.var, d);
    indent -= d;
    say(")");
  }

  void prExp(NilExp e, int d) {
    say("NilExp()");
  }

  void prExp(IntExp e, int d) {
    say("IntExp(");
    say(String.valueOf(e.value));
    say(")");
  }

  void prExp(StringExp e, int d) {
    say("StringExp(");
    say(e.value);
    say(")");
  }

  void prExp(CallExp e, int d) {
    sayln("CallExp(");
    indent += d;
    indent();
    say(e.func.toString());
    sayln(",");
    indent();
    prExplist(e.args, d);
    indent -= d;
    say(")");
  }

  void prExp(SeqExp e, int d) {
    sayln("SeqExp(");
    indent += d;
    indent();
    prExplist(e.list, d);
    indent -= d;
    say(")");
  }

  void prExp(AssignExp e, int d) {
    sayln("AssignExp(");
    indent += d;
    indent();
    prVar(e.var, d);
    sayln(",");
    indent();
    prExp(e.exp, d);
    indent -= d;
    say(")");
  }

  void prExp(IfExp e, int d) {
    sayln("IfExp(");
    indent += d;
    indent();
    prExp(e.test, d);
    sayln(",");
    indent();
    prExp(e.thenclause, d);
    if (e.elseclause != null) {
      sayln(",");
      indent();
      prExp(e.elseclause, d);
    }
    indent -= d;
    say(")");
  }

  void prExp(WhileExp e, int d) {
    sayln("WhileExp(");
    indent += d;
    indent();
    prExp(e.test, d);
    sayln(",");
    indent();
    prExp(e.body, d);
    indent -= d;
    say(")");
  }

  void prExp(ForExp e, int d) {
    sayln("ForExp(");
    indent += d;
    indent();
    say(e.var.name.toString());
    sayln(",");
    indent();
    prExp(e.var.init, d);
    sayln(",");
    indent();
    prExp(e.hi, d);
    sayln(",");
    indent();
    prExp(e.body, d);
    indent -= d;
    say(")");
  }

  void prExp(LetExp e, int d) {
    sayln("LetExp(");
    indent += d;
    indent();
    prDecList(e.decs, d);
    sayln(",");
    indent();
    prExp(e.body, d);
    indent -= d;
    say(")");
  }

  void prExp(ArrayExp e, int d) {
    sayln("ArrayExp(");
    indent += d;
    indent();
    say(e.typ.toString());
    sayln(",");
    indent();
    prExp(e.size, d);
    sayln(",");
    indent();
    prExp(e.init, d);
    indent -= d;
    say(")");
  }

  void prDec(FunctionDec d, int i) {
    sayln("FunctionDec(");
    indent += i;
    indent();
    say(d.name.toString());
    sayln(",");
    indent();
    prFieldlist(d.params, i);
    if (d.result != null) {
      sayln(",");
      indent();
      say(d.result.name.toString());
    }
    sayln(",");
    indent();
    prExp(d.body, i);
    indent -= i;
    say(")");
  }

  void prDec(VarDec d, int i) {
    sayln("VarDec(");
    indent += i;
    indent();
    say(d.name.toString());
    if (d.typ != null) {
      sayln(",");
      indent();
      say(d.typ.name.toString());
    }
    sayln(",");
    indent();
    prExp(d.init, i);
    if (d.escape) {
      sayln(",");
      indent();
      say("true");
    }
    indent -= i;
    say(")");
  }

  void prDec(TypeDec d, int i) {
    sayln("TypeDec(");
    indent += i;
    indent();
    say(d.name.toString());
    sayln(",");
    indent();
    prTy(d.ty, i);
    indent -= i;
    say(")");
  }

  void prTy(Ty d, int i) {
    if (d instanceof NameTy) prTy((NameTy)d, i);
    else if (d instanceof RecordTy) prTy((RecordTy)d, i);
    else if (d instanceof ArrayTy) prTy((ArrayTy)d, i);
    else throw new Error("Print.prTy");
  }


  void prTy(RecordTy t, int i) {
    sayln("RecordTy(");
    indent += i;
    indent();
    prFieldlist(t.fields, i);
    indent -= i;
    say(")");
  }

  void prTy(ArrayTy t, int i) {
    say("ArrayTy(");
    say(t.typ.toString());
    say(")");
  }

  void prFieldlist(FieldList f, int d) {
    if (f == null) {
      say("null");
      return;
    }
    sayln("FieldList(");
    indent += d;
    indent();
    say(f.name.toString());
    sayln(",");
    indent();
    say(f.typ.toString());
    if (f.escape) {
      sayln(",");
      indent();
      say("true");
    }
    if (f.tail != null) {
      sayln(",");
      indent();
      prFieldlist(f.tail, d);
    }
    indent -= d;
    say(")");
  }

  void prExplist(ExpList e, int d) {
    if (e == null) {
      say("null");
      return;
    }
    sayln("ExpList(");
    indent += d;
    indent();
    prExp(e.head, d);
    if (e.tail != null) {
      sayln(",");
      indent();
      prExplist(e.tail, d);
    }
    indent -= d;
    say(")");
  }

  public void prExp(Exp e, int d) {
    if (e == null) {
      say("null");
      return;
    }
    if (e instanceof OpExp) prExp((OpExp)e, d);
    else if (e instanceof VarExp) prExp((VarExp)e, d);
    else if (e instanceof NilExp) prExp((NilExp)e, d);
    else if (e instanceof IntExp) prExp((IntExp)e, d);
    else if (e instanceof StringExp) prExp((StringExp)e, d);
    else if (e instanceof CallExp) prExp((CallExp)e, d);
    else if (e instanceof SeqExp) prExp((SeqExp)e, d);
    else if (e instanceof AssignExp) prExp((AssignExp)e, d);
    else if (e instanceof IfExp) prExp((IfExp)e, d);
    else if (e instanceof WhileExp) prExp((WhileExp)e, d);
    else if (e instanceof ForExp) prExp((ForExp)e, d);
    else if (e instanceof LetExp) prExp((LetExp)e, d);
    else if (e instanceof ArrayExp) prExp((ArrayExp)e, d);
    else throw new Error("Print.prExp");
  }

  void prVar(Var v, int d) {
    if (v instanceof SimpleVar) prVar((SimpleVar)v, d);
    else if (v instanceof FieldVar) prVar((FieldVar)v, d);
    else if (v instanceof SubscriptVar) prVar((SubscriptVar)v, d);
    else throw new Error("Print.prVar");
  }

  void prDecList(DecList d, int i) {
    if (d == null) {
      say("null");
      return;
    }
    sayln("DecList(");
    indent += i;
    indent();
    if (d.head instanceof FunctionDec) prDec((FunctionDec)d.head, i);
    else if (d.head instanceof VarDec) prDec((VarDec)d.head, i);
    else if (d.head instanceof TypeDec) prDec((TypeDec)d.head, i);
    else throw new Error("Print.prDecList");
    if (d.tail != null) {
      sayln(",");
      indent();
      prDecList(d.tail, i);
    }
    indent -= i;
    say(")");
  }
}
