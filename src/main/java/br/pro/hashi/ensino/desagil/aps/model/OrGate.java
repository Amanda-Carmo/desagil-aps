package br.pro.hashi.ensino.desagil.aps.model;

public class OrGate extends Gate {
    private final NandGate Nand1;
    private final NandGate Nand2;
    private final NandGate Nand3;

    public OrGate() {
        super("OrGate", 2);
        Nand1 = new NandGate();
        Nand2 = new NandGate();
        Nand3 = new NandGate();

        Nand3.connect(0, Nand1);
        Nand3.connect(1, Nand2);
    }

    @Override
    public boolean read() {
        return Nand3.read();
    }

    @Override
    public void connect(int inputIndex, Emitter emitter) {
        if (inputIndex > 1 || inputIndex < 0) {
            throw new IndexOutOfBoundsException(inputIndex);
        }

        if (inputIndex == 0) {
            Nand1.connect(0, emitter);
            Nand1.connect(1, emitter);


        } else {
            Nand2.connect(1, emitter);
            Nand2.connect(0, emitter);
        }

    }
}
