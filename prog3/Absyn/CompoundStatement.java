package Absyn;

public class CompoundStatement extends Statement{
  public Dec dec_list;
  public Statement stmt_list;
  public CompoundStatement(int p, Dec dl, Statement sl) {
    pos = p;
    dec_list = dl;
    stmt_list = sl;
  }
}
