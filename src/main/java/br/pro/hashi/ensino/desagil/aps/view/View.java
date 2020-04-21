package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class View extends JPanel implements ActionListener {

    private final JComboBox<Gate> menu;
    private GateView gateview;


    public View(LinkedList<Gate> gates) {
        menu = new JComboBox<>();
        for (Gate gate : gates) {
            menu.addItem(gate);
        }
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(menu);
        addGateView(0);
        menu.addActionListener(this);
    }

    private void addGateView(int index) {
        Gate gate = menu.getItemAt(index);
        this.gateview = new GateView(gate);
        add(gateview);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        remove(gateview);

        int index = menu.getSelectedIndex();

        addGateView(index);

        ((JFrame) SwingUtilities.getRoot(this)).pack();
    }
}

