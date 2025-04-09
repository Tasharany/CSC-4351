package Semant;
import Types.Type;
import Translate.Level;
import Frame.Access;

abstract class Entry { }

class VarEntry extends Entry {
    Type ty;
    Access access;  // Added access field

    VarEntry(Type t, Access a) {  // Modified constructor
        ty = t;
        access = a;
    }
}

class LoopVarEntry extends VarEntry {
    LoopVarEntry(Type t, Access a) {  // Modified constructor
        super(t, a);
    }
}

class FunEntry extends Entry {
    Level level;    // Function's level
    Types.RECORD formals;
    Type result;

    FunEntry(Level l, Types.RECORD f, Type r) {
        level = l;
        formals = f;
        result = r;
    }
}

