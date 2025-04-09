package Types;
public abstract class Type {
   public Type actual() {
      return this;
   }

   public boolean coerceTo(Type t) {
      return this == t;
   }

   public boolean isArithmetic() {
      return (this instanceof INT || this instanceof CHAR);
   }

   public boolean isScalar() {
      return (this instanceof INT || this instanceof CHAR ||
              this instanceof POINTER);
   }
}
