package com.luisrard.primer.parcial.graficas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

public class Parcial1Practica13Calculadora extends JFrame{
    private final CalculatorButton [] numberButtons = new CalculatorButton[10];
    private final JTextField resultTextField = new JTextField("");

    private static final Pattern VALID_CHARACTERS_PATTERN = Pattern.compile("^[0-9C/*\\-+=.]+$");

    private static final Pattern SYMBOLS_PATTERN = Pattern.compile("[/*\\-+]+");

    private static final String SYMBOLS_REGEX = "[+*\\-/]+";
    private static final String ERROR_MESSAGE = "ERROR";
    private static final String DEFAULT_RESULT_TEXT_FIELD = "0";
    public static void main(String[] args) {
        new Parcial1Practica13Calculadora();
    }

    private CalculatorButton createButton(String symbol){
        return new CalculatorButton(symbol, e -> addSymbol(symbol.charAt(0)));
    }

    private CalculatorButton createButton(Integer symbol){
        return new CalculatorButton(symbol.toString(), e -> addSymbol(symbol.toString().charAt(0)));
    }

    public Parcial1Practica13Calculadora() throws HeadlessException {
        super("Calculator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,500);
        setResizable(false);
        setLayout(new GridBagLayout());
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(e.getKeyChar());
                addSymbol(e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        resultTextField.setText(DEFAULT_RESULT_TEXT_FIELD);
        resultTextField.setEditable(false);

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createButton(i);
        }

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.weightx = .25;
        c.weighty = .2;
        add(resultTextField, c);

        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        add(createButton("C"), c);
        c.gridx = 1;
        add(createButton("/"), c);
        c.gridx = 2;
        add(createButton("*"), c);
        c.gridx = 3;
        add(createButton("-"), c);

        c.gridy = 2;
        c.gridx = 0;
        add(numberButtons[7], c);
        c.gridx = 1;
        add(numberButtons[8], c);
        c.gridx = 2;
        add(numberButtons[9], c);
        c.gridx = 3;
        c.gridheight = 2;
        add(createButton("+"), c);
        c.gridheight = 1;

        c.gridy = 3;
        c.gridx = 0;
        add(numberButtons[4], c);
        c.gridx = 1;
        add(numberButtons[5], c);
        c.gridx = 2;
        add(numberButtons[6], c);

        c.gridy = 4;
        c.gridx = 0;
        add(numberButtons[1], c);
        c.gridx = 1;
        add(numberButtons[2], c);
        c.gridx = 2;
        add(numberButtons[3], c);
        c.gridx = 3;
        c.gridheight = 2;
        add(createButton("="), c);
        c.gridheight = 1;

        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 2;
        add(numberButtons[0], c);
        c.gridwidth = 1;
        c.gridx = 2;
        add(createButton("."), c);

        setVisible(true);
    }

    private void addSymbol(final char symbol){
        if (VALID_CHARACTERS_PATTERN.matcher(String.valueOf(symbol)).matches()) {
            String text = resultTextField.getText();
            if (ERROR_MESSAGE.equals(text)) {
                text = DEFAULT_RESULT_TEXT_FIELD;
            }

            String[] numbers = text.split(SYMBOLS_REGEX);
            if (symbol == '.' && numbers[numbers.length - 1].contains(".")) {
                return;
            }
            else if (symbol == '0' && text.endsWith("0") && "0".equals(numbers[numbers.length -1])) {
                    return;
            }
            else if (symbol >= '1' && symbol <= '9' && text.endsWith("0") && "0".equals(numbers[numbers.length -1])) {
                text = text.substring(0, text.length() - 1);
            }
            else if (symbol == '=') {
                calculateBuffer();
                return;
            }
            else if (symbol == 'C') {
                resultTextField.setText(DEFAULT_RESULT_TEXT_FIELD);
                return;
            }
            else {
                switch (symbol) {
                    case '+':
                    case '*':
                    case '/': {
                        char lastChar = text.charAt(text.length() - 1);

                        if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
                            return;
                        }
                        break;
                    }
                    case '-':
                        if(text.length() > 1){
                            char lastChar = text.charAt(text.length() - 1);
                            char lastPrevChar = text.charAt(text.length() - 2);

                            if (lastPrevChar == '+' || lastPrevChar == '-' || lastPrevChar == '*' || lastPrevChar == '/') {
                                if(lastChar == '-'){
                                    return;
                                }
                            }
                            break;
                        }
                }
            }
            resultTextField.setText(text + symbol);
        }
    }

    private void calculateBuffer() {
        String text = resultTextField.getText();

        if(ERROR_MESSAGE.equals(text)){
            return;
        }
        if (SYMBOLS_PATTERN.matcher(text.substring(text.length() - 1)).matches()) {
            resultTextField.setText(ERROR_MESSAGE);
            return;
        }

        String[] numbers = text.split("[+*\\-/]+");
        String[] symbols = text.split("[0-9.]+");
        if(symbols.length == 0){
            return;
        }
        int i = 1;
        double prev;

        if(!symbols[0].isEmpty()){
            i ++;
            prev = -Double.parseDouble(numbers[i - 1]);
        } else {
            prev = Double.parseDouble(numbers[i - 1]);
        }

        for(int n = 1; i < numbers.length; n++, i++){
            double next = Double.parseDouble(numbers[i]);
            if (symbols[n].length() > 1){
                next *= -1;
            }
            switch (symbols[n].charAt(0)){
                case '+':
                    prev += next;
                    break;
                case '-':
                    prev -= next;
                    break;
                case '*':
                    prev *= next;
                    break;
                case '/':
                    if (next == 0){
                        resultTextField.setText(ERROR_MESSAGE);
                        return;
                    }
                    prev /= next;
                    break;
            }
        }
        resultTextField.setText(Double.toString(prev));
    }

    private static class CalculatorButton extends JButton{
        public CalculatorButton(String text, ActionListener e) {
            super(text);
            addActionListener(e);
        }
    }
}
