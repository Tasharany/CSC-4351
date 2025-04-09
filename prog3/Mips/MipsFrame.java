package Mips;
import Frame.*;
import Symbol.Symbol;
import Temp.Temp;

public class MipsFrame extends Frame {
    private int offset = 0;
    private static final int wordSize = 4;

    public Access allocLocal(boolean escape) {
        if (escape) {
            offset -= wordSize;
            return new InFrame(offset);
        }
        return new InReg(new Temp());
    }

    public Frame newFrame(Symbol name, BoolList formals) {
        MipsFrame frame = new MipsFrame();
        frame.name = new Label(name.toString());
        int offset = 0;

        AccessList head = null, tail = null;
        for (BoolList b = formals; b != null; b = b.tail) {
            Access access;
            if (b.head) {
                offset += wordSize;
                access = new InFrame(offset);
            } else {
                access = new InReg(new Temp());
            }

            if (head == null)
                head = tail = new AccessList(access, null);
            else {
                tail.tail = new AccessList(access, null);
                tail = tail.tail;
            }
        }
        frame.formals = head;
        return frame;
    }

    public int wordSize() {
        return wordSize;
    }
}

