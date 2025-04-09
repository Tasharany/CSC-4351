package Mips;

class InFrame extends Frame.Access {
  int offset;

  InFrame(int o) {
    offset = o;
  }

  public String toString() {
    Integer offset = Integer.valueOf(this.offset);
    return offset.toString();
  }
}
