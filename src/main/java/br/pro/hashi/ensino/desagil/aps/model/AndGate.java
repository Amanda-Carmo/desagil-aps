package br.pro.hashi.ensino.desagil.aps.model;

public class AndGate extends Gate {
    private final NandGate NandA;
    private final NandGate NandB;

    public AndGate() {
        super("AndGate", 2);
        NandA = new NandGate();
        NandB = new NandGate();

        NandB.connect(0, NandA);
        NandB.connect(1, NandA);
    }

    @Override
    public boolean read() {
        return NandB.read();
    }

    @Override
    public void connect(int inputIndex, Emitter emitter) {
        if (inputIndex > 1 || inputIndex < 0) {
            throw new IndexOutOfBoundsException(inputIndex);
        }

        NandA.connect(inputIndex, emitter);

    }
}
