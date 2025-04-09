package Mips;

public class MipsFrame extends Frame.Frame {
    private int offset = 0;

    public Access allocLocal(boolean escape) {
        if (escape) {
            offset -= wordSize();
            return new InFrame(offset);
        } else {
            return new InReg(new Temp());
        }
    }

    public Frame.Access allocFormal(boolean escape) {
        if (escape) {
            offset += wordSize();
            return new InFrame(offset);
        } else {
            return new InReg(new Temp());
        }
    }

    public int wordSize() {
        return 4;
    }
}

class InFrame extends Frame.Access {
    int offset;
    InFrame(int o) { offset = o; }
}

class InReg extends Frame.Access {
    Temp.Temp temp;
    InReg(Temp.Temp t) { temp = t; }
}
