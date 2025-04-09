package Types;

public abstract class Type {
   public Type actual() {return this;}
         
   public boolean coerceTo(Type t) {return false;}
}
// Remove Tiger-specific types and add C types
public class POINTER extends Type {
   public Type base;

   public POINTER(Type t) {
      base = t;
   }

   @Override
   public boolean coerceTo(Type t) {
      if (t instanceof POINTER) {
         return base.coerceTo(((POINTER)t).base) ||
                 base instanceof VOID ||
                 ((POINTER)t).base instanceof VOID;
      }
      return false;
   }
}

// Modify existing types for C semantics
public class FUNCTION extends Type {
   public RECORD params;
   public Type result;

   public FUNCTION(RECORD p, Type r) {
      params = p;
      result = r;
   }
}
