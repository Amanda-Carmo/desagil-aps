package br.pro.hashi.ensino.desagil.aps.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

@SuppressWarnings("CanBeFinal")
public class GateView extends FixedPanel implements ActionListener, MouseListener {

    private final Gate gate;
    private JCheckBox resultField;
    private JCheckBox[] checkBoxes;
    private Switch[] switches;
    private Color color;
    private Image image;
    private Light light;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;


    public GateView(Gate gate) {

        super(WIDTH, HEIGHT);

        this.gate = gate;
        this.light = new Light(0,0,0);
        switches = new Switch[gate.getInputSize()];
        checkBoxes = new JCheckBox[gate.getInputSize()];

        for (int i = 0; i < gate.getInputSize(); i++) {
            checkBoxes[i] = new JCheckBox();
            checkBoxes[i].addActionListener(this);
            add(checkBoxes[i]);
            switches[i] = new Switch();
            gate.connect(i, switches[i]);
        }

        this.light.connect(0, gate);
        color = light.getColor();


        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);


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
        color = light.getColor();
        resultField.setSelected(this.gate.read());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }



    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        // Se o clique foi dentro do quadrado colorido...
        if (x >= 210 && x < 235 && y >= 311 && y < 336) {

            // ...então abrimos a janela seletora de cor...
            color = JColorChooser.showDialog(this, null, color);

            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }


    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        int[] imgSize = new int[2];

        imgSize[0] = 309;
        imgSize[1] = 125;

        g.drawImage(image, WIDTH/2 - imgSize[0]/2, HEIGHT/2 - imgSize[1]/2, imgSize[0], imgSize[1], this);

        // Desenha um quadrado cheio.
        int ovalRadius = 25;
        g.setColor(color);
        g.fillOval(WIDTH/2 - 15 + imgSize[0]/2, HEIGHT/2 - 15, 25, 30);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}
