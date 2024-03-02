package com.luisrard.primer.parcial.graficas;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Parcial1Practica11VisorImagenes extends JFrame{
    private JScrollPane scrollPane;
    private Screen screen;
    public static void main(String[] args) throws IOException {
        new Parcial1Practica11VisorImagenes("src/img/thestrokes.jpg");
    }

    public Parcial1Practica11VisorImagenes(String file) throws HeadlessException, IOException {
        super("Visor imagen");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BufferedImage myPicture = ImageIO.read(new File(file));
        Image img = new ImageIcon(myPicture).getImage();
        screen = new Screen(img);
        scrollPane = new JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPane);
        scrollPane.setViewportView(screen);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
    }

}
