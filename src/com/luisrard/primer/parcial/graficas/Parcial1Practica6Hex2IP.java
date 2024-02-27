package com.luisrard.primer.parcial.graficas;

public class Parcial1Practica6Hex2IP {
    private static final String ERROR_MESSAGE = "Error";
    public static void main(String[] args) {
        String converter = args[0];
        String valueToConvert = args[1];
        System.out.println(convertValue(converter, valueToConvert));
    }

    private static String convertValue(String converter, String valueToConvert) {
        String value;
        if ("-hex".equalsIgnoreCase(converter)){
            value = hexToIp(valueToConvert);
        } else if ("-ip".equalsIgnoreCase(converter)){
            value = ipToHex(valueToConvert);
        } else {
            value = ERROR_MESSAGE;
        }
        return value;
    }

    private static String ipToHex(String ipText) {
        String[] numbersIp = ipText.split("\\.");
        if(numbersIp.length != 4){
            return ERROR_MESSAGE;
        }
        StringBuilder hexString = new StringBuilder();
        for (String numberIp : numbersIp){
            try {
                int number = Integer.parseInt(numberIp);
                if (number > 255 || number < 0){
                    throw new Exception();
                }
                hexString.append(convertIntToHexString(number));
            } catch (Exception e){
                return ERROR_MESSAGE;
            }
        }
        return hexString.toString();
    }

    private static String convertIntToHexString(Integer number){
        return String.format("%02X", number);
    }

    private static String hexToIp(String hexText) {
        if (hexText.length()!= 8){
            return ERROR_MESSAGE;
        }
        StringBuilder ipString = new StringBuilder();
        for (int i = 0; i < 8; i += 2){
            try {
                String pairHex = hexText.substring(i, i + 2);
                int number = Integer.parseInt(pairHex, 16);
                ipString.append(number);
                if (i != 6){
                    ipString.append(".");
                }
            }catch (Exception e){
                return ERROR_MESSAGE;
            }
        }
        return ipString.toString();
    }
}
