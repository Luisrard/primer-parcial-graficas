package com.luisrard.primer.parcial.graficas;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {
    private final Image image;
    private Dimension tam;

    public Screen(Image image) {
        super();
        this.image = image;
        this.tam = null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(tam == null) {
            int width = image.getWidth(this);
            int height = image.getHeight(this);
            tam = new Dimension(
                    width,
                    height);
            setPreferredSize(tam);
            setMinimumSize(tam);
            setMaximumSize(tam);
            setSize(tam);
        }
        update(g);
    }

    @Override
    public void update(Graphics g) {
        g.drawImage(image, 0,0, this);
    }
}
