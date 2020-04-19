package br.pro.hashi.ensino.desagil.aps.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("CanBeFinal")
public class GateView extends JPanel implements ActionListener {

    private final Gate gate;
    private JCheckBox resultField;
    private JCheckBox[] checkBoxes;
    private Switch[] switches;

    public GateView(Gate gate) {
        this.gate = gate;
        switches = new Switch[gate.getInputSize()];
        checkBoxes = new JCheckBox[gate.getInputSize()];
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String txtEntradas = "Entradas";
        JLabel entradaLabel = new JLabel(txtEntradas);
        String txtSaidas = "Saídas";
        JLabel saidasLabel = new JLabel(txtSaidas);


        add(entradaLabel);
        for (int i = 0; i < gate.getInputSize(); i++) {
            checkBoxes[i] = new JCheckBox();
            checkBoxes[i].addActionListener(this);
            add(checkBoxes[i]);
            switches[i] = new Switch();
            gate.connect(i, switches[i]);
        }

        add(saidasLabel);


        resultField = new JCheckBox();
        add(resultField);
        resultField.setEnabled(false);


        // Update é o método que definimos abaixo para atualizar o
        // último campo de acordo com os valores dos primeiros.
        // Precisamos chamar esse método no final da construção
        // para garantir que a interface não nasce inconsistente.
        update();

    }


    public void update() {
        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                switches[i].turnOn();
                System.out.println("Switch at " + i + " on");
            } else {
                switches[i].turnOff();
                System.out.println("Switch at " + i + " off");
            }
        }
        resultField.setSelected(this.gate.read());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }
}