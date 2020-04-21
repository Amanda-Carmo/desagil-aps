package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

@SuppressWarnings({"CanBeFinal"})
public class GateView extends FixedPanel implements ActionListener, MouseListener {

    private static final int WIDTH = 300; // Largura da tela
    private static final int HEIGHT = 120; // Altura da tela
    private static final int ovalRadius = 20; // Raio do circulo seletor de cor (saida das portas)
    private JCheckBox[] checkBoxes;
    private Switch[] switches;
    //    Atributos adicionais
    private Color color;
    private Image image;
    private Light light;


    public GateView(Gate gate) {

        super(WIDTH, HEIGHT);

        this.light = new Light(255, 0, 0);
        switches = new Switch[gate.getInputSize()];
        checkBoxes = new JCheckBox[gate.getInputSize()];

        for (int i = 0; i < gate.getInputSize(); i++) {
            checkBoxes[i] = new JCheckBox();
            checkBoxes[i].addActionListener(this);
            checkBoxes[i].setSize(20, 20);
            switches[i] = new Switch();
            gate.connect(i, switches[i]);
            if (gate.getInputSize() == 1) {
                add(checkBoxes[i], 15, HEIGHT / 2 - 10, 20, 20);
            } else {
                add(checkBoxes[i], 15, HEIGHT * i / (gate.getInputSize() + 1) + 20 + 19 * i, 20, 20);
            }
        }

        this.light.connect(0, gate);
        color = light.getColor();


        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);


        addMouseListener(this);
        update();

    }


    public void update() {
        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                switches[i].turnOn();
//                System.out.println("Switch at " + i + " on");
            } else {
                switches[i].turnOff();
//                System.out.println("Switch at " + i + " off");
            }
        }
        color = light.getColor();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }


    @Override
    public void mouseClicked(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        int ovalXCenter = (WIDTH / 2) + 125;
        int ovalYCenter = HEIGHT / 2;

//        System.out.println("X:" + x);
//        System.out.println("Y:" + y);


//        Usando a funcao distanceSquared, que retorna o quadrado da distancia, determina se o clique foi dentro do circulo
        int distSq = distanceSquared(x, y, ovalXCenter, ovalYCenter);
        if (distSq <= ovalRadius * ovalRadius / 4) {
            light.setColor(JColorChooser.showDialog(this, null, color));
            update();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

//        Vetor de dimensoes da imagem [x (largura), y (altura)]
        int[] imgSize = new int[2];
        imgSize[0] = 250;
        imgSize[1] = 100;

        g.drawImage(image, WIDTH / 2 - imgSize[0] / 2, HEIGHT / 2 - imgSize[1] / 2, imgSize[0], imgSize[1], this);
        g.setColor(color);
        g.fillOval(WIDTH / 2 - ovalRadius / 2 + imgSize[0] / 2, HEIGHT / 2 - ovalRadius / 2, ovalRadius, ovalRadius);
        getToolkit().sync();
    }

    private int distanceSquared(int x, int y, int x2, int y2) {
        int dx = Math.abs(x - x2);
        int dy = Math.abs(y - y2);

//        Evita-se a operacao de raiz quadrada pois essa e muito custosa computacionalmente basta comparar os quadrados
        return dx * dx + dy * dy;
    }
}
