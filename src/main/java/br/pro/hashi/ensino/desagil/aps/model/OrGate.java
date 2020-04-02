package br.pro.hashi.ensino.desagil.aps.model;

public class OrGate extends Gate {
    private NandGate Nand1;
    private NandGate Nand2;
    private NandGate Nand3;

    public OrGate() {
        super("OR", 2);
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
        } else if (inputIndex == 1) {
            Nand2.connect(1, emitter);
        }

    }
}
