package br.com.erudio.converters;

public class NumberConverter {
    public static Double convertToDouble(String strNumber) {
        // verificar se o valor é nulo
        if(strNumber == null) return 0D;
        //Subistituíndo , por . para evitar erros
        String number = strNumber.replaceAll(",",".");
        // verificar se o valor é numérico para converter e retornar
        try {
            if(isNumeric(number)) return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0D;
    }

    public static Integer convertToInteger(String strNumber) {
        // verificar se o valor é nulo
        if(strNumber == null) return 0;
        //Subistituíndo , por . para evitar erros
        String number = strNumber.replaceAll(",",".");
        // verificar se o valor é numérico para converter e retornar
        if(isNumeric(number)) return Integer.parseInt(number);
        return 0;
    }

    public static boolean isNumeric(String strNumber) {
        if(strNumber == null) return false;
        String number = strNumber.replaceAll(",",".");
        //Verificar por meio do rejex
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
        /* Explicando a expressão do Rejex:
         * "[-+]" string vai iniciar com sinal positivo/negativo
         * "?" indica que a string pode iniciar ou com "-", ou com "+" ou sem nenhum.
         * "[0-9]" corresponde a qualquer dígito entre 0 e 9.
         * "*" caractere ou conjunto de caracteres anterior pode aparecer zero ou mais vezes
         * "\\." caracter que corresponde a qualquer caracter. (O primeiro "\" é usada como caracter de escape para a segunda barra)
         * "?" significa que o ponto é opcional
         * "[0-9]" corresponde a qualquer dígito entre 0 e 9.
         * "+"  indica que o caractere ou conjunto de caracteres anterior deve aparecer pelo menos uma vez. Isso significa que após o ponto opcional (se houver), deve haver pelo menos um dígito.
         */
    }

    public static boolean isIntegerNumeric(String strNumber) {
        if(strNumber == null) return false;
        String number = strNumber.replaceAll(",",".");
        //Verificar por meio do rejex
        return number.matches("[-+]?[0-9]*");
        /* Explicando a expressão do Rejex:
         * "[-+]" string vai iniciar com sinal positivo/negativo
         * "?" indica que a string pode iniciar ou com "-", ou com "+" ou sem nenhum.
         * "[0-9]" corresponde a qualquer dígito entre 0 e 9.
         * "*" caractere ou conjunto de caracteres anterior pode aparecer zero uma ou mais vezes
         */
    }
}
