package com.luisrard.primer.parcial.graficas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Parcial1Practica9DiagramaPastel extends JFrame{
    private Image buffer;

    private static final String INVALID_ARGUMENTS_MESSAGE = "Invalid arguments [name, amount]";
    private static class Values{
        private final String name;
        private final int amount;

        private Values(String name, int amount) {
            this.name = name;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public int getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return "Values{" +
                    "name='" + name + '\'' +
                    ", amount=" + amount +
                    '}';
        }
    }

    private final List<Values> valuesList;
    private final LinkedHashMap<Integer, Values> coordenates;
    private final int total;
    private final JLabel label;
    private final int xStart = 0;
    private final int yStart = 100;
    private Values currentValue = null;


    public static void main(String[] args) {
        if(args.length % 2 != 0) {
            throw new RuntimeException(INVALID_ARGUMENTS_MESSAGE);
        }
        List<Values> valuesList = new ArrayList<>(args.length / 2);
        int total = 0;
        for (int i = 0; i < args.length / 2; i ++) {
            try{
                int amount = Integer.parseInt(args[i * 2 + 1]);
                if (amount < 0){
                    throw new RuntimeException("Invalid amount for element in diagram, amount must be > 0");
                }
                total += amount;
                valuesList.add(new Values(args[i*2], amount));
            } catch (Exception e){
                throw new RuntimeException(INVALID_ARGUMENTS_MESSAGE);
            }
        }
        new Parcial1Practica9DiagramaPastel(valuesList, total);
    }

    public Parcial1Practica9DiagramaPastel(List<Values> valuesList, int total) throws HeadlessException {
        super("Pie diagram");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        int width = 800;
        setSize(width,width + 100);
        this.valuesList = valuesList;
        this.total = total;
        coordenates = new LinkedHashMap<>(valuesList.size());
        JPanel panel = new JPanel();
        label = new JLabel("");
        panel.add(label);
        add(panel);
        setVisible(true);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

                if (e.getY() > yStart){
                    boolean up;
                    int y = (yStart  + getWidth() / 2) - e.getY();
                    if(e.getY() > yStart + getWidth() / 2){ //down
                        up = false;
                    }else { //up
                        up = true;
                    }
                    int x = -(xStart + getWidth() / 2) + e.getX();

                    double tan = Math.atan((double) y / (double) x);
                    double degrees = Math.toDegrees(tan);

                    if (e.getX() > xStart + getWidth() / 2){//right
                        if (!up) {//270-360
                            degrees = 360 + degrees;
                        }
                    } else {//left
                        degrees = 180 + degrees;
                    }
                    printValues(getValues(degrees));
                }
            }
        });
    }

    private void printValues(Values values){
        if(!values.equals(currentValue)) {
            currentValue = values;
            label.setText(values.getName() + " " + values.getAmount() / (float) total * 100 + "%");
        }
    }

    private Values getValues(double degrees) {
        int piv = 0;
        for(Integer valueDegrees : coordenates.keySet()){
            if(degrees >= valueDegrees){
                piv = valueDegrees;
            } else {
                break;
            }
        }
        return coordenates.get(piv);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int startAngle = 0;
        for (Values values : valuesList){
            g.setColor(new Color((int) (Math.random() * Integer.MAX_VALUE)));
            int angleForValue = values.getAmount() * 360 / total;
            g.fillArc(xStart, yStart, getWidth(), getHeight() - yStart, startAngle, angleForValue);
            coordenates.put(startAngle, values);
            startAngle += angleForValue;
        }
    }

}
