package Semant;
class ExpTy {
  Translate.Exp exp;
  Types.Type ty;
  ExpTy(Translate.Exp e, Types.Type t) {
    exp = e;
    ty = t;
  }
}
