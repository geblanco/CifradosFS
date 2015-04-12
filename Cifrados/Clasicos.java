package Cifrados;

import Util.Constants;
import Util.Formatter;
import Util.Math;

public class Clasicos
{
    private static class Transposicion
    {
        /**
         * Codifica el texto dado mediante transposicion de columnas o filas
         *
         * @param text Texto a codificar
         * @param CoF  Codificacion por columnas o por filas (true/false)
         * @param n    Numero de columnas o filas
         * @return Texto codificado
         */

        public static String cifrar(String text, boolean CoF, int n)
        {
            int c = Math.roundDiv(text.length(), n);
            return cod(text, CoF ? n : c, CoF ? c : n);
        }

        public static String descifrar(String text, boolean CoF, int n)
        {
            int c = Math.roundDiv(text.length(), n);
            return cod(text, CoF ? c : n, CoF ? n : c);
        }

        private static String cod(String text, int nC, int nF)
        {
            String ret = "";

            for (int j = 0; j < nC; j++)
            {
                for (int i = j; i < nF * nC; i += nC)
                {
                    ret += i >= text.length() ? "X" : text.charAt(i);
                }
            }

            return Formatter.formatOutput(Formatter.replaceSpace(ret)).toUpperCase();
        }
    }

    private static class Vigenere
    {
        public static String cifrar(String text, String key)
        {//Ci = Mi + Ki mod27
            String ret = "";
            int count = 0, res;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ') {
                    ret += " ";
                } else {
                    res = Math.mod(Constants.getIdx(text.charAt(i)) + Constants.getIdx(key.charAt(count++)));
                    ret += Constants.ALFABETO[res];
                    count %= key.length();
                }
            }
            return ret;
        }

        public static String descifrar(String text, String key)
        {//Mi = Ci - Ki mod27
            String ret = "";
            int count = 0, res;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == ' ') {
                    ret += " ";
                } else {
                    res = Math.mod(Constants.getIdx(text.charAt(i)) - Constants.getIdx(key.charAt(count++)));
                    ret += Constants.ALFABETO[res];
                    count %= key.length();
                }
            }
            return ret;
        }
    }

    private static class SustitucionMonoAfin
    {
        /**
         * Cifra un texto dado mediante la tecnica de sustitucion afin
         * @param text Texto a cifrar
         * @param key  Clave de cifrado
         * @param a    Constante de decimacion (Debe ser primo relativo con el modulo del alfabeto)
         * @param b    Constante de desplazamiento
         * @return     Texto cifrado
         */
        //(Mi + b) * a mod27
        public static String cifrar(String text, String key, int a, int b)
        {
            //Test 'a' to be prime with mod
            if (!Math.isPrime(a))
                return ("La constante de decimación (a) debe ser primo relativo con el modulo del alfabeto: " + Constants.ALFABETO.length);
            String ret = "";
            int mod = 27, code;
            for (int i = 0; i < text.length(); i++)
            {
                if (text.charAt(i) != ' ') {
                    code = Constants.getIdx(text.charAt(i));
                    int decode = Math.mod((code + b) * a);
                    ret += Constants.ALFABETO[decode];
                }
            }
            return Formatter.formatOutput(ret);
        }

        //(Ci – b) * inv(a, 27) mod27
        public static String descifrar(String text, String key, int a, int b)
        {
            //Test 'a' to be prime with mod
            if (!Math.isPrime(a))
                return ("La constante de decimación (a) debe ser primo relativo con el modulo del alfabeto: " + Constants.ALFABETO.length);
            String ret = "";
            int mod = 27, code;
            for (int i = 0; i < text.length(); i++)
            {
                if (text.charAt(i) != ' ') {
                    code = Constants.getIdx(text.charAt(i));
                    int decode = Math.mod((code - b) * Math.getInverse(a, mod));
                    ret += Constants.ALFABETO[decode];
                }
            }
            return Formatter.formatOutput(ret);
        }
    }
}
